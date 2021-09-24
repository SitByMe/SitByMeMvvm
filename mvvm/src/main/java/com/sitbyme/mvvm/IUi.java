package com.sitbyme.mvvm;

import android.content.Intent;
import android.os.Bundle;

/**
 * @author SitByMe
 * date  : 2021/3/4
 * desc  : The activity or fragment does this interface with its own viewmodel, and the same method can be invoked in both.
 */
interface IUi {
    /**
     * show toast
     *
     * @param text content
     */
    void showToast(CharSequence text);

    /**
     * show loading ui
     *
     * @param loadingData loading ui data
     */
    void showLoading(LoadingDataBean loadingData);

    /**
     * dismiss loading ui
     */
    void dismissLoading();

    /**
     * start activity
     *
     * @param clz    target activity
     * @param extras extras
     */
    void startActivity(Class<?> clz, Bundle extras);

    /**
     * setResult
     *
     * @param resultCode resultCode
     * @param data       intent data
     */
    void resultAct(int resultCode, Intent data);

    /**
     * finish activity
     */
    void finishAct();

    /**
     * back pressed
     */
    void onBackPressed();
}
