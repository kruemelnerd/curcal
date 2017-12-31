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
import static org.mockito.Mockito.when;

/**
 * Created by pveit on 29.12.2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class MainPresentorTest {

    @Mock
    MainMVP.RequieredViewOps mMockMainView;


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
}
