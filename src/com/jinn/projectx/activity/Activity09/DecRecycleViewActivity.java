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
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.security.Permission;
import java.util.ArrayList;

public class DecRecycleViewActivity extends Activity {

    private RecyclerView mRecycleView;
    private final String url = "https://smartboard.vivo.com.cn/card/info/v1?";

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
        getDataAsync();
        adapter.setData(lists);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView.setAdapter(adapter);
    }

    private void requestPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(DecRecycleViewActivity.this,new String[]{Manifest.permission.ACCESS_NETWORK_STATE},1);
        }
    }

    private void getDataAsync() {
        OkHttpClient client = new OkHttpClient();
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
                    Logit.d("jinn", "onResponse:" + response.toString());
                }
            });
        } catch (Exception e) {
            Logit.d("jinn", e.toString());
        }

    }

}
