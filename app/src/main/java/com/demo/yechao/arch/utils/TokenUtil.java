package com.demo.yechao.arch.utils;

import com.alibaba.fastjson.JSON;
import com.demo.yechao.arch.activity.Main3Activity;
import com.demo.yechao.arch.utils.httpclient.InitRequest;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.util.List;


/**
 * @Author yechao111987@126.com
 * @date 2018/8/1 15:20
 */
public class TokenUtil {

    //设置APPID/AK/SK
    public static final String APP_ID = "11618674";
    public static final String API_KEY = "5Az0Yk0dwTf0s74mRt9yE5bg";
    public static final String SECRET_KEY = "OooPT9gHUz4Qp9kL8KlIWrSE8rtI3TC3";

    public static final String TOKEN_PATH = "app/src/main/res/token.txt";

    public static final String TOKEN_URL = "https://aip.baidubce.com/oauth/2.0/token";


//    public static void main(String[] args) {
//        // 初始化一个AipOcr
//        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
//
//        // 可选：设置网络连接参数
//        client.setConnectionTimeoutInMillis(2000);
//        client.setSocketTimeoutInMillis(60000);
//
//        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
////        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
////        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理
//
//        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
//        // 也可以直接通过jvm启动参数设置此环境变量
////        System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");
//
//        // 调用接口
//        String path = "app/src/main/res/p1.jpg";
//        JSONObject res = client.basicGeneral(path, new HashMap<String, String>());
//
//    }

    public static String getToken() throws Exception {
        HttpSendClient client = new HttpSendClient();
        HttpSendRequest request = InitRequest.init(TOKEN_URL);

        request.getContent().put("grant_type", "client_credentials");
        request.getContent().put("client_id", API_KEY);
        request.getContent().put("client_secret", SECRET_KEY);

        HttpSendResponse response = client.executeHttpPost(request);
        System.out.println(response.getResponseBody());
        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(response.getResponseBody());
        System.out.println("token:" + jsonObject.getString("access_token"));
        return jsonObject.getString("access_token");

    }

    public static void saveToken(String token) throws IOException {
        FileUtils.writeFile(Main3Activity.TOKEN_PATH, token);


    }

    public static void deleteToken(String token) throws IOException {
        FileUtils.deleteFile(Main3Activity.TOKEN_PATH);
    }


    public static String getTokenFromFile() {
        List<String> strs = null;
        try {
            strs = FileUtils.readFile(Main3Activity.TOKEN_PATH);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        if (strs != null && strs.size() >= 0) {
            return strs.get(0);
        } else {
            return "";
        }
    }


    @Test
    public void test1() throws Exception {
        String a = getToken();
        System.out.println(a);
    }


}
