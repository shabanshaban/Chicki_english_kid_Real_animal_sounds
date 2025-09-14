package com.farad.entertainment.kidsanimalenglish.data.dataSource

import com.farad.entertainment.kidsanimalenglish.data.db.dao.VideoYoutubeDao
import com.farad.entertainment.kidsanimalenglish.data.model.VideoYoutubeModel
import com.farad.entertainment.kidsanimalenglish.data.model.toEntity

class DataSourceLocalVideoYouTube(
    private val videoYoutubeDao: VideoYoutubeDao,
) {
    suspend fun saveVideoYoutube(youtubeModel: VideoYoutubeModel) =
        videoYoutubeDao.insert(youtubeModel.toEntity())


    suspend fun getListVideo()=videoYoutubeDao.getVideoYoutubeList()
}


