package com.insworks.idcard_identify;

import com.megvii.faceidiol.sdk.manager.IDCardDetectListener;
import com.megvii.faceidiol.sdk.manager.IDCardResult;

/**
 * @ProjectName: tftpay
 * @Package: com.insworks.idcard_identify
 * @ClassName: IdCardDetectFinishListener
 * @Author: Song Jian
 * @CreateDate: 2019/5/31 11:10
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/5/31 11:10
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: 身份证识别监听接口
 */
public abstract class IdCardDetectResultListener implements IDCardDetectListener {
    @Override
    public void onIdCardDetectFinish(IDCardResult idCardResult) {
        onIdCardDetectResult(idCardResult);
    }

    protected abstract void onIdCardDetectResult(IDCardResult idCardResult);
}
