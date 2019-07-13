package com.gh.ghtools;

import com.gh.netlib.api.BaseResultEntity;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 测试接口service-post相关
 * Created by WZG on 2016/12/19.
 */

public interface HttpPostService {
//    @POST("AppFiftyToneGraph/videoLink")
//    Call<RetrofitEntity> getAllVedio(@Body boolean once_no);
//
//    @POST("AppFiftyToneGraph/videoLink")
//    Observable<RetrofitEntity> getAllVedioBy(@Body boolean once_no);

    @FormUrlEncoded
    @POST("AppFiftyToneGraph/videoLink")
    Flowable<BaseResultEntity<List<SubjectResulte>>> getAllVedioBys(@Field("once") boolean once_no);

}
