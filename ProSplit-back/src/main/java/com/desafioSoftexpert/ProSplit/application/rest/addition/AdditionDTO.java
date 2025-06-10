package com.desafioSoftexpert.ProSplit.application.rest.addition;

import com.desafioSoftexpert.ProSplit.application.rest.billAdjustment.BillAdjustmentDTO;
import com.desafioSoftexpert.ProSplit.domain.Addition;

public class AdditionDTO extends BillAdjustmentDTO {
    public AdditionDTO(String name, float value, boolean isPercentage) {
        super(name, value, isPercentage);
    }

    public Addition toAddition() {
        return new Addition(name, value, isPercentage);
    }
}
