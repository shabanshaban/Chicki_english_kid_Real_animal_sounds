package com.farad.entertainment.kidsanimalenglish.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.farad.entertainment.kidsanimalenglish.base.BaseDao
import com.farad.entertainment.kidsanimalenglish.data.model.entity.WordGameEntity


@Dao
abstract class WordGameDao : BaseDao<WordGameEntity>() {
      @Query(" SELECT * FROM tbl_wordGame  ")
      abstract fun getAllWordLiveData(): LiveData<List<WordGameEntity>>

    @Query(" SELECT * FROM tbl_wordGame  ")
    abstract suspend fun getAllWordList(): List<WordGameEntity>
}

