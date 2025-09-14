package com.farad.entertainment.kidsanimalenglish.ui.fragment.gameHome.listAnimalPaint

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import coil.load
import com.farad.entertainment.kidsanimalenglish.base.BaseListAdapter
import com.farad.entertainment.kidsanimalenglish.base.BaseViewHolder
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.AnimalModel
import com.farad.entertainment.kidsanimalenglish.databinding.ItemListPaintBinding
import com.farad.entertainment.kidsanimalenglish.utils.animClickFast
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener


class ListAnimalPaintAdapter : BaseListAdapter<AnimalModel, ListAnimalPaintAdapter.Vh>(DIFF_UTIL) {
    private var setOnItemClickListener: ((AnimalModel) -> Unit)? = null
    fun setOnItemClickListener(listener: (AnimalModel) -> Unit) {
        setOnItemClickListener = listener
    }


    inner class Vh(val binding: ItemListPaintBinding) :
        BaseViewHolder<AnimalModel>(binding.root) {
        override fun bind(obj: AnimalModel) {
            binding.imagePaintColor.load(obj.imagePaintingSvg)
            binding.tvNamePaint.text = obj.title
            binding.imagePaintColor.setOnSafeClickListener {
                it.animClickFast()
                setOnItemClickListener?.invoke(obj)
            }
            binding.tvNamePaint.setOnSafeClickListener {
                it.animClickFast()
                setOnItemClickListener?.invoke(obj)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            ItemListPaintBinding.inflate(
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
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<AnimalModel>() {
            override fun areItemsTheSame(oldItem: AnimalModel, newItem: AnimalModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: AnimalModel,
                newItem: AnimalModel
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

}

