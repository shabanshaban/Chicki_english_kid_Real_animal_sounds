package com.farad.entertainment.kidsanimalenglish.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class DataStoriesGameHomeModel(val id:Int, val title:String, val image:Int, val stories: String, val soundUrl:String):Parcelable
