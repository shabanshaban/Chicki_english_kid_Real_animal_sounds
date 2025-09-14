package com.farad.entertainment.kidsanimalenglish.kids_ringtone_english.home


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.farad.entertainment.kidsanimalenglish.kids_ringtone_english.DataMainModel
import com.farad.entertainment.kidsanimalenglish.base.BaseListAdapter
import com.farad.entertainment.kidsanimalenglish.base.BaseViewHolder
import com.farad.entertainment.kidsanimalenglish.databinding.ItemAnimalBinding
import com.farad.entertainment.kidsanimalenglish.utils.animClickFast
import com.farad.entertainment.kidsanimalenglish.utils.implementSpringAnimationTrait
import com.farad.entertainment.kidsanimalenglish.utils.loadImage
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener

class AnimalAdapter :
    BaseListAdapter<DataMainModel, AnimalAdapter.Vh>(DIFF_UTIL) {
    private var setOnItemClickListener: ((DataMainModel) -> Unit)? = null

    fun setOnItemClickListener(listener: (DataMainModel) -> Unit) {
        setOnItemClickListener = listener
    }

    inner class Vh(private val binding: ItemAnimalBinding) :
        BaseViewHolder<DataMainModel>(binding.root) {
        override fun bind(obj: DataMainModel) {

            binding.tvTitle.text = obj.titleFarsi
            binding.tvTitleEnglish.text = obj.titleEnglish
            binding.imageItem.loadImage(obj.image, false)
            binding.root.implementSpringAnimationTrait()


            binding.root.setOnSafeClickListener {
                it.animClickFast()
                setOnItemClickListener?.invoke(obj)
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            ItemAnimalBinding.inflate(
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
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<DataMainModel>() {
            override fun areItemsTheSame(
                oldItem: DataMainModel,
                newItem: DataMainModel
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DataMainModel,
                newItem: DataMainModel
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

}