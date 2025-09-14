package com.farad.entertainment.kidsanimalenglish.data.dataSource

import androidx.lifecycle.map
import com.farad.entertainment.kidsanimalenglish.data.db.dao.ScratchGameDao
import com.farad.entertainment.kidsanimalenglish.data.model.entity.ScratchGame
import com.farad.entertainment.kidsanimalenglish.data.model.entity.toEntity
import com.farad.entertainment.kidsanimalenglish.data.model.entity.toModel

class DataSourceLocalScratchGame(
    private val scratchGameDao: ScratchGameDao,
) {
    suspend fun saveScratchGameAll(listWordGame: List<ScratchGame>) {

        scratchGameDao.insertAll(listWordGame.map { it.toEntity() })
    }

    suspend fun getAllScratchGame() = scratchGameDao.getScratchList().map { it.toModel() }
    fun getAllScratchGameLiveData() =
        scratchGameDao.getAllScratchLiveData().map { it.map { it.toModel() } }

    suspend fun updateScratch(scratchGame: ScratchGame) = scratchGameDao.update(scratchGame.toEntity())

    suspend fun nukeTable() = scratchGameDao.nukeTable()
}


