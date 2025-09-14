package com.farad.entertainment.kidsanimalenglish.data.model

data class ModelGalleryOnline(
    val id          : Int,
    val title       : String = "",
    val urlImage    : String = "",
    val soundName   : Int?,
    val soundAnimal : Int?,
    var isSelected  : Boolean = false
)
