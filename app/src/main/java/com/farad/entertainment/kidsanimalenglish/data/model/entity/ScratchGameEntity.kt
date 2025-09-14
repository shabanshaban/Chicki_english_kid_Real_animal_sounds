package com.farad.entertainment.kidsanimalenglish.data.model.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.StateGameScratch
import kotlinx.parcelize.Parcelize

@Entity(tableName = "tbl_scratchGame")
data class ScratchGameEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "Id")
    var id: Long,
    @ColumnInfo(name = "word")
    val listWord: List<String>,
    @ColumnInfo(name = "isImage")
    val image: Int,
    @ColumnInfo(name = "soundAnimal")
    val soundAnimal: Int,
    @ColumnInfo(name = "rating")
    val rating: Float,
    @ColumnInfo(name = "answer")
    val answer: String,
    @ColumnInfo(name = "isOpen")
    val stateGame: StateGameScratch,
    @ColumnInfo(name = "idItem")
    var idItem: Int,

    )

@Parcelize
data class ScratchGame(
    val id: Long, val listWord: List<String>, val image: Int, val soundAnimal: Int,
    var rating: Float, val answer: String,
    var stageGame: StateGameScratch, var idItem: Int = 0
) : Parcelable


fun ScratchGame.toEntity(): ScratchGameEntity {
    return ScratchGameEntity(id, listWord, image, soundAnimal, rating, answer, stageGame, idItem)
}

fun ScratchGameEntity.toModel(): ScratchGame {
    return ScratchGame(id, listWord, image, soundAnimal, rating, answer, stateGame, idItem)
}