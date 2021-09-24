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
        viewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(getViewModelClass());
        //私有的ViewModel与View的契约事件回调逻辑
        viewModel.setUiCallback(this);
        binding = DataBindingUtil.inflate(getLayoutInflater(), getLayoutId(), null, false);
        if (binding != null) {
            binding.setLifecycleOwner(this);
        } else {
            throw new RuntimeException(getClass().getName() + " binding is null");
        }
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

    @Override
    public void startActivity(Class<?> clz, Bundle extras) {
        Intent intent = new Intent(AbsVmActivity.this.getApplicationContext(), clz);
        if (extras != null) {
            intent.putExtras(extras);
        }
        startActivity(intent);
    }

    @Override
    public void resultAct(int resultCode, Intent data) {
        if (data == null) {
            setResult(resultCode);
        } else {
            setResult(resultCode, data);
        }
    }

    @Override
    public void finishAct() {
        finish();
    }
}
