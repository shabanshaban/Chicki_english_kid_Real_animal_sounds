package com.farad.entertainment.kidsanimalenglish.ui.activity.main

import androidx.lifecycle.viewModelScope
import com.farad.entertainment.kidsanimalenglish.base.BaseViewModel
import com.farad.entertainment.kidsanimalenglish.data.manager.SharedPreferencesManager
import com.farad.entertainment.kidsanimalenglish.data.model.VideoYoutubeModel
import com.farad.entertainment.kidsanimalenglish.data.model.entity.UserInfoModel
import com.farad.entertainment.kidsanimalenglish.data.repository.GameRepository
import com.farad.entertainment.kidsanimalenglish.di.viewModelModule
import kotlinx.coroutines.launch

class ViewModelMain(private val repository: GameRepository,private val  sharedPreferencesManager: SharedPreferencesManager) : BaseViewModel() {


    fun saveUserInfo(androidID: String) {

        viewModelScope.launch {

            if (sharedPreferencesManager.isSignUp.not()){
                val listNumberItemOpen = ArrayList<String>()



                val userInfoModel = UserInfoModel(androidID, "0", listNumberItemOpen)

                repository.saveUserInfo(userInfoModel)
            }

        }

    }

   suspend fun updateList(list: List<String>, deviceId: String, ) {

            repository.updateList(list,deviceId)

    }

    fun saveVideoYoutube(youtubeModel: VideoYoutubeModel){
        viewModelScope.launch {
            repository.saveVideoYoutube(youtubeModel)
        }
    }
    fun getUserInfoLiveData() = repository.getUserInfoLiveData()
   suspend fun getVideoYoutubeList() = repository.getVideoYoutubeList()
}