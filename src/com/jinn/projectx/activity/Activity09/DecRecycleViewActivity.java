package com.jinn.projectx.activity.Activity09;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jinn.projectx.R;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class DecRecycleViewActivity extends Activity {

    private RecyclerView mRecycleView;
    private final String url = "http://baobab.kaiyanapp.com/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_09);
        mRecycleView = (RecyclerView) findViewById(R.id.recycle_view);
        BaseAdapter adapter = new BaseAdapter(this);
        ArrayList lists = new ArrayList();
        lists.add("text1");
        lists.add("text2");
        lists.add("text3");
        lists.add("text4");
        lists.add("text5");
        lists.add("text6");
        requestPermission();
        getRetrofitDataObservable();
        adapter.setData(lists);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView.setAdapter(adapter);
    }

    private void requestPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(DecRecycleViewActivity.this,new String[]{Manifest.permission.ACCESS_NETWORK_STATE},1);
        }
    }

    /**
     * 使用retrofit，get请求，返回值为call
     */
    private void getRetrofitData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitService service = retrofit.create(RetrofitService.class);
        retrofit2.Call<ResponseBody> call = service.getInfo("123");
        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                Logit.d("jinn","currentThread:"+Thread.currentThread().getName());
                try {
                    Logit.d("jinn","onResponse:"+response.body().string());
                }catch (IOException e){

                }

            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                Logit.i("jinn","onFailure:"+ t.getMessage());
            }
        });

    }

    /**
     * 使用retrofit，post请求，返回值为call
     */
    private void postRetrofitData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitService service = retrofit.create(RetrofitService.class);
        Map<String,Object> map  = new HashMap<>();
        map.put("key","123");
        retrofit2.Call<ResponseBody> call = service.postInfo(map);
        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                Logit.d("jinn","currentThread:"+Thread.currentThread().getName());
                try {
                    Logit.d("jinn","onResponse:"+response.body().string());
                }catch (IOException e){

                }

            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                Logit.i("jinn","onFailure:"+ t.getMessage());
            }
        });
    }


    /**
     * 使用retrofit，返回值为Observable
     */
    private void getRetrofitDataObservable(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())    //區別就是這裏，决定返回值是observalbe还是call，如果返回值为call可以不加该配置
                .build();
        RetrofitService service = retrofit.create(RetrofitService.class);
        service.getInfo2("123")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Logit.d("jinn","onSubscribe:"+d.isDisposed());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try{

                            Logit.d("jinn","responseBody:"+responseBody.string());
                        }catch (IOException e){

                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logit.d("jinn","onError："+e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Logit.d("jinn","onComplete");
                    }
                });
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
