package com.farad.entertainment.kidsanimalenglish.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Keep
import androidx.constraintlayout.widget.ConstraintLayout
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.databinding.ViewAppSnackbarBinding
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.gson.annotations.SerializedName
import java.util.*

@Keep
enum class Status {
    @SerializedName( "Success")
    Success,
    @SerializedName( "Error")
    Error,
    @SerializedName( "Info")
    Info,
    @SerializedName( "Warning")
    Warning,
    @SerializedName( "Unknown")
    Unknown,
}
@Suppress("unused")
class SimpleSnackBar constructor(builder: Builder) {

    private var context: Context = builder.context
    private var layoutInflater: LayoutInflater = builder.layoutInflater
    private var anchor: View = builder.anchor
    private var type: Status = builder.type()
    private var text: String = builder.text()
    private var title: String = builder.title()
    private var isVisibility: Boolean = builder.getVisibilityIconClose()
    private var length: Int = builder.length()


    @SuppressLint("InflateParams")
    fun show() {
        val snackBar: Snackbar = Snackbar.make(anchor, "", length)
        val customSnackView2 = layoutInflater.inflate(R.layout.view_app_snackbar, null, true)
        val binding = ViewAppSnackbarBinding.bind(customSnackView2)
        title = text
        when (type) {
            Status.Success -> {
                if (title.isNotEmpty() && title.isNotNull()) {
                    binding.tvTitle.text = title
                } else {
                    context.getString(R.string.success).let {
                        binding.tvTitle.text = it
                    }
                }
                binding.cardView.setCardBackgroundColorCompat(R.color.color_success)
                binding.iconMessage.setImageResource(R.drawable.ic_snack_success)
            }


            Status.Error -> {
                if (title.isNotEmpty() && title.isNotNull()) {
                    binding.tvTitle.text = title
                } else {
                    context.getString(R.string.error).let {
                        binding.tvTitle.text = it
                    }
                }

                binding.cardView.setCardBackgroundColorCompat(R.color.error)
                binding.iconMessage.setImageResource(R.drawable.ic_snack_error)
            }

            Status.Warning -> {
                if (text.isNotEmpty() && text.isNotNull()) {
                    binding.tvTitle.text = text
                } else {
                    context.getString(R.string.warning).let {
                        binding.tvTitle.text = it
                    }
                }

                binding.cardView.setCardBackgroundColorCompat(R.color.warning)
                binding.iconMessage.setImageResource(R.drawable.ic_snack_warning)
            }
            Status.Info, Status.Unknown -> {

                if (title.isNotEmpty() && title.isNotNull()) {
                    binding.tvTitle.text = title
                } else {
                    context.getString(R.string.info).let {
                        binding.tvTitle.text = it
                    }
                }

                binding.cardView.setCardBackgroundColorCompat(R.color.white)
                binding.iconMessage.setImageResource(R.drawable.ic_snack_info)
                binding.iconMessage.setTintColorAttr(R.color.primary)
                binding.tvTitle.setTextColorCompat(R.color.primary)
                binding.cardView.strokeWidth = 1.px
            }
        }

        val params = snackBar.view.layoutParams as ViewGroup.LayoutParams
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        if (params is ConstraintLayout.LayoutParams) {

            snackBar.view.layoutParams = params
        }


        snackBar.behavior = BaseTransientBottomBar.Behavior()

        snackBar.view.setBackgroundColor(Color.TRANSPARENT)

        val snackBarLayout: Snackbar.SnackbarLayout =
            snackBar.view as Snackbar.SnackbarLayout

        snackBarLayout.addView(binding.root, 1)

        snackBar.show()


    }


    class Builder(
        val context: Context,
        val anchor: View,
        val layoutInflater: LayoutInflater,
    ) {
        private var type: Status = Status.Info
        private var text: String = ""
        private var title: String = ""
        private var isVisibility: Boolean = true
        private var length: Int = Snackbar.LENGTH_LONG

        fun type(type: Status): Builder {
            this.type = type
            return this
        }

        fun type(): Status {

            return type
        }

        fun text(text: String?): Builder {
            this.text = text ?: ""
            return this
        }

        fun text(): String {
            return text
        }

        fun title(title: String?): Builder {
            this.title = title ?: ""
            return this
        }

        fun title(): String {
            return title
        }

        fun setVisibilityIconClose(isVisibility: Boolean?): Builder {

            this.isVisibility = isVisibility ?: true
            return this
        }

        fun getVisibilityIconClose(): Boolean {

            return isVisibility
        }

        fun length(length: Int): Builder {
            this.length = length
            return this
        }

        fun length(): Int {
            return length
        }

        fun build() = SimpleSnackBar(this)
    }

}