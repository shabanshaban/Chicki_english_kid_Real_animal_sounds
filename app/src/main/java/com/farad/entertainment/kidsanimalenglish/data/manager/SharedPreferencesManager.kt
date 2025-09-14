package com.farad.entertainment.kidsanimalenglish.data.manager

import android.content.SharedPreferences
import androidx.core.content.edit
import app.king.mylibrary.ktx.getObject
import app.king.mylibrary.ktx.putObject
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.GameLevel
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.PlaySoundAnimal
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.TypeShowImageAnimal
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.ZoomImageAnimal

class SharedPreferencesManager(
    private val sharedPreferences: SharedPreferences,
) : SharedPreferences.OnSharedPreferenceChangeListener {


    var sharedPreferencesInit: SharedPreferences? = null

    private companion object {
        private const val DATA_DIALOG_EXIST = "dataDialogExist"
        private const val WHEN = "when"
        private const val SCRATCH_GAME = "scratch_game"
        private const val IS_SCRATCH_GAME = "is_scratch_game"
        private const val SCORE_SCRATCH = "score_scratch"
        private const val LEVEL_SCRATCH = "level_scratch"
        private const val USER_NAME = "userName"
        private const val DIALOG_NEED_NET = "dialogNeedNet"
        private const val DIALOG_UPDATE = "dialog_update"

        private const val RECORD_LEVEL_1 = "record_level_1"
        private const val RECORD_LEVEL_2 = "record_level_2"
        private const val RECORD_LEVEL_3 = "record_level_3"

        private const val RECORD_TIME_1 = "record_Time_1"
        private const val RECORD_TIME_2 = "record_Time_2"
        private const val RECORD_TIME_3 = "record_Time_3"
        private const val STATUS_SIZE = "Status_size"
        private const val MUTE_SOUND = "mute_sound"
        private const val type_image_memory = "type_image_memory"
        private const val INVITE_CODE = "invite_code"
        private const val SUB_USER_COUNT = "SUB_USER_COUNT"
        private const val COUNT_COIN = "countCoin"
        private const val SHOW_OPEN_ITEM = "showDialogOpen"

        //setting
        private const val PLAY_SOUND_ANIMAL       = "radioSoundSetting"
        private const val TYPE_SHOW_IMAGE_ANIMAL    = "radioImageAnimated"
        private const val ZOOM_IMAGE_ANIMAL           = "zoomImageAnimal"
        private const val IS_SIGNUP           = "isSignUp"
    }

    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)

        sharedPreferencesInit = sharedPreferences


    }
    var isSignUp: Boolean
        get() = sharedPreferences.getBoolean(IS_SIGNUP, false)
        set(value) {
            sharedPreferences.edit { putBoolean(IS_SIGNUP, value) }
        }
    var showOpenItem: Boolean
        get() = sharedPreferences.getBoolean(SHOW_OPEN_ITEM, false)
        set(value) {
            sharedPreferences.edit { putBoolean(SHOW_OPEN_ITEM, value) }
        }
    var inviteCode: String
        get() = sharedPreferences.getString(INVITE_CODE, "0").toString()
        set(value) {
            sharedPreferences.edit { putString(INVITE_CODE, value) }
        }

    var subUserCount: String
        get() = sharedPreferences.getString(SUB_USER_COUNT, "0").toString()
        set(value) {
            sharedPreferences.edit { putString(SUB_USER_COUNT, value) }
        }

    var  coinCount: String
        get() = sharedPreferences.getString(COUNT_COIN, "0").toString()
        set(value) {
            sharedPreferences.edit { putString(COUNT_COIN, value) }
        }
    var playSoundAnimal: PlaySoundAnimal?
        get() = if (sharedPreferences.getObject<PlaySoundAnimal>(PLAY_SOUND_ANIMAL)!=null)
            sharedPreferences.getObject<PlaySoundAnimal>(PLAY_SOUND_ANIMAL) else PlaySoundAnimal.ENGLISH_AND_FRANCE
        set(value) {
            sharedPreferences.edit { putObject(PLAY_SOUND_ANIMAL, value) }
        }
    var zoomImageAnimal: ZoomImageAnimal?
        get() = if (sharedPreferences.getObject<ZoomImageAnimal>(ZOOM_IMAGE_ANIMAL)==null) ZoomImageAnimal.ZOOM_IN else sharedPreferences.getObject<ZoomImageAnimal>(ZOOM_IMAGE_ANIMAL)
        set(value) {
            sharedPreferences.edit { putObject(ZOOM_IMAGE_ANIMAL, value) }
        }
    var typeShowImageAnimal: TypeShowImageAnimal?
        get() = if (sharedPreferences.getObject<TypeShowImageAnimal>(TYPE_SHOW_IMAGE_ANIMAL)==null) TypeShowImageAnimal.ANIMATED else  sharedPreferences.getObject<TypeShowImageAnimal>(TYPE_SHOW_IMAGE_ANIMAL)
        set(value) {
            sharedPreferences.edit { putObject(TYPE_SHOW_IMAGE_ANIMAL, value) }
        }

    var levelScratch: GameLevel?
        get() = sharedPreferences.getObject<GameLevel>(LEVEL_SCRATCH)
        set(value) {
            sharedPreferences.edit { putObject(LEVEL_SCRATCH, value) }
        }

    var typeImageMemory: String
        get() = sharedPreferences.getString(type_image_memory, "p_").toString()
        set(value) {
            sharedPreferences.edit { putString(type_image_memory, value) }
        }
    var dataDialogExist: String
        get() = sharedPreferences.getString(DATA_DIALOG_EXIST, "").toString()
        set(value) {
            sharedPreferences.edit { putString(DATA_DIALOG_EXIST, value) }
        }
    var timeWhen: Long
        get() = sharedPreferences.getLong(WHEN, 0)
        set(value) {
            sharedPreferences.edit { putLong(WHEN, value) }
        }

    var valueSoundGameScratch: Float
        get() = sharedPreferences.getFloat(SCRATCH_GAME, 0.5f)
        set(value) {
            sharedPreferences.edit { putFloat(SCRATCH_GAME, value) }
        }


    var recordMovementLevel1: Long
        get() = sharedPreferences.getLong(RECORD_LEVEL_1, 0)
        set(value) {
            sharedPreferences.edit { putLong(RECORD_LEVEL_1, value) }
        }
    var recordMovementLevel2: Long
        get() = sharedPreferences.getLong(RECORD_LEVEL_2, 0)
        set(value) {
            sharedPreferences.edit { putLong(RECORD_LEVEL_2, value) }
        }
    var recordMovementLevel3: Long
        get() = sharedPreferences.getLong(RECORD_LEVEL_3, 0)
        set(value) {
            sharedPreferences.edit { putLong(RECORD_LEVEL_3, value) }
        }
    var recordTimeLevel1: Long
        get() = sharedPreferences.getLong(RECORD_TIME_1, 0)
        set(value) {
            sharedPreferences.edit { putLong(RECORD_TIME_1, value) }
        }
    var recordTimeLevel2: Long
        get() = sharedPreferences.getLong(RECORD_TIME_2, 0)
        set(value) {
            sharedPreferences.edit { putLong(RECORD_TIME_2, value) }
        }
    var recordTimeLevel3: Long
        get() = sharedPreferences.getLong(RECORD_TIME_3, 0)
        set(value) {
            sharedPreferences.edit { putLong(RECORD_TIME_3, value) }
        }

    var scoreScratch: Long
        get() = sharedPreferences.getLong(SCORE_SCRATCH, 0)
        set(value) {
            sharedPreferences.edit { putLong(SCORE_SCRATCH, value) }
        }


    var muteSoundMemoryGame: Boolean
        get() = sharedPreferences.getBoolean(MUTE_SOUND, false)
        set(value) {
            sharedPreferences.edit { putBoolean(MUTE_SOUND, value) }
        }
    var dialogNeedNet: Boolean
        get() = sharedPreferences.getBoolean(DIALOG_NEED_NET, true)
        set(value) {
            sharedPreferences.edit { putBoolean(DIALOG_NEED_NET, value) }
        }
    var dialogUpdate: Boolean
        get() = sharedPreferences.getBoolean(DIALOG_UPDATE, true)
        set(value) {
            sharedPreferences.edit { putBoolean(DIALOG_UPDATE, value) }
        }

    var isScratchGame: Boolean
        get() = sharedPreferences.getBoolean(IS_SCRATCH_GAME, false)
        set(value) {
            sharedPreferences.edit { putBoolean(IS_SCRATCH_GAME, value) }
        }


    var userName: String?
        get() = sharedPreferences.getString(USER_NAME, "")
        set(value) {
            sharedPreferences.edit { putString(USER_NAME, value) }
        }


    fun clearAll() {
        sharedPreferences.edit().clear().apply()
    }


    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {

    }
}


