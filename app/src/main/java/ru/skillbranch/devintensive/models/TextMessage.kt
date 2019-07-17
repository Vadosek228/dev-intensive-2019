package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.extensions.humanizeDiff
import java.util.*

class TextMessage (
    id:String,
    from:User?,
    chat:Chat,
    isInkoming:Boolean = false, //исходящее сообщение
    date: Date = Date(), //когда было отправлено сообщение
    var text:String? //text message
) : BaseMessage(id, from, chat, isInkoming, date){
    override fun formateMessage(): String = "id:$id ${from?.firstName}" +
            " ${if (isInkoming) "получил" else "отправил"} сообщение \"$text\" ${date.humanizeDiff()}"
}