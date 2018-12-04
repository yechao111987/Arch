package com.demo.yechao.arch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DecimalFormat df = new DecimalFormat(".00");
                double rate = Double.parseDouble(rate_text.getText().toString());
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
                if (radio_pricipal.isChecked()) {
                    payInfo = Calculate.calculateEqualPrincipal(principal - houseFund, months,
                            rate * nums / 100, 1);
                    payInfo_house = Calculate.calculateEqualPrincipal(houseFund, months, 0.0325, 1);
                } else {
                    if (radio_interest.isChecked()) {
                        payInfo = Calculate.calculateEqualPrincipalAndInterest(principal - houseFund, months,
                                rate * nums / 100, 1);
                        payInfo_house = Calculate.calculateEqualPrincipalAndInterest(houseFund, months,
                                0.0325, 1);
                    } else {
                        Toast.makeText(MainActivity.this, "please choose payment method!",
                                Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                Log.d("payInfo:{}", JSON.toJSONString(payInfo));
                Log.d("payInfo:{}", JSON.toJSONString(payInfo_house));
                Toast.makeText(MainActivity.this, "Calculate Success!", Toast.LENGTH_SHORT).show();
                String payStr = "还款总额:" + df.format(payInfo.getTotal() + payInfo_house.getTotal()) + "\n" +
                        "总利息:" + df.format(payInfo.getInterest() - payInfo_house.getInterest()) + "\n" +
                        "首付金额:" + df.format(area * price * percent) + "\n" +
                        "首月还款额:" + df.format(payInfo.getPayPerMonth() + payInfo_house.getPayPerMonth()) + "\n" +
                        "公积金贷款金额:" + df.format(payInfo_house.getPrincipal()) + "\n" +
                        "公积金贷款利息:" + df.format(payInfo_house.getInterest()) + "\n" +
                        "商业贷款金额:" + df.format(payInfo.getPrincipal()) + "\n" +
                        "商业贷款利息:" + df.format(payInfo.getInterest());
                CharSequence res = new StringBuffer(payStr);
                result_view.setText(res);
            }
        });
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
