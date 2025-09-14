package com.farad.entertainment.kidsanimalenglish.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

data class UserInfoModel(
    val id: String,
    val coin: String,
    val listItem: List<String>
)

/*@Entity(tableName = "tbl_UserInfo")
data class UserInfoEntity(
    @PrimaryKey
    @ColumnInfo(name = "Id")
    val id: String,
    @ColumnInfo(name = "Coin")
    val coin: String?="0",
    @ColumnInfo(name = "listItem")
    val listItem: List<String>?= emptyList()
)*/

@Entity(tableName = "tbl_UserInfo")
data class UserInfoEntity(
    @PrimaryKey
    @ColumnInfo(name = "Id")
    val id: String,
    @ColumnInfo(name = "Coin")
    val coin: String,
    @ColumnInfo(name = "listItem")
    val listItem: List<String> = emptyList()
)

fun UserInfoEntity.toModel() = UserInfoModel(id, coin, listItem)
fun UserInfoModel.toEntity() = UserInfoEntity(id, coin, listItem)
