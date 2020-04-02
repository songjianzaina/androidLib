package com.insworks.lib_loading

/**
 * author : Song Jian
 * date   : 2020/4/2
 * desc   : 
 */
interface LoadingToggle {

    fun showLoading(show: Boolean)
    /**
     * 加载动画是否覆盖全屏
     */
    fun showLoadingCover()
}
