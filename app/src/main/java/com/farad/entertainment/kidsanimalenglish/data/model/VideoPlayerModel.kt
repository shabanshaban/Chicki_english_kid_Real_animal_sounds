package com.farad.entertainment.kidsanimalenglish.data.model

import android.os.Parcelable
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.ListKindergarten
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class VideoPlayerModel(
    @SerializedName("Id")
    var id: Long = 0,
    @SerializedName("Url")
    var url: String,
    @SerializedName("TitleRight")
    var titleHeaderRight: String = "",
    @SerializedName("TitleLeft")
    var titleHeaderLeft: String = "",
    @SerializedName("ImageRight")
    var imageHeaderRight: Int = R.drawable.icon_video,
    @SerializedName("ImageLeft")
    var imageHeaderLeft: Int = 0,
    @SerializedName("imageHeader")
    var imageHeader: Int = R.drawable.bg_header,
    @SerializedName("visibilityControlView")
    var visibilityControlView: Boolean = false,
    @SerializedName("description")
    var isDescription: Boolean = false,
    @SerializedName("isWhatsapp")
    var isWhatsapp: Boolean = false,
    @SerializedName("isLock")
    var isLock: Boolean = false,
    @SerializedName("typeKindergarten")
    var typeKindergarten: ListKindergarten?= null,
) : Parcelable