package com.sitbyme.mvvm;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author SitByMe
 * date  : 2021/3/4
 * desc  : viewModel 基类
 */
public class AbsViewModel extends AndroidViewModel implements IUi {

    /**
     * 管理RxJava，主要针对RxJava异步操作造成的内存泄漏
     */
    private CompositeDisposable compositeDisposable;

    private IUi uiCallback;

    public void setUiCallback(IUi uiCallback) {
        this.uiCallback = uiCallback;
    }

    public AbsViewModel(@NonNull Application application) {
        super(application);
    }

    protected final void addDisposable(@NonNull Disposable d) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(d);
    }

    @Override
    public final void showToast(CharSequence text) {
        if (uiCallback != null) {
            uiCallback.showToast(text);
        }
    }

    @Override
    public final void showLoading(LoadingDataBean loadingData) {
        if (uiCallback != null) {
            uiCallback.showLoading(loadingData);
        }
    }

    @Override
    public final void dismissLoading() {
        if (uiCallback != null) {
            uiCallback.dismissLoading();
        }
    }

    public final void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    @Override
    public final void startActivity(Class<?> clz, Bundle bundle) {
        if (uiCallback != null) {
            uiCallback.startActivity(clz, bundle);
        }
    }

    @Override
    public void resultAct(int resultCode, Intent data) {
        if (uiCallback != null) {
            uiCallback.resultAct(resultCode, data);
        }
    }

    @Override
    public void finishAct() {
        if (uiCallback != null) {
            uiCallback.finishAct();
        }
    }

    @Override
    public void onBackPressed() {
        if (uiCallback != null) {
            uiCallback.onBackPressed();
        }
    }

    @Override
    protected void onCleared() {
        // ViewModel销毁时会执行，同事取消所有异步操作
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
        super.onCleared();
    }
}
