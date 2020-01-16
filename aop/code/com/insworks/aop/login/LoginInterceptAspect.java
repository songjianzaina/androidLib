package com.insworks.aop.login;

import android.content.Context;
import android.util.Log;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Created by Fomin on 2018/8/30.
 */
@Aspect
public class LoginInterceptAspect {

    private static final String TAG = "LoginInterceptAspect";

    /**
     * 切点 设置过滤规则  只要实现了注解的 方法全部过滤出来
     */
    @Pointcut("execution(@com.insworks.aop.login.LoginIntercept  * *..*.*(..))")//这里使用private是因为方法中全部是private，也可以去除
    public void loginFilter() {
    }

    /**
     * 使用loginFilter的过滤规则
     * @Aspect：声明切面，标记类
     * @Pointcut(切点表达式)：定义切点，标记方法
     * @Before(切点表达式)：前置通知，切点之前执行
     * @Around(切点表达式)：环绕通知，切点前后执行
     * @After(切点表达式)：后置通知，切点之后执行
     * @AfterReturning(切点表达式)：返回通知，切点方法返回结果之后执行
     * @AfterThrowing(切点表达式)：异常通知，切点抛出异常时执行
     * 作者：GitLqr
     * 链接：https://www.jianshu.com/p/aa1112dbebc7
     * 来源：简书
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     * @param joinPoint
     * @throws Throwable
     */
    @Around("loginFilter()")
    public void aroundLoginPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        ILogin login = LoginSDK.getInstance().getLogin();
        if (login == null) {
//            throw new NoInitException("LoginSDK没有初始化！");
        }

        Signature signature = joinPoint.getSignature();
        if (!(signature instanceof MethodSignature)) {
//            throw new AnnotationException("LoginIntercept注解只能用于方法上");
        }

        MethodSignature methodSignature = (MethodSignature) signature;
        Log.d("Aspect", String.valueOf(methodSignature.getName()));
        Log.d("Aspect", String.valueOf(methodSignature.getMethod() == null));
        //获取方法上的注解对象
        LoginIntercept loginIntercept = methodSignature.getMethod().getAnnotation(LoginIntercept.class);
        if (loginIntercept== null) {
            return;
        }
        Context param = LoginSDK.getInstance().getContext();
        if (login.isLogin(param)) {
            //如果已登录 就执行方法
            joinPoint.proceed();
        } else {
            //如果还未登录 就执行自定义方法
            login.login(param, loginIntercept.actionDefine());
        }
    }
}
//注意：在LoginFilterAspect 类中如果有用到Context，可直接使用joinPoint.getTarget()类型转换成Context，这里是由于项目使用了databinding，部分getTarget()获取到的值不能强转为Context，所以这里用的MyApplication获取的Context。
//ILogin接口：
//
//ILogin：接口

