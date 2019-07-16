package ru.skillbranch.devintensive.extensions

import devintensive.skillbranch.ru.devintensive.utils.Utils
import ru.skillbranch.devintensive.models.User
import ru.skillbranch.devintensive.models.UserView

fun User.toUserView() : UserView {
    val nickName = Utils.transliteration("$firstName $lastName")
    val initials = Utils.toInitials(firstName, lastName)
    val status = if(lastVisit == null) "Еще ни разу не был" else if (isOnlain) "online" else "Последний раз был в сети " +
            "${lastVisit.humanizeDiff()}"

    return UserView(
        id,
        fullName = "$firstName $lastName",
        avatar = avatar,
        nickName = nickName,
        initials = initials,
        status = status )
}

