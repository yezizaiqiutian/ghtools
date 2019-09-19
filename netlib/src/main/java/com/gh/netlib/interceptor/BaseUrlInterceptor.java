package com.gh.netlib.interceptor;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author: gh
 * @description:
 * @date: 2019-09-17.
 * @from:
 */
public class BaseUrlInterceptor implements Interceptor {

    private String headerKey = "BASE_URL";

    @Override
    public Response intercept(Chain chain) throws IOException {
        // 获取request
        Request request = chain.request();
        // 从request中获取原有的HttpUrl实例oldHttpUrl
        HttpUrl oldHttpUrl = request.url();
        // 获取request的创建者builder
        Request.Builder builder = request.newBuilder();
        // 从request中获取headers，通过给定的键url_name
        List<String> headerValues = request.headers(headerKey);
        if (headerValues != null && headerValues.size() > 0) {
            // 如果有这个header，先将配置的header删除，因此header仅用作app和okhttp之间使用
            builder.removeHeader(headerKey);
            builder.removeHeader("User-Agent");
            builder.addHeader("User-Agent", "");
            // 匹配获得新的BaseUrl
            String headerValue = headerValues.get(0);
            HttpUrl newBaseUrl = null;
            newBaseUrl = HttpUrl.parse(headerValue);
            // 重建新的HttpUrl，修改需要修改的url部分
            HttpUrl newFullUrl = oldHttpUrl
                    .newBuilder()
                    // 更换网络协议
                    .scheme(newBaseUrl.scheme())
                    // 更换主机名
                    .host(newBaseUrl.host())
                    // 更换端口
                    .port(newBaseUrl.port())
                    .build();
            // 重建这个request，通过builder.url(newFullUrl).build()；
            // 然后返回一个response至此结束修改

            return chain
                    .withConnectTimeout(2, TimeUnit.SECONDS)
                    .withReadTimeout(2, TimeUnit.SECONDS)
                    .withWriteTimeout(2, TimeUnit.SECONDS)
                    .proceed(builder.url(newFullUrl).build());
        }
        return chain.proceed(request);
    }

}
