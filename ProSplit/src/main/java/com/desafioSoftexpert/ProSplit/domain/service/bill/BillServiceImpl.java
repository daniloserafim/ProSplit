package com.desafioSoftexpert.ProSplit.domain.service.bill;

import com.desafioSoftexpert.ProSplit.domain.Bill;
import com.desafioSoftexpert.ProSplit.domain.FriendBill;
import com.desafioSoftexpert.ProSplit.domain.BillAdjustment;
import com.desafioSoftexpert.ProSplit.domain.exception.EmptyListException;
import com.desafioSoftexpert.ProSplit.domain.exception.InvalidValueException;
import com.desafioSoftexpert.ProSplit.utils.MathUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillServiceImpl implements IBillService {

    @Override
    public List<FriendBill> calculateBill(Bill bill) {
        validateBill(bill);
        bill.getFriendBills().forEach(friendBill -> validateExpenses(friendBill.getIncomingExpenses()));

        float totalExpenses = bill.calculateTotalExpenses();

        for (FriendBill friendBill : bill.getFriendBills()) {
            float friendTotalExpend = friendBill.calculateTotalExpend();

            friendBill.setTotalExpend(MathUtils.round(friendTotalExpend));

            float friendDiscount = calculateProportionalAdjustment(bill.getDiscounts(), friendTotalExpend, totalExpenses);
            float friendAddition = calculateProportionalAdjustment(bill.getAdditions(), friendTotalExpend, totalExpenses);

            friendBill.setTotalDiscount(MathUtils.round(friendDiscount));
            friendBill.setTotalAddition(MathUtils.round(friendAddition));

            float friendTotalPayment = friendTotalExpend - friendDiscount + friendAddition;
            if (friendTotalPayment < 0) {
                friendTotalPayment = 0;
            }
            friendBill.setTotalPayment(MathUtils.round(friendTotalPayment));
        }

        return bill.getFriendBills();
    }

    private void validateBill(Bill bill) {
        if (bill.getFriendBills() == null || bill.getFriendBills().isEmpty()) {
            throw new EmptyListException("Bill must have at least one FriendBill.");
        }
        validateAdjustments(bill.getDiscounts(), "Bill Discounts must have positive int values.");
        validateAdjustments(bill.getAdditions(), "Bill Additions must have positive int values.");
    }

    private void validateAdjustments(List<? extends BillAdjustment> adjustments, String errorMessage) {
        if (adjustments == null) {
            throw new InvalidValueException(errorMessage);
        }
        if (adjustments.stream().anyMatch(adjustment -> adjustment == null || adjustment.getValue() < 0)) {
            throw new InvalidValueException(errorMessage);
        }
    }

    private void validateExpenses(List<Float> expenses) {
        if (expenses.isEmpty()) {
            throw new EmptyListException("Each FriendBill must have at least one valid incoming expense.");
        }
        if (expenses.stream().anyMatch(expense -> expense == null || expense < 0)) {
            throw new InvalidValueException("Incoming expenses must be positive int values.");
        }
    }

    private float calculateProportionalAdjustment(List<? extends BillAdjustment> adjustments, float friendTotalExpend, float totalExpenses) {
        float totalAdjustment = calculateTotalAdjustment(adjustments, totalExpenses);
        float proportion = friendTotalExpend / totalExpenses;
        return totalAdjustment * proportion;
    }

    private float calculateTotalAdjustment(List<? extends BillAdjustment> adjustments, float totalExpenses) {
        return adjustments.stream()
                .map(adjustment -> adjustment.isPercentage() ? totalExpenses * (adjustment.getValue() / 100) : adjustment.getValue())
                .reduce(0f, Float::sum);
    }
}
