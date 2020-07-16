package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button mButtonZero, mButtonOne, mButtonTwo, mButtonThree, mButtonFour, mButtonFive, mButtonDot,
            mButtonSix, mButtonSeven, mButtonEight, mButtonNine, mButtonEqual, mButtonSum, mButtonSub,
            mButtonDiv, mButtonMulti;
    private ImageView mImageViewRemoveLast, mImageViewClean;
    private TextView mTextViewResult, mTextViewInput;

    private Calculator mCalculator = new Calculator();
    private String mStringInputPrint = "", mStringCurrentInput = "";
    private Long mIntMemory;
    private Double mDoubleMemory;
    private char mCharLastOperation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findAllView();
        setAllListener();
        if (mStringInputPrint.length() != 0) {
            char operations = mStringInputPrint.charAt(mStringInputPrint.length() - 1);
            if (operations == 'x' || operations == '+' || operations == '-' || operations == '/') {
                mImageViewRemoveLast.setClickable(false);
            } else mImageViewRemoveLast.setClickable(true);
        }
    }

    private void findAllView() {
        mImageViewRemoveLast = findViewById(R.id.button_remove_last1);
        mImageViewClean = findViewById(R.id.imageView_clean);
        mButtonDiv = findViewById(R.id.button_div);
        mButtonDot = findViewById(R.id.button_dot);
        mButtonZero = findViewById(R.id.button_zero);
        mButtonOne = findViewById(R.id.button_one);
        mButtonTwo = findViewById(R.id.button_two);
        mButtonThree = findViewById(R.id.button_three);
        mButtonFour = findViewById(R.id.button_four);
        mButtonFive = findViewById(R.id.button_five);
        mButtonSix = findViewById(R.id.button_six);
        mButtonSeven = findViewById(R.id.button_seven);
        mButtonEight = findViewById(R.id.button_eight);
        mButtonNine = findViewById(R.id.button_nine);
        mButtonMulti = findViewById(R.id.button_multiplication);
        mButtonSum = findViewById(R.id.button_summation);
        mButtonSub = findViewById(R.id.button_subtraction);
        mButtonEqual = findViewById(R.id.button_equal);

        mTextViewInput = findViewById(R.id.text_view_input);
        mTextViewResult = findViewById(R.id.text_view_result);
    }

    private void setAllListener() {
        mButtonDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStringInputPrint += ".";
                mStringCurrentInput += ".";
                mTextViewInput.setText(mStringInputPrint);
            }
        });

        mButtonZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStringInputPrint += "0";
                mStringCurrentInput += "0";
                mTextViewInput.setText(mStringInputPrint);
            }
        });

        mButtonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickNumbers("1");
            }
        });

        mButtonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickNumbers("2");
            }
        });

        mButtonThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickNumbers("3");
            }
        });

        mButtonFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickNumbers("4");
            }
        });

        mButtonFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickNumbers("5");
            }
        });

        mButtonSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickNumbers("6");
            }
        });

        mButtonSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickNumbers("7");
            }
        });

        mButtonEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickNumbers("8");
            }
        });

        mButtonNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickNumbers("9");
            }
        });

        //error
        mImageViewRemoveLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                char op = mStringInputPrint.charAt(mStringInputPrint.length() - 1);
                if (op == '+' || op == '/' || op == '-' || op == 'x') {
                    Toast.makeText(MainActivity.this, "sorry , cant delete operation....", Toast.LENGTH_SHORT).show();
                } else {
                    if (mStringInputPrint.length() == 1){
                        Toast.makeText(MainActivity.this, "Input is empty...", Toast.LENGTH_SHORT).show();
                        mTextViewResult.setBackgroundResource(R.drawable.text_input);
                    }
                    if (mStringInputPrint.length() != 0) {
                        mStringInputPrint = mStringInputPrint.substring(0, mStringInputPrint.length() - 1);
                        mStringCurrentInput = mStringCurrentInput.substring(0, mStringCurrentInput.length() - 1);
                        mTextViewInput.setText(mStringInputPrint);
                    }
                }
            }

        });
        mImageViewClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStringInputPrint = "";
                mStringCurrentInput = "";
                mCalculator.reset();
                mDoubleMemory = null;
                mIntMemory = null;
                mTextViewResult.setText("");
                mCharLastOperation = ',';
                mTextViewInput.setText(mStringInputPrint);
                mTextViewResult.setBackgroundResource(R.drawable.text_input);
            }
        });

        mButtonSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOperationRepetition("+");
            }
        });

        mButtonSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOperationRepetition("-");
            }
        });

        mButtonMulti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOperationRepetition("x");
            }
        });

        mButtonDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOperationRepetition("/");
            }
        });

        mButtonEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((mCharLastOperation != '+' && mCharLastOperation != '/' && mCharLastOperation != '-' && mCharLastOperation != 'x') && (mStringInputPrint.charAt(mStringInputPrint.length() - 1) != '+' && mStringInputPrint.charAt(mStringInputPrint.length() - 1) != '-' &&
                        mStringInputPrint.charAt(mStringInputPrint.length() - 1) != '/' && mStringInputPrint.charAt(mStringInputPrint.length() - 1) != 'x')) {
                    Toast.makeText(MainActivity.this, "No operation performed...", Toast.LENGTH_SHORT).show();
                } else {
                    calc();
                    mTextViewInput.setText(mTextViewResult.getText());
                    mStringInputPrint = (String) mTextViewResult.getText();
                    mStringCurrentInput = (String) mTextViewResult.getText();
                    mCharLastOperation = ',';
                    mTextViewResult.setBackgroundResource(R.drawable.back_equal);
                }
            }
        });
    }

    private void operationsAction(String s) {
        if (mCharLastOperation != '+' && mCharLastOperation != '/' && mCharLastOperation != '-' && mCharLastOperation != 'x') {
            mStringInputPrint += s;
            mCharLastOperation = s.charAt(0);
            mTextViewInput.setText(mStringInputPrint);
            if (mStringCurrentInput.contains(".")) {
                mDoubleMemory = Double.parseDouble(mStringCurrentInput);
            } else mIntMemory = Long.parseLong(mStringCurrentInput);
            mStringCurrentInput = "";
        } else {
            calc();
            mStringInputPrint += s;
            mCharLastOperation = s.charAt(0);
            mTextViewInput.setText(mStringInputPrint);
            mStringCurrentInput = "";
        }
    }

    private void calc() {
        if (mDoubleMemory != null || mStringCurrentInput.contains(".")) {

            if (mDoubleMemory != null && mStringCurrentInput.contains(".")) {
                if (mCharLastOperation == '+') {
                    mTextViewResult.setText(String.valueOf(mCalculator.add(mDoubleMemory, Double.parseDouble(mStringCurrentInput))));
                    mDoubleMemory = (mCalculator.add(mDoubleMemory, Double.parseDouble(mStringCurrentInput)));
                } else if (mCharLastOperation == '-') {
                    mTextViewResult.setText(String.valueOf(mCalculator.subtraction(mDoubleMemory, Double.parseDouble(mStringCurrentInput))));
                    mDoubleMemory = (mCalculator.subtraction(mDoubleMemory, Double.parseDouble(mStringCurrentInput)));
                } else if (mCharLastOperation == '/') {
                    if (Double.parseDouble(mStringCurrentInput) == 0) {
                        Toast.makeText(this, "wrong , numbers cant division by zero !!!", Toast.LENGTH_SHORT).show();
                    } else {
                        mTextViewResult.setText(String.valueOf(mCalculator.division(mDoubleMemory, Double.parseDouble(mStringCurrentInput))));
                        mDoubleMemory = (mCalculator.division(mDoubleMemory, Double.parseDouble(mStringCurrentInput)));
                    }
                } else {
                    mTextViewResult.setText(String.valueOf(mCalculator.multiply(mDoubleMemory, Double.parseDouble(mStringCurrentInput))));
                    mDoubleMemory = (mCalculator.multiply(mDoubleMemory, Double.parseDouble(mStringCurrentInput)));
                }
            } else if (mDoubleMemory != null && !mStringCurrentInput.contains(".")) {
                if (mCharLastOperation == '+') {
                    mTextViewResult.setText(String.valueOf(mCalculator.add(mDoubleMemory, Integer.parseInt(mStringCurrentInput))));
                    mDoubleMemory = (mCalculator.add(mDoubleMemory, Integer.parseInt(mStringCurrentInput)));
                } else if (mCharLastOperation == '-') {
                    mTextViewResult.setText(String.valueOf(mCalculator.subtraction(mDoubleMemory, Integer.parseInt(mStringCurrentInput))));
                    mDoubleMemory = (mCalculator.subtraction(mDoubleMemory, Integer.parseInt(mStringCurrentInput)));
                } else if (mCharLastOperation == '/') {
                    if (Integer.parseInt(mStringCurrentInput) == 0) {
                        Toast.makeText(this, "wrong , numbers cant division by zero !!!", Toast.LENGTH_SHORT).show();
                    } else {
                        mTextViewResult.setText(String.valueOf(mCalculator.division(mDoubleMemory, Integer.parseInt(mStringCurrentInput))));
                        mDoubleMemory = (mCalculator.division(mDoubleMemory, Integer.parseInt(mStringCurrentInput)));
                    }
                } else {
                    mTextViewResult.setText(String.valueOf(mCalculator.multiply(mDoubleMemory, Integer.parseInt(mStringCurrentInput))));
                    mDoubleMemory = (mCalculator.multiply(mDoubleMemory, Integer.parseInt(mStringCurrentInput)));
                }
            } else if (mDoubleMemory == null && mStringCurrentInput.contains(".")) {
                if (mCharLastOperation == '+') {
                    mTextViewResult.setText(String.valueOf(mCalculator.add(mIntMemory, Double.parseDouble(mStringCurrentInput))));
                    mDoubleMemory = (mCalculator.add(mIntMemory, Double.parseDouble(mStringCurrentInput)));
                } else if (mCharLastOperation == '-') {
                    mTextViewResult.setText(String.valueOf(mCalculator.subtraction(mIntMemory, Double.parseDouble(mStringCurrentInput))));
                    mDoubleMemory = (mCalculator.subtraction(mIntMemory, Double.parseDouble(mStringCurrentInput)));
                } else if (mCharLastOperation == '/') {
                    if (Double.parseDouble(mStringCurrentInput) == 0) {
                        Toast.makeText(this, "wrong , numbers cant division by zero !!!", Toast.LENGTH_SHORT).show();
                    } else {
                        mTextViewResult.setText(String.valueOf(mCalculator.division(mIntMemory, Double.parseDouble(mStringCurrentInput))));
                        mDoubleMemory = (mCalculator.division(mIntMemory, Double.parseDouble(mStringCurrentInput)));
                    }
                } else {
                    mTextViewResult.setText(String.valueOf(mCalculator.multiply(mIntMemory, Double.parseDouble(mStringCurrentInput))));
                    mDoubleMemory = (mCalculator.multiply(mIntMemory, Double.parseDouble(mStringCurrentInput)));
                }
            }
        } else {
            if (mCharLastOperation == '+') {
                mTextViewResult.setText(String.valueOf(mCalculator.add(mIntMemory, Integer.parseInt(mStringCurrentInput))));
                mIntMemory = mCalculator.add(mIntMemory, Integer.parseInt(mStringCurrentInput));
            } else if (mCharLastOperation == '-') {
                mTextViewResult.setText(String.valueOf(mCalculator.subtraction(mIntMemory, Integer.parseInt(mStringCurrentInput))));
                mIntMemory = mCalculator.subtraction(mIntMemory, Integer.parseInt(mStringCurrentInput));
            } else if (mCharLastOperation == '/') {
                if (Integer.parseInt(mStringCurrentInput) == 0) {
                    Toast.makeText(this, "wrong , numbers cant division by zero !!!", Toast.LENGTH_SHORT).show();
                } else {
                    mTextViewResult.setText(String.valueOf(mCalculator.division(mIntMemory, Integer.parseInt(mStringCurrentInput))));
                    mIntMemory = mCalculator.division(mIntMemory, Integer.parseInt(mStringCurrentInput));
                }

            } else {
                mTextViewResult.setText(String.valueOf(mCalculator.multiply(mIntMemory, Integer.parseInt(mStringCurrentInput))));
                mIntMemory = mCalculator.multiply(mIntMemory, Integer.parseInt(mStringCurrentInput));
            }
        }
    }

    private void onClickNumbers(String s) {
        // if (mCharLastOperation != '+' && mCharLastOperation != '/' && mCharLastOperation != '-' && mCharLastOperation != '*') {
        mStringInputPrint += s;
        mStringCurrentInput += s;
        mTextViewInput.setText(mStringInputPrint);
//        } else {
//            mStringInputPrint += s;
//            mStringCurrentInput += s;
//            mTextViewInput.setText(mStringInputPrint);

//            if (mDoubleMemory != null || mStringCurrentInput.contains(".")) {
//                mTextViewResult.setText(String.valueOf(mCalculator.add(mDoubleMemory, Double.parseDouble(mStringCurrentInput))));
//                mDoubleMemory = (mCalculator.add(mDoubleMemory, Double.parseDouble(mStringCurrentInput)));
//                Toast.makeText(this, String.valueOf(mDoubleMemory), Toast.LENGTH_SHORT).show();
//            } else {
//                mTextViewResult.setText(String.valueOf(mCalculator.add(mIntMemory, Integer.parseInt(mStringCurrentInput))));
//                mIntMemory = mCalculator.add(mIntMemory, Integer.parseInt(mStringCurrentInput));
//            }
        // }
    }

    private void checkOperationRepetition(String s) {
        if (mStringInputPrint.length() == 0) {
            Toast.makeText(MainActivity.this, "please enter number ...", Toast.LENGTH_SHORT).show();
            return;
        }
        if ((mStringInputPrint.charAt(mStringInputPrint.length() - 1) != '+' && mStringInputPrint.charAt(mStringInputPrint.length() - 1) != '-' &&
                mStringInputPrint.charAt(mStringInputPrint.length() - 1) != '/' && mStringInputPrint.charAt(mStringInputPrint.length() - 1) != 'x')) {
            operationsAction(s);
        } else {
            Toast.makeText(MainActivity.this, "please enter number ...", Toast.LENGTH_SHORT).show();
        }
    }
}