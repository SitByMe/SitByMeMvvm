package com.sitbyme.mvvm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

/**
 * @author SitByMe
 * date  : 2021/3/4
 * desc  : vm fragment 基类
 */
public abstract class AbsVmFragment<DB extends ViewDataBinding, VM extends AbsViewModel> extends Fragment implements IUi {
    protected FragmentActivity act;

    protected DB binding;
    public VM viewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        act = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(this.requireActivity().getApplication())).get(getViewModelClass());
        //私有的ViewModel与View的契约事件回调逻辑
        registerUiChangeLiveData();
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), null, false);
        if (binding != null) {
            binding.setLifecycleOwner(this);
            return binding.getRoot();
        } else {
            throw new RuntimeException(getClass().getName() + " binding is null");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
        //加载对话框显示/隐藏
        viewModel.getUc().getLoadingDialogEvent()
                .observe(this, loadingDataBean -> {
                    if (loadingDataBean != null && loadingDataBean.isShowType()) {
                        showLoading(loadingDataBean);
                    } else {
                        dismissLoading();
                    }
                });
        //弹出Toast
        viewModel.getUc().getShowToastEvent().observe(this, this::showToast);
        //跳入新页面
        viewModel.getUc().getStartActivityEvent().observe(this,
                params -> {
                    if (params == null) {
                        showToast("启动页面参数为空！");
                    } else {
                        Class<?> clz = (Class<?>) params.get(AbsViewModel.ParameterField.CLASS);
                        Bundle extras = (Bundle) params.get(AbsViewModel.ParameterField.EXTRAS);
                        startActivity(clz, extras);
                    }
                });
        //关闭界面
        viewModel.getUc().getFinishEvent().observe(this, aVoid -> finish());
        //关闭上一层
        viewModel.getUc().getOnBackPressedEvent().observe(this, aVoid -> onBackPressed());
    }

    @Override
    public void startActivity(Class<?> clz, Bundle extras) {
        Intent intent = new Intent(getActivity(), clz);
        if (extras != null) {
            intent.putExtras(extras);
        }
        startActivity(intent);
    }

    @Override
    public void finish() {
        act.finish();
    }
}
