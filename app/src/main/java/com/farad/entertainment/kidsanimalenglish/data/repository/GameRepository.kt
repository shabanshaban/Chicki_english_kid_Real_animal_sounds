package com.farad.entertainment.kidsanimalenglish.data.repository

import androidx.lifecycle.LiveData
import com.farad.entertainment.kidsanimalenglish.data.model.VideoYoutubeModel
import com.farad.entertainment.kidsanimalenglish.data.model.entity.ScratchGame
import com.farad.entertainment.kidsanimalenglish.data.model.entity.UserInfoEntity
import com.farad.entertainment.kidsanimalenglish.data.model.entity.UserInfoModel
import com.farad.entertainment.kidsanimalenglish.data.model.entity.WordGame

interface GameRepository {


    suspend fun saveWordAll(listWordGame: List<WordGame>)

    suspend fun getAllWord():List<WordGame>
      fun getAllWordLiveData():LiveData<List<WordGame>>

    suspend  fun updateWordGame(wordGame: WordGame)

        //********************************

    suspend fun saveScratchGameAll(listScratchGame: List<ScratchGame>)

    suspend fun getScratchGame():List<ScratchGame>
    fun getScratchGameLiveData():LiveData<List<ScratchGame>>
    suspend fun getScratchGameList():List<ScratchGame>

    suspend  fun updateScratchGame(scratchGame: ScratchGame)

    suspend fun getUserInfoList(): UserInfoEntity
      fun getUserInfoLiveData(): LiveData< UserInfoModel?>?
    suspend fun updateUserInfo(userInfoModel: UserInfoModel)
    suspend fun saveUserInfo(userInfoModel: UserInfoModel)
    suspend fun updateCoin(coin:String,deviceId:String)
    suspend fun updateList(listItem:List<String>, deviceId:String)
    suspend fun saveVideoYoutube(videoYoutubeModel: VideoYoutubeModel)
    suspend fun getVideoYoutubeList():List<VideoYoutubeModel>

    suspend fun nukeTable()

}