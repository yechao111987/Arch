package com.demo.yechao.arch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.autofill.AutofillValue;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private EditText editText;
    private EditText editText2;
    private EditText editText3;

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initEditText();
        initButton();
        initView();
        System.out.println(editText.getText().toString());
        Log.d("", "2222");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("22222");
                System.out.println(editText.getText().toString());
                Log.d("", "2222");
                Toast.makeText(MainActivity.this, "click buttons", Toast.LENGTH_LONG).show();
                CharSequence aa = new StringBuffer("yechao");
//                String aa = "yechao";

                textView.setText(aa);

//                textView.append("click");
            }
        });
//        textView.append("123456789");
    }


    private void initView() {
        textView = findViewById(R.id.textView6);
    }

    private void initEditText() {
        editText = findViewById(R.id.area_text);
        editText2 = findViewById(R.id.price_text);
        editText3 = findViewById(R.id.paypercent_text);

    }

    private void initButton() {
        button = findViewById(R.id.button);
    }
}
