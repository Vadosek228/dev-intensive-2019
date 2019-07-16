package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.absoluteValue
import kotlin.math.sign

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern:String="HH:mm:ss dd.MM.yy"):String{
    val dateFormat =
        when(pattern) {
            "HH:mm" -> SimpleDateFormat("HH:mm", Locale("ru"))
            else -> SimpleDateFormat(pattern, Locale("ru"))
        }

    return dateFormat.format(this)
}

//разбиение даты на несколько строк
fun Date.stroke(pattern: String?):Pair<String?, String?>{

    val formatPattern : List<String>? = pattern?.split(" ")

    var fullTime = formatPattern?.getOrNull(0)
    var lastTime = formatPattern?.getOrNull(1)

    if (fullTime.isNullOrBlank()) fullTime = ""
    if (lastTime.isNullOrBlank()) lastTime = ""

    return fullTime to lastTime
}

//позволяет добовлять к объекту дату, которая будет изменить сдвиг
fun Date.add(value:Int, units: TimeUnits = TimeUnits.SECOND) : Date{ //по умолчанию - секунды
    var time = this.time

    time += when(units){ //принимает единицу измерения
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }

    this.time = time

    return this
}

//преобразовывает как расширение дату для пользователя
fun Date.humanizeDiff(date: Date = Date()): String? {
    val sign = (date.time - this.time).sign
    val diff = (date.time - this.time).absoluteValue
    val diffSecond = diff / SECOND
    val diffMinute = diff / MINUTE
    val diffHour = diff / HOUR
    val diffDay = diff / DAY

    return when {
        diffSecond in (0..1) ->         if (sign < 0) "вот-вот случится" else "только что"
        diffSecond in (1..45) ->        if (sign < 0) "через несколько секунд" else "несколько секунд назад"
        diffSecond in (45..75) ->       if (sign < 0) "через минуту" else "минуту назад"
        diffSecond in (75..45 * 60) ->  if (sign < 0) "через ${toRightTime(diffMinute, TimeUnits.MINUTE)}" else "${toRightTime(diffMinute, TimeUnits.MINUTE)} назад"
        diffMinute in (45..75) ->       if (sign < 0) "через час" else "час назад"
        diffMinute in (75..22 * 60) ->  if (sign < 0) "через ${toRightTime(diffHour, TimeUnits.HOUR)}" else "${toRightTime(diffHour, TimeUnits.HOUR)} назад"
        diffHour in (22..26) ->         if (sign < 0) "через день" else "день назад"
        diffHour in (26..360 * 24) ->   if (sign < 0) "через ${toRightTime(diffDay, TimeUnits.DAY)}" else "${toRightTime(diffDay, TimeUnits.DAY)} назад"
        diffDay > 360 ->                if (sign < 0) "более чем через год" else "более года назад"

        else -> "нипанятна"
    }
}

fun toRightTime(value: Long, timeUnit: TimeUnits): String {
    if (value in 11..19) {
        return "$value ${timeUnit.many}"
    }

    val lastIndex = value.toString().length

    return when (value.toString().substring(lastIndex - 1, lastIndex).toInt()) {
        1 -> "$value ${timeUnit.one}"
        in 2..4 -> "$value ${timeUnit.few}"
        else -> "$value ${timeUnit.many}"
    }
}

//класс перечеслений (жестко объявим какие единицы можно использовать в данной функции)
enum class TimeUnits(val one: String, val few: String, val many: String) {
    SECOND("секунду", "секунды", "секунд"),
    MINUTE("минуту", "минуты", "минут"),
    HOUR("час", "часа", "часов"),
    DAY("день", "дня", "дней");

    fun plural(value: Int): String {
        return toRightTime(value.toLong(), this)
    }
}


