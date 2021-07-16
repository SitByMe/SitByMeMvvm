package com.sitbyme.demo.mvvm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.sitbyme.demo.mvvm.databinding.ActivityMainBinding;
import com.sitbyme.mvvm.AbsViewModel;
import com.sitbyme.mvvm.AbsVmActivity;

public class MainActivity extends BaseActivity<ActivityMainBinding, AbsViewModel> {

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected Class<AbsViewModel> getViewModelClass() {
        return AbsViewModel.class;
    }
}