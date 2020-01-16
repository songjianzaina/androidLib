package com.insworks.lib_cloudbase.webview;

import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.insworks.lib_base.utils.ToastUtil;

/**
 * @ProjectName: AndroidTemplateProject2
 * @Package: com.inswork.lib_cloudbase.webview
 * @ClassName: H5InterActive
 * @Author: Song Jian
 * @CreateDate: 2019/7/23 9:49
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/23 9:49
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: H5交互
 */
class H5InterActive {

    /**
     * H5调android 方法
     */
    @JavascriptInterface
    public void setMessage() {
        //     document.getElementById("btn0").onclick = function(){
        ////android是传过来的对象名称，setmessage是android中的方法
        //            android.setMessage();
        //        };

        ToastUtil.showToast("H5调用了空参方法");
    }
    /**
     * H5调android 方法
     */
    @JavascriptInterface
    public void setMessage(String name) {
        //      document.getElementById("btn1").onclick = function(){
        ////android是传过来的对象名称，setmessage是android中的方法
        //            android.setMessage(name);
        //        };
        ToastUtil.showToast("H5调用了带参方法");
    }

    /**
     * android 调H5
     * @param webView
     */
    public void invokeH5Method(WebView webView,String methodName) {
        //参数 “javascript:”  +  js方法名
//        webView.loadUrl("javascript:message()");
        webView.loadUrl("javascript:"+methodName+"()");

    }
    /**
     * android 调H5
     * @param webView
     */
    public void invokeH5Method(WebView webView,String methodName,String param) {
        //在android调用js有参的函数的时候参数要加单引号
        webView.loadUrl("javascript:"+methodName+"('" + param + "')");

    }
}
