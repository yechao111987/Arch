package com.demo.yechao.arch.utils.httpclient;

import okhttp3.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author yechao111987@126.com
 * @date 2018/7/17 11:04
 */
public class HttpClient {

    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json;charset=utf-8");
    public static final MediaType MEDIA_URLENCODED = MediaType.parse("application/x-www-form-urlencoded;charset=utf-8");
    public static final MediaType MEDIA_MULTIPART = MediaType.parse("multipart/form-data");
    public static final MediaType MEDIA_URLENCODED_WITH_GBK = MediaType.parse("application/x-www-form-urlencoded;charset=GBK");


    private static final OkHttpClient CLIENT;
    private static HttpClient instance = null;

    static {
        CLIENT = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .cookieJar(new CookieJarImpl(new MemoryCookieStore()))
                .build();
    }

//    private HttpClient() {
//    }

    public static HttpClient getInstance() {
        if (null == instance) {
            synchronized (HttpClient.class) {
                if (null == instance) {
                    instance = new HttpClient();
                }
            }
        }
        return instance;
    }

    public String get(String url) throws IOException {
        return getWithHeader(url, null);
    }

    public String getWithHeader(String url, Map<String, String> header) throws IOException {
        Request.Builder builder = new Request.Builder();
        if (null != header && header.size() > 0) {
            for (Map.Entry<String, String> entry : header.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        Request request = builder.url(url).build();
        Response response = CLIENT.newCall(request).execute();
        ResponseBody rspBody = response.body();
        if (response.isSuccessful() && null != rspBody) {
            return rspBody.string();
        }
        return null;
    }

    public String post(String url, MediaType mediaType, String param) throws IOException {
        return postWithHeader(url, param, mediaType, null);
    }


    public CookieToken postReturnCookies(String url, MediaType mediaType, String param) throws IOException {
        return postReturnCookiesWithHeader(url, param, mediaType, null);
    }


    public CookieToken postReturnCookiesWithHeader(String url, String param, MediaType mediaType, Map<String, String> header) throws IOException {

        CookieToken ct = new CookieToken();
        RequestBody body = RequestBody.create(mediaType, param);
        Request.Builder builder = new Request.Builder();
        if (null != header && header.size() > 0) {
            for (Map.Entry<String, String> entry : header.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        Request request = builder.url(url).post(body).build();
        Response response = CLIENT.newCall(request).execute();

        List<Cookie> cookies = CLIENT.cookieJar().loadForRequest(request.url());

        String cook = "";
        String token = "";
        for (Cookie cookie : cookies) {

            if (cookie.name().equals("ipcrs.acn") && !cookie.value().equals("")) {
                cook = cookie.toString();
//                System.out.println("cook = " + cook);
            }
            if (cookie.name().equals("ipcrs.w.m.b.n") && !cookie.value().equals("")) {
                cook = cook + ";" + cookie.toString();
//                System.out.println("cook = " + cook);
            }
            if (cookie.name().equals("jsid_ipcrs_monica_auth") && !cookie.value().equals("")) {
                cook = cook + ";" + cookie.toString();
                token = cookie.value();
//                System.out.println("cook = " + cook);
            }
        }


        ResponseBody rspBody = response.body();
        if (response.isSuccessful() && null != rspBody) {
            ct.setCookies(cook);
            ct.setToken(token);
            return ct;
        }
        return null;
    }


    public String postWithHeader(String url, String param, MediaType mediaType, Map<String, String> header) throws IOException {
        RequestBody body = RequestBody.create(mediaType, param);
        Request.Builder builder = new Request.Builder();
        if (null != header && header.size() > 0) {
            for (Map.Entry<String, String> entry : header.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        Request request = builder.url(url).post(body).build();
        Response response = CLIENT.newCall(request).execute();

        List<Cookie> cookies = CLIENT.cookieJar().loadForRequest(request.url());

        String cook;
        for (Cookie cookie : cookies) {

            if (cookie.name().equals("ipcrs.acn")) {
                cook = cookie.toString();
                System.out.println("cook = " + cook);
            }
            System.out.println("name = " + cookie.name());
            System.out.println("value = " + cookie.value());
        }


        ResponseBody rspBody = response.body();
        if (response.isSuccessful() && null != rspBody) {
            return rspBody.string();
        }
        return null;
    }

    public String patch(String url, Map<String, String> header, String reqJson) throws IOException {
        Request.Builder builder = new Request.Builder();
        if (null != header && header.size() > 0) {
            for (Map.Entry<String, String> entry : header.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, reqJson);
        Request request = builder.url(url).patch(body).build();
        Response response = CLIENT.newCall(request).execute();
        ResponseBody rspBody = response.body();
        if (response.isSuccessful() && null != rspBody) {
            return rspBody.string();
        }
        return null;
    }
}
