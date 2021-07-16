package com.sitbyme.mvvm;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.internal.util.OpenHashSet;

/**
 * @author SitByMe
 * date  : 2021/3/4
 * desc  : viewModel 基类
 */
public class AbsViewModel extends AndroidViewModel implements IUi {
    /**
     * 管理扩展的BaseViewModel，支持调用其他BaseViewModel的方法并支持自动回收
     */
    private OpenHashSet<AbsViewModel> otherViewModelSet;

    /**
     * 管理RxJava，主要针对RxJava异步操作造成的内存泄漏
     */
    private CompositeDisposable compositeDisposable;

    private UiChangeLiveData uc;

    public AbsViewModel(@NonNull Application application) {
        super(application);
    }

    public final void addDisposable(@NonNull Disposable d) {
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

    public final void addViewModel(@NonNull AbsViewModel... vms) {
        if (otherViewModelSet == null) {
            otherViewModelSet = new OpenHashSet<>();
        }
        for (AbsViewModel vm : vms) {
            ObjectHelper.requireNonNull(vm, "BaseViewModel item is null");
            this.otherViewModelSet.add(vm);
        }
    }

    @Override
    public final void showToast(CharSequence text) {
        getUc().getShowToastEvent().postValue(text);
    }

    public final void showLoading() {
        showLoading(new LoadingDataBean());
    }

    public final void showLoading(CharSequence loadingText) {
        showLoading(new LoadingDataBean(loadingText));
    }

    public final void showLoading(boolean outside) {
        showLoading(new LoadingDataBean(outside));
    }

    public final void showLoading(CharSequence loadingText, boolean outside) {
        showLoading(new LoadingDataBean(loadingText, outside));
    }

    @Override
    public final void showLoading(LoadingDataBean loadingData) {
        getUc().getShowDialogEvent().postValue(loadingData);
    }

    @Override
    public final void dismissLoading() {
        getUc().getDismissDialogEvent().call();
    }

    @Override
    public final void startActivity(Class<?> clz, Bundle bundle) {
        Map<String, Object> params = new HashMap<>(16);
        params.put(ParameterField.CLASS, clz);
        if (bundle != null) {
            params.put(ParameterField.BUNDLE, bundle);
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
        super.onCleared();
        synchronized (this) {
            onClear(otherViewModelSet);
        }
        // ViewModel销毁时会执行，同事取消所有异步操作
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    /**
     * BaseViewModel the contents of the OpenHashSet by suppressing non-fatal
     * Throwable till the end.
     *
     * @param set the OpenHashSet to baseViewModel elements of
     */
    void onClear(OpenHashSet<AbsViewModel> set) {
        if (set == null) {
            return;
        }
        List<Throwable> errors = null;
        Object[] array = set.keys();
        for (Object o : array) {
            if (o instanceof AbsViewModel) {
                try {
                    ((AbsViewModel) o).onCleared();
                } catch (Throwable ex) {
                    Exceptions.throwIfFatal(ex);
                    if (errors == null) {
                        errors = new ArrayList<>();
                    }
                    errors.add(ex);
                }
            }
        }
        if (errors != null) {
            if (errors.size() == 1) {
                throw ExceptionHelper.wrapOrThrow(errors.get(0));
            }
            throw new CompositeException(errors);
        }
    }

    public static final class ParameterField {
        public static String CLASS = "CLASS";
        public static String BUNDLE = "BUNDLE";
    }
}
