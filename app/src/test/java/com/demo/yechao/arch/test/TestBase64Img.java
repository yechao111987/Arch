package com.demo.yechao.arch.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.yechao.arch.utils.Base64Img;
import com.demo.yechao.arch.utils.HttpSendClient;
import com.demo.yechao.arch.utils.HttpSendRequest;
import com.demo.yechao.arch.utils.HttpSendResponse;
import com.demo.yechao.arch.utils.httpclient.InitRequest;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author yechao111987@126.com
 * @date 2018/7/17 10:44
 */
public class TestBase64Img {

    @Test
    public void test1() {
        System.out.println("test1");
        String path = "e:/p1.jpg";
        String str = Base64Img.GetImageStrFromPath(path);
        System.out.println(str);
    }

    public static String getWord(String base64str) throws Exception {
        HttpSendClient client = new HttpSendClient();
        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/general_basic?access_token=24.f5ddd935132c418280d3cef777694650.2592000.1534325602.282335-11542612";
        HttpSendRequest httpSendRequest = InitRequest.init(url);
        httpSendRequest.getContent().put("image", base64str);
        HttpSendResponse response = client.executeHttpPostForm(httpSendRequest);
        String text = response.getResponseBody();
        JSONObject res = JSON.parseObject(text);
        JSONArray jsonArray = res.getJSONArray("words_result");
        String strs = "";
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObjects = (JSONObject) jsonArray.get(i);
            System.out.println(jsonObjects.getString("words"));
            strs = strs + jsonObjects.getString("words");
        }
        return strs;

    }

    @Test
    public void test2() throws Exception {
        HttpSendClient client = new HttpSendClient();
        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/general_basic?access_token=24.f5ddd935132c418280d3cef777694650.2592000.1534325602.282335-11542612";
        HttpSendRequest httpSendRequest = InitRequest.init(url);
        String path = "e:/p1.jpg";
        String img = Base64Img.GetImageStrFromPath(path);
        httpSendRequest.getContent().put("image", img);
        HttpSendResponse response = client.executeHttpPostForm(httpSendRequest);
        String text = response.getResponseBody();
        JSONObject res = JSON.parseObject(text);
        String words = res.getString("words_result");
        JSONArray jsonArray = res.getJSONArray("words_result");
        String strs = "";
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObjects = (JSONObject) jsonArray.get(i);
            System.out.println(jsonObjects.getString("words"));
            strs = strs + jsonObjects.getString("words");
        }

        System.out.println(strs);
    }
}