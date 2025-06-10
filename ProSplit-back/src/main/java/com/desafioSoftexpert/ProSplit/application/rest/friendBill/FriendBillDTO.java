package com.desafioSoftexpert.ProSplit.application.rest.friendBill;

import com.desafioSoftexpert.ProSplit.domain.FriendBill;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FriendBillDTO {
    private String friendName;
    @NotEmpty(message = "Incoming expenses cannot be empty.")
    @NotNull(message = "Incoming expenses cannot be null.")
    @Valid
    private List<@NotNull(message = "Expense cannot be null.") @Positive(message = "Expense must be a positive number.") Float> incomingExpenses;
    private float totalExpend;
    private float totalDiscount;
    private float totalAddition;
    private float totalPayment;

    public FriendBillDTO(String friendName, List<Float> incomingExpenses) {
        this.friendName = friendName;
        this.incomingExpenses = incomingExpenses;
    }

    public FriendBill toFriendBill() {
        return new FriendBill(friendName, incomingExpenses);
    }
}