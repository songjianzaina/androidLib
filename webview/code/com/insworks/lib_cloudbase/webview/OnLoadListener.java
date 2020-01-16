package com.insworks.lib_cloudbase.webview;

import android.graphics.Bitmap;
import android.net.Uri;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;

/**
 * 作者 create by moziqi on 2018/9/26
 * 邮箱 709847739@qq.com
 * 说明
 **/
public interface OnLoadListener {
    //------------------MeWebChromeClient----------------------------------
    void openFileChooserCallBack(ValueCallback<Uri> uploadMsg, String acceptType);

    void showFileChooserCallBack(ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams);

    void onReceivedPageTitle(WebView view, String title);

    void onProgressChanged(WebView view, int newProgress);
    //------------------MeWebChromeClient----------------------------------




    //------------------MeWebViewClient----------------------------------
    boolean overrideUrlLoading(WebView view, String url);

    void onPageFinished(WebView view, String url);

    void onPageStart(WebView view, String url, Bitmap favicon);

    void onReceivedPageError(WebView view, WebResourceRequest request, WebResourceError error);
    //------------------MeWebViewClient----------------------------------
}
