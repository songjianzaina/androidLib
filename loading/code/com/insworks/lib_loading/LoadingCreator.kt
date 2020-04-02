package com.insworks.lib_loading

import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.isVisible
import com.inswork.lib_cloudbase.R
import com.inswork.lib_cloudbase.utils.dip

/**
 * 佛祖保佑         永无BUG
 *
 * @author Created by joker on 2019-07-22
 */
 class  LoadingCreator(val viewGroup: ViewGroup?) : LoadingToggle {

    private val rotateAnim: RotateAnimation by lazy {
        RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f).apply {
            interpolator = LinearInterpolator()
            duration = 1500
            repeatCount = Animation.INFINITE
            fillAfter = true
        }
    }
    private val loading: AppCompatImageView? by lazy {
        val cxt = viewGroup?.context
        if (cxt != null) {
            AppCompatImageView(cxt).apply {
                layoutParams = RelativeLayout.LayoutParams(dip(30), dip(30))
                setImageResource(R.mipmap.circle_loading)
            }
        }else null
    }
    private val loadContainer: RelativeLayout by lazy {
        RelativeLayout(viewGroup?.context).apply {
            gravity = Gravity.CENTER
            loading?.let { addView(it) }
            isClickable = false
        }
    }


    override fun showLoading(show: Boolean) {
        if (show && loadContainer.parent == null) {
            viewGroup?.addView(loadContainer,
                    ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            )
        }
        if (show) {
            loading?.startAnimation(rotateAnim)
            loadContainer.isVisible = true
            loadContainer.bringToFront()
            loadContainer.setBackgroundColor(Color.TRANSPARENT)
        } else {
            loadContainer.isVisible = false
            loading?.clearAnimation()
            (loadContainer.parent as? ViewGroup)?.removeView(loadContainer)
        }
        loadContainer.isClickable = false
    }

    /**
     * 全屏覆盖的loading
     */
    override fun showLoadingCover() {
        if (loadContainer.parent == null) {
            viewGroup?.addView(loadContainer,
                    ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            )
        }
        loading?.startAnimation(rotateAnim)
        loadContainer.setBackgroundColor(Color.WHITE)
        loadContainer.isVisible = true
        loadContainer.bringToFront()
        loadContainer.isClickable = false
    }
}