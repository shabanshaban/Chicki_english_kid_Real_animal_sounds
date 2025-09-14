package com.farad.entertainment.kidsanimalenglish.ui.fragment.gameHome.memoryGame

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BaseListAdapter
import com.farad.entertainment.kidsanimalenglish.base.BaseViewHolder
import com.farad.entertainment.kidsanimalenglish.data.model.StructMemoryScore
import com.farad.entertainment.kidsanimalenglish.databinding.ItemScoreMemoryBinding
import com.farad.entertainment.kidsanimalenglish.utils.getDrawableCompat
import com.farad.entertainment.kidsanimalenglish.utils.getDurationString

class ListRecordMemoryGameAdapter :
    BaseListAdapter<StructMemoryScore, ListRecordMemoryGameAdapter.Vh>(DIFF_UTIL) {
    private var setOnItemClickListener: ((StructMemoryScore) -> Unit)? = null

    fun setOnItemClickListener(listener: (StructMemoryScore) -> Unit) {
        setOnItemClickListener = listener
    }

    inner class Vh(val binding: ItemScoreMemoryBinding) :
        BaseViewHolder<StructMemoryScore>(binding.root) {
        override fun bind(obj: StructMemoryScore) {

            if (absoluteAdapterPosition % 2 == 0) {
                binding.lineItemRoot.background = binding.root.context.getDrawableCompat(R.drawable.shape_memory_score_list)
            } else {
                binding.lineItemRoot.background =binding.root.context.getDrawableCompat(R.drawable.shape_white)
            }

            binding.txtRanking.text = (absoluteAdapterPosition + 1).toString()
            binding.txtName.text = obj.name
            if (obj.score < 3000) {
                binding.txtScore.text =
                    binding.root.context.getString(R.string.movement_s, obj.score.toString())
            } else {
                binding.txtScore.text = "_"
            }

            binding.txtTime.text = getDurationString(obj.time)

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            ItemScoreMemoryBinding.inflate(
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
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<StructMemoryScore>() {
            override fun areItemsTheSame(
                oldItem: StructMemoryScore,
                newItem: StructMemoryScore
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: StructMemoryScore,
                newItem: StructMemoryScore
            ): Boolean {
                return oldItem == newItem
            }

        }
    }


}