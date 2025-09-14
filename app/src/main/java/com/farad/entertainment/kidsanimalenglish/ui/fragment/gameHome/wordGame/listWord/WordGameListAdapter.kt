package com.farad.entertainment.kidsanimalenglish.ui.fragment.gameHome.wordGame.listWord

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BaseListAdapter
import com.farad.entertainment.kidsanimalenglish.base.BaseViewHolder
import com.farad.entertainment.kidsanimalenglish.data.model.entity.WordGame
import com.farad.entertainment.kidsanimalenglish.databinding.ItemWordGameBinding
import com.farad.entertainment.kidsanimalenglish.utils.animClickFast
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener


class WordGameListAdapter :
    BaseListAdapter<WordGame, WordGameListAdapter.Vh>(DIFF_UTIL) {
    private var setOnItemClickListener: ((WordGame, Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (WordGame, Int) -> Unit) {
        setOnItemClickListener = listener
    }

    inner class Vh(val binding: ItemWordGameBinding) :
        BaseViewHolder<WordGame>(binding.root) {
        override fun bind(obj: WordGame) {

            val number=(absoluteAdapterPosition+1)
            binding.tvNumber.text=number.toString()

            binding.root.setOnSafeClickListener {
                it.animClickFast()
                setOnItemClickListener?.invoke(obj,absoluteAdapterPosition)
            }
            if (obj.isOpen){
                binding.imageItem.setImageResource(R.drawable.button_play_next)
            }else{
                binding.imageItem.setImageResource(R.drawable.padlock)
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            ItemWordGameBinding.inflate(
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
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<WordGame>() {
            override fun areItemsTheSame(
                oldItem: WordGame,
                newItem: WordGame
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: WordGame,
                newItem: WordGame
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

}

