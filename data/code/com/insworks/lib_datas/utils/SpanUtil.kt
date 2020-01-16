import android.text.Spannable
import android.text.SpannableString
import com.inswork.lib_cloudbase.R
import com.inswork.lib_cloudbase.utils.SystemUtils.mContext
import com.insworks.lib_datas.utils.CustomImageSpan

/**
 * 获取所有包含指定字符串的角标位置集合
 */
fun getAllPlace(content:String,targetStr:String): ArrayList<Int> {
    //获取所有"|"的位置
    val arr = arrayListOf<Int>()
    var startIndex = 0
    while (content.indexOf(targetStr, startIndex) > -1) {
        startIndex = content.indexOf(targetStr, startIndex)
        arr.add(startIndex)
        startIndex += 1
    }
    return arr

}

/**
 * 批量替换
 */
fun replaceAll(content:String,arr:ArrayList<Int>) {
    //批量替换成图片
    val sp= SpannableString(content)
    for (i in arr) {
        sp.apply {
            mContext?.let {
                setSpan(
                        CustomImageSpan(it, R.drawable.icon_divide_in_like, 0f),
                        i ,
                        i  + 1,
                        Spannable.SPAN_INCLUSIVE_EXCLUSIVE
                )
            }
        }
    }
}