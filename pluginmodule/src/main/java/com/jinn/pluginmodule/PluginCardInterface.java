package com.jinn.pluginmodule;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jinnlee on 2020/4/21.
 */
public interface PluginCardInterface {
    public View getView(Context context);

    public void onMovingInHiboard();

    public void onMovingOutHiBoard();

    public void onCardVisible();

    public void onCardInvisible();

    public void onScrollStart();

    public void onScrollStop();

    public boolean initTitleView(ViewGroup customArea);

    public void onKeyguardLocked(boolean isLocked);

    public void onDismissResult(int result);

    public void onPrivacyChange(boolean isPrivacyOpen);


}
