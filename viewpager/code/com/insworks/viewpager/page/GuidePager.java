package com.insworks.viewpager.page;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.inswork.lib_cloudbase.R;
import com.zhengsr.viewpagerlib.bean.PageBean;
import com.zhengsr.viewpagerlib.callback.PageHelperListener;
import com.zhengsr.viewpagerlib.indicator.NormalIndicator;
import com.zhengsr.viewpagerlib.view.GlideViewPager;

import java.util.List;

/**
 * @ProjectName: tftpay
 * @Package: com.insworks.lib_viewpager
 * @ClassName: GuidePager
 * @Author: Song Jian
 * @CreateDate: 2019/6/14 10:58
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/14 10:58
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: java类作用描述
 */
public class GuidePager {
    private static GlideViewPager viewPager;
    private static NormalIndicator textIndicator;
    protected final Button button;

    public GuidePager(Activity activity, ViewGroup parentView) {
        View view = View.inflate(activity, R.layout.lib_viewpager_activity_glide_normal, parentView);
        viewPager = (GlideViewPager) view.findViewById(R.id.splase_viewpager);
        textIndicator = (NormalIndicator) view.findViewById(R.id.splase_bottom_layout);
        button = (Button) view.findViewById(R.id.splase_start_btn);
    }

    /**
     * 显示guide导航页pager  一共有三种指示器样式 直接布局文件修改即可
     *
     * @param images
     * @param buttonClickListener
     */
    public void show(List<Integer> images, View.OnClickListener buttonClickListener) {
        //配置pagerbean，这里主要是为了viewpager的指示器的作用，注意记得写上泛型
        PageBean bean = new PageBean.Builder<Integer>()
                .setDataObjects(images)
                .setIndicator(textIndicator)
                .setOpenView(button)
                .builder();

        // 把数据添加到 viewpager中，并把view提供出来，这样除了方便调试，也不会出现一个view，多个
        // parent的问题，这里在轮播图比较明显
        viewPager.setPageListener(bean, R.layout.lib_viewpager_image_layout, new PageHelperListener() {
            @Override
            public void getItemView(View view, Object data) {
                ImageView imageView = view.findViewById(R.id.icon);
                imageView.setImageResource((Integer) data);
            }
        });


        //点击实现跳转功能
        button.setOnClickListener(buttonClickListener);
    }

}
