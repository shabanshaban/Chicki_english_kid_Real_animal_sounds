package com.farad.entertainment.kidsanimalenglish.ui.fragment.home.detailsAnimal

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.farad.entertainment.kidsanimalenglish.base.BaseListAdapter
import com.farad.entertainment.kidsanimalenglish.base.BaseViewHolder
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.ToolsAnimalModel
import com.farad.entertainment.kidsanimalenglish.databinding.ItemToolsAnimal2Binding
import com.farad.entertainment.kidsanimalenglish.utils.animClickFast
import com.farad.entertainment.kidsanimalenglish.utils.playMusicFadeOutScaleAnim
import com.farad.entertainment.kidsanimalenglish.utils.setBackgroundTint
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener


class ToolsAnimalAdapter : BaseListAdapter<ToolsAnimalModel, ToolsAnimalAdapter.Vh>(DIFF_UTIL) {
    var isFirstRun = false
    private var setOnItemClickListener: ((ToolsAnimalModel) -> Unit)? = null
    fun setOnItemClickListener(listener: (ToolsAnimalModel) -> Unit) {
        setOnItemClickListener = listener
    }


    inner class Vh(val binding: ItemToolsAnimal2Binding) :
        BaseViewHolder<ToolsAnimalModel>(binding.root) {
        override fun bind(obj: ToolsAnimalModel) {
            //  numViewsToShowOnScreen(binding.cardView.context,binding.cardView,4.1f,0, 0, 0, 26)
            if (!isFirstRun)
                binding.cardView.playMusicFadeOutScaleAnim()
            binding.imageTools.setImageResource(obj.image)
            binding.imageTools.setBackgroundTint(obj.colorItem)
             binding.cardView.setCardBackgroundColor(obj.strokeCardColor)
            binding.cardView.setStrokeColor(ColorStateList.valueOf( obj.strokeCardColor))

            binding.tvNameTools.text = obj.title

            // binding.cardView.setCardBackgroundColor(obj.color)
            binding.cardView.setOnSafeClickListener {
                it.animClickFast()
                setOnItemClickListener?.invoke(obj)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            ItemToolsAnimal2Binding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.bind(getItem(position))
    }

    private companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<ToolsAnimalModel>() {
            override fun areItemsTheSame(
                oldItem: ToolsAnimalModel,
                newItem: ToolsAnimalModel
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ToolsAnimalModel,
                newItem: ToolsAnimalModel
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

}

