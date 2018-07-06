package com.demo.yechao.arch.activity;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import com.demo.yechao.arch.MainActivity;
import com.demo.yechao.arch.R;

public class Main2Activity extends Activity {

    private Button button_left;
    private Button button_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initButton();
        button_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Main2Activity.this, MainActivity.class);
                startActivity(intent);
                Main2Activity.this.finish();
            }
        });
    }

    private void initButton() {
        button_left = findViewById(R.id.button_left2);
        button_right = findViewById(R.id.button_right2);
    }

}
