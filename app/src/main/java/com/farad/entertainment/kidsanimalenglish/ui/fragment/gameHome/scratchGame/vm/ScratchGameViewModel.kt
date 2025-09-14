package com.farad.entertainment.kidsanimalenglish.ui.fragment.gameHome.scratchGame.vm

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.farad.entertainment.kidsanimalenglish.app.BaseApp
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.getListData
import com.farad.entertainment.kidsanimalenglish.data.model.entity.ScratchGame
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.StateGameScratch
import com.farad.entertainment.kidsanimalenglish.data.repository.GameRepository
import com.farad.entertainment.kidsanimalenglish.utils.SIZE_ITEM
import kotlinx.coroutines.launch

class ScratchGameViewModel(private val repository: GameRepository, private val baseApp: BaseApp) :
    AndroidViewModel(baseApp) {
    private val listWordRandom = ArrayList<ScratchGame>()

    private fun initWordGame(): ArrayList<ScratchGame> {
        baseApp.applicationContext.getListData().let { listAllAnimal ->

            val listWord = ArrayList<String>()

            listAllAnimal.forEach {
                listWord.add(it.title)
            }
            val listNumber = ArrayList<Int>()
            while (listNumber.size != SIZE_ITEM) {
                val randomNumber = (0..<SIZE_ITEM).random()
                if (!listNumber.contains(randomNumber)) {
                    listNumber.add(randomNumber)
                }
            }


            listNumber.forEachIndexed { index, it ->

                val answer = listWord[it]
                val selectAnimal = listAllAnimal[it]
                val listNumber4WordRandom = ArrayList<String>()

                while (listNumber4WordRandom.size != 3) {

                    val number = (0..<SIZE_ITEM).random()
                    if (!listNumber4WordRandom.contains(listWord[number])) {
                        listNumber4WordRandom.add(listWord[number])
                    }
                }
                listNumber4WordRandom.add(answer)
                val listFinalWord = ArrayList<String>()
                generate4NumberRandom().forEach { nnn ->
                    val word = listNumber4WordRandom[nnn]
                    listFinalWord.add(word)
                }


                val stageGame: StateGameScratch = if (index == 0) {
                    StateGameScratch.IS_OPEN
                } else {
                    StateGameScratch.IS_LOCK
                }

                selectAnimal.soundAnimal?.let {
                    val model = ScratchGame(
                        index.toLong(),
                        listFinalWord,
                        selectAnimal.bigImage,
                        it,
                        5f,
                        answer,
                        stageGame
                    )
                    listWordRandom.add(model)
                }


            }


        }
        return listWordRandom


    }

    init {
        saveAllScratchGame()
    }


    private fun generate4NumberRandom(): ArrayList<Int> {

        val listNumberRandom = ArrayList<Int>()



        while (listNumberRandom.size != 4) {
            val random = (0..3).random()
            if (!listNumberRandom.contains(random))
                listNumberRandom.add(random)
        }

        return listNumberRandom
    }

    fun getAllScratchGameLiveData() = repository.getScratchGameLiveData()
    suspend fun getScratchGameList() = repository.getScratchGameList()
    private fun saveAllScratchGame() {
        viewModelScope.launch {
            repository.getScratchGame().let {

                if (it.isEmpty()) {

                    repository.saveScratchGameAll(initWordGame())
                }
            }
        }
    }

    fun updateScratchGame(scratchGame: ScratchGame) {
        viewModelScope.launch {
            repository.updateScratchGame(scratchGame)
        }
    }
    fun nukeTable(){
        viewModelScope.launch {
            repository.nukeTable()
        }
    }

}