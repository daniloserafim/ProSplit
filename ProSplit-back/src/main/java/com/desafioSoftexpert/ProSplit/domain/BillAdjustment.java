package com.desafioSoftexpert.ProSplit.domain;

import lombok.Getter;

@Getter
public abstract class BillAdjustment {
    protected String name;
    protected float value;
    protected boolean isPercentage;

    protected BillAdjustment(String name, float value, boolean isPercentage) {
        this.name = name;
        this.value = value;
        this.isPercentage = isPercentage;
    }
}
