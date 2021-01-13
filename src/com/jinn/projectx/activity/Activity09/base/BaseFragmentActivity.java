package com.jinn.projectx.activity.Activity09.base;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

/**
 * Created by jinnlee on 2020/10/14.
 */
public abstract class BaseFragmentActivity <VM extends ViewModel>extends FragmentActivity {
    protected VM mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected VM createViewModel(){
     //   return ViewModelProviders.of(this).get(onBindViewModel());
        return ViewModelProviders.of(this,onBindViewModelFactory()).get(onBindViewModel());
        //第二个参数作用是让viewModel的构造函数与model对应,由于在get ViewModel时只会走默认的构造函数，model无处赋值
    }

    protected abstract Class<VM> onBindViewModel();

    protected abstract ViewModelProvider.Factory onBindViewModelFactory();


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
