package com.example.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn1, btn2, btn3, btn_clear, btn_plus, btn_equal, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btn_mult, btn_div, btn_dot, btn_minus;
    TextView text_display;
    double val1, val2;
    enum Operator{none, add, minus, mult, div}
    Operator optr = Operator.none;
    // This is to evaluate the math expression

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn0 = (Button) findViewById(R.id.btn0);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);
        btn_dot = (Button) findViewById(R.id.btn_dot);
        btn_mult = (Button) findViewById(R.id.btn_mult);
        btn_div = (Button) findViewById(R.id.btn_div);
        btn_minus = (Button) findViewById(R.id.btn_minus);
        btn_plus = (Button) findViewById(R.id.btn_plus);
        btn_equal = (Button) findViewById(R.id.btn_equal);
        btn_clear = (Button) findViewById(R.id.btn_clear);
        text_display = (TextView) findViewById(R.id.textview_input_display);

        setClickListeners();
    }

    private void setClickListeners() {
        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn_mult.setOnClickListener(this);
        btn_div.setOnClickListener(this);
        btn_minus.setOnClickListener(this);
        btn_dot.setOnClickListener(this);
        btn_plus.setOnClickListener(this);
        btn_equal.setOnClickListener(this);
        btn_clear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn0:
                addNumber("0");
                break;
            case R.id.btn1:
                addNumber("1");
                break;
            case R.id.btn2:
                addNumber("2");
                break;
            case R.id.btn3:
                addNumber("3");
                break;
            case R.id.btn4:
                addNumber("4");
                break;
            case R.id.btn5:
                addNumber("5");
                break;
            case R.id.btn6:
                addNumber("6");
                break;
            case R.id.btn7:
                addNumber("7");
                break;
            case R.id.btn8:
                addNumber("8");
                break;
            case R.id.btn9:
                addNumber("9");
                break;
            case R.id.btn_mult:
                addNumber("x");
                break;
            case R.id.btn_div:
                addNumber("/");
                break;
            case R.id.btn_dot:
                addNumber(".");
                break;
            case R.id.btn_minus:
                addNumber("-");
                break;
            case R.id.btn_plus:
                addNumber("+");
                break;
            case R.id.btn_equal:
                String result = null;
                try {
                    result = evaluate(text_display.getText().toString());
                    text_display.setText(result);
                } catch (Exception e) {
                    text_display.setText("Error");
                }
                break;
            case R.id.btn_clear:
                clear_display();
                break;
        }
    }

    private String evaluate(String expression) throws Exception {
        if (expression == null || expression.isEmpty()) {
            return "0";
        }

        List<String> item = new ArrayList<>();
        StringBuilder number = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isDigit(c) || c == '.') {
                number.append(c); // build the number
            } else if (c == '+' || c == '-' || c == 'x' || c == '/') {
                if (number.length() > 0) {
                    item.add(number.toString());
                    number.setLength(0);
                }
                item.add(String.valueOf(c)); // add the operator
            }
        }

        // add last number
        if (number.length() > 0) {
            item.add(number.toString());
        }

        List<Double> numbers = new ArrayList<>();
        List<Character> operators = new ArrayList<>();

        for (String token:item) {
            if (token.equals("+") || token.equals("-") || token.equals("x") || token.equals("/")) {
                operators.add(token.charAt(0));
            } else {
                numbers.add(Double.parseDouble(token));
            }
        }

        for (int i = 0; i < operators.size(); i++) {
            char op = operators.get(i);
            if (op == 'x' || op == '/') {
                double a = numbers.get(i);
                double b = numbers.get(i + 1);
                double res = (op == 'x') ? a * b : a / b;

                // replace results in lists
                numbers.set(i, res);
                numbers.remove(i + 1);
                operators.remove(i);
                i--; // step back after removal
            }
        }

        double result = numbers.get(0);
        for (int i = 0; i < operators.size(); i++) {
            char op = operators.get(i);
            double next = numbers.get(i + 1);
            if (op == '+') {
                result += next;
            }
            else if (op == '-') {
                result -= next;
            }
        }

        BigDecimal decimal = new BigDecimal(result);

        return decimal.stripTrailingZeros().toPlainString();
    }

    private void addNumber(String number) {
        text_display.setText(text_display.getText() + number);
    }

    private void clear_display() {
        text_display.setText("");
    }
}
