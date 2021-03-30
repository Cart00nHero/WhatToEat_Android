package com.cartoonHero.source.props

import java.text.SimpleDateFormat
import java.util.*

fun Long.toDateText(datePattern: String = "yyyy/MM/dd HH:mm",locale: Locale): String {
    val simpleDateFormat = SimpleDateFormat(datePattern, locale)
    return simpleDateFormat.format(Date(this))
}
@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
fun String.toTimeStamp(datePattern: String = "yyyy/MM/dd HH:mm", locale: Locale): Long {
    val simpleDateFormat = SimpleDateFormat(datePattern, locale)
    return simpleDateFormat.parse(this).time
}
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
fun String.toDate(
    dateFormat: String = "yyyy/MM/dd HH:mm",
    timeZone: TimeZone = TimeZone.getTimeZone("UTC")): Date {
    val parser = SimpleDateFormat(dateFormat, Locale.getDefault())
    parser.timeZone = timeZone
    return parser.parse(this)
}
fun Date.formatTo(dateFormat: String, timeZone: TimeZone = TimeZone.getDefault()): String {
    val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
    formatter.timeZone = timeZone
    return formatter.format(this)
}