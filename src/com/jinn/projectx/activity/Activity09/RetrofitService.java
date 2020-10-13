package com.jinn.projectx.activity.Activity09;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by jinnlee on 2020/10/9.
 */
public interface RetrofitService {
    @GET("v2/feed?")
    Call<ResponseBody> getInfo(@Query("key")String key);

    @POST("v2/feed")
    @FormUrlEncoded
    Call<ResponseBody> postInfo(@FieldMap Map<String,Object>map);


    @GET("v2/feed?")
    Observable<ResponseBody> getInfo2(@Query("key")String key);
}
