package com.example.norman_lee.myapplication;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    // 5.4 Write unit tests to check the ExchangeRate class
    @Test
    public void exchangeRateDefaultRate(){
        String defaultExchangeRate = "2.95";
        ExchangeRate exchangeRate = new ExchangeRate();
        assertEquals(defaultExchangeRate, exchangeRate.getExchangeRate().toString());
    }

    @Test
    public void exchangeRateHome10Foreign30(){
        String correctExchangeRate = "0.34";
        ExchangeRate exchangeRate = new ExchangeRate("10", "30");
        assertEquals(correctExchangeRate, exchangeRate.getExchangeRate().toString());
    }

    @Test
    public void exchangeRateHome40Foreign20(){
        String correctExchangeRate = "2.00";
        ExchangeRate exchangeRate = new ExchangeRate("40", "20");
        assertEquals(correctExchangeRate, exchangeRate.getExchangeRate().toString());
    }

    @Test (expected = NumberFormatException.class)
    public void numberFormatException() {
        Utils.checkInvalidInputs("");
    }

    @Test (expected = IllegalArgumentException.class)
    public void IllegalArgumentException() {
        Utils.checkInvalidInputs("-1");
    }
}