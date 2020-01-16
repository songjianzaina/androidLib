package com.insworks.viewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.LinearLayout;

import com.inswork.lib_cloudbase.R;
import com.insworks.viewpager.bean.LoopBean;
import com.insworks.viewpager.page.TabPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ViewPagerTestActivity extends FragmentActivity {
    private static final int[] RES = {R.mipmap.guide1, R.mipmap.guide2, R.mipmap.guide3,
            R.mipmap.guide4};
    private static final int[] RESURL = {
            R.mipmap.beauty1,
            R.mipmap.beauty2,
            R.mipmap.beauty3,
            R.mipmap.beauty4,};


    private static final String[] TEXT = {"图像处理", "LSB开发", "游戏开发", "梦想"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lib_viewpager_activity_viewpager_test);

        LinearLayout ll = (LinearLayout) findViewById(R.id.parent);


        //配置数据
        //导航页数据
        List<Integer> images = new ArrayList<>();
        for (int i = 0; i < RES.length; i++) {
            images.add(RES[i]);

        }
        //轮播图数据
        ArrayList<LoopBean> loopBeens = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            LoopBean bean2 = new LoopBean();
            bean2.res = RESURL[i];
            bean2.text = TEXT[i];
            loopBeens.add(bean2);

        }
        //tab数据
       List<String> mTitle = Arrays.asList("新闻","娱乐","学习",
                "新闻","娱乐","学习",
                "新闻","娱乐","学习");

        //导航页面测试
//        EasyViewPager.initGuidePager(this, ll).show(images, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(ViewPagerTestActivity.this, "多谢点击", Toast.LENGTH_SHORT).show();
//            }
//        });
//        //轮播图测试
//        EasyViewPager.initLoopPager(this, ll).show(loopBeens, false, LoopPager.PAGES_TYLE_ZOOM_AC, LoopPager.INDICATOR_STYLE_ZOOM, LoopPager.PAGEANIM_STYLE_ONE, new LoopPager.OnLoopPagerClickListener() {
//            @Override
//            public void onClick(View view, LoopBean data) {
//                Toast.makeText(ViewPagerTestActivity.this, "多谢点击", Toast.LENGTH_SHORT).show();
//            }
//        });

        //tab页面测试
        List<Fragment> fragementContainer = EasyViewPager.initTabPager(this, ll).getFragementContainer();

        for (String string : mTitle) {
            CusFragment fragment = CusFragment.newInStance(string);
            fragementContainer.add(fragment);
        }
        EasyViewPager.initTabPager(this, ll).showTriTab(mTitle, TabPager.TAB_STYLE_SHADE);
    }


}
