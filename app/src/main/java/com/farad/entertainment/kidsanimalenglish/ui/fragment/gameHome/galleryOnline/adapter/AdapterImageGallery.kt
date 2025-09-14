package com.farad.entertainment.kidsanimalenglish.ui.fragment.gameHome.galleryOnline.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.farad.entertainment.kidsanimalenglish.base.BaseListAdapter
import com.farad.entertainment.kidsanimalenglish.base.BaseViewHolder
import com.farad.entertainment.kidsanimalenglish.databinding.ItemImageGalleryBinding
import com.farad.entertainment.kidsanimalenglish.data.model.ModelGalleryOnline
import com.farad.entertainment.kidsanimalenglish.utils.animClickFast
import com.farad.entertainment.kidsanimalenglish.utils.gone
import com.farad.entertainment.kidsanimalenglish.utils.loadImage
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener
import com.farad.entertainment.kidsanimalenglish.utils.visible

class AdapterImageGallery : BaseListAdapter<ModelGalleryOnline, AdapterImageGallery.Vh>(DIFF_UTIL) {


    var selectLevelId = 0
    var selectedPosition = -1

    private var setOnItemClickListener: ((Int, ModelGalleryOnline) -> Unit)? = null
    fun setOnItemClickListener(listener: (Int, ModelGalleryOnline) -> Unit) {
        setOnItemClickListener = listener
    }

    private var setOnItemNameClickListener: ((Int, ModelGalleryOnline) -> Unit)? = null
    fun setOnItemNameClickListener(listener: (Int, ModelGalleryOnline) -> Unit) {
        setOnItemNameClickListener = listener
    }


    inner class Vh(val binding: ItemImageGalleryBinding) :
        BaseViewHolder<ModelGalleryOnline>(binding.root) {
        override fun bind(obj: ModelGalleryOnline) {
            itemView.isSelected = false
            numViewsToShowOnScreen(binding.root.context, binding.root, 4f, 16, 16)

            binding.imageItem.loadImage(obj.urlImage,true)
            binding.tvTitle.text = obj.title
            checkSelect(obj)
            binding.root.setOnSafeClickListener {
                selectLevelId = obj.id
                selectLevel()
                it.animClickFast()
                setOnItemClickListener?.invoke(absoluteAdapterPosition, obj)

            }


            binding.tvTitle.setOnSafeClickListener {
                selectLevelId = obj.id
                selectLevel()
                it.animClickFast()
                setOnItemNameClickListener?.invoke(absoluteAdapterPosition, obj)
            }

        }

        private fun checkSelect(obj: ModelGalleryOnline) {
            if (selectLevelId == obj.id) {
                selectedPosition = absoluteAdapterPosition
                itemView.isSelected = true
                binding.imageItemColor.visible()
                obj.isSelected = true

            } else {
                binding.imageItemColor.gone()
                obj.isSelected = false
            }

        }

        fun selectLevel() {
            if (selectedPosition >= 0)
                notifyItemChanged(selectedPosition)
            selectedPosition = absoluteAdapterPosition
            notifyItemChanged(selectedPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            ItemImageGalleryBinding.inflate(
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
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<ModelGalleryOnline>() {
            override fun areItemsTheSame(
                oldItem: ModelGalleryOnline,
                newItem: ModelGalleryOnline
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ModelGalleryOnline,
                newItem: ModelGalleryOnline
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

}