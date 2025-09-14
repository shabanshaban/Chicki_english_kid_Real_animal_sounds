package com.farad.entertainment.kidsanimalenglish.kids_ringtone_english

import android.annotation.SuppressLint
import android.content.Context
import android.os.Parcelable
import androidx.annotation.Keep
import com.farad.entertainment.kidsanimalenglish.utils.getImageDrawableByName
import com.farad.entertainment.kidsanimalenglish.utils.getMusicInRawByName
import com.farad.entertainment.kidsanimalenglish.utils.getStringByName
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
enum class ListDataMainEnum(val numberId: Int) : Parcelable {


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
    P_76(75),
    P_77(76),
    P_78(77),
    P_79(78),
    P_80(79),
    P_81(80),
    P_82(81),
    P_83(82),
    P_84(83),
    P_85(84),
    P_86(85),
    P_87(86),
    P_88(87),
    P_89(88),
    P_90(89),
    P_91(90),
    P_92(91),
    P_93(92),
    P_94(93),
    P_95(94),
    P_96(95),
    P_97(96),
    P_98(97),
    P_99(98),
    P_100(99),
    P_101(100),
    P_102(101),
    P_103(102),
    P_104(103),
    P_105(104),
    P_106(105),
    P_107(106),
    P_108(107),
    P_109(108),
    P_110(109),
    P_111(110),
    P_112(111),
    P_113(112),
    P_114(113),
    P_115(114),

}

@Parcelize
data class DataMainModel(
    val id: ListDataMainEnum,
    val titleFarsi: String,
    val titleEnglish: String,
    val image: Int,
    val soundPlay: Int?

) : Parcelable


@SuppressLint("DiscouragedApi")
fun Context.getListData(): ArrayList<DataMainModel> {
    val listData = ArrayList<DataMainModel>()

    for (i in 1..114) {


        listData.add(
            DataMainModel(
                id = ListDataMainEnum.valueOf("P_$i"),
                titleFarsi = getStringByName("french_$i"),
                titleEnglish = getStringByName("english_$i"),
                image = getImageDrawableByName("p_$i"),
                soundPlay = getMusicInRawByName("s$i"),
            )
        )
    }

    return listData
}
