package com.sitbyme.mvvm;

import java.util.Map;

/**
 * @author SitByMe
 * date  : 2021/3/4
 * desc  : ViewModel 中需要调用 View 中方法的 liveData
 */
class UiChangeLiveData extends SingleLiveEvent<Void> {
    private SingleLiveEvent<CharSequence> showToastEvent;
    private SingleLiveEvent<LoadingDataBean> loadingDialogEvent;
    private SingleLiveEvent<Map<String, Object>> startActivityEvent;
    @Deprecated
    private SingleLiveEvent<Map<String, Object>> startContainerActivityEvent;
    private SingleLiveEvent<Void> finishEvent;
    private SingleLiveEvent<Void> onBackPressedEvent;

    public SingleLiveEvent<CharSequence> getShowToastEvent() {
        return showToastEvent = createLiveData(showToastEvent);
    }

    public SingleLiveEvent<LoadingDataBean> getLoadingDialogEvent() {
        return loadingDialogEvent = createLiveData(loadingDialogEvent);
    }

    public SingleLiveEvent<Map<String, Object>> getStartActivityEvent() {
        return startActivityEvent = createLiveData(startActivityEvent);
    }

    @Deprecated
    public SingleLiveEvent<Map<String, Object>> getStartContainerActivityEvent() {
        return startContainerActivityEvent = createLiveData(startContainerActivityEvent);
    }

    public SingleLiveEvent<Void> getFinishEvent() {
        return finishEvent = createLiveData(finishEvent);
    }

    public SingleLiveEvent<Void> getOnBackPressedEvent() {
        return onBackPressedEvent = createLiveData(onBackPressedEvent);
    }

    private <T> SingleLiveEvent<T> createLiveData(SingleLiveEvent<T> liveData) {
        if (liveData == null) {
            liveData = new SingleLiveEvent<T>();
        }
        return liveData;
    }
}
