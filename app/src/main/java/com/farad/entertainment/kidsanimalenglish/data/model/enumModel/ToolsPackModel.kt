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
            urlVideo = "https://s1.uupload.ir/files/mrghooghooli/mrghooghooli/video_origami_introduce/video_origami_introduce_"+1+".mp4"
        ),
        OrigamiParkModel(
            ListOrigamiParkEnum.FISH_LAKE,
            R.drawable.video_origami_pic_2,
            size = SizeTypeTools.SMALL,
            urlVideo = "https://s1.uupload.ir/files/mrghooghooli/mrghooghooli/video_origami_introduce/video_origami_introduce_"+ 2 +".mp4"
        ),
        OrigamiParkModel(
            ListOrigamiParkEnum.ANIMAL_MOUNTAIN,
            R.drawable.video_origami_pic_3,
            size = SizeTypeTools.SMALL,
            urlVideo = "https://s1.uupload.ir/files/mrghooghooli/mrghooghooli/video_origami_introduce/video_origami_introduce_"+ 3 +".mp4"
        ),
        OrigamiParkModel(
            ListOrigamiParkEnum.VIDEO_1,
            R.drawable.video_origami_pic_4,
            size = SizeTypeTools.NORMAL,
            urlVideo = "https://s1.uupload.ir/files/mrghooghooli/mrghooghooli/video_origami_introduce/video_origami_introduce_"+ 4 +".mp4"
        ),
        OrigamiParkModel(
            ListOrigamiParkEnum.VIDEO_2,
            R.drawable.video_origami_pic_5,
            size = SizeTypeTools.NORMAL,
            urlVideo = "https://s1.uupload.ir/files/mrghooghooli/mrghooghooli/video_origami_introduce/video_origami_introduce_"+ 5 +".mp4"
        )


    )


}