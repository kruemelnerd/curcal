package de.philippveit.curcal.mvp;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;

import de.philippveit.curcal.R;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;

/**
 * Created by pveit on 27.12.2017.
 */

public class MainPresentor implements MainMVP.PresenterOps {

    private WeakReference<MainMVP.RequieredViewOps> mView;

    BigDecimal  firstNumber;
    BigDecimal secondNumber;
    boolean isSecondNumberSet = false;
    Operations operator = null;

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
            firstNumber = firstNumber.multiply(BigDecimal.TEN);
            firstNumber = firstNumber.add(new BigDecimal(number));
            mView.get().setMainTextLine(String.valueOf(firstNumber));
        }else {
            isSecondNumberSet = true;
            secondNumber = secondNumber.multiply(BigDecimal.TEN);
            secondNumber = secondNumber.add(new BigDecimal(number));
            mView.get().setMainTextLine(getMainLine());
        }

    }

    @Override
    public void removeLastNumber() {
        if(operator == null){
            firstNumber = removeLastNumberWithDecimals(firstNumber);
            mView.get().setMainTextLine(getMainLine());
        }else {
            isSecondNumberSet = true;
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
            number = number.subtract(overhang);
            number = number.divide(TEN);
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
    }

    private void handleCompleteCalc(){
        BigDecimal mainLine = ZERO;
        if(operator == null){
            return;
        }

        switch (operator){
            case ADD:
                mainLine = firstNumber.add(secondNumber);
                break;
            case MINUS:
                mainLine = firstNumber.subtract(secondNumber);
                break;
            case MULTIPLY:
                mainLine = firstNumber.multiply(secondNumber);
                break;
            case DIVIDE:
                if(secondNumber.intValue() == 0){
                    mView.get().showErrorMessage(mView.get().getContext().getString(R.string.error_message_divide_by_zero));
                    return;
                }
                mainLine = firstNumber.divide(secondNumber);
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
