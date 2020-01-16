package com.insworks.lib_cloudbase.webview;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 作者 create by moziqi on 2018/9/26
 * 邮箱 709847739@qq.com
 * 说明
 **/
public class MeWebViewClient extends WebViewClient {
    private OnLoadListener listener;


    public void setListener(OnLoadListener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
        super.onReceivedHttpAuthRequest(view, handler, host, realm);

    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        //super.onReceivedSslError(view, handler, error);注意一定要去除这行代码，否则设置无效。
            // handler.cancel();// Android默认的处理方式
            handler.proceed();// 接受所有网站的证书
            // handleMessage(Message msg);// 进行其他处理
    }

    /** 开始加载网页
     *
     * @param view
     * @param url
     * @param favicon
     */
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        if (listener != null) {
            listener.onPageStart(view, url,favicon);
        }
    }

    /** 完成加载网页
     *
     * @param view
     * @param url
     */
    @Override
    public void onPageFinished(WebView view, String url) {
        WebViewHelper.loadJs(view, "javascript:window.local_obj.showSource(document.getElementsByTagName('html')[0].innerHTML);");
        if (listener != null) {
            listener.onPageFinished(view, url);
        }
    }

    /** 网页加载错误时回调，这个方法会在 onPageFinished 之前调用
     *
     * @param view
     * @param request
     * @param error
     */
    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        if (listener != null) {
            listener.onReceivedPageError(view,request,error);
        }
    }

    /** 跳转到其他链接
     *
     * @param view
     * @param url
     * @return
     */
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (null != listener) {
            if (listener.overrideUrlLoading(view, url))
                // 已经处理该链接请求
                return true;
        }
        return super.shouldOverrideUrlLoading(view, url);
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
    }
}


