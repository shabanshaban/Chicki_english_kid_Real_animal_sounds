package com.farad.entertainment.kidsanimalenglish.ui.fragment.home.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BaseListAdapter
import com.farad.entertainment.kidsanimalenglish.base.BaseViewHolder
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.AnimalModel
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.TypeShowImageAnimal
import com.farad.entertainment.kidsanimalenglish.databinding.ItemMainAnimalBinding
import com.farad.entertainment.kidsanimalenglish.utils.animClick
import com.farad.entertainment.kidsanimalenglish.utils.animClickFast
import com.farad.entertainment.kidsanimalenglish.utils.implementSpringAnimationTrait
import com.farad.entertainment.kidsanimalenglish.utils.isNotNull
import com.farad.entertainment.kidsanimalenglish.utils.loadImage
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener
import com.farad.entertainment.kidsanimalenglish.utils.setTextColorCompat
import com.farad.entertainment.kidsanimalenglish.utils.visibleOrInvisible


class MainAnimalAdapter : BaseListAdapter<AnimalModel, MainAnimalAdapter.Vh>(DIFF_UTIL) {

    var typeImage = TypeShowImageAnimal.ANIMATED

    private var setOnItemClickListener: ((AnimalModel, Int) -> Unit)? = null
    fun setOnItemClickListener(listener: (AnimalModel, Int) -> Unit) {
        setOnItemClickListener = listener
    }

    private var setOnNameEnglishClickListener: ((AnimalModel) -> Unit)? = null
    private var setOnNameFrenchClickListener: ((AnimalModel) -> Unit)? = null
    fun setOnNameEnglishClickListener(listener: (AnimalModel) -> Unit) {
        setOnNameEnglishClickListener = listener
    }

    fun setOnNameFrenchClickListener(listener: (AnimalModel) -> Unit) {
        setOnNameFrenchClickListener = listener
    }

    private var setOnMusicClickListener: ((AnimalModel) -> Unit)? = null
    fun setOnMusicClickListener(listener: (AnimalModel) -> Unit) {
        setOnMusicClickListener = listener
    }

    private var setOnLockClickListener: ((AnimalModel, View) -> Unit)? = null
    fun setOnLockClickListener(listener: (AnimalModel, View) -> Unit) {
        setOnLockClickListener = listener
    }

    inner class Vh(val binding: ItemMainAnimalBinding) :
        BaseViewHolder<AnimalModel>(binding.root) {
        private fun isLockNot(obj: AnimalModel, action: () -> Unit) {
            if (obj.isLock.not()) {

                action()
            } else {
                //  setOnLockClickListener?.invoke(obj, binding.layoutLock)
            }
        }

        override fun bind(obj: AnimalModel) {

            binding.tvNameEnglishAnimal.text = obj.title
            binding.tvNameFrenchAnimal.text =
                obj.titleFrench.replace("\u200C", "\n").replace("-", "\n")

            binding.tvNameFrenchAnimal.setTextColorCompat(R.color.orange_text)
            binding.tvNameEnglishAnimal.setTextColorCompat(R.color.black)


            if (typeImage == TypeShowImageAnimal.ANIMATED) {
                binding.imageAnimal.loadImage(obj.image)
            } else {
                binding.imageAnimal.loadImage(obj.bigImage)
            }

            //   binding.itemAnimal.setBackgroundResource(obj.shapeItemDrawable)

            binding.root.implementSpringAnimationTrait()
            binding.btnMusic.visibleOrInvisible(obj.soundPlay.isNotNull())
            binding.btnMusic.setOnSafeClickListener {
                //  isLockNot(obj) {
                it.animClickFast()
                setOnMusicClickListener?.invoke(obj)
                // }

            }
            /* binding.layoutLock.setOnSafeClickListener {
                 isLockNot(obj) {

                 }
             }*/
            binding.tvNameFrenchAnimal.setOnSafeClickListener {
                // isLockNot(obj) {
                it.animClickFast()
                setOnNameFrenchClickListener?.invoke(obj)
                //  }


            }
            binding.imageAnimal.setOnSafeClickListener {
                //  isLockNot(obj) {
                it.animClickFast()
                setOnItemClickListener?.invoke(obj, absoluteAdapterPosition)
                // }

            }
            binding.root.setOnSafeClickListener {
                //    isLockNot(obj) {
                it.animClickFast()
                setOnItemClickListener?.invoke(obj, absoluteAdapterPosition)
                // }

            }
            binding.tvNameEnglishAnimal.setOnSafeClickListener {
                //  isLockNot(obj) {
                it.animClick()
                setOnNameEnglishClickListener?.invoke(obj)
                // }

            }
            binding.btnMusic.setOnSafeClickListener {
                //  isLockNot(obj) {
                it.animClickFast()
                setOnMusicClickListener?.invoke(obj)
                // }

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            ItemMainAnimalBinding.inflate(
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

            override fun areContentsTheSame(oldItem: AnimalModel, newItem: AnimalModel): Boolean {
                return oldItem == newItem
            }

        }
    }

}

