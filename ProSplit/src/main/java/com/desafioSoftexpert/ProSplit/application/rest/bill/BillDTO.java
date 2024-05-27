package com.desafioSoftexpert.ProSplit.application.rest.bill;

import com.desafioSoftexpert.ProSplit.application.rest.addition.AdditionDTO;
import com.desafioSoftexpert.ProSplit.application.rest.dicount.DiscountDTO;
import com.desafioSoftexpert.ProSplit.application.rest.friendBill.FriendBillDTO;
import com.desafioSoftexpert.ProSplit.domain.Addition;
import com.desafioSoftexpert.ProSplit.domain.Bill;
import com.desafioSoftexpert.ProSplit.domain.Discount;
import com.desafioSoftexpert.ProSplit.domain.FriendBill;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BillDTO {
    @NotEmpty(message = "The bill must have at least one friend.")
    @Valid
    private List<FriendBillDTO> friendBills;
    private List<AdditionDTO> additions;
    private List<DiscountDTO> discounts;

    public Bill toBill() {
        List<FriendBill> friendBillsDomain = friendBills.stream().map(FriendBillDTO::toFriendBill).collect(Collectors.toList());
        List<Addition> additionsDomain = additions.stream().map(AdditionDTO::toAddition).collect(Collectors.toList());
        List<Discount> discountsDomain = discounts.stream().map(DiscountDTO::toDiscount).collect(Collectors.toList());
        return new Bill(friendBillsDomain, additionsDomain, discountsDomain);
    }
}