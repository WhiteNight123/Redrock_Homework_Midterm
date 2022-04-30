package com.example.linggan.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.widget.Toast

/**
 * ...
 * @author WhiteNight123(Guo XiaoQiang)
 * @email 1448375249@qq.com
 * @date 2022/4/30
 */

/**
 * 显示短时间的吐司
 *
 * @param text String, 显示的内容
 */
fun showToast(context: Context?, text: String) {
    if (context == null || TextUtils.isEmpty(text)) return
    if (Thread.currentThread() === Looper.getMainLooper().thread) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    } else {
        Handler(context.mainLooper).post {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        }
    }
}