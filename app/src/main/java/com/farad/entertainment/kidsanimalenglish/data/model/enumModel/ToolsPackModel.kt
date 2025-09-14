package com.farad.entertainment.kidsanimalenglish.data.model.enumModel

import android.os.Parcelable
import androidx.annotation.Keep
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.ui.fragment.kindergarten.parkOrigami.OrigamiParkFragment
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
enum class ListOrigamiParkEnum : Parcelable {
    PARK_ANIMAL,
    FISH_LAKE,
    ANIMAL_MOUNTAIN,
    VIDEO_1,
    VIDEO_2,
}

data class OrigamiParkModel(
    val id: ListOrigamiParkEnum,
    val image: Int,
    var size: SizeTypeTools,
    var urlVideo: String,
)

fun OrigamiParkFragment.getOrigamiParkList(): ArrayList<OrigamiParkModel> {
    return arrayListOf(
        OrigamiParkModel(
            ListOrigamiParkEnum.PARK_ANIMAL,
            R.drawable.video_origami_pic_1,
            size = SizeTypeTools.SMALL,
            urlVideo = "Tgn6lKyMBck"
        ),
        OrigamiParkModel(
            ListOrigamiParkEnum.FISH_LAKE,
            R.drawable.video_origami_pic_2,
            size = SizeTypeTools.SMALL,
            urlVideo = "mK1stA4KMFc"
        ),
        OrigamiParkModel(
            ListOrigamiParkEnum.ANIMAL_MOUNTAIN,
            R.drawable.video_origami_pic_3,
            size = SizeTypeTools.SMALL,
            urlVideo = "e66eV37LzPM"
        ),
        OrigamiParkModel(
            ListOrigamiParkEnum.VIDEO_1,
            R.drawable.video_origami_pic_4,
            size = SizeTypeTools.NORMAL,
            urlVideo = "OUW9p4B73O8"
        ),
        OrigamiParkModel(
            ListOrigamiParkEnum.VIDEO_2,
            R.drawable.video_origami_pic_5,
            size = SizeTypeTools.NORMAL,
            urlVideo = "nFhb3wQ3IKM"
        )


    )


}