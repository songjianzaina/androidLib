package com.insworks.richtext;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @ProjectName: tftpay
 * @Package: com.insworks.lib_richtext
 * @ClassName: RichText
 * @Author: Song Jian
 * @CreateDate: 2019/7/15 10:34
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/15 10:34
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: 富文本操作入口类
 */
public class RichText {

    private static RichText richText;
    private static Activity activity;
    protected final ArrayList<SpannableString> spannArrs;

    private static void testInitialize() {
        if (activity == null)
            throw new ExceptionInInitializerError("请先调用 RichText.init() 初始化！");
    }

    public RichText() {
        spannArrs = new ArrayList<>();
    }

    /**
     * 初始化扫描库
     *
     * @param activity
     */
    public static RichText init(Activity activity) {
        RichText.activity = activity;
        richText = new RichText();
//        if (richText == null) {
//            synchronized (RichText.class) {
//                if (richText == null) {
//                }
//            }
//        }
        return richText;
    }

    /**
     * 添加文本内容
     *
     * @param charSequence
     */
    public RichText append(CharSequence charSequence) {
        SpannableString spannableString = new SpannableString(charSequence);
        spannArrs.add(spannableString);
        return this;

    }

    /**
     * 设置前景色
     */
    public RichText setForegroundColor(int color) {
        SpannableString spannableString = spannArrs.get(spannArrs.size() - 1);
        //ForegroundColorSpan
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
        spannableString.setSpan(colorSpan, 0, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return this;

    }

    /**
     * 设置背景色
     */
    public RichText setBackgroundColor(int color) {
        SpannableString spannableString = spannArrs.get(spannArrs.size() - 1);
        BackgroundColorSpan colorSpan = new BackgroundColorSpan(color);
        spannableString.setSpan(colorSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;

    }

    /**
     * 设置删除线
     */
    public RichText setStrikethrough() {
        SpannableString spannableString = spannArrs.get(spannArrs.size() - 1);
        StrikethroughSpan whatSpan = new StrikethroughSpan();
        spannableString.setSpan(whatSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;

    }

    /**
     * 设置下划线
     */
    public RichText setUnderlineSpan() {
        SpannableString spannableString = spannArrs.get(spannArrs.size() - 1);
        UnderlineSpan whatSpan = new UnderlineSpan();
        spannableString.setSpan(whatSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;

    }

    /**
     * 设置字体大小 单位为dp
     */
    public RichText setTextSize(int size) {
        SpannableString spannableString = spannArrs.get(spannArrs.size() - 1);
        AbsoluteSizeSpan whatSpan = new AbsoluteSizeSpan(size, true);
        spannableString.setSpan(whatSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;

    }

    /**
     * 设置字体样式 粗体
     */
    public RichText setTextBold() {
        SpannableString spannableString = spannArrs.get(spannArrs.size() - 1);
        StyleSpan whatSpan = new StyleSpan(Typeface.BOLD);
        spannableString.setSpan(whatSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;

    }

    /**
     * 设置字体样式 斜体
     */
    public RichText setTextItalic() {
        SpannableString spannableString = spannArrs.get(spannArrs.size() - 1);
        StyleSpan whatSpan = new StyleSpan(Typeface.ITALIC);
        spannableString.setSpan(whatSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;

    }

    /**
     * 设置下标 (数学公式)
     */
    public RichText setSubscript() {
        SpannableString spannableString = spannArrs.get(spannArrs.size() - 1);
        SubscriptSpan whatSpan = new SubscriptSpan();
        spannableString.setSpan(whatSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;

    }

    /**
     * 设置上标 (数学公式)
     */
    public RichText setSuperscriptSpan() {
        SpannableString spannableString = spannArrs.get(spannArrs.size() - 1);
        SuperscriptSpan whatSpan = new SuperscriptSpan();
        spannableString.setSpan(whatSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;

    }

    /**
     * 设置点击事件
     */
    public RichText setOnclickListener(ClickableSpan whatSpan) {
        SpannableString spannableString = spannArrs.get(spannArrs.size() - 1);
        spannableString.setSpan(whatSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;

    }

    /**
     * 设置字体样式 粗斜体
     */
    public RichText setTextBoldItalic() {
        SpannableString spannableString = spannArrs.get(spannArrs.size() - 1);
        StyleSpan whatSpan = new StyleSpan(Typeface.BOLD_ITALIC);
        spannableString.setSpan(whatSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;

    }

    /**
     * 追加图片 覆盖文字 图片和文字底部对齐
     */
    public RichText addImageSpan(Bitmap bitmap) {
        SpannableString spannableString = spannArrs.get(spannArrs.size() - 1);
        @SuppressWarnings("deprecation")
        Drawable d = new BitmapDrawable(bitmap);
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
        spannableString.setSpan(span, 0, spannableString.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return this;


    }

    /**
     * 追加图片 覆盖文字 图片和文字底部对齐
     */
    public RichText addImageSpan(Drawable drawable) {
        SpannableString spannableString = spannArrs.get(spannArrs.size() - 1);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
        spannableString.setSpan(span, 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;


    }


    /**
     * 设置超链接
     */
    public RichText setUrl(String url) {
        SpannableString spannableString = spannArrs.get(spannArrs.size() - 1);
        //URLSpan
        URLSpan urlSpan = new URLSpan(url);
        spannableString.setSpan(urlSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return this;
    }


    /**
     * 添加至TextView中
     */
    public void into(TextView textView) {
        //让点击生效
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        //点击时去除背景颜色
        textView.setHighlightColor(Color.TRANSPARENT);
        SpannableStringBuilder bulider = new SpannableStringBuilder();
        for (SpannableString spannArr : spannArrs) {
            bulider.append(spannArr);
        }
        textView.setText(bulider);

    }


}
