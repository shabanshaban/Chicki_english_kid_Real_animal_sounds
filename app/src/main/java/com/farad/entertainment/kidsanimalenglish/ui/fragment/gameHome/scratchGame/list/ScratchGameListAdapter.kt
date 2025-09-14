package com.farad.entertainment.kidsanimalenglish.ui.fragment.gameHome.scratchGame.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BaseListAdapter
import com.farad.entertainment.kidsanimalenglish.base.BaseViewHolder
import com.farad.entertainment.kidsanimalenglish.data.model.entity.ScratchGame
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.StateGameScratch
import com.farad.entertainment.kidsanimalenglish.databinding.ItemScratchGameBinding
import com.farad.entertainment.kidsanimalenglish.utils.animClickFast
import com.farad.entertainment.kidsanimalenglish.utils.gone
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener
import com.farad.entertainment.kidsanimalenglish.utils.visible


class ScratchGameListAdapter :
    BaseListAdapter<ScratchGame, ScratchGameListAdapter.Vh>(DIFF_UTIL) {
    private var setOnItemClickListener: ((ScratchGame, Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (ScratchGame, Int) -> Unit) {
        setOnItemClickListener = listener
    }

    inner class Vh(val binding: ItemScratchGameBinding) :
        BaseViewHolder<ScratchGame>(binding.root) {
        override fun bind(obj: ScratchGame) {


            binding.root.setOnSafeClickListener {
                it.animClickFast()
                setOnItemClickListener?.invoke(obj, absoluteAdapterPosition)
            }
            binding.ratingStar.rating= obj.rating
            binding.ratingStar.gone()
            when(obj.stageGame){
                StateGameScratch.IMAGE_ANIMAL->{
                    binding.imageItem.setImageResource(obj.image)
                    binding.ratingStar.visible()
                }
                StateGameScratch.IsFalse->{
                    binding.imageItem.setImageResource(R.drawable.x_mark_red_small)
                }
                StateGameScratch.IS_LOCK->{
                    binding.imageItem.setImageResource(R.drawable.padlock)
                }
                StateGameScratch.IS_OPEN->{
                    binding.imageItem.setImageResource(R.drawable.button_play_next)
                }
            }

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            ItemScratchGameBinding.inflate(
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
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<ScratchGame>() {
            override fun areItemsTheSame(
                oldItem: ScratchGame,
                newItem: ScratchGame
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ScratchGame,
                newItem: ScratchGame
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

}

