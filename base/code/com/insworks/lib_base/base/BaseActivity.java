package com.insworks.lib_base.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.inswork.lib_cloudbase.R;
import com.insworks.lib_base.utils.ActivityUtil;
import com.insworks.lib_base.utils.BroadcastUtil;
import com.insworks.lib_base.utils.SystemBarTintManager;
import com.insworks.lib_base.utils.ToastUtil;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;

import java.util.Random;


/**
 * activity基类 Created by Robin on 15/6/27.
 */
public abstract class BaseActivity extends RxAppCompatActivity implements OnClickListener {
    public Context mContext;
    protected SystemBarTintManager tintManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initUtils();
        if (getLayoutResId() != 0) {
            setContentView(getLayoutResId());
            if (isStatusBarTintEnable()) {
                initStatusBar();
            }
            contentViewfilled();
        } else {
            throw new IllegalArgumentException("You must return a right contentView layout resource Id");
        }
        initView(savedInstanceState);
        onViewCreated();
        setListener();
        Intent intent = getIntent();
        initData(intent, savedInstanceState);
        onDataFilled();
    }

    /**
     * initData()方法结束后调用
     */
    protected abstract void onDataFilled();

    /**
     * initView()方法结束后调用方法
     */
    protected abstract void onViewCreated();

    /**
     * setContentView调用结束后执行该方法  可以在该方法里面设置ButterKnife或者状态栏
     */
    protected abstract void contentViewfilled();

    protected abstract boolean isStatusBarTintEnable();

    private void initUtils() {
        ActivityUtil.init(this);
        ToastUtil.init(this);
        BroadcastUtil.init(this);
        initUtil();
    }

    protected abstract void initUtil();

    /**
     * 设置状态栏颜色
     */
    protected void initStatusBar() {
        tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(getStatusBarTintRes() == 0 ? R.color.transparent : getStatusBarTintRes());/*通知栏所需颜色*/
        ViewGroup rootView = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        rootView.setFitsSystemWindows(true);
        rootView.setClipToPadding(true);
    }

    /**
     * 默认返回0 为透明状态栏
     *
     * @return
     */
    protected abstract int getStatusBarTintRes();

    /**
     * 获取布局资源id @return 返回此界面的布局资源id
     */
    protected abstract int getLayoutResId();

    /**
     * 初始化View @param savedInstanceState
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 初始化数据，会在{@link #initView(Bundle)}方法后立即执行 @param intent             启动此activity的intent @param savedInstanceState
     */
    protected abstract void initData(Intent intent, Bundle savedInstanceState);

    /**
     * 设置监听，会在{@link #initData(Intent, Bundle)}方法后立即执行
     */
    protected abstract void setListener();

    /**
     * 当View被点击时调用 @param v
     */
    protected abstract void onViewClick(View v);

    //---------------------------------点击事件拦截---------------------

    /**
     * 批量设置控件监听事件
     * @param views
     */
    protected void setOnClickEvent(View... views){
        for (final View view : views){
            //不做防重复点击
            view.setOnClickListener(this);
            //添加防重复点击
//            view.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (ClickManager.getInstance().doubleClick()) {
//                        return;
//                    } else {
//                        onViewClick(v)
//                    }
//                }
//            });
        }
    }
    //---------------------------------点击事件拦截---------------------
    @Override
    public void onClick(View v) {
        onViewClick(v);
    }


    @Override
    public void finish() {
        hideSoftKeyboard();
        super.finish();
    }

    /**
     * 隐藏软键盘
     */
    private void hideSoftKeyboard() {
        // 隐藏软键盘，避免软键盘引发的内存泄露
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (manager != null) {
                manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    /**
     * startActivityForResult 方法优化
     */

    private ActivityCallback mActivityCallback;
    private int mActivityRequestCode;

    public void startActivityForResult(Class<? extends Activity> cls, ActivityCallback callback) {
        startActivityForResult(new Intent(this, cls), null, callback);
    }

    public void startActivityForResult(Intent intent, ActivityCallback callback) {
        startActivityForResult(intent, null, callback);
    }

    public void startActivityForResult(Intent intent, Bundle options, ActivityCallback callback) {
        if (mActivityCallback == null) {
            mActivityCallback = callback;
            // 随机生成请求码，这个请求码在 0 - 255 之间
            mActivityRequestCode = new Random().nextInt(255);
            startActivityForResult(intent, mActivityRequestCode, options);
        } else {
            // 回调还没有结束，所以不能再次调用此方法，这个方法只适合一对一回调，其他需求请使用原生的方法实现
            throw new IllegalArgumentException("Error, The callback is not over yet");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (mActivityCallback != null && mActivityRequestCode == requestCode) {
            mActivityCallback.onActivityResult(resultCode, data);
            mActivityCallback = null;
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * Activity 回调接口
     */
    public interface ActivityCallback {

        /**
         * 结果回调
         *
         * @param resultCode 结果码
         * @param data       数据
         */
        void onActivityResult(int resultCode, @Nullable Intent data);
    }
}
