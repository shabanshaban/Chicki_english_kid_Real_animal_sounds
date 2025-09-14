package com.farad.entertainment.kidsanimalenglish.data.model.enumModel

import android.content.Context
import android.os.Parcelable
import androidx.annotation.Keep
import com.farad.entertainment.kidsanimalenglish.R
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
enum class ListKindergarten : Parcelable {
    ORIGAMI,
    CREATIVITY,
    PAINTING,
    HAND_PRINT,
    STATUE,
    STORIES
}

@Parcelize
data class KindergartenModel(
    val id: ListKindergarten,
    val title: String,
    val bgItem: Int,
    val icon: Int,
    val iconFilm: Int,



) : Parcelable

fun Context.getKindergarten(): ArrayList<KindergartenModel> {

    return arrayListOf(
        KindergartenModel(
            ListKindergarten.ORIGAMI,
            getString(R.string.origami_text),
            R.drawable.bg_rounded_ripple_shape_bg_kinder_item2,
            R.drawable.kinder_origami_icon,
            R.drawable.icon_origami
        ),
        KindergartenModel(
            ListKindergarten.CREATIVITY,
            getString(R.string.creativity),
            R.drawable.bg_rounded_ripple_shape_bg_kinder_item3,
            R.drawable.kinder_creativity_icon,
            R.drawable.kinder_creativity_icon_header
        ),
        KindergartenModel(
            ListKindergarten.PAINTING,
            getString(R.string.painting),
            R.drawable.bg_rounded_ripple_shape_bg_kinder_item1,
            R.drawable.kinder_draw_icon,
            R.drawable.icon_draw_header
        ),
        KindergartenModel(
            ListKindergarten.HAND_PRINT,
            getString(R.string.hand_print),
            R.drawable.bg_rounded_ripple_shape_bg_kinder_item4,
            R.drawable.kinder_hand_print_icon,
            R.drawable.icon_hand_print_header
        ),
        KindergartenModel(
            ListKindergarten.STATUE,
            getString(R.string.statue),
            R.drawable.bg_rounded_ripple_shape_bg_kinder_item5,
            R.drawable.kinder_statue_icon,
            R.drawable.icon_statue_header
        ),
        /*  KindergartenModel(
              ListKindergarten.STORIES,
              getString(R.string.stories),
              R.drawable.bg_rounded_ripple_shape_bg_kinder_item6,
              R.drawable.kinder_story_icon,
              R.drawable.icon_story_header
          ),*/

    )
}



fun ListKindergarten.getLinkVideo(idAnimal: Int): String {
    var linkVideo = ""
    if (idAnimal>=0) {
        when (this) {
            ListKindergarten.ORIGAMI -> {
                linkVideo = "https://s1.uupload.ir/files/mrghooghooli/mrghooghooli/real_origami/real_origami_${idAnimal+1}.mp4"

            }

            ListKindergarten.CREATIVITY -> {

                linkVideo = "https://s1.uupload.ir/files/mrghooghooli/mrghooghooli/real_creativity/real_creativity_${idAnimal+1}.mp4"

            }

            ListKindergarten.PAINTING -> {
                val paintList=ArrayList<String>()

                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_1.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_2.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_3.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_4.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_5.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_6.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_7.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_8.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_9.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_10.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_11.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_12.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_13.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_14.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_15.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_16.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_17.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_18.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_19.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_20.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_21.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_22.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_23.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_24.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_25.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_26.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_27.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_28.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_29.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_30.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_31.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_32.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_33.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_34.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_35.mp4")
                paintList.add("https://s27.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_36.mp4")
                paintList.add("https://s27.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_37.mp4")
                paintList.add("https://s27.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_38.mp4")
                paintList.add("https://s27.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_39.mp4")
                paintList.add("https://s27.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_40.mp4")
                paintList.add("https://s27.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_41.mp4")
                paintList.add("https://s27.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_42.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_43.mp4")
                paintList.add("https://s27.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_44.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_45.mp4")
                paintList.add("https://s27.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_46.mp4")
                paintList.add("https://s27.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_47.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_48.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_49.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_50.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_51.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_52.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_53.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_54.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_55.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_56.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_57.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_58.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_59.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_60.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_61.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_62.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_63.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_64.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_65.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_66.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_67.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_68.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_69.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_70.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_71.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_72.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_73.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_74.mp4")
                paintList.add("https://s31.uupload.ir/files/mrghooghooli/mrghooghooli/real_draw_new/draw_75.mp4")
                linkVideo = paintList[idAnimal]
            }

            ListKindergarten.HAND_PRINT -> {
                linkVideo = "https://s1.uupload.ir/files/mrghooghooli/mrghooghooli/hand_print/hand_print_${idAnimal+1}.mp4"


            }

            ListKindergarten.STATUE -> {
                linkVideo = "https://s1.uupload.ir/files/mrghooghooli/mrghooghooli/real_statue/real_statue_${idAnimal+1}.mp4"

            }

            ListKindergarten.STORIES -> {
                linkVideo = "https://s5.uupload.ir/files/mrghooghooli/mrghooghooli/ghesseh_nazeri/gh_e_${idAnimal + 1}.mp4"
            }
        }
    }
    return linkVideo
}