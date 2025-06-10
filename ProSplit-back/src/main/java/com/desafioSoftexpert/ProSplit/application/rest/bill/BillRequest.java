package com.desafioSoftexpert.ProSplit.application.rest.bill;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BillRequest {
    @NotNull(message = "Bill cannot be null.")
    @Valid
    private BillDTO bill;
}