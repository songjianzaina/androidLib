package com.insworks.aop.login;

import android.content.Context;

/**
 * Created by Fomin on 2018/8/30.
 */
public class LoginSDK {
    private static LoginSDK instance;

    private LoginSDK() {
    }

    public static LoginSDK getInstance() {
        if (instance == null) {
            synchronized (LoginSDK.class) {
                if (instance == null) {
                    instance = new LoginSDK();
                }
            }
        }
        return instance;
    }

    private ILogin login;
    private Context context;
    
    /**
     * 初始化
     *
     * @param context Context
     * @param iLogin  登录事件
     */
    public void init(Context context, ILogin iLogin) {
        this.context = context;
        this.login = iLogin;
    }

    public ILogin getLogin() {
        return login;
    }

    public Context getContext() {
        return context;
    }
}