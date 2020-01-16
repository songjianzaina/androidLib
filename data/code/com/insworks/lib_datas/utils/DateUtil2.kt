package com.insworks.lib_datas.utils

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.util.*


/**
 * 根据当前时间点返回 当天 昨天 前天
 * 返回 0便是当天 1为昨天
 */
fun Date?.amountDays(): Int {
    if (this != null ) {
        //当天0点
        val zero = getZeroTime(0)
        //昨天0点
        val yezero = getZeroTime(-24)
        when {
            this.time > zero -> return 0 //当天
            this.time in (yezero + 1) until zero -> return 1 //昨天
            this.time < yezero -> return 2 //前天
        }
    }
    return 0
}
fun getZeroTime(hour:Int): Long {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.HOUR_OF_DAY, hour)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    //当天0点
    return calendar.timeInMillis

}
