package com.farad.entertainment.kidsanimalenglish.data.db.converter

import androidx.room.TypeConverter
import app.king.mylibrary.ktx.deserializeList
import app.king.mylibrary.ktx.serializeList
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.ListKindergarten
import com.google.gson.Gson

class ListStringConverters {
    @TypeConverter
    fun fromString(value: String): List<String> {
        return value.deserializeList() ?: arrayListOf()
    }

    @TypeConverter
    fun toString(model: List<String>): String {
        return model.serializeList()
    }


    @TypeConverter
    fun fromListKindergarten(value: String): ListKindergarten {
        return Gson().fromJson(value, ListKindergarten::class.java)
    }

    @TypeConverter
    fun toListKindergarten(type: ListKindergarten): String {
        return Gson().toJson(type)
    }
}


