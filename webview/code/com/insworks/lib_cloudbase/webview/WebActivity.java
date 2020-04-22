package com.insworks.lib_cloudbase.webview;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.inswork.lib_cloudbase.R;
import com.inswork.lib_cloudbase.base.UIActivity;
import com.inswork.lib_cloudbase.event.Event;
import com.inswork.lib_cloudbase.utils.SystemUtils;
import com.insworks.lib_log.LogUtil;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 浏览器界面
 */
public class WebActivity extends UIActivity {
    public final static String URL = "webview_url";
    public static final String TITLE = "webview_title";
    public static final String RIGHT_TEXT = "webview_right_text";
    public static final String HTML_STR = "htmlStr";

    ProgressBar mProgressBar;
    public static WebView mWebView;
    protected String mTitle;
    protected TextView closeTextBar;
    ValueCallback<Uri> mUploadMessage;
    int RESULT_CODE = 0;

    @Override
    protected boolean isRegisterEventBus() {
        return false;
    }

    @Override
    protected boolean isTitleBarBgTrans() {
        return false;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_web_title;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            // 后退网页并且拦截该事件
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        WebViewLifecycleUtils.onResume(mWebView);
        super.onResume();
    }

    @Override
    protected void onPause() {
        WebViewLifecycleUtils.onPause(mWebView);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        WebViewLifecycleUtils.onDestroy(mWebView);
        super.onDestroy();
    }

    @Override
    protected void receiveEvent(Event event) {

    }

    @Override
    protected void receiveStickyEvent(Event event) {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_web;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        mWebView = findViewById(R.id.wv_web_view);
        mProgressBar = findViewById(R.id.pb_web_progress);


        //关闭标题
        closeTextBar = getTitleBar().setMiddleLeftBar("关闭", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        InJavaScriptLocalObj inJavaScriptLocalObj = new InJavaScriptLocalObj();
//        inJavaScriptLocalObj.setEntity(webViewBean);
        //配置webview
        WebViewHelper.setDefaultWebSettings(mWebView, null);


    }


    @Override
    protected void initData(Intent intent, Bundle savedInstanceState) {
        //webUrl地址
        String url = getIntent().getStringExtra(URL);
        if (url != null) {
            LogUtil.d("webview地址: " + url);
            mWebView.loadUrl(url);
        }

        //webHtml字符串
        String htmlStr = getIntent().getStringExtra(HTML_STR);
        if (htmlStr != null) {
            LogUtil.d("webview webHtml字符串: " + htmlStr);
            mWebView.loadDataWithBaseURL(null, htmlStr, "text/html", "utf-8", null);
        }

        //web标题
        mTitle = getIntent().getStringExtra(TITLE);
        if (mTitle != null) {
            LogUtil.d("webview 标题: " + mTitle);
            setTitle(mTitle);
            if (mTitle.equals("hide")) {
                //隐藏标题栏
                getTitleBar().setVisibility(View.GONE);
                ((LinearLayout.LayoutParams) mWebView.getLayoutParams()).topMargin = getStatusBarHeight();
            }
        }
        //以下是云中付自定义部分
        String rightText = getIntent().getStringExtra(RIGHT_TEXT);
        if (rightText != null) {
            getTitleBar().setRightBar(rightText, rightBarOnclickListener);
        }

    }

    //-------------云中付定制部分------------------------
    private static View.OnClickListener rightBarOnclickListener;

    public static void setRightBarOnclickListener(View.OnClickListener rightBarOnclickListener) {

        WebActivity.rightBarOnclickListener = rightBarOnclickListener;
    }

    //-------------云中付定制部分------------------------

    @Override
    public boolean onBackStack() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            return super.onBackStack();
        }
        return false;
    }

    @Override
    protected void setListener() {
        mWebView.setDownloadListener((url, userAgent, contentDisposition, mimeType, contentLength) -> {
            // H5中包含下载链接的话让外部浏览器去处理
            SystemUtils.downloadByWeb(url);
        });
        WebViewHelper.setWebClient(mWebView, new OnLoadListener() {


            @Override
            public boolean overrideUrlLoading(WebView view, String url) {
                String scheme = Uri.parse(url).getScheme();
                boolean hasIntercept = false;
                if ("http".equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme)) {
                    //正常链接
                    mWebView.loadUrl(url);
                } else {
                    //H5内链
                    if (url == null) {
                        url = "";
                    }
                    hasIntercept = handleUrlLoading(view, url);
                }
                // 已经处理该链接请求
                return hasIntercept;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mProgressBar.setVisibility(View.GONE);
                //多重页面就显示关闭按钮 单层页面就不显示
                if (mWebView.canGoBack()) {
                    closeTextBar.setVisibility(View.VISIBLE);
                } else {
                    closeTextBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageStart(WebView view, String url, Bitmap favicon) {
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onReceivedPageError(WebView view, WebResourceRequest request, WebResourceError error) {
                // 避免出现默认的错误界面
                view.loadUrl("about:blank");
                // 加载自定义错误页面
//                view.loadUrl(mErrorUrl);
            }


            @Override
            public void openFileChooserCallBack(ValueCallback<Uri> uploadMsg, String acceptType) {
                mUploadMessage = uploadMsg;
                //文件选择
                pickFile();
            }

            @Override
            public void showFileChooserCallBack(ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                //开启文件选择器
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                WebActivity.this.startActivity(i);
            }

            @Override
            public void onReceivedPageTitle(WebView view, String title) {
                //如果外部没有传标题进来,那就使用网页自带的标题
                if (mTitle == null) {
                    if (title != null) {
                        setTitle(title);
                    }
                }
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                mProgressBar.setProgress(newProgress);
            }
        });

    }

    protected boolean handleUrlLoading(WebView view, String url) {
        return false;
    }

    @Override
    protected void onViewClick(View v) {

    }

    public void pickFile() {
        Intent chooserIntent = new Intent(Intent.ACTION_GET_CONTENT);
        chooserIntent.setType("image/*");
        startActivityForResult(chooserIntent, RESULT_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == RESULT_CODE) {
            if (null == mUploadMessage) {
                return;
            }
            Uri result = intent == null || resultCode != Activity.RESULT_OK ? null : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        }
    }
}