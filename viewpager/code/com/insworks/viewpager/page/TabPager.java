package com.insworks.viewpager.page;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.insworks.lib_base.base.BaseFragmentAdapter;
import com.inswork.lib_cloudbase.R;
import com.zhengsr.viewpagerlib.indicator.TabIndicator;

import java.util.List;

/**
 * @ProjectName: tftpay
 * @Package: com.insworks.lib_viewpager
 * @ClassName: TabPager
 * @Author: Song Jian
 * @CreateDate: 2019/6/14 14:56
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/14 14:56
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: tab界面配置类
 */
public class TabPager {
    public static final int TAB_STYLE_TRI = 101;
    public static final int TAB_STYLE_RECT = 102;
    public static final int TAB_STYLE_SHADE = 103;
    private final View view;
    private BaseFragmentAdapter<Fragment> mPagerAdapter;

    public TabPager(FragmentActivity activity, ViewGroup parentView) {
        mPagerAdapter = new BaseFragmentAdapter<>(activity);
        view = View.inflate(activity, R.layout.lib_viewpager_activity_tritab_page, parentView);
        ;

    }

    public void showTriTab(List<String> mTitle, int tabStyle) {
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        TabIndicator tabIndecator;
        if (tabStyle == TAB_STYLE_TRI) {
            //三角形指示器
            tabIndecator = (TabIndicator) view.findViewById(R.id.tri_indicator);
        } else if (tabStyle == TAB_STYLE_RECT) {
            //条形指示器
            tabIndecator = (TabIndicator) view.findViewById(R.id.rect_indicator);
        } else if (tabStyle == TAB_STYLE_SHADE) {
            //颜色渐变指示器
            tabIndecator = (TabIndicator) view.findViewById(R.id.shade_indicator);
        } else {
            //默认颜色渐变指示器
            tabIndecator = (TabIndicator) view.findViewById(R.id.shade_indicator);
        }
        tabIndecator.setVisibility(View.VISIBLE);
        viewPager.setAdapter(mPagerAdapter);
        /**
         * 把 TabIndicator 跟viewpager关联起来
         */
        tabIndecator.setViewPagerSwitchSpeed(viewPager, 600);
        tabIndecator.setTabData(viewPager, mTitle, new TabIndicator.TabClickListener() {
            @Override
            public void onClick(int position) {
                //顶部点击的方法公布出来
                viewPager.setCurrentItem(position);
            }
        });
    }

    /**
     * 添加fragment对象
     *
     * @param fragment
     * @return
     */
    public TabPager addFragment(Fragment fragment) {
        mPagerAdapter.addFragment(fragment);
        return this;
    }

    public List<Fragment> getFragementContainer() {
        return mPagerAdapter.getAllFragment();
    }


}
