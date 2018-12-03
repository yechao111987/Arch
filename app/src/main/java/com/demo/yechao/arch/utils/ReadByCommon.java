package com.demo.yechao.arch.utils;

import android.speech.tts.TextToSpeech;
import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.yechao.arch.activity.Main3Activity;
import com.demo.yechao.arch.utils.httpclient.InitRequest;
import org.apache.commons.codec.binary.StringUtils;
import org.junit.Test;

/**
 * @Author yechao111987@126.com
 * @date 2018/7/17 10:44
 */
public class ReadByCommon {

    @Test
    public void test1() throws Exception {
        System.out.println("test1");
        String path = "e:/p1.jpg";
        String str = Base64Img.GetImageStrFromPath(path);
        System.out.println(str);
        String words = getWord(str);
        System.out.println(words);
    }


    private static HttpSendResponse getResponse(String image) {
        HttpSendClient client = new HttpSendClient();
        String token = TokenUtil.getTokenFromFile();
        if (StringUtil.isEmpty(token)) {
            try {
                token = TokenUtil.getToken();
                TokenUtil.saveToken(token);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("tokenHuoqu", "token");
                System.out.println("token获取异常");
                return null;
            }
        }
        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/general_basic?access_token=" + token;
        System.out.println(url);
        HttpSendRequest httpSendRequest = InitRequest.init(url);
        httpSendRequest.getContent().put("image", image);
        HttpSendResponse response = null;
        try {
            response = client.executeHttpPostForm(httpSendRequest);
            if (response.getResponseBody().matches("token")) {
                FileUtils.deleteFile(Main3Activity.TOKEN_PATH);
                token = TokenUtil.getToken();
                TokenUtil.saveToken(token);
                url = "https://aip.baidubce.com/rest/2.0/ocr/v1/general_basic?access_token=" + token;
                HttpSendRequest httpSendRequest1 = InitRequest.init(url);
                httpSendRequest1.getContent().put("image", image);
                response = client.executeHttpPost(httpSendRequest);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }


    public static String getWord(String base64) throws Exception {
        HttpSendResponse response = getResponse(base64);
        if (null == response) {
            return "http response is null";
        }
        String text = response.getResponseBody();
        System.out.println(text);
//        Log.d("response", text);
//        if (text.matches("token")) {
//
//        }
        JSONObject res = JSON.parseObject(text);
        if (res.containsKey("error_msg")) {
            System.out.println(res.getString("error_msg"));
            if (res.getString("error_msg").contains("Access token expired")) {
                FileUtils.deleteFile(Main3Activity.TOKEN_PATH);
                String token = TokenUtil.getToken();
                TokenUtil.saveToken(token);
            }
            return "接口返回错误:" + res.getString("error_msg");
        }
        JSONArray jsonArray = res.getJSONArray("words_result");
        if (jsonArray.size() < 1) {
            System.out.println("解析结果为空");
            return "数据为空";
        }
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
        System.out.println(img);
        img = "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";
        httpSendRequest.getContent().put("image", img);
        HttpSendResponse response = client.executeHttpPostForm(httpSendRequest);
        String text = response.getResponseBody();
        JSONObject res = JSON.parseObject(text);
        if (res.containsKey("error_msg")) {
            System.out.println(res.getString("error_msg"));
            return;
        }
        System.out.println(res);
        String words = res.getString("words_result");
        JSONArray jsonArray = res.getJSONArray("words_result");
        if (jsonArray.size() < 1) {
            System.out.println("解析结果为空");
            return;
        }
        String strs = "";
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObjects = (JSONObject) jsonArray.get(i);
            System.out.println(jsonObjects.getString("words"));
            strs = strs + jsonObjects.getString("words");
        }

        System.out.println(strs);
    }
}