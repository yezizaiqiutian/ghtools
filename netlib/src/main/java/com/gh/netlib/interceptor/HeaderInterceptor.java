package com.gh.netlib.interceptor;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author: gh
 * @description: 为全局请求添加header
 * @date: 2019-09-17.
 * @from:
 */
public class HeaderInterceptor implements Interceptor {

    private String headerKey = "BASE_URL";
    private Map<String, String> headersMap;

    public HeaderInterceptor(Map<String, String> headersMap) {
        this.headersMap = headersMap;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        // 获取request
        Request request = chain.request();
        if (headersMap == null) {
            return chain.proceed(request);
        }
        Set set = headersMap.entrySet();
        Iterator i = set.iterator();
        Request.Builder xmlHttpRequest = chain.request().newBuilder();

        while(i.hasNext()){
            Map.Entry<String, String> entry1=(Map.Entry<String, String>)i.next();
            xmlHttpRequest.addHeader(entry1.getKey(),entry1.getValue());
        }
        request = xmlHttpRequest.build();
        return chain.proceed(request);
    }

}
