package de.philippveit.curcal.mvp;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import de.philippveit.curcal.R;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;

/**
 * Created by pveit on 27.12.2017.
 */

public class MainPresentor implements MainMVP.PresenterOps {

    private WeakReference<MainMVP.RequieredViewOps> mView;

    private BigDecimal  firstNumber;
    private BigDecimal secondNumber;
    private boolean isSecondNumberSet = false;
    private boolean isDecimalMarkSet = false;
    private Operations operator = null;

    private MathContext mc = new MathContext(10, RoundingMode.HALF_UP);

    public MainPresentor(MainMVP.RequieredViewOps mView) {
        this.mView = new WeakReference<MainMVP.RequieredViewOps>(mView);
    }

    @Override
    public void clearAllNumbers() {
        firstNumber = ZERO;
        secondNumber = ZERO;
        isSecondNumberSet = false;
        operator = null;
    }

    @Override
    public void addNumber(int number) {
        if(operator == null){
            if(isDecimalMarkSet || firstNumber.scale() != 0){
                isDecimalMarkSet = true;
                firstNumber = addDecimalDigitToTheEnd(firstNumber, new BigDecimal(number));
            }else {
                firstNumber = addDigitToTheEnd(firstNumber, new BigDecimal(number));
            }
            mView.get().setMainTextLine(String.valueOf(firstNumber));
        }else {
            isSecondNumberSet = true;
            if(isDecimalMarkSet || secondNumber.scale() != 0){
                isDecimalMarkSet = true;
                secondNumber = addDecimalDigitToTheEnd(secondNumber, new BigDecimal(number));
            }else {
                secondNumber = addDigitToTheEnd(secondNumber, new BigDecimal(number));
            }
            mView.get().setMainTextLine(getMainLine());
        }
    }

    /**
     * adds another decimal digit to the number.
     *
     * @param number original Number
     * @param addDigit number which will be added as a decimal Number
     * @return
     */
    public BigDecimal addDecimalDigitToTheEnd(BigDecimal number, BigDecimal addDigit){
        int scale = number.scale();
        BigDecimal divideBy = TEN;
        if(scale != 0) {
            if(ZERO.equals(addDigit)){
                return number.setScale(scale + 1);
            }else {
                for (int i = 0; i < scale; i++) {
                    divideBy = TEN.multiply(divideBy);
                }
            }
        }
        addDigit = addDigit.divide(divideBy);
        return number.add(addDigit);
    }

    public BigDecimal addDigitToTheEnd(BigDecimal number, BigDecimal addDigit){
        return number.multiply(TEN).add(addDigit);
    }

    @Override
    public void removeLastNumber() {
        if(!isSecondNumberSet){
            firstNumber = removeLastNumberWithDecimals(firstNumber);
            mView.get().setMainTextLine(getMainLine());
        }else {
            firstNumber = removeLastNumberWithDecimals(firstNumber);
            mView.get().setMainTextLine(getMainLine());
        }
    }

    public BigDecimal removeLastNumberWithDecimals(BigDecimal number){

        //Check if Number has decimal
        if(ZERO.compareTo(number.remainder(ONE)) != 0){
            return number.setScale(number.scale()-1, BigDecimal.ROUND_DOWN);
        }else{
            BigDecimal overhang =  number.remainder(TEN);
            number = number.subtract(overhang, mc);
            number = number.divide(TEN, mc);
            return number;
        }

    }

    @Override
    public void pressedOperator(Operations operator) {
        if(operator == null){
            // Not really necessary
            mView.get().showErrorMessage( mView.get().getContext().getString(R.string.error_message_operator_not_found) );
        }

        switch (operator){
            case ADD:
            case MINUS:
            case DIVIDE:
            case MULTIPLY:
                this.operator = operator;
                mView.get().setMainTextLine(getMainLine());
                break;

            case EQUALS:
                handleCompleteCalc();
                break;
            default:
                break;
        }
        isDecimalMarkSet = false;
    }

    @Override
    public void handleDecimalMark() {
            isDecimalMarkSet = true;

            mView.get().setMainTextLine(getMainLine() + ".");
    }

    private void handleCompleteCalc(){
        BigDecimal mainLine = ZERO;
        if(operator == null){
            return;
        }

        switch (operator){
            case ADD:
                mainLine = firstNumber.add(secondNumber, mc);
                break;
            case MINUS:
                mainLine = firstNumber.subtract(secondNumber, mc);
                break;
            case MULTIPLY:
                mainLine = firstNumber.multiply(secondNumber, mc);
                break;
            case DIVIDE:
                if(secondNumber.intValue() == 0){
                    mView.get().showErrorMessage(mView.get().getContext().getString(R.string.error_message_divide_by_zero));
                    return;
                }
                mainLine = firstNumber.divide(secondNumber, mc);
                break;
            default:
                return;
        }
        mView.get().setThirdTextLine(getMainLine());
        mView.get().setMainTextLine(String.valueOf(mainLine));

        firstNumber = mainLine;
        secondNumber = ZERO;
        operator = null;
    }

    private String getMainLine(){
        StringBuffer line = new StringBuffer().append(firstNumber);
        if(operator != null){
            String operatorSign;
            switch (operator){
                case ADD:
                    operatorSign = mView.get().getContext().getString(R.string.operator_character_add);
                    break;
                case MINUS:
                    operatorSign = mView.get().getContext().getString(R.string.operator_character_minus);
                    break;
                case MULTIPLY:
                    operatorSign = mView.get().getContext().getString(R.string.operator_character_multipy);
                    break;
                case DIVIDE:
                    operatorSign = mView.get().getContext().getString(R.string.operator_character_divide);
                    break;
                default:
                    operatorSign = "?";
                    break;
            }
            line.append(operatorSign);
            if(isSecondNumberSet){
                line.append(secondNumber);
            }

        }
        return line.toString();
    }

    public enum Operations {
        ADD, MINUS, DIVIDE, MULTIPLY, EQUALS
    }
}
