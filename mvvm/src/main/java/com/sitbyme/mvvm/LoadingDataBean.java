package com.sitbyme.mvvm;

import android.content.DialogInterface;

/**
 * @author SitByMe
 * date  : 2021/3/4
 * desc  : loading ui data
 */
public class LoadingDataBean {
    private CharSequence loadingText;
    private boolean showCancelable;
    private boolean outside;
    private DialogInterface.OnShowListener listener;

    public LoadingDataBean() {
    }

    public LoadingDataBean(CharSequence loadingText) {
        this.loadingText = loadingText;
    }

    public LoadingDataBean(boolean outside) {
        this.outside = outside;
    }

    public LoadingDataBean(CharSequence loadingText, boolean outside) {
        this.loadingText = loadingText;
        this.outside = outside;
    }

    public LoadingDataBean(CharSequence loadingText, boolean showCancelable, boolean outside, DialogInterface.OnShowListener listener) {
        this.loadingText = loadingText;
        this.showCancelable = showCancelable;
        this.outside = outside;
        this.listener = listener;
    }

    public CharSequence getLoadingText() {
        return loadingText;
    }

    public boolean isShowCancelable() {
        return showCancelable;
    }

    public boolean isOutside() {
        return outside;
    }

    public DialogInterface.OnShowListener getListener() {
        return listener;
    }
}
