package com.farad.entertainment.kidsanimalenglish.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.farad.entertainment.kidsanimalenglish.data.db.converter.ListStringConverters
import com.farad.entertainment.kidsanimalenglish.data.db.dao.ScratchGameDao
import com.farad.entertainment.kidsanimalenglish.data.db.dao.UserInfoDao
import com.farad.entertainment.kidsanimalenglish.data.db.dao.VideoYoutubeDao
import com.farad.entertainment.kidsanimalenglish.data.db.dao.WordGameDao
import com.farad.entertainment.kidsanimalenglish.data.model.VideoYoutubeEntity
import com.farad.entertainment.kidsanimalenglish.data.model.entity.ScratchGameEntity
import com.farad.entertainment.kidsanimalenglish.data.model.entity.UserInfoEntity
import com.farad.entertainment.kidsanimalenglish.data.model.entity.WordGameEntity


@Database(
    version = 2,
    exportSchema = true,
    entities = [
        WordGameEntity::class,
        ScratchGameEntity::class,
        UserInfoEntity::class,
        VideoYoutubeEntity::class,
    ],
)

@TypeConverters(
    ListStringConverters::class,
)

abstract class MainDatabase : RoomDatabase() {
    abstract fun wordGameDao(): WordGameDao
    abstract fun scratchGameDao(): ScratchGameDao
    abstract fun userInfoDao(): UserInfoDao
    abstract fun VideoYoutubeDao(): VideoYoutubeDao


    fun init() {}
}
