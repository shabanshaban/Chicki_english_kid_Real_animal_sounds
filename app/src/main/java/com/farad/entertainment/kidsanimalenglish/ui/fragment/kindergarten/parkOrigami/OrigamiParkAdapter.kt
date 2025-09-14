package com.farad.entertainment.kidsanimalenglish.ui.fragment.kindergarten.parkOrigami

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.farad.entertainment.kidsanimalenglish.base.BaseListAdapter
import com.farad.entertainment.kidsanimalenglish.base.BaseViewHolder
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.OrigamiParkModel
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.SizeTypeTools
import com.farad.entertainment.kidsanimalenglish.databinding.ItemOrigamiParkBinding
import com.farad.entertainment.kidsanimalenglish.utils.animClickFast
import com.farad.entertainment.kidsanimalenglish.utils.getScreenWidth
import com.farad.entertainment.kidsanimalenglish.utils.loadImage
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener


class OrigamiParkAdapter :
    BaseListAdapter<OrigamiParkModel, OrigamiParkAdapter.Vh>(DIFF_UTIL) {
    private var setOnItemClickListener: ((OrigamiParkModel) -> Unit)? = null

    fun setOnItemClickListener(listener: (OrigamiParkModel) -> Unit) {
        setOnItemClickListener = listener
    }

    inner class Vh(val binding: ItemOrigamiParkBinding) :
        BaseViewHolder<OrigamiParkModel>(binding.root) {
        override fun bind(obj: OrigamiParkModel) {
            checkSize(obj.size)
            binding.imageItem.loadImage(obj.image)
            binding.root.setOnSafeClickListener {
                it.animClickFast()
                setOnItemClickListener?.invoke(obj)
            }
        }

        private fun checkSize(sizeTypeTools: SizeTypeTools) {
            val width = binding.root.context.applicationContext.getScreenWidth()
            when (sizeTypeTools) {
                SizeTypeTools.SMALL -> {

                    binding.root.layoutParams.width = (width / 3.5).toInt()
                }
                SizeTypeTools.NORMAL -> {
                    binding.root.layoutParams.width = (width / 2.3).toInt()
                }
                SizeTypeTools.RECOMMENDATION_SIZE -> {
                    binding.root.layoutParams.width = (width / 2.5).toInt()
                }
                SizeTypeTools.BIG -> {
                    binding.root.layoutParams.width = (width)
                }


            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            ItemOrigamiParkBinding.inflate(
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
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<OrigamiParkModel>() {
            override fun areItemsTheSame(
                oldItem: OrigamiParkModel,
                newItem: OrigamiParkModel
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: OrigamiParkModel,
                newItem: OrigamiParkModel
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

}

