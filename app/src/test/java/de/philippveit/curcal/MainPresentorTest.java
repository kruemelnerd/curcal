package de.philippveit.curcal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import de.philippveit.curcal.mvp.MainMVP.RequieredViewOps;
import de.philippveit.curcal.mvp.MainPresentor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MainPresentorTest {



    @Mock
    private RequieredViewOps mMockMainView;

    private MainPresentor mPresenter;

    @Before
    public void setUp() throws Exception {

        mPresenter = new MainPresentor(mMockMainView);

        mPresenter.clearAllNumbers();
    }

    @Test
    public void clearAllNumbersTest() throws Exception {
        verify(mMockMainView).clearTextEverywhere();
    }


    @Test
    public void addNumber_normalNumberTest() throws Exception {
        mPresenter.clearAllNumbers();

        mPresenter.addNumber(1);
        verify(mMockMainView).setMainTextLine("1");
        mPresenter.addNumber(2);
        verify(mMockMainView).setMainTextLine("12");
        mPresenter.addNumber(3);
        verify(mMockMainView).setMainTextLine("123");
        mPresenter.addNumber(4);
        verify(mMockMainView).setMainTextLine("1234");
        mPresenter.addNumber(5);
        verify(mMockMainView).setMainTextLine("12345");
        mPresenter.addNumber(6);
        verify(mMockMainView).setMainTextLine("123456");
        mPresenter.addNumber(7);
        verify(mMockMainView).setMainTextLine("1234567");
        mPresenter.addNumber(8);
        verify(mMockMainView).setMainTextLine("12345678");
        mPresenter.addNumber(9);
        verify(mMockMainView).setMainTextLine("123456789");
        mPresenter.addNumber(0);
        verify(mMockMainView).setMainTextLine("1234567890");
    }


    @Test
    public void addNumber_decimalSimpleNullCheckTest() throws Exception {
        mPresenter.clearAllNumbers();
        mPresenter.addNumber(0);
        verify(mMockMainView).setMainTextLine("0");
        mPresenter.handleDecimalMark();
        mPresenter.addNumber(7);
        verify(mMockMainView).setMainTextLine("0.7");

    }

    @Test
    public void addNumber_decimalMultipleNullCheckTest() throws Exception {
        mPresenter.clearAllNumbers();
        mPresenter.addNumber(0);
        verify(mMockMainView).setMainTextLine("0");
        mPresenter.handleDecimalMark();
        mPresenter.addNumber(0);
        verify(mMockMainView).setMainTextLine("0.0");
        mPresenter.addNumber(0);
        verify(mMockMainView).setMainTextLine("0.00");
        mPresenter.addNumber(5);
        verify(mMockMainView).setMainTextLine("0.005");
    }


    @Test
    public void addNumber_decimalNumberTest() throws Exception {

        mPresenter.addNumber(1);
        verify(mMockMainView).setMainTextLine("1");

        mPresenter.handleDecimalMark();

        mPresenter.addNumber(2);
        verify(mMockMainView).setMainTextLine("1.2");
        mPresenter.addNumber(3);
        verify(mMockMainView).setMainTextLine("1.23");
        mPresenter.addNumber(4);
        verify(mMockMainView).setMainTextLine("1.234");
        mPresenter.addNumber(5);
        verify(mMockMainView).setMainTextLine("1.2345");
        mPresenter.addNumber(6);
        verify(mMockMainView).setMainTextLine("1.23456");
        mPresenter.addNumber(7);
        verify(mMockMainView).setMainTextLine("1.234567");
        mPresenter.addNumber(8);
        verify(mMockMainView).setMainTextLine("1.2345678");
        mPresenter.addNumber(9);
        verify(mMockMainView).setMainTextLine("1.23456789");
        mPresenter.addNumber(0);
        verify(mMockMainView).setMainTextLine("1.234567890");
        mPresenter.addNumber(0);
        verify(mMockMainView).setMainTextLine("1.2345678900");
        mPresenter.addNumber(1);
        verify(mMockMainView).setMainTextLine("1.23456789001");
    }


    @Test
    public void removeLastDigitWithDecimalsTest(){
        assertTrue("1".equals(mPresenter.removeLastDigitWithDecimals(new BigDecimal(12)).toPlainString()));
        assertTrue("0".equals(mPresenter.removeLastDigitWithDecimals(new BigDecimal(1)).toPlainString()));
        assertTrue("123".equals(mPresenter.removeLastDigitWithDecimals(new BigDecimal(1234)).toPlainString()));
        assertTrue("523423".equals(mPresenter.removeLastDigitWithDecimals(new BigDecimal(5234234)).toPlainString()));
        assertTrue("0".equals(mPresenter.removeLastDigitWithDecimals(new BigDecimal(0)).toPlainString()));

        assertTrue("0".equals(mPresenter.removeLastDigitWithDecimals(new BigDecimal("0.1")).toPlainString()));
        assertTrue("0.1".equals(mPresenter.removeLastDigitWithDecimals(new BigDecimal("0.11")).toPlainString()));
        assertTrue("123.456".equals(mPresenter.removeLastDigitWithDecimals(new BigDecimal("123.4567")).toPlainString()));
        assertTrue("123.45678".equals(mPresenter.removeLastDigitWithDecimals(new BigDecimal("123.456789")).toPlainString()));
    }

    @Test
    public void addDigitToTheEndTest(){
        assertTrue(new BigDecimal("11").equals(mPresenter.addDigitToTheEnd(new BigDecimal("1"), new BigDecimal("1"))));
        assertTrue(new BigDecimal("111").equals(mPresenter.addDigitToTheEnd(new BigDecimal("11"), new BigDecimal("1"))));
        assertTrue(new BigDecimal("123").equals(mPresenter.addDigitToTheEnd(new BigDecimal("12"), new BigDecimal("3"))));
        assertTrue(new BigDecimal("1234567890").equals(mPresenter.addDigitToTheEnd(new BigDecimal("123456789"), new BigDecimal("0"))));

    }

    @Test
    public void addDecimalDigitToTheEndTest(){
        assertTrue(new BigDecimal("1.1").equals(mPresenter.addDecimalDigitToTheEnd(new BigDecimal("1"), new BigDecimal("1"))));
        assertTrue(new BigDecimal("11.1").equals(mPresenter.addDecimalDigitToTheEnd(new BigDecimal("11"), new BigDecimal("1"))));
        assertTrue(new BigDecimal("12.3").equals(mPresenter.addDecimalDigitToTheEnd(new BigDecimal("12"), new BigDecimal("3"))));
        assertTrue(new BigDecimal("123456789.0").equals(mPresenter.addDecimalDigitToTheEnd(new BigDecimal("123456789"), new BigDecimal("0"))));
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

    @Test
    public void removeLastDigitTest(){





        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        mPresenter.addNumber(5);
        mPresenter.addNumber(6);
        mPresenter.addNumber(7);

        verify(mMockMainView, atLeastOnce()).setMainTextLine(captor.capture());
        assertEquals("567", captor.getValue());

        mPresenter.addNumber(8); //5678
        verify(mMockMainView, atLeastOnce()).setMainTextLine(captor.capture());
        assertEquals("5678", captor.getValue());

        mPresenter.removeLastDigit(); //567
        verify(mMockMainView, atLeastOnce()).setMainTextLine(captor.capture());
        assertEquals("567", captor.getValue());


        mPresenter.addNumber(9); //5679
        verify(mMockMainView, atLeastOnce()).setMainTextLine(captor.capture());
        assertEquals("5679", captor.getValue());

        mPresenter.handleDecimalMark();
        verify(mMockMainView, atLeastOnce()).setMainTextLine(captor.capture());
        assertEquals("5679.", captor.getValue());

        mPresenter.addNumber(3); //5679.3
        verify(mMockMainView, atLeastOnce()).setMainTextLine(captor.capture());
        assertEquals("5679.3", captor.getValue());

        mPresenter.removeLastDigit(); //5679.
        verify(mMockMainView, atLeastOnce()).setMainTextLine(captor.capture());
        assertEquals("5679.", captor.getValue());

        mPresenter.removeLastDigit();
        verify(mMockMainView, atLeastOnce()).setMainTextLine(captor.capture());
        assertEquals("5679", captor.getValue()); //but is at the momement: 567.

        mPresenter.removeLastDigit();
        verify(mMockMainView, atLeastOnce()).setMainTextLine(captor.capture());
        assertEquals("567", captor.getValue()); //but is at the momement: 56.

        mPresenter.removeLastDigit();
        verify(mMockMainView, atLeastOnce()).setMainTextLine(captor.capture());
        assertEquals("56", captor.getValue()); //but is at the momement: 5.

        mPresenter.removeLastDigit();
        verify(mMockMainView, atLeastOnce()).setMainTextLine(captor.capture());
        assertEquals("5", captor.getValue()); //but is at the momement: 0.

        mPresenter.removeLastDigit();
        verify(mMockMainView, atLeastOnce()).setMainTextLine(captor.capture());
        assertEquals("0", captor.getValue()); //but is at the momement: 0.





    }
}
