package com.farad.entertainment.kidsanimalenglish.ui.fragment.kindergarten.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.farad.entertainment.kidsanimalenglish.base.BaseListAdapter
import com.farad.entertainment.kidsanimalenglish.base.BaseViewHolder
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.KindergartenModel
import com.farad.entertainment.kidsanimalenglish.databinding.ItemKindergartenBinding
import com.farad.entertainment.kidsanimalenglish.utils.animClickFast
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener


class ListKindergartenAdapter :
    BaseListAdapter<KindergartenModel, ListKindergartenAdapter.Vh>(DIFF_UTIL) {
    private var setOnItemClickListener: ((KindergartenModel) -> Unit)? = null

    fun setOnItemClickListener(listener: (KindergartenModel) -> Unit) {
        setOnItemClickListener = listener
    }

    inner class Vh(val binding: ItemKindergartenBinding) :
        BaseViewHolder<KindergartenModel>(binding.root) {
        override fun bind(obj: KindergartenModel) {

            binding.btnOrigami.setBackgroundResource(obj.bgItem)
            binding.imageOrigami.setImageResource(obj.icon)
            binding.tvTitle.text = obj.title
            binding.root.setOnSafeClickListener {
                it.animClickFast()
                setOnItemClickListener?.invoke(obj)
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            ItemKindergartenBinding.inflate(
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
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<KindergartenModel>() {
            override fun areItemsTheSame(
                oldItem: KindergartenModel,
                newItem: KindergartenModel
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: KindergartenModel,
                newItem: KindergartenModel
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

}

