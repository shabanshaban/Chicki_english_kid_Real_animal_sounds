package com.farad.entertainment.kidsanimalenglish.ui.fragment.home.albumImageUser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.farad.entertainment.kidsanimalenglish.base.BaseListAdapter
import com.farad.entertainment.kidsanimalenglish.base.BaseViewHolder
import com.farad.entertainment.kidsanimalenglish.databinding.ItemAlbumBinding
import com.farad.entertainment.kidsanimalenglish.utils.animClickFast
import com.farad.entertainment.kidsanimalenglish.utils.loadImage
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener


class AlbumAdapter : BaseListAdapter<String, AlbumAdapter.Vh>(DIFF_UTIL) {
    private var setOnItemClickListener: ((String) -> Unit)? = null

    fun setOnItemClickListener(listener: (String) -> Unit) {
        setOnItemClickListener = listener
    }

    inner class Vh(val binding: ItemAlbumBinding) : BaseViewHolder<String>(binding.root) {
        override fun bind(obj: String) {

            binding.imageSlider.loadImage(obj,true)

            binding.root.setOnSafeClickListener {
                it.animClickFast()
                setOnItemClickListener?.invoke(obj)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.bind(getItem(position))
    }

    private companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(
                oldItem: String,
                newItem: String
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: String,
                newItem: String
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

}

