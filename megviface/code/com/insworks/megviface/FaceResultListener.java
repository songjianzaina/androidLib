package com.insworks.megviface;

/**
 * @ProjectName: tftpay
 * @Package: com.insworks.lib_face
 * @ClassName: FaceResultListener
 * @Author: Song Jian
 * @CreateDate: 2019/5/31 16:12
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/5/31 16:12
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: 活体检测结果监听类
 */
public interface  FaceResultListener{
//    @Override
//    public void onSuccess(int code, String msg) {
//        LogUtil.d("活体检测成功" + msg);
//        success(code, msg);
//    }
//
//    @Override
//    public void onFailed(int code, String msg) {
//        LogUtil.d("活体检测失败" + msg);
//        failed(code, msg);
//    }
//
//    protected abstract void success(int code, String msg);
//
//    protected abstract void failed(int code, String msg);


     void onSuccess(int code, String msg);

     void onFailed(int code, String msg);
}
