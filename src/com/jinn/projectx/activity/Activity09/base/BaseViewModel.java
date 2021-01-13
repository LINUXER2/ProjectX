package com.jinn.projectx.activity.Activity09.base;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

/**
 * Created by jinnlee on 2020/10/13.
 */
public class BaseViewModel<T extends  BaseModel> extends AndroidViewModel {
    protected T mModel;

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    public BaseViewModel(@NonNull Application application,T model){
        super(application);
        mModel = model;
    }

}
