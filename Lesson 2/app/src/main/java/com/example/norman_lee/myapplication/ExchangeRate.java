package com.example.norman_lee.myapplication;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class ExchangeRate {

    private BigDecimal exchangeRate;
    private static String defaultRate = "2.95000";
    private static final int DEFAULT_PRECISION = 5;
    private int precision = DEFAULT_PRECISION;
    private MathContext mathContext;


    ExchangeRate(){
        exchangeRate = new BigDecimal(defaultRate);
        instantiateMathContext(DEFAULT_PRECISION);
    }

    ExchangeRate(String exchangeRate){
        this.exchangeRate = new BigDecimal(exchangeRate);
        instantiateMathContext(DEFAULT_PRECISION);
    }

    ExchangeRate(String home, String foreign) {
        instantiateMathContext(DEFAULT_PRECISION);
        // 3.13a The constructor initializes exchangeRate by calculating the exchangeRate
        if ((home == null) && (foreign == null)){
            this.exchangeRate = new BigDecimal(defaultRate);
        }

        else {
            BigDecimal homeAmount = new BigDecimal(home);
            BigDecimal foreignAmount = new BigDecimal(foreign);
            this.exchangeRate = homeAmount.divide(foreignAmount, this.mathContext);
        }
    }

    BigDecimal getExchangeRate(){
        return exchangeRate.setScale(2, RoundingMode.CEILING); // convert to 2 decimal places
    }

    BigDecimal calculateAmount(String foreign){
        // 2.5a complete this method to return the amount
        BigDecimal foreignAmount = new BigDecimal(foreign);
        BigDecimal homeAmount;

        if (this.exchangeRate == null){
            BigDecimal defaultRateAmount = new BigDecimal(defaultRate);
            homeAmount = foreignAmount.multiply(defaultRateAmount, this.mathContext);
        }

        else{
            homeAmount = foreignAmount.multiply(this.exchangeRate, this.mathContext);
        }

        return homeAmount.setScale(2, RoundingMode.CEILING); // convert to 2 decimal places
    }

    void setPrecision(int precision){
        this.precision = precision;
        instantiateMathContext(precision);
    }

    private void instantiateMathContext(int precision){
        mathContext = new MathContext(precision, RoundingMode.HALF_UP);
    }
}
