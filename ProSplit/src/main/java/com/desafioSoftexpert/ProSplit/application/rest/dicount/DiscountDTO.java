package com.desafioSoftexpert.ProSplit.application.rest.dicount;

import com.desafioSoftexpert.ProSplit.application.rest.billAdjustment.BillAdjustmentDTO;
import com.desafioSoftexpert.ProSplit.domain.Discount;

public class DiscountDTO extends BillAdjustmentDTO {
    public DiscountDTO(String name, float value, boolean isPercentage) {
        super(name, value, isPercentage);
    }

    public Discount toDiscount() {
        return new Discount(name, value, isPercentage);
    }
}
