package de.philippveit.curcal.mvp;

import android.content.Context;

/**
 * Created by pveit on 27.12.2017.
 */

public interface MainMVP {

    /**
     * View mandatory methods. Available to the presenter
     *      Presenter -> View
     */
    interface RequieredViewOps {
        void clearTextEverywhere();
        void setMainTextLine(String mainLine);
        void setFirstTextLine(String firstLine);
        void setSecondTextLine(String secondLine);
        void setThirdTextLine(String thirdLine);
        void showErrorMessage(String error);

        Context getContext();
    }


    /**
     * Operations offered from Presenter to View
     *      View -> Presenter
     */
    interface PresenterOps {
        void clearAllNumbers();
        void addNumber(int number);
        void removeLastNumber();
        void pressedOperator(MainPresentor.Operations operator);
        void handleDecimalMark();
    }



}
