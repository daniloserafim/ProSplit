package com.desafioSoftexpert.ProSplit.application.rest.billAdjustment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class BillAdjustmentDTO {
    protected String name;
    @NotNull(message = "Adjustments values cannot be null.")
    @Positive(message = "Adjustments values must be positive.")
    protected Float value;
    protected boolean isPercentage;

    protected BillAdjustmentDTO(String name, float value, boolean isPercentage) {
        this.name = name;
        this.value = value;
        this.isPercentage = isPercentage;
    }
}