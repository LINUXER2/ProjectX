package com.jinn.projectx.activity.Activity09.viewModel;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by jinnlee on 2021/1/13.
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    private static ViewModelFactory sInstance;
    private Application mApplication;

    private ViewModelFactory(Application context){
        mApplication = context;
    };

    public static ViewModelFactory getInstance(Application application){
        if(sInstance == null ){
            synchronized (ViewModelFactory.class){
                if(sInstance==null){
                    sInstance = new ViewModelFactory(application);
                }
            }
        }
        return sInstance;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(HomeViewModel.class)){
           return (T) new HomeViewModel(mApplication,new HomeModel());
        }
        return null;
    }
}
