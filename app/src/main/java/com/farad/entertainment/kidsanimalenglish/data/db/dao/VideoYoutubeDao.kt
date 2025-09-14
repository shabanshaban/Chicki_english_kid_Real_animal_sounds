package com.farad.entertainment.kidsanimalenglish.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.farad.entertainment.kidsanimalenglish.base.BaseDao
import com.farad.entertainment.kidsanimalenglish.data.model.VideoYoutubeEntity


@Dao
abstract class VideoYoutubeDao : BaseDao<VideoYoutubeEntity>() {


    @Query(" SELECT * FROM tbl_VideoYoutube  ")
    abstract suspend fun getVideoYoutubeList(): List<VideoYoutubeEntity>


}

