package com.insworks.lib_idcard.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.inswork.lib_cloudbase.R;
import com.insworks.lib_idcard.LocalCardIdentify;
import com.insworks.lib_idcard.utils.CheckPermissionUtil;

import io.github.dltech21.ocr.IDCardEnum;
import io.github.dltech21.ocr.IdentityInfo;
import io.github.dltech21.ocr.OcrConfig;


/**
 * Created by Donal on 2017/8/15.
 */

public class IdCardTestActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lib__id_card_activity_card_test);
        //另一个权限申请框架
//        AndPermission.with(this)
//                .permission(new String[]{Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE, Permission.CAMERA})
//                .onGranted(new Action() {
//                    @Override
//                    public void onAction(List<String> permissions) {
//                    }
//                })
//                .start();
//        initPermission();

        findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IdCardTestActivity.this, IdCameraActivity.class));
            }
        });

        findViewById(R.id.bt2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalCardIdentify.init(IdCardTestActivity.this).startIDCardEnum();
            }
        });

        findViewById(R.id.bt3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalCardIdentify.init(IdCardTestActivity.this).startNationalEmblem();
            }
        });
    }

    /**
     * 初始化权限事件
     */
    private void initPermission() {
        //检查权限
        String[] permissions = CheckPermissionUtil.checkPermission(this);
        if (permissions.length == 0) {
            //权限都申请了
            //是否登录
        } else {
            //申请权限
            ActivityCompat.requestPermissions(this, permissions, 100);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1001) {
            String filepath = data.getStringExtra(OcrConfig.OCR_PHOTO_PATH);
            IdentityInfo identityInfo = (IdentityInfo) data.getSerializableExtra(OcrConfig.OCR_IDENTITYINFO);
            StringBuffer localStringBuffer = new StringBuffer();
            localStringBuffer.append("姓名：").append(identityInfo.getName()).append("\n");
            localStringBuffer.append("身份号码：").append(identityInfo.getCertid()).append("\n");
            localStringBuffer.append("性别：").append(identityInfo.getSex()).append("\n");
            localStringBuffer.append("民族：").append(identityInfo.getFork()).append("\n");
            localStringBuffer.append("出生：").append(identityInfo.getBirthday()).append("\n");
            localStringBuffer.append("住址：").append(identityInfo.getAddress()).append("\n");
            localStringBuffer.append("签发机关：").append(identityInfo.getIssue_authority()).append("\n");
            localStringBuffer.append("有效期限：").append(identityInfo.getVaild_priod()).append("\n");
            localStringBuffer.append(identityInfo.getType() == IDCardEnum.FaceEmblem ? "人像面" : "国徽面").append("\n");
            ((TextView) findViewById(R.id.idresult)).setText(localStringBuffer.toString());
            ((ImageView) findViewById(R.id.idimgview)).setImageBitmap(BitmapFactory.decodeFile(filepath));
        }
    }
}