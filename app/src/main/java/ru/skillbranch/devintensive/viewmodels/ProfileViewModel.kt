package ru.skillbranch.devintensive.viewmodels

import android.app.AppComponentFactory
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.skillbranch.devintensive.models.Profile
import ru.skillbranch.devintensive.repositories.PreferenesRepository

class ProfileViewModel : ViewModel() {

    private val repository: PreferenesRepository = PreferenesRepository
    private val profileDate = MutableLiveData<Profile>()
    private val appTheme = MutableLiveData<Int>()
    private val repositoryError = MutableLiveData<Boolean>()
    private val isRepoError = MutableLiveData<Boolean>()

    init {
        Log.d("M_ProfileViewModel", "init view model")
        profileDate.value = repository.getProfile()
        appTheme.value = repository.getAppTheme()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("M_ProfileViewModel", "view model cleared")
    }

    fun getProfileData() : LiveData<Profile> = profileDate

    fun getTheme() : LiveData<Int> = appTheme

    fun getRepositoryError(): LiveData<Boolean> = repositoryError

    fun getIsRepoError():LiveData<Boolean> = isRepoError

    fun saveProfileData(profile:Profile){
        repository.saveProfile(profile)
        profileDate.value = profile
    }

    fun switchTheme() {
        if(appTheme.value == AppCompatDelegate.MODE_NIGHT_YES){
            appTheme.value = AppCompatDelegate.MODE_NIGHT_NO
        }else{
            appTheme.value = AppCompatDelegate.MODE_NIGHT_YES
        }
        repository.saveAppTheme(appTheme.value!!)
    }

    fun onRepoChanged(repository: String) {
        repositoryError.value = isRepoValid(repository)
    }


    fun onRepoEditCompleted(isError: Boolean) {
        isRepoError.value = isError
    }

    private fun isRepoValid(repoText: String): Boolean {
        val regex = """^(?:https://)?(?:www.)?(?:github.com/)[^/|\\s]+(?<!${getExceptionsWords()})(?:/)?$""".toRegex()
        return (repoText.isNotEmpty() && !regex.matches(repoText))
    }

    private fun getExceptionsWords(): String {
        return arrayOf(
            "enterprise", "features", "topics", "collections", "trending", "events", "marketplace", "pricing",
            "nonprofit", "customer-stories", "security", "login", "join"
        ).joinToString("|\\b","\\b")
    }
}