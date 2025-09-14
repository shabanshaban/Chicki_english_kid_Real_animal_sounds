package com.farad.entertainment.kidsanimalenglish.ui.fragment.gameHome.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.farad.entertainment.kidsanimalenglish.base.BaseListAdapter
import com.farad.entertainment.kidsanimalenglish.base.BaseViewHolder
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.GameHomeModel
import com.farad.entertainment.kidsanimalenglish.databinding.ItemGameHomeBinding
import com.farad.entertainment.kidsanimalenglish.utils.animClickFast
import com.farad.entertainment.kidsanimalenglish.utils.loadImage
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener


class GameHomeAdapter : BaseListAdapter<GameHomeModel, GameHomeAdapter.Vh>(DIFF_UTIL) {
    private var setOnItemClickListener: ((GameHomeModel,Int) -> Unit)? = null
    fun setOnItemClickListener(listener: (GameHomeModel,Int) -> Unit) {
        setOnItemClickListener = listener
    }


    inner class Vh(val binding: ItemGameHomeBinding) :
        BaseViewHolder<GameHomeModel>(binding.root) {
        override fun bind(obj: GameHomeModel) {
            binding.imageItem.loadImage(obj.image)
            binding.itemName.text = obj.title
            binding.root.setOnSafeClickListener {
                it.animClickFast()
                setOnItemClickListener?.invoke(obj,absoluteAdapterPosition)
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            ItemGameHomeBinding.inflate(
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
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<GameHomeModel>() {
            override fun areItemsTheSame(oldItem: GameHomeModel, newItem: GameHomeModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: GameHomeModel,
                newItem: GameHomeModel
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

}

