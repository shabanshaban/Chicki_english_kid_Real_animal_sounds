package com.farad.entertainment.kidsanimalenglish.ui.fragment.gameHome.stories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.farad.entertainment.kidsanimalenglish.base.BaseListAdapter
import com.farad.entertainment.kidsanimalenglish.base.BaseViewHolder
import com.farad.entertainment.kidsanimalenglish.data.model.DataStoriesGameHomeModel
import com.farad.entertainment.kidsanimalenglish.databinding.ItemStoriesBinding
import com.farad.entertainment.kidsanimalenglish.utils.loadImage
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener


class StoriesListAdapter :
    BaseListAdapter<DataStoriesGameHomeModel, StoriesListAdapter.Vh>(DIFF_UTIL) {
    private var setOnItemClickListener: ((DataStoriesGameHomeModel, Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (DataStoriesGameHomeModel, Int) -> Unit) {
        setOnItemClickListener = listener
    }

    inner class Vh(val binding: ItemStoriesBinding) :
        BaseViewHolder<DataStoriesGameHomeModel>(binding.root) {
        override fun bind(obj: DataStoriesGameHomeModel) {


            binding.imageItem.loadImage(obj.image)
            binding.tvTitle.text = obj.title
            binding.root.setOnSafeClickListener {
                setOnItemClickListener?.invoke(obj, absoluteAdapterPosition)
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            ItemStoriesBinding.inflate(
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
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<DataStoriesGameHomeModel>() {
            override fun areItemsTheSame(
                oldItem: DataStoriesGameHomeModel,
                newItem: DataStoriesGameHomeModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: DataStoriesGameHomeModel,
                newItem: DataStoriesGameHomeModel
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

}

