package com.farad.entertainment.kidsanimalenglish.data.model.enumModel

import android.content.Context
import android.os.Parcelable
import androidx.annotation.Keep
import com.farad.entertainment.kidsanimalenglish.utils.checkLanguage
import com.farad.entertainment.kidsanimalenglish.utils.getFromAssets
import com.farad.entertainment.kidsanimalenglish.utils.getImageDrawableByName
import com.farad.entertainment.kidsanimalenglish.utils.getMusicInRawByName
import com.farad.entertainment.kidsanimalenglish.utils.getStringByName
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
enum class ListAnimalEnum(val numberId: Int) : Parcelable {
    P_1(0),
    P_2(1),
    P_3(2),
    P_4(3),
    P_5(4),
    P_6(5),
    P_7(6),
    P_8(7),
    P_9(8),
    P_10(9),
    P_11(10),
    P_12(11),
    P_13(12),
    P_14(13),
    P_15(14),
    P_16(15),
    P_17(16),
    P_18(17),
    P_19(18),
    P_20(19),
    P_21(20),
    P_22(21),
    P_23(22),
    P_24(23),
    P_25(24),
    P_26(25),
    P_27(26),
    P_28(27),
    P_29(28),
    P_30(29),
    P_31(30),
    P_32(31),
    P_33(32),
    P_34(33),
    P_35(34),
    P_36(35),
    P_37(36),
    P_38(37),
    P_39(38),
    P_40(39),
    P_41(40),
    P_42(41),
    P_43(42),
    P_44(43),
    P_45(44),
    P_46(45),
    P_47(46),
    P_48(47),
    P_49(48),
    P_50(49),
    P_51(50),
    P_52(51),
    P_53(52),
    P_54(53),
    P_55(54),
    P_56(55),
    P_57(56),
    P_58(57),
    P_59(58),
    P_60(59),
    P_61(60),
    P_62(61),
    P_63(62),
    P_64(63),
    P_65(64),
    P_66(65),
    P_67(66),
    P_68(67),
    P_69(68),
    P_70(69),
    P_71(70),
    P_72(71),
    P_73(72),
    P_74(73),
    P_75(74),
}

@Parcelize
data class AnimalModel(
    val id: ListAnimalEnum,
    val title: String,
    val titleFrench: String,
    val image: Int,
    val bigImage: Int,
    val imageGif: String,
    val fullImage: String,
    val video: String,
    val soundAnimal: Int?,
    val soundNameEnglish: Int?,
    val soundNameFrench: Int?,
    val soundPlay: Int? = null,
    val textLrcItem: String,
    val imagePainting: String,
    val imageColoring: String,
    val imagePaintingSvg: Int,
    var isLock: Boolean = true

) : Parcelable


fun getListImageBigAnimal(): ArrayList<String> {
    val animalsGalleryLinksList = ArrayList<String>()
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_1" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_2" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_3" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_4" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_5" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_6" + ".jpg")
        animalsGalleryLinksList.add("https://s15.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_7_2" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_8" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_9" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_10" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_11" + ".jpg")
        animalsGalleryLinksList.add("https://s15.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_12_2" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_13" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_14" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_15" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_16" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_17" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_18" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_19" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_20" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_21" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_22" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_23" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_24" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_25" + ".jpg")
        animalsGalleryLinksList.add("https://s15.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_26_2" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_27" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_28" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_29" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_30" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_31" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_32" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_33" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_34" + ".jpg")
        animalsGalleryLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_35_1" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_36" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_37" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_38" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_39" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_40" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_41" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_42" + ".jpg")
        animalsGalleryLinksList.add("https://s15.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_43_2" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_44" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_45" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_46" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_47" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_48" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_49" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_50" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_51" + ".jpg")
        animalsGalleryLinksList.add("https://s15.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_52_2" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_53" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_54" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_55" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_56" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_57" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_58" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_59" + ".jpg")
        animalsGalleryLinksList.add("https://s15.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_60_2" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_61" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_62" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_63" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_64" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_65" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_66" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_67" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_68" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_69" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_70" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_71" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_72" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_73" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_74" + ".jpg")
        animalsGalleryLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_75" + ".jpg")

    return animalsGalleryLinksList
}

private fun getImagePaintingInAssets(id: Int): String {
    return "image_painting/p_paint_${id}.png"

}

private fun Context.getImageColoring(idAnimal: Int): String {
    return "assets://image_coloring/p_color_${idAnimal}.png"
}

private val animalsVideoLinksList = ArrayList<String>()
private val listGif = ArrayList<String>()
private val listData = ArrayList<AnimalModel>()

private fun addLinkVideo() {
    animalsVideoLinksList.clear()
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_1.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_2.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_3.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_4.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_5.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_6.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_7.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_8.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_9.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_10.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_11.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_12.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_13.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_14.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_15.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_16.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_17.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_18.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_19.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_20.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_21.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_22.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_23.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_24.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_25.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_26.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_27.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_28.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_29.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_30.mp4")
    animalsVideoLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/animal_31.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_32.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_33.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_34.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_35.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_36.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_37.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_38.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_39.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_40.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_41.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_42.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_43.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_44.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_45.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_46.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_47.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_48.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_49.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_50.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_51.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_52.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_53.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_54.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_55.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_56.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_57.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_58.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_59.mp4")
    animalsVideoLinksList.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/animal_60.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_61.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_62.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_63.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_64.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_65.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_66.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_67.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_68.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_69.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_70.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_71.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_72.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_73.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_74.mp4")
    animalsVideoLinksList.add("https://s21.uupload.ir/files/mrghooghooli/mrghooghooli/animal_video_new/1/animal_75.mp4")
}

private fun addLinkGif() {
    listGif.clear()


    (1..75).forEach {
        listGif.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/zoo%20town%20gif/g$it.gif")
    }

}

fun Context.getListData(): ArrayList<AnimalModel> {
    var soundNameFrench = "fa"
    checkLanguage(farsi = {
        soundNameFrench = "fa"
    }, english = {
        soundNameFrench = "fh"
    })
    if (listData.isEmpty()) {
        listData.clear()
        val listImageBig = getListImageBigAnimal()
        addLinkVideo()
        addLinkGif()
        (1..75).forEach { i ->



            val model = AnimalModel(
                ListAnimalEnum.valueOf("P_$i"),
                title = getStringByName("english_$i"),
                titleFrench = getStringByName("french_$i"),
                image = getImageDrawableByName("pa_$i"),
                bigImage = getImageDrawableByName("p_$i"),
                imageGif = listGif[i - 1],
                fullImage = listImageBig[i - 1],
                video = animalsVideoLinksList[i - 1],
                soundAnimal = getMusicInRawByName("s$i"),
                soundNameEnglish = getMusicInRawByName("en$i"),
                soundNameFrench = getMusicInRawByName("$soundNameFrench$i"),
                soundPlay = getMusicInRawByName("music$i"),
                textLrcItem = getFromAssets("lyric/music${i}.lrc"),
                imagePainting = getImagePaintingInAssets(i),
                imageColoring = getImageColoring(i),
                imagePaintingSvg = getImageDrawableByName("p_paint_$i"),
            )
            listData.add(model)

        }

    }
    return listData
}

