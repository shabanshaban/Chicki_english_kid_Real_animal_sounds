package com.farad.entertainment.kidsanimalenglish.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.farad.entertainment.kidsanimalenglish.base.BaseDao
import com.farad.entertainment.kidsanimalenglish.data.model.entity.ScratchGameEntity


@Dao
abstract class ScratchGameDao : BaseDao<ScratchGameEntity>() {
      @Query(" SELECT * FROM tbl_scratchGame  ")
      abstract fun getAllScratchLiveData(): LiveData<List<ScratchGameEntity>>

    @Query(" SELECT * FROM tbl_scratchGame  ")
    abstract suspend fun getScratchList(): List<ScratchGameEntity>

    @Query("DELETE FROM tbl_scratchGame")
    abstract suspend fun nukeTable()
}

