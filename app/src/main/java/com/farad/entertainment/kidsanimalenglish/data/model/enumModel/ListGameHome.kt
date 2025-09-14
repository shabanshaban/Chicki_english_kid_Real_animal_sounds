package com.farad.entertainment.kidsanimalenglish.data.model.enumModel

import android.content.Context
import android.os.Parcelable
import androidx.annotation.Keep
import com.farad.entertainment.kidsanimalenglish.R
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
enum class ListGameHomeEnum : Parcelable {
    WORD_GAME,
    BUBBLE,
    BALLOON,
    SCRATCH,
    GUESS,
    ANIMAL_FARM,
    GALLERY,
    PAINTING,
    POETRY_AND_STORIES,
    MEMORY,
    MOBILE,

    MR_ANBEH,
    KIDS_MUSIC,
    MR_GHOOGHOOLI,
    Alphabet,
    ENGLISH_ALPHABET,
    JOBS_SEASONS_COLORS,
    BODY_ORGANS,
    ANIMAL_SOUND,
    CALM_SONGS,
    HAPPY_KIDS_SONG,
    BIRTHDAY_SONG,
    EKA,
    ANIMAL_FOREST,
    AUDIO_50_STORIES,
    ALIVE_PAINTING,
}

@Parcelize
data class GameHomeModel(
    val id: ListGameHomeEnum,
    val title: String,
    val image: Int,
    val address:String=""
) : Parcelable

fun Context.getListGameHome(): ArrayList<GameHomeModel> {
    return arrayListOf(
        GameHomeModel(
            ListGameHomeEnum.WORD_GAME,
            getString(R.string.word_game_t_1),
            R.drawable.item_1
        ),
        GameHomeModel(
            ListGameHomeEnum.BUBBLE,
            getString(R.string.bubble_t_2)
            , R.drawable.item_2),
        GameHomeModel(
            ListGameHomeEnum.BALLOON,
            getString(R.string.balloon_t_3),
            R.drawable.item_3),
        GameHomeModel(
            ListGameHomeEnum.SCRATCH,
            getString(R.string.scratch_t_4),
            R.drawable.item_4),
        GameHomeModel(
            ListGameHomeEnum.GUESS
            , getString(R.string.guess_t_5)
            , R.drawable.item_5),
        GameHomeModel(
            ListGameHomeEnum.ANIMAL_FARM,
            getString(R.string.animal_farm_t_6),
            R.drawable.item_6
        ),

        GameHomeModel(
            ListGameHomeEnum.GALLERY
            , getString(R.string.gallery_t_7)
            , R.drawable.item_7),
        GameHomeModel(
            ListGameHomeEnum.PAINTING,
            getString(R.string.painting_t_8),
            R.drawable.item_8
        ),
        GameHomeModel(
            ListGameHomeEnum.POETRY_AND_STORIES,
            getString(R.string.stories),
            R.drawable.item_9
        ),
        GameHomeModel(
            ListGameHomeEnum.MEMORY,
            getString(R.string.memory_t_10),
            R.drawable.item_10),
        GameHomeModel(
            ListGameHomeEnum.MOBILE,
            getString(R.string.mobile_t_11),
            R.drawable.item_11
        ),
      /*  GameHomeModel(
            ListGameHomeEnum.MR_ANBEH,
            getString(R.string.mr_anbeh_t_12),
            R.drawable.item_12,
            "com.farad.entertainment.kidsanimalenglish.kids_fruit"
        ),
        GameHomeModel(
            ListGameHomeEnum.KIDS_MUSIC,
            getString(R.string.kids_music_t_13),
            R.drawable.item_13,
            "com.farad.entertainment.kidsanimalenglish.storymusic"
        ),
        GameHomeModel(
            ListGameHomeEnum.MR_GHOOGHOOLI,
            getString(R.string.mr_ghooghooli_t_14),
            R.drawable.item_14,
            "com.farad.entertainment.kidsanimalenglish."
        ),
        GameHomeModel(
            ListGameHomeEnum.Alphabet,
            getString(R.string.alphabet_t_15),
            R.drawable.item_15,
            "com.mrghooghooli.entertainment.alefbahush"
        ),
        GameHomeModel(
            ListGameHomeEnum.ENGLISH_ALPHABET,
            getString(R.string.english_alphabet_t_16),
            R.drawable.item_16,
            "com.farad.entertainment.kidsanimalenglish.kidsenglishalphabet"
        ),
        GameHomeModel(
            ListGameHomeEnum.JOBS_SEASONS_COLORS,
            getString(R.string.Jobs_seasons_colors_t_17),
            R.drawable.item_17,
            "com.farad.entertainment.kidsanimalenglish.kids_tools"
        ),
        GameHomeModel(
            ListGameHomeEnum.BODY_ORGANS,
            getString(R.string.body_organs_t_18),
            R.drawable.item_18,
            "com.farad.entertainment.kidsanimalenglish.kids_body"
        ),
        GameHomeModel(
            ListGameHomeEnum.ANIMAL_SOUND,
            getString(R.string.animal_sounds_t_19),
            R.drawable.item_19,
            "com.farad.entertainment.kidsanimalenglish.kids_sound"
        ),
        GameHomeModel(
            ListGameHomeEnum.CALM_SONGS,
            getString(R.string.calm_songs_t_20),
            R.drawable.item_20,
            "com.farad.entertainment.kidsanimalenglish.kids_lalaei"
        ),
        GameHomeModel(
            ListGameHomeEnum.HAPPY_KIDS_SONG,
            getString(R.string.happy_kids_song_t_21),
            R.drawable.item_21,
            "com.mohammad.entertainment.kids_new_music1"
        ),
        GameHomeModel(
            ListGameHomeEnum.BIRTHDAY_SONG,
            getString(R.string.birthday_song_t_22),
            R.drawable.item_22,
            "com.farad.entertainment.kidsanimalenglish.birthday"
        ),
        GameHomeModel(
            ListGameHomeEnum.EKA,
            getString(R.string.eka_t_23),
            R.drawable.item_23,
            "ir.dimodeveloper.funnyenglish"
        ),
        GameHomeModel(
            ListGameHomeEnum.ANIMAL_FOREST,
            getString(R.string.animal_forest_t_24)
            , R.drawable.item_24,
            "com.StarGames.Alefba"),
        GameHomeModel(
            ListGameHomeEnum.AUDIO_50_STORIES,
            getString(R.string.audio50_stories_t_25),
            R.drawable.item_25,
            "com.mrghooghooli.entertainment.story_voice_2"
        ),
        GameHomeModel(
            ListGameHomeEnum.ALIVE_PAINTING,
            getString(R.string.alive_painting_t_26),
            R.drawable.item_26,
            "com.mrghooghooli.kids_painting"
        ),*/


        )
}