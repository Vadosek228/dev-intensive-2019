package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import kotlin.math.roundToInt

//закрытие клавиатуры
//fun Activity.hideKeyboard(activity: Activity){
//    val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
//    //Найдите текущий фокусированный вид, чтобы мы могли получить из него правильный маркер окна.
//    var view = activity.getCurrentFocus()
//    //Если ни один вид в данный момент не имеет фокуса, создайте новый, просто чтобы мы могли извлечь из него маркер окна
//    if (view == null) {
//        view = View(activity)
//    }
//    imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
//}

fun Activity.hideKeyboard() {
    val view = this.currentFocus
    view?.let { v ->
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.let { it.hideSoftInputFromWindow(v.windowToken, 0) }
    }
}

//проверка, открыта или не открыта клавиатура
fun Activity.getRootView(): View {
    return findViewById<View>(android.R.id.content)
}
fun Context.convertDpToPx(dp: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        this.resources.displayMetrics
    )
}

fun Activity.isKeyboardOpen(): Boolean {
    val visibleBounds = Rect()
    this.getRootView().getWindowVisibleDisplayFrame(visibleBounds)
    val heightDiff = getRootView().height - visibleBounds.height()
    val marginOfError = this.convertDpToPx(50F).roundToInt()
    return heightDiff > marginOfError
}

fun Activity.isKeyboardClosed(): Boolean {
    return !this.isKeyboardOpen()
}