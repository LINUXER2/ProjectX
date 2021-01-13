package com.jinn.projectx.activity.Activity09.viewModel;

import com.jinn.projectx.activity.Activity09.RetrofitService;
import com.jinn.projectx.activity.Activity09.base.BaseModel;
import com.jinn.projectx.activity.Utils.Logit;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jinnlee on 2020/10/13.
 * 负责获取数据
 */
public class HomeModel extends BaseModel {
    private String url = "https://baobab.kaiyanapp.com/api/";

    /**
     * 使用retrofit，返回值為Observable
     * @return
     */
    public Observable<ResponseBody> requestHomeDataObservable(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())    //區別就是這裏，决定返回值是observalbe还是call，如果返回值为call可以不加该配置
                .build();
        RetrofitService service = retrofit.create(RetrofitService.class);
        return service.getHomeDataObservable("123");
    }

    /**
     * 使用retrofit，get请求，返回值为call
     */
    public retrofit2.Call requestHomeDataCall(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitService service = retrofit.create(RetrofitService.class);
        return  service.getHomeDataCall("123");
    }

    /**
     * 使用retrofit，post请求，返回值为call
     */
    private retrofit2.Call postHomeDataCall(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitService service = retrofit.create(RetrofitService.class);
        Map<String,Object> map  = new HashMap<>();
        map.put("key","123");
        return service.postHomeDataCall(map);
    }


    /**
     * OKhttp获取数据,get请求
     */
    private void getDataAsync() {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(10, TimeUnit.SECONDS);
        client.setReadTimeout(10,TimeUnit.SECONDS);
        client.setWriteTimeout(10,TimeUnit.SECONDS);
        Request request = new Request.Builder().url(url).get().build();
        Call call = client.newCall(request);
        try {
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    Logit.d("jinn", "onFailure:" + request.toString());
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    Logit.d("jinn", "onResponse:" + response.body().string());
                }
            });
        } catch (Exception e) {
            Logit.d("jinn", e.toString());
        }
    }

    /**
     *  OkHttp获取数据，post请求
     */
    private void postDataAsync(){
        OkHttpClient client =  new OkHttpClient();
        try{
            JSONObject object = new JSONObject();
            object.put("modle","1");
            RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"),object.toString());
            Request request = new Request.Builder().url(url).post(body).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    Logit.d("jinn","onFailure:"+request.body().toString());
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    Logit.d("jinn","onResponse:"+response.body().string());
                }
            });
        }catch (JSONException e){
            Logit.d("jinn", e.toString());
        }
    }
}
