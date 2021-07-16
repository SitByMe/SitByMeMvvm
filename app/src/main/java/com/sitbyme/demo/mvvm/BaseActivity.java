package com.sitbyme.demo.mvvm;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;

import com.sitbyme.mvvm.AbsViewModel;
import com.sitbyme.mvvm.AbsVmActivity;
import com.sitbyme.mvvm.LoadingDataBean;

/**
 * @author SitByMe
 * date  : 2021/7/17
 * desc  :
 */
public abstract class BaseActivity<DB extends ViewDataBinding, VM extends AbsViewModel> extends AbsVmActivity<DB, VM> {

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup rootView = (ViewGroup) ViewGroup.inflate(this, R.layout.activity_base, null);
        rootView.addView(binding.getRoot());
        setContentView(rootView);
        initView(savedInstanceState);
    }

    /**
     * 初始化界面
     */
    protected abstract void initView(@Nullable Bundle savedInstanceState);

    @Override
    public void showToast(CharSequence text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading(LoadingDataBean loadingData) {
        Toast.makeText(this, "show loading", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void dismissLoading() {
        Toast.makeText(this, "dismiss loading", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startActivity(Class<?> clz, Bundle extras) {
        Intent intent = new Intent(this, clz);
        if (extras != null) {
            intent.putExtras(extras);
        }
        startActivity(intent);
    }
}
