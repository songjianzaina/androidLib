package com.insworks.megviface.faceid;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.insworks.lib_log.LogUtil;

import java.util.HashMap;
import java.util.Map;


public class HttpRequestManager {
    private static HttpRequestManager instance;

    public static final String URL_GET_BIZTOKEN = "https://openapi.faceid.com/face/v1.2/sdk/get_biz_token";
    public static final String URL_GET_RESULT = "https://openapi.faceid.com/face/v1.2/sdk/get_result";

    public static HttpRequestManager getInstance() {
        if (instance == null) {
            instance = new HttpRequestManager();
        }
        return instance;
    }


    public void getBizToken(Context context, String url, String sign, String signVersoin, String livenessType, int comparisonType, String idcardName, String idcardNum, String uuid, byte[] image_ref1, String bizno,HttpRequestCallBack listener){
        MultipartEntity entity = new MultipartEntity();
        entity.addStringPart("sign",sign);
        entity.addStringPart("sign_version", signVersoin);
        entity.addStringPart("liveness_type", livenessType);
        entity.addStringPart("bizno", bizno);
        entity.addStringPart("comparison_type", ""+comparisonType);
        if (comparisonType==1){
            entity.addStringPart("idcard_name", idcardName);
            entity.addStringPart("idcard_number", idcardNum);
        }else if (comparisonType==0){
            entity.addStringPart("uuid", uuid);
            entity.addBinaryPart("image_ref1", image_ref1);
        }
        entity.addStringPart("verbose", 1+"");
        sendMultipartRequest(context,url,entity,new HashMap<String, String>(),listener);
    }


    public void getResult(Context context, String url, String sign, String signVersoin, String bizToken, HttpRequestCallBack listener){
        HashMap<String, String> entity = new HashMap<>();
        entity.put("sign",sign);
        entity.put("sign_version", signVersoin);
        entity.put("biz_token", bizToken);
        entity.put("verbose", 1+"");
        LogUtil.d("============"+entity.toString());
        sendGetRequest(context,url,entity,listener);
    }

    private void sendPostRequest(Context context, String url, final Map<String, String> params, final Map<String, String> header, final HttpRequestCallBack listener) {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (listener != null)
                    listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error == null) {
                    if (listener != null)
                        listener.onFailure(-1, "timeout exception".getBytes());
                } else if (error.networkResponse == null) {
                    if (listener != null)
                        listener.onFailure(-1, "timeout exception".getBytes());
                } else {
                    if (listener != null)
                        listener.onFailure(error.networkResponse.statusCode, error.networkResponse.data);
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return header;
            }
        };
        VolleyHelper.getInstance(context).addToRequestQueue(request);
    }

    private void sendGetRequest(Context context, String url, final Map<String, String> header, final HttpRequestCallBack listener) {
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error == null) {
                    listener.onFailure(-1, "timeout exception".getBytes());
                } else if (error.networkResponse == null) {
                    listener.onFailure(-1, "timeout exception".getBytes());
                } else {
                    listener.onFailure(error.networkResponse.statusCode, error.networkResponse.data);
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return header;
            }
        };
        VolleyHelper.getInstance(context).addToRequestQueue(request);
    }

    private void sendMultipartRequest(Context context, String url, MultipartEntity mult, final Map<String, String> header, final HttpRequestCallBack listener) {
        MultipartRequest multipartRequest = new MultipartRequest(
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error == null) {
                    listener.onFailure(-1, "timeout exception".getBytes());
                } else if (error.networkResponse == null) {
                    listener.onFailure(-1, "timeout exception".getBytes());
                } else {
                    listener.onFailure(error.networkResponse.statusCode, error.networkResponse.data);
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return header;
            }
        };
        // 通过MultipartEntity来设置参数
        multipartRequest.setmMultiPartEntity(mult);

        VolleyHelper.getInstance(context).addToRequestQueue(multipartRequest);
    }

}
