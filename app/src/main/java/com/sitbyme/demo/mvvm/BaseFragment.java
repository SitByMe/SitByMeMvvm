package com.sitbyme.demo.mvvm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;

import com.sitbyme.mvvm.AbsViewModel;
import com.sitbyme.mvvm.AbsVmFragment;
import com.sitbyme.mvvm.LoadingDataBean;

/**
 * @author SitByMe
 * date  : 2021/7/17
 * desc  : fragment 基类
 */
public abstract class BaseFragment<DB extends ViewDataBinding, VM extends AbsViewModel> extends AbsVmFragment<DB, VM> {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return binding.getRoot();
    }

    @Override
    public void showToast(CharSequence text) {
        Toast.makeText(act, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading(LoadingDataBean loadingData) {
        Toast.makeText(act, "show loading", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void dismissLoading() {
        Toast.makeText(act, "dismiss loading", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startActivity(Class<?> clz, Bundle extras) {

    }

    @Override
    public void onBackPressed() {

    }
}
