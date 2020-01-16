package com.insworks.lib_cloudbase.webview;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class MeWebChromeClient extends WebChromeClient {


    private OnLoadListener listener;

    // 收到网页标题
        @Override
        public void onReceivedTitle(WebView view, String title) {
            if (listener!=null) {
                listener.onReceivedPageTitle(view, title);
            }

        }
        // 收到加载进度变化
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (listener!=null) {
                listener.onProgressChanged(view, newProgress);
            }

        }
        // For Android > 5.0
        @Override
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            if (listener != null) {
                listener.showFileChooserCallBack(filePathCallback, fileChooserParams);
            }
            return true;
        }


        // For Android < 3.0
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            openFileChooser(uploadMsg, "");
        }
        //For Android 3.0 - 4.0
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
            if (listener != null) {
                listener.openFileChooserCallBack(uploadMsg, acceptType);
            }
        }

        // For Android 4.0 - 5.0
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            openFileChooser(uploadMsg, acceptType);
        }

      

    public void setListener(OnLoadListener onLoadListener) {

        listener = onLoadListener;
    }


    }