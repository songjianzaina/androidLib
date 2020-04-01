package com.insworks.lib_base.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commitNow

/**
 * 佛祖保佑         永无BUG
 *
 * @author Created by joker on 2019-06-13
 * 针对startActivityForResult 封装
 */

private const val ResultTag = "StartActivityForResult"


/**
 * 针对Activity只有一个需要[startActivityForResult]的情况
 */
fun Context?.startFragmentForResult(clazz: Class<*>, back: (requestCode: Int, data: Intent?) -> Unit = { _, _ -> }) {
    startFragmentForResult(clazz, hashCode(), back)
}

fun Context?.startFragmentForResult(clazz: Class<*>, requestCode: Int, back: (requestCode: Int, data: Intent?) -> Unit = { _, _ -> }) {
    startFragmentForResult(Intent(this, clazz), requestCode, back)
}

fun Context?.startFragmentForResult(intent: Intent, requestCode: Int, back: (requestCode: Int, data: Intent?) -> Unit = { _, _ -> }) {
    (this as? FragmentActivity)?.supportFragmentManager?.let { manager ->
        val fragment = (manager.findFragmentByTag(ResultTag) as? StartActivityForResult)
                ?: StartActivityForResult().apply {
                    manager.commitNow(allowStateLoss = true) {
                        add(this@apply, ResultTag)
                    }
                }
        fragment.startFragmentForResult(intent, requestCode, back)

    }
}

class StartActivityForResult : Fragment() {

    private var backResult: (requestCode: Int, data: Intent?) -> Unit = { _, _ -> }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            backResult(requestCode, data)
        }
    }

    fun startFragmentForResult(intent: Intent, requestCode: Int, back: (requestCode: Int, data: Intent?) -> Unit = { _, _ -> }) {
        backResult = back

        if(isAdded ){
            startActivityForResult(intent, requestCode)
        }
    }

}