package com.farad.entertainment.kidsanimalenglish.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.ListKindergarten

data class VideoYoutubeModel(val id: Long, val type: ListKindergarten)

@Entity(tableName = "tbl_VideoYoutube")
data class VideoYoutubeEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "Id")
    val id: Long,
    @ColumnInfo(name = "type")
    val type: ListKindergarten
)
// مایگریشن از نسخه 1 به 2
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // ایجاد جدول UserInfoEntity
        database.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `tbl_UserInfo` (
                `Id` TEXT NOT NULL,
                `Coin` TEXT NOT NULL,
                `listItem` TEXT NOT NULL,
                PRIMARY KEY(`Id`)
            )
            """.trimIndent()
        )

        // ایجاد جدول VideoYoutubeEntity
        database.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `tbl_VideoYoutube` (
                `Id` INTEGER NOT NULL,
                `type` TEXT NOT NULL,
                PRIMARY KEY(`Id`)
            )
            """.trimIndent()
        )
    }
}




fun VideoYoutubeModel.toEntity(): VideoYoutubeEntity {
  return  VideoYoutubeEntity(id, type)
}

fun VideoYoutubeEntity.toModel(): VideoYoutubeModel {
    return VideoYoutubeModel(id, type)
}