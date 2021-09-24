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
        viewModel.setUiCallback(this);
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


    @Override
    public void startActivity(Class<?> clz, Bundle extras) {
        Intent intent = new Intent(getActivity(), clz);
        if (extras != null) {
            intent.putExtras(extras);
        }
        startActivity(intent);
    }

    @Override
    public void resultAct(int resultCode, Intent data) {
        if (data == null) {
            act.setResult(resultCode);
        } else {
            act.setResult(resultCode, data);
        }
    }

    @Override
    public void finishAct() {
        act.finish();
    }
}
