/*
 * Copyright (C) 2018 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.insworks.aop.permission;


import com.insworks.aop.EasyAOP;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.List;

/**
 * <pre>
 *     desc   : 申请系统权限切片，根据注解值申请所需运行权限
 *     author : xuexiang
 *     time   : 2018/4/22 下午8:50
 * </pre>
 */
@Aspect
public class PermissionAspectJ {
    @Pointcut("within(@com.insworks.permission.lib_aop.Permission *)")
    public void withinAnnotatedClass() {
        //注解切入
    }

    @Pointcut("execution(!synthetic * *(..)) && withinAnnotatedClass()")
    public void methodInsideAnnotatedType() {

    }

    @Pointcut("execution(@com.insworks.aop.permission.Permission * *(..)) || methodInsideAnnotatedType()")
    public void method() {
    }  //方法切入点

    @Around("method() && @annotation(permission)")
    public void aroundJoinPoint(final ProceedingJoinPoint joinPoint, Permission permission) throws Throwable {
        AndPermission.with(EasyAOP.getContext())
                .permission(permission.value())
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        try {
                            joinPoint.proceed();//获得权限，执行原方法
                        } catch (Throwable e) {
                            e.printStackTrace();
//                            LogUtil.e(e, "权限申请成功后执行方法出现异常");
                        }
                    }
                }).onDenied(new Action() {
            @Override
            public void onAction(List<String> permissions) {
//                Log.e("权限申请被拒绝:" , Utils.listToString(permissions));
            }
        }).start();
    }


}


