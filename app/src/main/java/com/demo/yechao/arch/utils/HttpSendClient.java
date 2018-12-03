package com.demo.yechao.arch.utils;


import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.util.EncodingUtil;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class HttpSendClient {

    String Content_type = "application/json";
    String Content_type2 = "application/x-www-form-urlencoded";

    public String parmUrlEncode(NameValuePair[] nameValuePair, String charset) {
        return EncodingUtil.formUrlEncode(nameValuePair, charset);
    }

    public HttpSendResponse executeHttpPost(HttpSendRequest request) throws Exception {
        if (request == null) {
            throw new Exception("executeHttpPost param exception, request is null");
        }
        String queryString = "";
        PostMethod httpMethod = new PostMethod(request.getUrl());
        if (request.getContent() != null && request.getContent().size() > 0) {
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            List<String> keys = new ArrayList<String>(request.getContent().keySet());
            Collections.sort(keys);
            for (String key : keys) {
                String value = request.getContent().get(key);
                NameValuePair nv = new NameValuePair(key, value);
                list.add(nv);
            }
            NameValuePair[] data = list.toArray(new NameValuePair[list.size()]);
            queryString = this.parmUrlEncode(data, request.getCharset());
        }
        // 设置Request默认的header
        httpMethod.setRequestHeader("Content-type", Content_type2);// text/html//application/json
        httpMethod.setRequestHeader("charset", request.getCharset());
        httpMethod.setRequestHeader("Connection", "close");
        // httpMethod.setRequestHeader("Accept-Language", "zh-cn");
        // httpMethod.setRequestHeader("Cache-Control", "no-cache");

        if (request.getHeads() != null && request.getHeads().size() > 0) {
            for (String key : request.getHeads().keySet()) {
                String value = request.getHeads().get(key);
                httpMethod.setRequestHeader(key, value);
            }
        }
//        queryString = request.getContent().get("content");
        System.out.println("queryString is " + queryString);

        httpMethod.setRequestEntity(
                new StringRequestEntity(queryString, Content_type + request.getCharset(), request.getCharset()));

        long startTime = System.currentTimeMillis();
        HttpSendResponse res = this.execute_(httpMethod, request.getTimeout(), request.getCharset());
        long endTime = System.currentTimeMillis();
        System.out.println("excTime=" + (endTime - startTime));

        return res;
    }


    // form提交
    public HttpSendResponse executeHttpPostForm(HttpSendRequest request) throws Exception {
        if (request == null) {
            throw new Exception("executeHttpPost param exception, request is null");
        }
        String queryString = "";

        PostMethod httpMethod = new PostMethod(request.getUrl());

        if (request.getContent() != null && request.getContent().size() > 0) {

            List<NameValuePair> list = new ArrayList<NameValuePair>();

            List<String> keys = new ArrayList<String>(request.getContent().keySet());

            Collections.sort(keys);

            for (String key : keys) {

                String value = request.getContent().get(key);

                NameValuePair nv = new NameValuePair(key, value);

                list.add(nv);
            }

            NameValuePair[] data = list.toArray(new NameValuePair[list.size()]);

            queryString = this.parmUrlEncode(data, request.getCharset());
        }
        // 设置Request默认的header
        httpMethod.setRequestHeader("Content-type", "application/x-www-form-urlencoded"); // text/html
        // application/json
        // application/x-www-form-urlencoded
        httpMethod.setRequestHeader("charset", request.getCharset());
        httpMethod.setRequestHeader("Connection", "close");
        httpMethod.setRequestHeader("Accept-Language", "zh-cn");
        httpMethod.setRequestHeader("Cache-Control", "no-cache");

        if (request.getHeads() != null && request.getHeads().size() > 0) {
            for (String key : request.getHeads().keySet()) {
                String value = request.getHeads().get(key);
                httpMethod.setRequestHeader(key, value);
            }
        }
        httpMethod.setRequestEntity(new StringRequestEntity(queryString,
                "application/x-www-form-urlencoded" + request.getCharset(), request.getCharset()));
        long startTime = System.currentTimeMillis();
        HttpSendResponse res = this.execute_(httpMethod, request.getTimeout(), request.getCharset());
        long endTime = System.currentTimeMillis();
        System.out.println("excTime=" + (endTime - startTime));

        return res;
    }

    public HttpSendResponse executeHttpPost(HttpSendRequest request, File targetFile) throws Exception {
        if (request == null) {
            throw new Exception("executeHttpPost param exception, request is null");
        }
        String queryString = null;
        if (request.getContent() != null && request.getContent().size() > 0) {
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            List<String> keys = new ArrayList<String>(request.getContent().keySet());
            Collections.sort(keys);
            for (String key : keys) {
                String value = request.getContent().get(key);
                NameValuePair nv = new NameValuePair(key, value);
                list.add(nv);
            }
            NameValuePair digest = new NameValuePair("sign", request.getSign());
            list.add(digest);
            NameValuePair[] data = list.toArray(new NameValuePair[list.size()]);
            queryString = this.parmUrlEncode(data, request.getCharset());
        }

        PostMethod httpMethod = new PostMethod(request.getUrl());

        // 设置Request默认的header
        // httpMethod.setRequestHeader("Content-type", "text/xml; charset=" +
        // request.getCharset());

        httpMethod.setRequestHeader("Accept-Language", "zh-cn");
        httpMethod.setRequestHeader("Cache-Control", "no-cache");
        if (request.getHeads() != null && request.getHeads().size() > 0) {
            for (String key : request.getHeads().keySet()) {
                String value = request.getHeads().get(key);
                httpMethod.setRequestHeader(key, value);
            }
        }
        queryString = request.getContent().get("content");
        httpMethod.setRequestEntity(
                new StringRequestEntity(queryString, "text/xml" + request.getCharset(), request.getCharset()));

        Part[] parts = {new FilePart(targetFile.getName(), targetFile)};
        httpMethod.setRequestEntity(new MultipartRequestEntity(parts, httpMethod.getParams()));
        return this.execute_(httpMethod, request.getTimeout(), request.getCharset());
    }

    public HttpSendResponse executeHttpGet(HttpSendRequest request) throws Exception {
        if (request == null) {
            throw new Exception("executeHttpGet param exception, request is null");
        }
        String queryString = "";
        if (request.getContent() != null && request.getContent().size() > 0) {
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            List<String> keys = new ArrayList<String>(request.getContent().keySet());

            //神坑，DA的接口居然强制要求顺序。先去掉排序
//            Collections.sort(keys);
            for (String key : keys) {
                String value = request.getContent().get(key);
                NameValuePair nv = new NameValuePair(key, value);
                list.add(nv);
            }
            NameValuePair digest = new NameValuePair("sign", request.getSign());
            list.add(digest);
            NameValuePair[] data = list.toArray(new NameValuePair[list.size()]);
            queryString = this.parmUrlEncode(data, request.getCharset());
        }

        GetMethod httpMethod = new GetMethod(request.getUrl());
        httpMethod.setRequestHeader("Content-type", "application/json; charset=" + request.getCharset());
        httpMethod.setRequestHeader("Accept-Language", "zh-cn");
        httpMethod.setRequestHeader("Cache-Control", "no-cache");

        if (request.getHeads() != null && request.getHeads().size() > 0) {
            for (String key : request.getHeads().keySet()) {
                String value = request.getHeads().get(key);
                httpMethod.setRequestHeader(key, value);
            }
        }
        httpMethod.setQueryString(queryString);

        long startTime = System.nanoTime();
        HttpSendResponse res = this.execute_(httpMethod, request.getTimeout(), request.getCharset());
        long endTime = System.nanoTime();
        System.out.println("excTime=" + TimeUnit.NANOSECONDS.toMillis(endTime - startTime));
        return res;
    }

    private HttpSendResponse execute_(HttpMethod httpMethod, int timeout, String charset) throws Exception {

        HttpSendResponse response = new HttpSendResponse();
        HttpClient client = new HttpClient(); // HttpClient创建
        HttpClientParams clientParams = client.getParams();


        clientParams.setParameter("http.socket.timeout", timeout + 30000); // socket等待数据
        clientParams.setParameter("http.connection.timeout", timeout); // http

        // connection建立超时
        clientParams.setParameter("http.connection-manager.timeout", new Long(timeout)); // 从http

        // connection
        // manager获取可用的Http
        // connection超时
        // clientParams.setParameter("http.method.retry-handler", new
        // DefaultHttpMethodRetryHandler()); // 如果Http出错，三次重连
//        clientParams.setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(1, false)); // 禁用自动重连

        clientParams.setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(1, true)); // 禁用自动重连
        // 设置Response默认的字符集为GBK，防止对方没有返回“Content-type”的header造成解析中文出错
        clientParams.setParameter("http.protocol.content-charset", charset);

        try {
            int status = client.executeMethod(httpMethod);
            response.setResponseStatus(status);

            // 使用getResponseBodyAsString方法会有警告
            // response.setResponseBody(httpMethod.getResponseBodyAsString());

            // 转换inputstream到string 解决 警告: Going to buffer response body of
            // large or unknown size.


            InputStream inputStream = httpMethod.getResponseBodyAsStream();

            BufferedReader br;
            /**
             * inputStream返回值有可能为空  比如status code =204的情况
             */

            if (null != inputStream) {
                br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            } else {
                br = null;
            }
            StringBuffer stringBuffer = new StringBuffer();
            String str = "";

            while (null != br && (str = br.readLine()) != null) {
                stringBuffer.append(str);
            }

            response.setResponseBody(stringBuffer.toString());
            // end
            response.setResponseHeaders(headsToMap(httpMethod.getResponseHeaders()));

        } catch (IOException e) {
            httpMethod.releaseConnection();
            throw e;
        } finally {
            httpMethod.releaseConnection();

        }
        return response;
    }

    private Map<String, String> headsToMap(Header[] headers) {
        Map<String, String> result = new HashMap<String, String>();
        if (null != headers) {
            for (Header header : headers) {
                result.put(header.getName(), header.getValue());
            }
        }
        return result;
    }

}
