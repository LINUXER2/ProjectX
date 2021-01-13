package com.jinn.projectx.activity.Activity09;

import android.Manifest;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jinn.projectx.R;
import com.jinn.projectx.activity.Activity09.base.BaseFragmentActivity;
import com.jinn.projectx.activity.Activity09.viewModel.HomeModel;
import com.jinn.projectx.activity.Activity09.viewModel.HomeViewModel;
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
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class DecRecycleViewActivity  extends BaseFragmentActivity <HomeViewModel>{

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
        adapter.setData(lists);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView.setAdapter(adapter);
        mViewModel  =  createViewModel();

        mViewModel.getLists().observe(this, new android.arch.lifecycle.Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {
                Logit.d("jinn","onChanged:"+strings.toString());
            }
        });
        mViewModel.getHomeData();
    }



    @Override
    protected Class onBindViewModel() {
        return HomeViewModel.class;
    }

    private void requestPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(DecRecycleViewActivity.this,new String[]{Manifest.permission.ACCESS_NETWORK_STATE},1);
        }
    }



}
