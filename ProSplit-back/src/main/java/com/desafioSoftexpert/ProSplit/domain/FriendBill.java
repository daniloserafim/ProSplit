package com.desafioSoftexpert.ProSplit.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendBill {
    private String friendName;
    private List<Float> incomingExpenses;
    private float totalExpend;
    private float totalDiscount;
    private float totalAddition;
    private float totalPayment;

    public FriendBill(String friendName, List<Float> incomingExpenses) {
        this.friendName = friendName;
        this.incomingExpenses = incomingExpenses;
    }

    public float calculateTotalExpend() {
        return getIncomingExpenses().stream().reduce(0f, Float::sum);
    }
}
