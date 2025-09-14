package com.farad.entertainment.kidsanimalenglish.data.model.enumModel

import android.content.Context
import android.graphics.Color
import android.os.Parcelable
import androidx.annotation.Keep
import com.farad.entertainment.kidsanimalenglish.R
import kotlinx.parcelize.Parcelize


@Parcelize
@Keep
enum class ListToolsAnimal : Parcelable {
    KINDERGARTEN,
    VIDEO,
    BIG_IMAGE,
    SOUND,
    PAINT,
    PUZZLE,
    ABOUT,
    GALLERY
}

data class ToolsAnimalModel(
    val id: ListToolsAnimal,
    val title: String,
    val image: Int,
    val colorItem: Int,
    val strokeCardColor: Int,
    )

fun Context.getListTools(): ArrayList<ToolsAnimalModel> {

    return arrayListOf(
        ToolsAnimalModel(
            ListToolsAnimal.KINDERGARTEN,
            getString(R.string.kindergarten),
            R.drawable.p_animal_tools_1,
         colorItem      =  Color.parseColor("#f9b20d"),
         strokeCardColor=   Color.parseColor("#fde09e")
        ),
       /* ToolsAnimalModel(
            ListToolsAnimal.VIDEO,
            getString(R.string.video),
            R.drawable.p_animal_tools_2,
            getColorCompat(R.color.tools_video)
        ),*/
        ToolsAnimalModel(
            ListToolsAnimal.BIG_IMAGE,
            getString(R.string.big_picture),
            R.drawable.p_animal_tools_3,
           colorItem      = Color.parseColor("#eb7734"),
           strokeCardColor= Color.parseColor("#f7c9ae"),
        ),
        ToolsAnimalModel(
            ListToolsAnimal.ABOUT,
            getString(R.string.about),
            R.drawable.p_animal_tools_7,
           colorItem      = Color.parseColor("#d9ba43"),
           strokeCardColor= Color.parseColor("#f0e3b4"),
        ),
      /*  ToolsAnimalModel(
            ListToolsAnimal.SOUND,
            getString(R.string.voice),
            R.drawable.p_animal_tools_4,
            getColorCompat(R.color.tools_voice)
        ),*/
        ToolsAnimalModel(
            ListToolsAnimal.PAINT,
            getString(R.string.painting),
            R.drawable.p_animal_tools_5,
           colorItem      = Color.parseColor("#8f9d14"),
           strokeCardColor= Color.parseColor("#d2d8a1"),
        ),
        ToolsAnimalModel(
            ListToolsAnimal.GALLERY,
            getString(R.string.album),
            R.drawable.p_animal_tools_8,
           colorItem      = Color.parseColor("#3babda"),
           strokeCardColor= Color.parseColor("#b1ddf0"),
        ),
        ToolsAnimalModel(
            ListToolsAnimal.PUZZLE,
            getString(R.string.puzzle),
            R.drawable.p_animal_tools_6,
            colorItem      = Color.parseColor("#ff99bb"),
            strokeCardColor=  Color.parseColor("#fabbc9"),
        ),



        )
}