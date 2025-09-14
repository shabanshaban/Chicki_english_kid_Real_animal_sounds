package com.farad.entertainment.kidsanimalenglish.data.dataSource

import androidx.lifecycle.map
import com.farad.entertainment.kidsanimalenglish.data.db.dao.WordGameDao
import com.farad.entertainment.kidsanimalenglish.data.model.entity.WordGame
import com.farad.entertainment.kidsanimalenglish.data.model.entity.toEntity
import com.farad.entertainment.kidsanimalenglish.data.model.entity.toModel

class DataSourceLocalWordGameHome(
    private val wordGameDao: WordGameDao,
) {
    suspend fun saveWordGameAll(listWordGame: List<WordGame>) {

        wordGameDao.insertAll(listWordGame.map { it.toEntity() })
    }
      suspend fun getAllWord() =wordGameDao.getAllWordList().map { it.toModel() }
        fun getAllWordLiveData() =wordGameDao.getAllWordLiveData().map { it.map { it.toModel() } }
       suspend fun getAllWordList() =wordGameDao.getAllWordList().map {  it.toModel()  }

    suspend fun updateWord(wordGame: WordGame) = wordGameDao.update(wordGame.toEntity())
}


