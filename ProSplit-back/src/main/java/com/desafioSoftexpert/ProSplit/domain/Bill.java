package com.desafioSoftexpert.ProSplit.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Bill {
    private List<FriendBill> friendBills;
    private List<Addition> additions;
    private List<Discount> discounts;

    public float calculateTotalExpenses() {
        return friendBills.stream()
                .map(friendBill -> friendBill.getIncomingExpenses().stream().reduce(0f, Float::sum))
                .reduce(0f, Float::sum);
    }
}
