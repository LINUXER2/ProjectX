package com.jinn.projectx.activity.Activity09.viewModel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.jinn.projectx.activity.Activity09.base.BaseViewModel;
import com.jinn.projectx.activity.Utils.Logit;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

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

    public void getHomeData(){
        mModel.requestHomeData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        Logit.d("jinn",responseBody.string());
                        List<String>list = new ArrayList<>();
                        list.add("123");
                        list.add("456");
                        lists.postValue(list);
                    }
                });
    }

    public MutableLiveData<List<String>> getLists(){
        return lists;
    }

}
