package com.zhifeng.wineculture.net.service;


import com.lgh.huanglib.retrofitlib.Api.BaseResultEntity;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
* 
* @author lgh
* created at 2019/5/13 0013 14:38
*/
public interface HttpPostService {

//
//    @GET(WebUrlUtil.GET_ABOUT)
//    Observable<BaseResultEntity> getAbout();
//
//    @GET(WebUrlUtil.GET_ABOUT)
//    Observable<String> getAboutString();
//
//    @GET(WebUrlUtil.GET_ABOUT)
//    Call<BaseResultEntity> getAboutByCall();
//
//    @GET(WebUrlUtil.URL_GET_MAIN)
//    Observable<BaseResultEntity> getHome();


    /**
     * POST请求
     * @param url
     * @return
     */
    @POST
    Observable<BaseResultEntity> PostData(@Url String url);
    @POST
    Observable<BaseResultEntity> PostData(@Body Map<Object, Object> body, @Url String url);
    @POST
    Observable<BaseResultEntity> PostData(@Body RequestBody body, @Url String url);

    /**
     * GET请求
     * @param url
     * @return
     */
    @GET
    Observable<BaseResultEntity> GetData(@Url String url);

    /**
     * 带code的get请求
     * @param id
     * @param url
     * @return
     */
    @GET
    Observable<BaseResultEntity> GetData(@Url String url, @Query("code") int id);



}
