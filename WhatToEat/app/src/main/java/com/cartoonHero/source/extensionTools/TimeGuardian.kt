package com.cartoonHero.source.extensionTools

import java.text.SimpleDateFormat
import java.util.*

object TimeGuardian {
    @JvmStatic
    fun timeStampToDate(time: Long, locale: Locale): String {
        //val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale)
        val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm", locale)
        return simpleDateFormat.format(Date(time))
    }
    @JvmStatic
    fun dateToTimeStamp(date: String, locale: Locale): Long {
        val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm", locale)
        /// 輸出為毫秒為單位
        return simpleDateFormat.parse(date).time
    }

    fun String.toDate(dateFormat: String = "yyyy/MM/dd HH:mm", timeZone: TimeZone = TimeZone.getTimeZone("UTC")): Date {
        val parser = SimpleDateFormat(dateFormat, Locale.getDefault())
        parser.timeZone = timeZone
        return parser.parse(this)
    }

    fun Date.formatTo(dateFormat: String, timeZone: TimeZone = TimeZone.getDefault()): String {
        val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
        formatter.timeZone = timeZone
        return formatter.format(this)
    }
}