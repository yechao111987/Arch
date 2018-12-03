package com.demo.yechao.arch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.autofill.AutofillValue;
import android.widget.*;
import com.alibaba.fastjson.JSON;
import com.demo.yechao.arch.activity.Main2Activity;
import com.demo.yechao.arch.vo.PayInfo;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private TextView result_view;
    private EditText area_text;
    private EditText price_text;
    private EditText percents_text;
    private EditText nums_text;
    private EditText rate_text;
    private EditText houseFund_text;
    private EditText month_text;

    private Button button;
    private Button button_next;

    private RadioButton radio_pricipal;
    private RadioButton radio_interest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initEditText();
        initButton();
        initView();
        initRadio();
        System.out.println(result_view.getText().toString());
        Log.d("", "2222");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DecimalFormat df = new DecimalFormat(".00");
                double rate = Double.parseDouble(rate_text.getText().toString()) / 1000;
                Log.d("rate", String.valueOf(rate));
                double area = Double.parseDouble(area_text.getText().toString());
                double price = Double.parseDouble(price_text.getText().toString());
                double percent = Double.parseDouble(percents_text.getText().toString()) / 10;
                double nums = Double.parseDouble(nums_text.getText().toString());
                double houseFund = Double.parseDouble(houseFund_text.getText().toString());
                Log.d("percent", String.valueOf(percent));
                double principal = area * price * (1 - percent);
                Log.d("principal", String.valueOf(principal));
                int months = Integer.parseInt(month_text.getText().toString());
                PayInfo payInfo = null;
                PayInfo payInfo_house = null;
//                DecimalFormat df1 = new DecimalFormat(".000000");
//                Double temp = rate * nums;
////                double rate_new = Double.valueOf(df1.format(temp));
                if (radio_pricipal.isChecked()) {
                    payInfo = Test.calculateEqualPrincipal(principal - houseFund, months, rate * nums, 1);
                    payInfo_house = Test.calculateEqualPrincipal(houseFund, months, 0.00325, 1);
                } else {
                    if (radio_interest.isChecked()) {
                        payInfo = Test.calculateEqualPrincipalAndInterest(principal - houseFund, months, rate * nums, 1);
                        payInfo_house = Test.calculateEqualPrincipalAndInterest(houseFund, months, 0.00325, 1);

                    } else {
                        Toast.makeText(MainActivity.this, "please choose payment method!", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                Log.d("principal", String.valueOf(principal));
                Toast.makeText(MainActivity.this, "calucate Success!", Toast.LENGTH_SHORT).show();
                String payStr = "贷款总额:" + df.format(payInfo.getPrincipal() + payInfo_house.getPrincipal()) + "\n" +
//                        "总本金:" + df.format(payInfo.getPrincipal()) + "\n" +
//                        "总利息:" + df.format(payInfo.getInterest()) + "\n" +
                        "首月还款额:" + df.format(payInfo.getPayPerMonth() + payInfo_house.getPayPerMonth());
                CharSequence res = new StringBuffer(payStr);
                result_view.setText(res);
            }
        });
//        textView.append("123456789");
        Log.d("button_next", button_next.toString());

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Main2Activity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });

    }

    private void initRadio() {
        radio_pricipal = findViewById(R.id.principal_radio);
        radio_interest = findViewById(R.id.interest_radio);
    }


    private void initView() {
        result_view = findViewById(R.id.result_view);
    }

    private void initEditText() {
        area_text = findViewById(R.id.area_text);
        price_text = findViewById(R.id.price_text);
        percents_text = findViewById(R.id.paypercent_text);
        nums_text = findViewById(R.id.num_text);
        rate_text = findViewById(R.id.rate_text);
        houseFund_text = findViewById(R.id.houseFund_text);
        month_text = findViewById(R.id.month_text);

    }

    private void initButton() {
        button = findViewById(R.id.button);
        button_next = findViewById(R.id.next);
    }
}
