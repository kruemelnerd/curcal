package de.philippveit.curcal;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import de.philippveit.curcal.mvp.MainMVP;
import de.philippveit.curcal.mvp.MainPresentor;
import de.philippveit.curcal.mvp.StateMaintainer;

public class MainActivity extends AppCompatActivity implements MainMVP.RequieredViewOps {


    private final String TAG = getClass().getSimpleName();

    // Responsible to maintain the objects state during chaning configuration
    private final StateMaintainer mStateMaintainer = new StateMaintainer(this.getFragmentManager(), TAG);

    // Presenter operations
    private MainMVP.PresenterOps mPresenter;

    private TextView mTextViewFirstLine;
    private TextView mTextViewSecondLine;
    private TextView mTextViewThirdLine;
    private TextView mTextViewMain;


    private ImageButton mButtonClear;
    private ImageButton mButtonBack;
    private Button mButtonBracket;

    private Button mButtonOperationsAdd;
    private Button mButtonOperationsMinus;
    private Button mButtonOperationsDivide;
    private Button mButtonOperationsMultiply;
    private Button mButtonOperationsEquals;

    private Button mButtonNumberZero;
    private Button mButtonNumberOne;
    private Button mButtonNumberTwo;
    private Button mButtonNumberThree;
    private Button mButtonNumberFour;
    private Button mButtonNumberFive;
    private Button mButtonNumberSix;
    private Button mButtonNumberSeven;
    private Button mButtonNumberEight;
    private Button mButtonNumberNine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startMVPOps();
        setContentView(R.layout.activity_main);

        initTextViews();
        initButtons();
        createClickListenersForAllButtons();

        clearTextEverywhere();
    }

    @Override
    public Context getContext() {
        return this;
    }

    public void startMVPOps() {
        try {
            if (mStateMaintainer.firstTimeIn()) {
                Log.d(TAG, "onCreate() called for the first time");
                initialize(this);
            } else {
                Log.d(TAG, "onCreate() called more than once");
                reinitialize(this);
            }
        } catch (InstantiationException | IllegalAccessException e) {
            Log.d(TAG, "onCreate() " + e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Initialize relevant MVP Objects.
     * Creates a Presenter instance, saves the presenter in {@link StateMaintainer}
     *
     * @param view
     */
    private void initialize(MainMVP.RequieredViewOps view) throws InstantiationException, IllegalAccessException {
        mPresenter = new MainPresentor(view);
        mStateMaintainer.put(mPresenter);
    }

    private void reinitialize(MainMVP.RequieredViewOps view) throws InstantiationException, IllegalAccessException {
        mPresenter = mStateMaintainer.get(MainMVP.PresenterOps.class.getSimpleName());
        if (mPresenter == null) {
            Log.w(TAG, "recreating Presenter");
            initialize(this);
        } else {
//            mPresenter.onConfigurationChanged( view );
        }
    }

    @Override
    public void clearTextEverywhere() {
        setFirstTextLine("");
        setSecondTextLine("");
        setThirdTextLine("");
        setMainTextLine("0");
        mPresenter.clearAllNumbers();
    }

    @Override
    public void setMainTextLine(String mainLine) {
        mTextViewMain.setText(mainLine);
    }

    @Override
    public void setFirstTextLine(String firstLine) {
        mTextViewFirstLine.setText(firstLine);
    }

    @Override
    public void setSecondTextLine(String secondLine) {
        mTextViewSecondLine.setText(secondLine);
    }

    @Override
    public void setThirdTextLine(String thirdLine) {
        mTextViewThirdLine.setText(thirdLine);
    }


    private void initTextViews() {
        mTextViewMain = (TextView) findViewById(R.id.textViewMainOutput);
        mTextViewFirstLine = (TextView) findViewById(R.id.textViewFirstLine);
        mTextViewSecondLine = (TextView) findViewById(R.id.textViewSecondLine);
        mTextViewThirdLine = (TextView) findViewById(R.id.textViewThirdLine);

    }

    private void initButtons() {
        //Handling the additional characters
        mButtonClear = (ImageButton) findViewById(R.id.buttonKeypadActionClear);
        mButtonBack = (ImageButton) findViewById(R.id.buttonKeypadBack);
        mButtonBracket = (Button) findViewById(R.id.buttonKeypadBracket);

        // Operations
        mButtonOperationsAdd = (Button) findViewById(R.id.buttonKeypadActionPlus);
        mButtonOperationsMinus = (Button) findViewById(R.id.buttonKeypadActionMinus);
        mButtonOperationsDivide = (Button) findViewById(R.id.buttonKeypadActionDivide);
        mButtonOperationsMultiply = (Button) findViewById(R.id.buttonKeypadActionMulti);
        mButtonOperationsEquals = (Button) findViewById(R.id.buttonKeypadActionEquals);

        // Handling the numbers
        mButtonNumberZero = (Button) findViewById(R.id.buttonKeypadNumber0);
        mButtonNumberOne = (Button) findViewById(R.id.buttonKeypadNumber1);
        mButtonNumberTwo = (Button) findViewById(R.id.buttonKeypadNumber2);
        mButtonNumberThree = (Button) findViewById(R.id.buttonKeypadNumber3);
        mButtonNumberFour = (Button) findViewById(R.id.buttonKeypadNumber4);
        mButtonNumberFive = (Button) findViewById(R.id.buttonKeypadNumber5);
        mButtonNumberSix = (Button) findViewById(R.id.buttonKeypadNumber6);
        mButtonNumberSeven = (Button) findViewById(R.id.buttonKeypadNumber7);
        mButtonNumberEight = (Button) findViewById(R.id.buttonKeypadNumber8);
        mButtonNumberNine = (Button) findViewById(R.id.buttonKeypadNumber9);
    }

    private void createClickListenersForAllButtons() {

        // Operations

        mButtonOperationsAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonOperationsAdd();
            }
        });

        mButtonOperationsMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonOperationsMinus();
            }
        });


        mButtonOperationsMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonOperationsMultiply();
            }
        });

        mButtonOperationsDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonOperationsDivide();
            }
        });

        mButtonOperationsEquals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonOperationsEquals();
            }
        });

        // Numbers

        mButtonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonClear();
            }
        });
        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonBack();
            }
        });

        mButtonNumberZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonNumberZero();
            }
        });
        mButtonNumberOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonNumberOne();
            }
        });
        mButtonNumberTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonNumberTwo();
            }
        });
        mButtonNumberThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonNumberThree();
            }
        });
        mButtonNumberFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonNumberFour();
            }
        });
        mButtonNumberFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonNumberFive();
            }
        });
        mButtonNumberSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonNumberSix();
            }
        });
        mButtonNumberSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonNumberSeven();
            }
        });
        mButtonNumberEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonNumberEight();
            }
        });
        mButtonNumberNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonNumberNine();
            }
        });
    }

    private void handleButtonBack() {
        showDebugMessage("Operation Back");
        mPresenter.removeLastNumber();
    }

    private void handleButtonOperationsEquals() {
        showDebugMessage("Operation: Equals");
        mPresenter.pressedOperator(MainPresentor.Operations.EQUALS);
    }

    private void handleButtonOperationsDivide() {
        showDebugMessage("Operation: Divide");
        mPresenter.pressedOperator(MainPresentor.Operations.DIVIDE);
    }

    private void handleButtonOperationsMultiply() {
        showDebugMessage("Operation: Multiply");
        mPresenter.pressedOperator(MainPresentor.Operations.MULTIPLY);
    }

    private void handleButtonOperationsMinus() {
        showDebugMessage("Operation: Add");
        mPresenter.pressedOperator(MainPresentor.Operations.MINUS);
    }

    private void handleButtonOperationsAdd() {
        showDebugMessage("Operation: Add");
        mPresenter.pressedOperator(MainPresentor.Operations.ADD);
    }

    private void handleButtonClear() {
        showDebugMessage("clear");
        clearTextEverywhere();
        mPresenter.clearAllNumbers();
    }

    private void handleButtonNumberZero() {
        showDebugMessage("0 clicked");
        mPresenter.addNumber(0);
    }

    private void handleButtonNumberOne() {
        showDebugMessage("1 clicked");
        mPresenter.addNumber(1);
    }

    private void handleButtonNumberTwo() {
        showDebugMessage("2 clicked");
        mPresenter.addNumber(2);
    }

    private void handleButtonNumberThree() {
        showDebugMessage("3 clicked");
        mPresenter.addNumber(3);
    }

    private void handleButtonNumberFour() {
        showDebugMessage("4 clicked");
        mPresenter.addNumber(4);
    }

    private void handleButtonNumberFive() {
        showDebugMessage("5 clicked");
        mPresenter.addNumber(5);
    }

    private void handleButtonNumberSix() {
        showDebugMessage("6 clicked");
        mPresenter.addNumber(6);
    }

    private void handleButtonNumberSeven() {
        showDebugMessage("7 clicked");
        mPresenter.addNumber(7);
    }

    private void handleButtonNumberEight() {
        showDebugMessage("8 clicked");
        mPresenter.addNumber(8);
    }

    private void handleButtonNumberNine() {
        showDebugMessage("9 clicked");
        mPresenter.addNumber(9);
    }

    private void showDebugMessage(String text) {
        Log.d(TAG, text);
    }

    @Override
    public void showErrorMessage(String error) {
        Toast toast = Toast.makeText(this, error, Toast.LENGTH_SHORT);
        toast.show();
    }


}
