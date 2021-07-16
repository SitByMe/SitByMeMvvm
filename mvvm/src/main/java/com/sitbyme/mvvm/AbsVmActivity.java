package com.sitbyme.mvvm;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

/**
 * @author SitByMe
 * date  : 2021/3/4
 * desc  : vm activity 基类
 */
public abstract class AbsVmActivity<DB extends ViewDataBinding, VM extends AbsViewModel> extends AppCompatActivity implements IUi {
    public DB binding;
    public VM viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), getLayoutId(), null, false);
        viewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(getViewModelClass());
        binding.setLifecycleOwner(this);
        //私有的ViewModel与View的契约事件回调逻辑
        registerUiChangeLiveData();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (binding != null) {
            binding.unbind();
        }
    }

    /**
     * 配置 layout id
     *
     * @return 布局 layout 的 id
     */
    @LayoutRes
    protected abstract int getLayoutId();

    /**
     * 指定 ViewModel 的类型
     *
     * @return the class type of absViewModel
     */
    protected abstract Class<VM> getViewModelClass();

    /**
     * 私有的ViewModel与View的契约事件回调逻辑
     */
    private void registerUiChangeLiveData() {
        //加载对话框显示
        viewModel.getUc().getShowDialogEvent().observe(this, this::showLoading);
        //加载对话框消失
        viewModel.getUc().getDismissDialogEvent().observe(this, aVoid -> dismissLoading());
        //弹出Toast
        viewModel.getUc().getShowToastEvent().observe(this, this::showToast);
        //跳入新页面
        viewModel.getUc().getStartActivityEvent().observe(this,
                params -> {
                    if (params == null) {
                        showToast("启动页面参数为空！");
                    } else {
                        Class<?> clz = (Class<?>) params.get(AbsViewModel.ParameterField.CLASS);
                        Bundle bundle = (Bundle) params.get(AbsViewModel.ParameterField.BUNDLE);
                        Intent intent = new Intent(AbsVmActivity.this.getApplicationContext(), clz);
                        if (bundle != null) {
                            intent.putExtras(bundle);
                        }
                        startActivity(intent);
                    }
                });
        //关闭界面
        viewModel.getUc().getFinishEvent().observe(this, aVoid -> finish());
        //关闭上一层
        viewModel.getUc().getOnBackPressedEvent().observe(this, aVoid -> onBackPressed());
    }
}
