package com.sitbyme.mvvm;

import android.content.DialogInterface;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author SitByMe
 * date  : 2021/3/4
 * desc  : loading ui data
 */
public class LoadingDataBean {
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({LOADING_ACTION.DISMISS, LOADING_ACTION.SHOW})
    @interface LOADING_ACTION {
        int DISMISS = -1;
        int SHOW = 0;
    }

    @LOADING_ACTION
    private final int loadingAction;
    private CharSequence loadingText;
    private boolean showCancelable;
    private boolean outside;
    private DialogInterface.OnShowListener listener;

    public LoadingDataBean(@LOADING_ACTION int loadingAction) {
        this.loadingAction = loadingAction;
    }

    public static LoadingDataBean createShow() {
        return new LoadingDataBean(LOADING_ACTION.SHOW);
    }

    public static LoadingDataBean createDismiss() {
        return new LoadingDataBean(LOADING_ACTION.DISMISS);
    }

    /**
     * 是否是show模式
     */
    public boolean isShowType() {
        return loadingAction == LOADING_ACTION.SHOW;
    }

    public CharSequence getLoadingText() {
        return loadingText;
    }

    public LoadingDataBean setLoadingText(CharSequence loadingText) {
        this.loadingText = loadingText;
        return this;
    }

    public boolean isShowCancelable() {
        return showCancelable;
    }

    public LoadingDataBean setShowCancelable(boolean showCancelable) {
        this.showCancelable = showCancelable;
        return this;
    }

    public boolean isOutside() {
        return outside;
    }

    public LoadingDataBean setOutside(boolean outside) {
        this.outside = outside;
        return this;
    }

    public LoadingDataBean setListener(DialogInterface.OnShowListener listener) {
        this.listener = listener;
        return this;
    }

    public DialogInterface.OnShowListener getListener() {
        return listener;
    }
}
