package ru.skillbranch.devintensive.repositories

import android.app.WallpaperInfo
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatDelegate
import ru.skillbranch.devintensive.App
import ru.skillbranch.devintensive.models.Profile

//реализация сохранения данных в ПреференсисРепозитории
object PreferenesRepository {
    private const val FIRST_NAME = "FIRST_NAME"
    private const val LAST_NAME = "LAST_NAME"
    private const val ABOUT = "ABOUT"
    private const val REPOSITORY = "REPOSITORY"
    private const val RATING = "RATING"
    private const val RESPECT = "RESPECT"
    private const val APP_THEME = "APP_THEME"

    private val pref : SharedPreferences by lazy {
        val ctx = App.applicationContext()//обратились к контексту приложения и передали ее в контекст менеджер для получения дефолтного значения
        PreferenceManager.getDefaultSharedPreferences(ctx)
    }

    fun getProfile(): Profile = Profile(
        pref.getString(FIRST_NAME, "")!!,
        pref.getString(LAST_NAME, "")!!,
        pref.getString(ABOUT, "")!!,
        pref.getString(REPOSITORY, "")!!,
        pref.getInt(RATING, 0),
        pref.getInt(RESPECT, 0)
    )

    fun saveProfile(profile: Profile) {
        with(profile){
            putValue(FIRST_NAME to firstName)
            putValue(LAST_NAME to lastName)
            putValue(ABOUT to about)
            putValue(REPOSITORY to repository)
            putValue(RATING to rating)
            putValue(RESPECT to respect)
        }
    }

    //метод для записи и сохранения данных в SharedPreference
    private fun putValue(pair: Pair<String, Any>) = with(pref.edit()){
        val key = pair.first
        val value = pair.second

        when(value){
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            is Boolean -> putBoolean(key, value)
            is Long -> putLong(key, value)
            is Float -> putFloat(key, value)
            else -> error("Only primitives types can be stored in Shared Preferences")
        }

        apply()
    }

    fun saveAppTheme(theme: Int) {
        putValue(APP_THEME to theme)
    }

    fun getAppTheme() : Int = pref.getInt(APP_THEME, AppCompatDelegate.MODE_NIGHT_NO)

}