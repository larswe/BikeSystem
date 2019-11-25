package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.Objects;

public class BikeType {
    private Utils.EBikeType type;
    private BigDecimal fullReplacementValue;

    ///constructors
    public BikeType(Utils.EBikeType t, BigDecimal replacementValue)
    {
        type = t; 
        fullReplacementValue= replacementValue;
    }

    ///getters
    public BigDecimal getReplacementValue() {
        return fullReplacementValue;
    }
    public Utils.EBikeType getType() {
        return type;
    }
    //private parts
}