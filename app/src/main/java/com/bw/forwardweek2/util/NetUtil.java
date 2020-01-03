package com.bw.forwardweek2.util;


import com.bw.forwardweek2.model.Api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 1、单例
 * 2、构造器中，create出api   ps：细节： 拦截器、超时、baseurl、支持Gson、支持Rxjava
 * 3、getApi
 */
public class NetUtil {


    // TODO: 2020/1/2 这个baseurl必须修改
    private static final String BASE_URL = "http://172.17.8.100/";
    private final Api api;

    //单例 私有的构造器
    private NetUtil() {
        //拦截器
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //OkhttpClient
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                //设置三个超时
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                //日至拦截器添加
                .addInterceptor(httpLoggingInterceptor)
                .build();

        //Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                //公共Url
                .baseUrl(BASE_URL)
                //ok平台
                .client(okHttpClient)
                //支持Gson
                .addConverterFactory(GsonConverterFactory.create())
                //支持Rxjava
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        // TODO: 2020/1/3 终极目标，create 的出api
        api = retrofit.create(Api.class);
    }

    //单例 geti
    public static NetUtil getInstance() {
        return SingleHolder.NET_UTIL;
    }

    //单例 静态内部类 持有单例对象
    private static final class SingleHolder {
        private static final NetUtil NET_UTIL = new NetUtil();
    }


    // TODO: 2020/1/3 终极目标，拿到api
    public Api getApi() {
        return api;
    }
}
