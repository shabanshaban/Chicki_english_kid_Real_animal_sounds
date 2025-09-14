package com.farad.entertainment.kidsanimalenglish.data.model.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "tbl_wordGame")
data class WordGameEntity (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "Id")
    var id: Long ,
    @ColumnInfo(name = "word")
    val word: String,
    @ColumnInfo(name = "wordConfusion")
    val wordConfusion: String,
    @ColumnInfo(name = "isOpen")
    val isOpen: Boolean,
    @ColumnInfo(name = "idItem")
    var idItem: Int ,
)

@Parcelize
data class WordGame(val id:Long, val word:String, val wordConfusion:String,
                    var isOpen:Boolean=false, var idItem:Int=0):Parcelable


fun WordGame.toEntity(): WordGameEntity {
 return   WordGameEntity(id,word,wordConfusion,isOpen,idItem)
}
fun WordGameEntity.toModel(): WordGame {
    return   WordGame(id,word,wordConfusion,isOpen,idItem)
}