package com.jinn.projectx.activity.Activity09.base;

import android.arch.lifecycle.ViewModel;
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
        return ViewModelProviders.of(this).get(onBindViewModel());
    }

    protected abstract Class<VM> onBindViewModel();


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
