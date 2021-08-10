package com.sitbyme.mvvm;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.HashMap;
import java.util.Map;

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

    private UiChangeLiveData uc;

    public AbsViewModel(@NonNull Application application) {
        super(application);
    }

    protected final void addDisposable(@NonNull Disposable d) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(d);
    }

    UiChangeLiveData getUc() {
        if (uc == null) {
            uc = new UiChangeLiveData();
        }
        return uc;
    }

    @Override
    public final void showToast(CharSequence text) {
        getUc().getShowToastEvent().postValue(text);
    }

    @Override
    public final void showLoading(LoadingDataBean loadingData) {
        getUc().getLoadingDialogEvent().postValue(loadingData);
    }

    @Override
    public final void dismissLoading() {
        getUc().getLoadingDialogEvent().postValue(LoadingDataBean.Creator.createDismissAction().create());
    }

    @Override
    public final void startActivity(Class<?> clz, Bundle bundle) {
        Map<String, Object> params = new HashMap<>(16);
        params.put(ParameterField.CLASS, clz);
        if (bundle != null) {
            params.put(ParameterField.EXTRAS, bundle);
        }
        getUc().getStartActivityEvent().postValue(params);
    }

    @Override
    public void finish() {
        getUc().getFinishEvent().call();
    }

    @Override
    public void onBackPressed() {
        getUc().getOnBackPressedEvent().call();
    }

    @Override
    protected void onCleared() {
        // ViewModel销毁时会执行，同事取消所有异步操作
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
        super.onCleared();
    }

    public static final class ParameterField {
        public static String CLASS = "CLASS";
        public static String EXTRAS = "BUNDLE";
    }
}
