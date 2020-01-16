package com.insworks.idcard_identify.megvii;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.inswork.lib_cloudbase.R;


public class ResultActivity extends Activity implements View.OnClickListener{
    private ImageView mIDCardFaceImageView;
    private ImageView mIDCardEmblemImageView;
    private ImageView mPortraitImageView;
    private TextView mIDCardFaceSize;
    private TextView mIDCardEmblemSize;
    private TextView mPortraitSize;
    private LinearLayout ll_megvii_face, ll_megvii_emblem,ll_megvii_protrait,ll_megvii_info;
    private int mIDCardSide;
    private RadioButton rb1,rb2,rb3,rb4;
    private TextView name,idcardNum,data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resutl);

        mIDCardSide = getIntent().getIntExtra("side", -1);
        init();
    }

    void init() {
        rb1= (RadioButton) findViewById(R.id.rb1);
        rb1.setOnClickListener(this);
        rb2= (RadioButton) findViewById(R.id.rb2);
        rb2.setOnClickListener(this);
        rb3= (RadioButton) findViewById(R.id.rb3);
        rb3.setOnClickListener(this);
        rb4= (RadioButton) findViewById(R.id.rb4);
        rb4.setOnClickListener(this);

        name= (TextView) findViewById(R.id.tv_megvii_name);
        idcardNum= (TextView) findViewById(R.id.tv_megvii_idcardnumb);
        data = (TextView) findViewById(R.id.tv_megvii_date);

        ll_megvii_emblem = (LinearLayout) findViewById(R.id.ll_megvii_emblem);
        ll_megvii_face = (LinearLayout) findViewById(R.id.ll_megvii_face);
        ll_megvii_protrait= (LinearLayout) findViewById(R.id.ll_megvii_protrait);
        ll_megvii_info= (LinearLayout) findViewById(R.id.ll_megvii_info);
        mIDCardFaceImageView = (ImageView) findViewById(R.id.result_face_image);
        mIDCardEmblemImageView = (ImageView) findViewById(R.id.result_emblem_image);
        mPortraitImageView = (ImageView) findViewById(R.id.result_portrait_image);

        mIDCardFaceSize = (TextView) findViewById(R.id.result_face_size);
        mIDCardEmblemSize = (TextView) findViewById(R.id.result_emblem_size);
        mPortraitSize = (TextView) findViewById(R.id.result_portrait_size);

        byte[] faceImgData = DemoActivity.faceImg;
        if (faceImgData!=null){
            Bitmap faceBmp = BitmapFactory.decodeByteArray(faceImgData, 0,
                    faceImgData.length);
            mIDCardFaceImageView.setImageBitmap(faceBmp);
        }
//        mIDCardFaceSize.setText(faceBmp.getWidth() + "-" + faceBmp.getHeight());

        byte[] emblemImgData=DemoActivity.emblemImg;
        if (emblemImgData!=null){
            Bitmap emblemBmp=BitmapFactory.decodeByteArray(emblemImgData,0,emblemImgData.length);
            mIDCardEmblemImageView.setImageBitmap(emblemBmp);
        }

        byte[] portraitImgData = DemoActivity.portraitImg;
        if (portraitImgData!=null){
            Bitmap img = BitmapFactory.decodeByteArray(portraitImgData, 0,
                    portraitImgData.length);
            mPortraitImageView.setImageBitmap(img);
        }
//        mPortraitSize.setText(img.getWidth() + "_" + img.getHeight());
        String nameStr = getIntent().getStringExtra("name");
        if (nameStr!=null){
            name.setText("姓名："+nameStr);
        }
        String idcardNumStr=getIntent().getStringExtra("idcardNum");
        if (idcardNumStr!=null){
            idcardNum.setText("身份证号："+idcardNumStr);

        }
        String begin_str=getIntent().getStringExtra("dateBegin");
        String end_str=getIntent().getStringExtra("dateEnd");
        StringBuilder sb=new StringBuilder("有效期：");
        if (begin_str!=null){
            sb.append(begin_str);
        }
        sb.append("-");
        if (end_str!=null){
            sb.append(end_str);
        }
        data.setText(sb.toString());
    }

    private void showView(boolean rb1,boolean rb2,boolean rb3,boolean rb4){
        ll_megvii_face.setVisibility(View.GONE);
        ll_megvii_emblem.setVisibility(View.GONE);
        ll_megvii_protrait.setVisibility(View.GONE);
        ll_megvii_info.setVisibility(View.GONE);
        if (rb1){
            ll_megvii_face.setVisibility(View.VISIBLE);
        }
        if (rb2){
            ll_megvii_emblem.setVisibility(View.VISIBLE);
        }
        if (rb3){
            ll_megvii_protrait.setVisibility(View.VISIBLE);
        }
        if (rb4){
            ll_megvii_info.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.rb1) {
            showView(rb1.isChecked(), false, false, false);
        } else if (i == R.id.rb2) {
            showView(false, rb2.isChecked(), false, false);
        } else if (i == R.id.rb3) {
            showView(false, false, rb3.isChecked(), false);
        } else if (i == R.id.rb4) {
            showView(false, false, false, rb4.isChecked());
        }
    }
}