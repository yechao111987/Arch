package com.demo.yechao.arch.utils.httpclient;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

import java.util.List;

/**
 * @author hztanhuayou
 * @date 2017/12/4
 */
public interface CookieStore {

    void add(HttpUrl uri, List<Cookie> cookie);

    List<Cookie> get(HttpUrl uri);

    List<Cookie> getCookies();

    boolean remove(HttpUrl uri, Cookie cookie);

    boolean removeAll();

}
