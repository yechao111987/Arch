package com.demo.yechao.arch.utils;

import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Author yechao111987@126.com
 * @date 2018/7/17 10:36
 */
public class Base64Img {

    /**
     * @param imgURL 网络资源位置
     * @return Base64字符串
     * @Title: GetImageStrFromUrl
     * @Description: TODO(将一张网络图片转化成Base64字符串)
     */
    public static String GetImageStrFromUrl(String imgURL) {
        byte[] data = null;
        try {
            // 创建URL
            URL url = new URL(imgURL);
            // 创建链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            InputStream inStream = conn.getInputStream();
            data = new byte[inStream.available()];
            inStream.read(data);
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 返回Base64编码过的字节数组字符串
        return Base64.encodeBase64String(data);
    }

    /**
     * @param imgPath
     * @return
     * @Title: GetImageStrFromPath
     * @Description: TODO(将一张本地图片转化成Base64字符串)
     */
    public static String GetImageStrFromPath(String imgPath) {
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(imgPath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 返回Base64编码过的字节数组字符串
        return Base64.encodeBase64String(data);
    }

    /**
     * @param imgStr
     * @return
     * @Title: GenerateImage
     * @Description: TODO(base64字符串转化成图片)
     */
    public static boolean GenerateImage(String imgStr) {
        if (imgStr == null) // 图像数据为空
            return false;
        try {
            // Base64解码
            byte[] b = Base64.decodeBase64(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            // 生成jpeg图片
            String imgFilePath = "d://222.jpg";
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
