package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager

//закрытие клавиатуры
fun Activity.hideKeyboard(activity: Activity){
    val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    //Найдите текущий фокусированный вид, чтобы мы могли получить из него правильный маркер окна.
    var view = activity.getCurrentFocus()
    //Если ни один вид в данный момент не имеет фокуса, создайте новый, просто чтобы мы могли извлечь из него маркер окна
    if (view == null) {
        view = View(activity)
    }
    imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
}

////проверка, открыта или не открыта клавиатура
//fun Activity.isKeyboardOpen Activity.isKeyboardClosed(){
//
//}