package com.farad.entertainment.kidsanimalenglish.data.model

import com.farad.entertainment.kidsanimalenglish.R


val listSoundTrue = arrayListOf(
    R.raw.exam_true_1,
    R.raw.exam_true_2,
    R.raw.exam_true_3,
    R.raw.exam_true_4,
    R.raw.exam_true_5,
)
val listSoundFalse = arrayListOf(
    R.raw.exam_false_1,
    R.raw.exam_false_2,
    R.raw.exam_false_3,
    R.raw.exam_false_4,
    R.raw.exam_false_5,
    R.raw.exam_false_7,
    R.raw.exam_false_8,
    R.raw.exam_false_9,
    R.raw.exam_false_10,
)


fun getSoundTrueRandom(): Int {
    val number = (0 until listSoundTrue.size).random()

    return listSoundTrue[number]
}

fun getSoundFalseRandom(): Int {
    val number = (0 until listSoundFalse.size).random()

    return listSoundFalse[number]
}