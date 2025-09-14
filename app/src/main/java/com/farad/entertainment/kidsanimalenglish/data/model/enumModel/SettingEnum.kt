package com.farad.entertainment.kidsanimalenglish.data.model.enumModel

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize


@Parcelize
@Keep
enum class TypeShowImageAnimal(val value: Int) : Parcelable {
    REAL(1),
    ANIMATED(2),
}

@Parcelize
@Keep
enum class ZoomImageAnimal(val value: Int) : Parcelable {
    ZOOM_IN(1),
    ZOOM_OUT(2),
}

@Parcelize
@Keep
enum class PlaySoundAnimal(val value: Int) : Parcelable {
    ENGLISH_AND_FRANCE(1),
    FRANCE(2),
    ENGLISH(3),
}