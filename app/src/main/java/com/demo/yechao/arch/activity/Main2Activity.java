package com.demo.yechao.arch.activity;

import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.webkit.*;
import android.widget.Button;
import android.widget.FrameLayout;
import com.demo.yechao.arch.MainActivity;
import com.demo.yechao.arch.R;

public class Main2Activity extends Activity {

    private Button button_left;
    private Button button_right;
    private WebView webView;
    private FrameLayout mWebViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mWebViewContainer = findViewById(R.id.web_view_container);
        webView = new WebView(Main2Activity.this);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//设置支持javascript
//        webView.loadUrl("http://www.126.com");

        webSettings.setDomStorageEnabled(true);
        mWebViewContainer.addView(webView, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));

        webView.loadUrl("http://112.74.57.63:8080/app/house");
        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                return super.shouldOverrideUrlLoading(view, request);
//            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });
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
        button_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Main2Activity.this, Main3Activity.class);
                startActivity(intent);
                Main2Activity.this.finish();
            }
        });
    }

    private void initButton() {

        button_left = findViewById(R.id.button_left2);
        button_right = findViewById(R.id.button_right2);
//        button_click = findViewById(R.id.button_click);
    }

}
