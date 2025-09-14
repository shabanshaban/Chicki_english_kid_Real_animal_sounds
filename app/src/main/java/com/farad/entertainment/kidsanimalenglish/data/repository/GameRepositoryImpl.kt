package com.farad.entertainment.kidsanimalenglish.data.repository

import androidx.lifecycle.map
import com.farad.entertainment.kidsanimalenglish.data.dataSource.DataSourceLocalScratchGame
import com.farad.entertainment.kidsanimalenglish.data.dataSource.DataSourceLocalUserInfo
import com.farad.entertainment.kidsanimalenglish.data.dataSource.DataSourceLocalVideoYouTube
import com.farad.entertainment.kidsanimalenglish.data.dataSource.DataSourceLocalWordGameHome
import com.farad.entertainment.kidsanimalenglish.data.model.VideoYoutubeModel
import com.farad.entertainment.kidsanimalenglish.data.model.entity.ScratchGame
import com.farad.entertainment.kidsanimalenglish.data.model.entity.UserInfoModel
import com.farad.entertainment.kidsanimalenglish.data.model.entity.WordGame
import com.farad.entertainment.kidsanimalenglish.data.model.entity.toModel
import com.farad.entertainment.kidsanimalenglish.data.model.toModel

class GameRepositoryImpl(

    private val wordLocal: DataSourceLocalWordGameHome,
    private val scratchGameLocal: DataSourceLocalScratchGame,
    private val userInfo: DataSourceLocalUserInfo,
    private val videoYoutube: DataSourceLocalVideoYouTube,
    private val videoYouTube: DataSourceLocalVideoYouTube,
) : GameRepository {

    override suspend fun saveWordAll(listWordGame: List<WordGame>) =
        wordLocal.saveWordGameAll(listWordGame)

    override suspend fun getAllWord() = wordLocal.getAllWord()
    override fun getAllWordLiveData() = wordLocal.getAllWordLiveData()
    override suspend fun updateWordGame(wordGame: WordGame) = wordLocal.updateWord(wordGame)
    override suspend fun saveScratchGameAll(listScratchGame: List<ScratchGame>) =
        scratchGameLocal.saveScratchGameAll(listScratchGame)

    override suspend fun getScratchGame() = scratchGameLocal.getAllScratchGame()

    override fun getScratchGameLiveData() = scratchGameLocal.getAllScratchGameLiveData()
    override suspend fun getScratchGameList() = scratchGameLocal.getAllScratchGame()

    override suspend fun updateScratchGame(scratchGame: ScratchGame) =
        scratchGameLocal.updateScratch(scratchGame)

    override suspend fun getUserInfoList() = userInfo.getUserInfoList()

    override   fun getUserInfoLiveData() = userInfo.getUserInfoLiveData().map {
        it?.toModel()
    }

    override suspend fun updateUserInfo(userInfoModel: UserInfoModel) =
        userInfo.updateUserInfo(userInfoModel)

    override suspend fun saveUserInfo(userInfoModel: UserInfoModel) {
        userInfo.saveUserInfo(userInfoModel)
    }

    override suspend fun updateCoin(coin: String, deviceId:String) {
         userInfo.updateCoin(coin,deviceId)
    }

    override suspend fun updateList(listItem: List<String>, deviceId: String) {
         userInfo.updateList(listItem, deviceId)
    }
    override suspend fun nukeTable() = scratchGameLocal.nukeTable()

    override suspend fun getVideoYoutubeList(): List<VideoYoutubeModel> {
      return  videoYoutube.getListVideo().map { it.toModel() }
    }

    override suspend fun saveVideoYoutube(videoYoutubeModel: VideoYoutubeModel) {
        videoYouTube.saveVideoYoutube(videoYoutubeModel)
    }
}