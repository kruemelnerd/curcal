package de.philippveit.curcal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import de.philippveit.curcal.mvp.MainMVP;
import de.philippveit.curcal.mvp.MainPresentor;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class MainPresentorTest {

    @Mock
    private MainMVP.RequieredViewOps mMockMainView;


    @Test
    public void removeLastNumberWithDecimalsTest(){
        MainPresentor mPresenter = new MainPresentor(mMockMainView);

        assertTrue("1".equals(mPresenter.removeLastNumberWithDecimals(new BigDecimal(12)).toPlainString()));
        assertTrue("0".equals(mPresenter.removeLastNumberWithDecimals(new BigDecimal(1)).toPlainString()));
        assertTrue("123".equals(mPresenter.removeLastNumberWithDecimals(new BigDecimal(1234)).toPlainString()));
        assertTrue("523423".equals(mPresenter.removeLastNumberWithDecimals(new BigDecimal(5234234)).toPlainString()));
        assertTrue("0".equals(mPresenter.removeLastNumberWithDecimals(new BigDecimal(0)).toPlainString()));

        assertTrue("0".equals(mPresenter.removeLastNumberWithDecimals(new BigDecimal("0.1")).toPlainString()));
        assertTrue("0.1".equals(mPresenter.removeLastNumberWithDecimals(new BigDecimal("0.11")).toPlainString()));
        assertTrue("123.456".equals(mPresenter.removeLastNumberWithDecimals(new BigDecimal("123.4567")).toPlainString()));
        assertTrue("123.45678".equals(mPresenter.removeLastNumberWithDecimals(new BigDecimal("123.456789")).toPlainString()));
    }

    @Test
    public void addDigitToTheEndTest(){
        MainPresentor mPresenter = new MainPresentor(mMockMainView);
        assertTrue(new BigDecimal("11").equals(mPresenter.addDigitToTheEnd(new BigDecimal("1"), new BigDecimal("1"))));
        assertTrue(new BigDecimal("111").equals(mPresenter.addDigitToTheEnd(new BigDecimal("11"), new BigDecimal("1"))));
        assertTrue(new BigDecimal("123").equals(mPresenter.addDigitToTheEnd(new BigDecimal("12"), new BigDecimal("3"))));
        assertTrue(new BigDecimal("1234567890").equals(mPresenter.addDigitToTheEnd(new BigDecimal("123456789"), new BigDecimal("0"))));

    }

    @Test
    public void addDecimalDigitToTheEndTest(){
        MainPresentor mPresenter = new MainPresentor(mMockMainView);
        assertTrue(new BigDecimal("1.1").equals(mPresenter.addDecimalDigitToTheEnd(new BigDecimal("1"), new BigDecimal("1"))));
        assertTrue(new BigDecimal("11.1").equals(mPresenter.addDecimalDigitToTheEnd(new BigDecimal("11"), new BigDecimal("1"))));
        assertTrue(new BigDecimal("12.3").equals(mPresenter.addDecimalDigitToTheEnd(new BigDecimal("12"), new BigDecimal("3"))));
        assertTrue(new BigDecimal("123456789").equals(mPresenter.addDecimalDigitToTheEnd(new BigDecimal("123456789"), new BigDecimal("0"))));
        assertTrue(new BigDecimal("1.2345678901").equals(mPresenter.addDecimalDigitToTheEnd(new BigDecimal("1.234567890"), new BigDecimal("1"))));
        assertTrue(new BigDecimal("1.234567890").equals(mPresenter.addDecimalDigitToTheEnd(new BigDecimal("1.23456789"), new BigDecimal("0"))));

        assertTrue(new BigDecimal("141123.7890").equals(mPresenter.addDecimalDigitToTheEnd(new BigDecimal("141123.789"), new BigDecimal("0"))));
        assertTrue(new BigDecimal("141123.7891").equals(mPresenter.addDecimalDigitToTheEnd(new BigDecimal("141123.789"), new BigDecimal("1"))));
        assertTrue(new BigDecimal("141123.7892").equals(mPresenter.addDecimalDigitToTheEnd(new BigDecimal("141123.789"), new BigDecimal("2"))));
        assertTrue(new BigDecimal("141123.7893").equals(mPresenter.addDecimalDigitToTheEnd(new BigDecimal("141123.789"), new BigDecimal("3"))));
        assertTrue(new BigDecimal("141123.7894").equals(mPresenter.addDecimalDigitToTheEnd(new BigDecimal("141123.789"), new BigDecimal("4"))));
        assertTrue(new BigDecimal("141123.7895").equals(mPresenter.addDecimalDigitToTheEnd(new BigDecimal("141123.789"), new BigDecimal("5"))));
        assertTrue(new BigDecimal("141123.7896").equals(mPresenter.addDecimalDigitToTheEnd(new BigDecimal("141123.789"), new BigDecimal("6"))));
        assertTrue(new BigDecimal("141123.7897").equals(mPresenter.addDecimalDigitToTheEnd(new BigDecimal("141123.789"), new BigDecimal("7"))));
        assertTrue(new BigDecimal("141123.7898").equals(mPresenter.addDecimalDigitToTheEnd(new BigDecimal("141123.789"), new BigDecimal("8"))));
        assertTrue(new BigDecimal("141123.7899").equals(mPresenter.addDecimalDigitToTheEnd(new BigDecimal("141123.789"), new BigDecimal("9"))));
    }
}
