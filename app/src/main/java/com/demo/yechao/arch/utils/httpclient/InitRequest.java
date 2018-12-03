package com.demo.yechao.arch.utils.httpclient;

import com.demo.yechao.arch.utils.HttpSendRequest;


import java.util.HashMap;
import java.util.Map;

public class InitRequest {


    public static HttpSendRequest init(String url) {
        HttpSendRequest request = new HttpSendRequest();
        Map<String, String> content = new HashMap<String, String>();
        Map<String, String> header = new HashMap<String, String>();
        header.put("Connection", "keep-alive");
        request.setUrl(url);
        request.setCharset("utf-8");
        request.setTimeout(300000);
        request.setContent(content);
        request.setHeads(header);
        return request;
    }

}
