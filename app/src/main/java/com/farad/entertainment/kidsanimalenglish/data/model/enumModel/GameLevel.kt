package com.farad.entertainment.kidsanimalenglish.data.model.enumModel

import android.os.Parcelable
import androidx.annotation.Keep
import com.farad.entertainment.kidsanimalenglish.R
import kotlinx.parcelize.Parcelize
@Parcelize
@Keep
enum class GameLevel(val value:Int) :Parcelable{
    Easy(R.string.easy),
    Medium(R.string.medium),
    Hard(R.string.hard),
    VeryHard(R.string.very_hard)
}

