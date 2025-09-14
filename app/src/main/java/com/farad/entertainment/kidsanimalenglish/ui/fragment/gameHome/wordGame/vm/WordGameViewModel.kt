package com.farad.entertainment.kidsanimalenglish.ui.fragment.gameHome.wordGame.vm

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.farad.entertainment.kidsanimalenglish.data.model.entity.WordGame
import com.farad.entertainment.kidsanimalenglish.data.repository.GameRepository
import com.farad.entertainment.kidsanimalenglish.utils.SIZE_ITEM
import com.farad.entertainment.kidsanimalenglish.utils.getStringByName
import kotlinx.coroutines.launch

class WordGameViewModel(private val repository: GameRepository,  application: Application) : AndroidViewModel(application) {
    private val listWordRandom = ArrayList<WordGame>()
    val listSortOpenWord = ArrayList<WordGame>()



    init {
        saveAllWordGame(application.applicationContext)
    }

    private fun initWordGame(context: Context): ArrayList<WordGame> {


        val listWord = ArrayList<WordGame>()
        val listNumber = ArrayList<Long>()
        while (listNumber.size != SIZE_ITEM) {
            val randomNumber = (0..<SIZE_ITEM).random()
            if (!listNumber.contains(randomNumber.toLong())) {
                listNumber.add(randomNumber.toLong())
            }
        }
        context.applicationContext.apply {

            (1..SIZE_ITEM).forEach {
                val id = listNumber[it-1]
                val number=id+1
                listWord.add(
                    WordGame(
                        id,
                        getStringByName("english_$number"),
                        getStringByName("reveres_$number")
                    )
                )
            }

        }


        listWord.forEach { wordGame ->
            if (wordGame.word.length<12) {
                listWordRandom.add(wordGame)
            }
        }
        listWordRandom.first().isOpen=true

        return listWordRandom


    }
    fun getAllWordGameLiveData() = repository.getAllWordLiveData()
    private fun saveAllWordGame(context: Context) {
        viewModelScope.launch {
            repository.getAllWord().let {

                if (it.isEmpty()) {

                    repository.saveWordAll(initWordGame(context))
                }
            }
        }
    }

    fun updateWordGame(wordGame: WordGame) {
        viewModelScope.launch {

            repository.updateWordGame(wordGame)
        }
    }

}