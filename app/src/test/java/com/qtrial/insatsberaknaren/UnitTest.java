package com.qtrial.insatsberaknaren;

import org.junit.Assert;
import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UnitTest {

    CalculatorUtil calculatorUtil = new CalculatorUtil();
    TestHelper testHelper = new TestHelper();

    @Test
    public void positive() {
        int result = calculatorUtil.calcluteDownPaymentWithParameters(3000000, 1000000,450000);
        Assert.assertEquals(527200, result);
    }

    @Test
    public void positivtWithZeroPantbrev() {
        int result = calculatorUtil.calcluteDownPaymentWithParameters(4450000, 3831500, 667500);
        Assert.assertEquals(735075, result);
    }

    @Test
    public void zeroPantbrev() {
        int result = calculatorUtil.calcluteDownPaymentWithParameters(3500000, 3000000, 525000);
        Assert.assertEquals(578325, result);
    }

    @Test
    public void countPantbrev() {
        double pantbrev = calculatorUtil.sumPantbrev(3500000, 3000000, 525000);
        int i = testHelper.doubleToInt(pantbrev);
        Assert.assertEquals(i, 0);
    }

    @Test
    public void countLagfart() {
        double lagfart = calculatorUtil.sumLagfart(3500000);
        int i = testHelper.doubleToInt(lagfart);
        Assert.assertEquals(i, 53325);
    }
}