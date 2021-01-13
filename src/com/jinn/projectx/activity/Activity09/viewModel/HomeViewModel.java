package com.jinn.projectx.activity.Activity09.viewModel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jinn.projectx.activity.Activity09.base.BaseViewModel;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jinnlee on 2020/10/13.
 * 业务逻辑处理，负责更新数据，持有model，并监听model数据变化，回调给view
 */
public class HomeViewModel extends BaseViewModel<HomeModel> {
    private MutableLiveData<List<String>> lists = new MutableLiveData();

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    public HomeViewModel(@NonNull Application application, HomeModel model) {
        super(application, model);
        mModel = model;
    }


    /**
     *  获取主页数据，以Observable形式
     */
    public void getHomeData1(){
        mModel.requestHomeDataObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                      Log.d("jinn","HomeViewModel->onSubscribe");
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try{
                            String result = responseBody.string();
                            Log.d("jinn","HomeViewModel->onNext,"+result);
                        }catch (IOException e){

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("jinn","HomeViewModel->onError,"+e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.d("jinn","HomeViewModel->onComplete");
                    }
                });
    }


    /**
     * 获取主页数据，以call形式
     */
    public void getHomeData2(){
        mModel.requestHomeDataCall().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.d("jinn","HomeViewModel->onResponse:"+response.body().string());
                }catch (IOException e){

                }

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("jinn","HomeViewModel->onFail,"+t.toString());
            }
        });
    }


    public MutableLiveData<List<String>> getLists(){
        return lists;
    }

}
