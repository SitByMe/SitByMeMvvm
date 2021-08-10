package com.sitbyme.mvvm;

import android.content.DialogInterface;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author SitByMe
 * date  : 2021/3/4
 * desc  : loading ui data
 */
public class LoadingDataBean {
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ACTION.DISMISS, ACTION.SHOW})
    @interface ACTION {
        int DISMISS = -1;
        int SHOW = 0;
    }

    @ACTION
    private final int action;
    private CharSequence loadingText;
    private boolean showCancelable;
    private boolean outside;
    private DialogInterface.OnShowListener listener;

    private LoadingDataBean(@ACTION int action) {
        this.action = action;
    }

    /**
     * 是否是show模式
     */
    public boolean isShowType() {
        return action == ACTION.SHOW;
    }

    public CharSequence getLoadingText() {
        return loadingText;
    }

    public boolean isShowCancelable() {
        return showCancelable;
    }

    public boolean isOutside() {
        return outside;
    }

    public DialogInterface.OnShowListener getListener() {
        return listener;
    }

    public static class Creator {
        @ACTION
        private final int action;
        private CharSequence loadingText;
        private boolean showCancelable;
        private boolean outside;
        private DialogInterface.OnShowListener listener;

        private Creator(@ACTION int action) {
            this.action = action;
        }

        public static Creator createShowAction() {
            return new Creator(ACTION.SHOW);
        }

        public static Creator createDismissAction() {
            return new Creator(ACTION.DISMISS);
        }

        public Creator setLoadingText(CharSequence loadingText) {
            this.loadingText = loadingText;
            return this;
        }

        public Creator setShowCancelable(boolean showCancelable) {
            this.showCancelable = showCancelable;
            return this;
        }

        public Creator setOutside(boolean outside) {
            this.outside = outside;
            return this;
        }

        public Creator setListener(DialogInterface.OnShowListener listener) {
            this.listener = listener;
            return this;
        }

        public LoadingDataBean create() {
            LoadingDataBean loadingDataBean = new LoadingDataBean(action);
            loadingDataBean.loadingText = loadingText;
            loadingDataBean.showCancelable = showCancelable;
            loadingDataBean.outside = outside;
            loadingDataBean.listener = listener;
            return loadingDataBean;
        }
    }
}
