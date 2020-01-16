package com.insworks.viewpager.page;

import android.app.Activity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inswork.lib_cloudbase.image.ImageLoader;
import com.inswork.lib_cloudbase.R;
import com.insworks.viewpager.bean.LoopBean;
import com.zhengsr.viewpagerlib.anim.DepthPageTransformer;
import com.zhengsr.viewpagerlib.anim.MzTransformer;
import com.zhengsr.viewpagerlib.anim.ZoomOutPageTransformer;
import com.zhengsr.viewpagerlib.bean.PageBean;
import com.zhengsr.viewpagerlib.callback.PageHelperListener;
import com.zhengsr.viewpagerlib.view.ArcImageView;
import com.zhengsr.viewpagerlib.view.BannerViewPager;

import java.util.List;

/**
 * @ProjectName: tftpay
 * @Package: com.insworks.lib_viewpager
 * @ClassName: LoopPager
 * @Author: Song Jian
 * @CreateDate: 2019/6/14 11:02
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/14 11:02
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: java类作用描述
 */
public class LoopPager  {
    public static final int PAGE_STYLE_ZOOM = 101;
    public static final int PAGE_STYLE_NORMAL = 102;
    public static final int PAGES_TYLE_ZOOM_AC = 103;
    public static final int INDICATOR_STYLE_TEXT_ROUND = 201;
    public static final int INDICATOR_STYLE_ZOOM = 202;
    public static final int INDICATOR_STYLE_ITEM = 203;
    public static final int PAGEANIM_STYLE_ONE = 301;
    public static final int PAGEANIM_STYLE_TWO = 302;
    public static final int PAGEANIM_STYLE_THREE = 303;
    public  BannerViewPager mBannerCountViewPager;
    protected LinearLayout indicator;
    protected final View view;

    public LoopPager(Activity activity, ViewGroup parentView) {
        view = View.inflate(activity, R.layout.lib_viewpager_loop_layout, parentView);
    }

    /**
     * 显示page
     * @param loopBeens 数据
     * @param isResFromNet 图片是否选择的是网络地址
     * @param pageStyle //page的样式
     * @param indicatorStyle 指示器的样式
     */
    public void show(List<LoopBean> loopBeens, final boolean isResFromNet, int pageStyle, int indicatorStyle, int PageAnimStyle, final OnLoopPagerClickListener onLoopPagerClickListener) {
        //设置指示器样式
        if (indicatorStyle== INDICATOR_STYLE_TEXT_ROUND) {
            //圆形数字版指示器
            indicator =  view.findViewById(R.id.bottom_text_layout);
        } else if (indicatorStyle == INDICATOR_STYLE_ZOOM) {
            indicator =  view.findViewById(R.id.bottom_scale_layout);
            //缩放版指示器

        }else if (indicatorStyle == INDICATOR_STYLE_ITEM) {
            //条目样式指示器
            indicator = view.findViewById(R.id.bottom_trans_layout);
        }

        //配置pagerbean，这里主要是为了viewpager的指示器的作用，注意记得写上泛型
        PageBean bean = new PageBean.Builder<LoopBean>()
                .setDataObjects(loopBeens)
                .setIndicator(indicator)
                .builder();
        //设置pager样式
        if (pageStyle== PAGE_STYLE_ZOOM) {
            mBannerCountViewPager = (BannerViewPager) view.findViewById(R.id.loop_viewpager_zoom);
            //放大版
            setWithTextPager(isResFromNet, onLoopPagerClickListener, bean);

        } else if (pageStyle== PAGE_STYLE_NORMAL) {
            //普通版 平面
            mBannerCountViewPager = (BannerViewPager) view.findViewById(R.id.loop_viewpager_normal);
            setWithTextPager(isResFromNet, onLoopPagerClickListener, bean);

        } else if (pageStyle== PAGES_TYLE_ZOOM_AC) {
            //弧形放大版
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            lp.gravity = Gravity.BOTTOM|Gravity.CENTER;
            indicator.setLayoutParams(lp);
            mBannerCountViewPager = (BannerViewPager) view.findViewById(R.id.loop_viewpager_zoom);
            mBannerCountViewPager.setPageTransformer(false,new MzTransformer());
            mBannerCountViewPager.setPageListener(bean, R.layout.lib_viewpager_arc_loop_layout, new PageHelperListener<LoopBean>() {
                @Override
                public void getItemView(final View view, final LoopBean data) {
                    ArcImageView imageView = view.findViewById(R.id.arc_icon);
                    if (isResFromNet) {
                        //网络数据
                        ImageLoader.loadImage(imageView,data.url);
                    } else {
                        //本地数据
                        imageView.setImageResource(data.res);
                    }
                    //如若你要设置点击事件，也可以直接通过这个view 来设置，或者图片的更新等等
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (onLoopPagerClickListener!=null) {
                                onLoopPagerClickListener.onClick(view, data);
                            }
                        }
                    });
                }
            });
        }
        //设置page轮播动画 一共内置了三种动画
        if (PageAnimStyle== PAGEANIM_STYLE_ONE) {
            mBannerCountViewPager.setPageTransformer(false,new MzTransformer());
        }else if(PageAnimStyle== PAGEANIM_STYLE_TWO) {
            mBannerCountViewPager.setPageTransformer(false,new ZoomOutPageTransformer());
        }else if(PageAnimStyle== PAGEANIM_STYLE_THREE) {
            mBannerCountViewPager.setPageTransformer(false,new DepthPageTransformer());
        }
        //显示轮播图
        mBannerCountViewPager.setVisibility(View.VISIBLE);
        indicator.setVisibility(View.VISIBLE);

    }

    /**
     * 设置底部带文字的布局页面
     * @param isResFromNet
     * @param onLoopPagerClickListener
     * @param bean
     */
    private void setWithTextPager(final boolean isResFromNet, final OnLoopPagerClickListener onLoopPagerClickListener, PageBean bean) {
        mBannerCountViewPager.setPageListener(bean, R.layout.lib_view_pager_with_text_layout, new PageHelperListener<LoopBean>() {
            @Override
            public void getItemView(final View view, final LoopBean data) {

                ImageView imageView = view.findViewById(R.id.loop_icon);
                TextView textView = view.findViewById(R.id.loop_text);
                if (isResFromNet) {
                    //网络数据
                    ImageLoader.loadImage(imageView, data.url);
                } else {
                    //本地数据
                    imageView.setImageResource(data.res);
                }

                //有文字数据就显示文字条目 如果没有就默认隐藏
                FrameLayout flText = view.findViewById(R.id.fl_text);
                if (!TextUtils.isEmpty(data.text)) {
                    flText.setVisibility(View.VISIBLE);
                }
                textView.setText(data.text);

                //如若你要设置点击事件，也可以直接通过这个view 来设置，或者图片的更新等等
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onLoopPagerClickListener != null) {
                            onLoopPagerClickListener.onClick(view, data);
                        }
                    }
                });
            }
        });
    }


    public interface OnLoopPagerClickListener {
        void onClick(View view, LoopBean data);
    }

}
