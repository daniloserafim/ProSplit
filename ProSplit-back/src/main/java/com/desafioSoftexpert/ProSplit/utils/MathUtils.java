package com.desafioSoftexpert.ProSplit.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtils {

    public static float round(float value) {
        BigDecimal bd = new BigDecimal(Float.toString(value));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.floatValue();
    }
}
