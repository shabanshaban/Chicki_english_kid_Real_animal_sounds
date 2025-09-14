package com.farad.entertainment.kidsanimalenglish.ui.fragment.kindergarten.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BaseListAdapter
import com.farad.entertainment.kidsanimalenglish.base.BaseViewHolder
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.AnimalModel
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.ListKindergarten
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.getLinkVideo
import com.farad.entertainment.kidsanimalenglish.databinding.ItemKindergartenVideoBinding
import com.farad.entertainment.kidsanimalenglish.utils.animClickFast
import com.farad.entertainment.kidsanimalenglish.utils.loadImage
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener


class ListVideoKindergartenAdapter :
    BaseListAdapter<AnimalModel, ListVideoKindergartenAdapter.Vh>(DIFF_UTIL) {
    var typeKindergarten = ListKindergarten.ORIGAMI
    private var setOnItemClickListener: ((String,AnimalModel) -> Unit)? = null

    fun setOnItemClickListener(listener: (String,AnimalModel) -> Unit) {
        setOnItemClickListener = listener
    }


    inner class Vh(val binding: ItemKindergartenVideoBinding) :
        BaseViewHolder<AnimalModel>(binding.root) {
        override fun bind(obj: AnimalModel) {
            binding.tvNumber.text = (absoluteAdapterPosition + 1).toString()
            binding.tvNameVideo.text = obj.title
            binding.imageItem.loadImage(obj.image)

            when (typeKindergarten) {
                ListKindergarten.ORIGAMI -> {
                    binding.tvNumber.setBackgroundResource(R.drawable.shape_animal_whats_app_orange_rounded)
                    binding.tvNameVideo.setBackgroundResource(R.drawable.shape_kinder_video_1)
                    binding.imageSolidShape.setBackgroundResource(R.drawable.shape_kinder_video_1)
                    binding.dashItem.setImageResource(R.drawable.shape_dash_orange)
                    binding.imageSolidShape.setImageResource(R.drawable.shape_kinder_video_1)
                    binding.frameLayoutTvName.setBackgroundResource(R.drawable.shape_dash_orange)

                }

                ListKindergarten.CREATIVITY -> {
                    binding.tvNumber.setBackgroundResource(R.drawable.shape_animal_whats_app_green_rounded)
                    binding.tvNameVideo.setBackgroundResource(R.drawable.shape_kinder_video_2)
                    binding.imageSolidShape.setBackgroundResource(R.drawable.shape_kinder_video_2)
                    binding.dashItem.setImageResource(R.drawable.shape_dash_green)
                    binding.imageSolidShape.setImageResource(R.drawable.shape_kinder_video_2)
                    binding.frameLayoutTvName.setBackgroundResource(R.drawable.shape_dash_green)
                }

                ListKindergarten.PAINTING -> {
                    binding.tvNumber.setBackgroundResource(R.drawable.shape_animal_whats_app_pink_rounded)
                    binding.tvNameVideo.setBackgroundResource(R.drawable.shape_kinder_video_3)
                    binding.imageSolidShape.setBackgroundResource(R.drawable.shape_kinder_video_3)
                    binding.dashItem.setImageResource(R.drawable.shape_dash_pink)
                    binding.imageSolidShape.setImageResource(R.drawable.shape_kinder_video_3)
                    binding.frameLayoutTvName.setBackgroundResource(R.drawable.shape_dash_pink)
                }

                ListKindergarten.HAND_PRINT -> {
                    binding.tvNumber.setBackgroundResource(R.drawable.shape_animal_whats_app_blue_rounded)
                    binding.tvNameVideo.setBackgroundResource(R.drawable.shape_kinder_video_4)
                    binding.imageSolidShape.setBackgroundResource(R.drawable.shape_kinder_video_4)
                    binding.dashItem.setImageResource(R.drawable.shape_dash_blue)
                    binding.imageSolidShape.setImageResource(R.drawable.shape_kinder_video_4)
                    binding.frameLayoutTvName.setBackgroundResource(R.drawable.shape_dash_blue)
                }

                ListKindergarten.STATUE -> {
                    binding.tvNumber.setBackgroundResource(R.drawable.shape_animal_whats_app_brown_rounded)
                    binding.tvNameVideo.setBackgroundResource(R.drawable.shape_kinder_video_5)
                    binding.imageSolidShape.setBackgroundResource(R.drawable.shape_kinder_video_5)
                    binding.dashItem.setImageResource(R.drawable.shape_dash_brown)
                    binding.imageSolidShape.setImageResource(R.drawable.shape_kinder_video_5)
                    binding.frameLayoutTvName.setBackgroundResource(R.drawable.shape_dash_brown)
                }

                ListKindergarten.STORIES -> {
                    binding.tvNumber.setBackgroundResource(R.drawable.shape_animal_whats_app_purple_rounded)
                    binding.tvNameVideo.setBackgroundResource(R.drawable.shape_kinder_video_6)
                    binding.imageSolidShape.setBackgroundResource(R.drawable.shape_kinder_video_6)
                    binding.dashItem.setImageResource(R.drawable.shape_dash_purple)
                    binding.imageSolidShape.setImageResource(R.drawable.shape_kinder_video_6)
                    binding.frameLayoutTvName.setBackgroundResource(R.drawable.shape_dash_purple)
                }
            }

            binding.root.setOnSafeClickListener {
                it.animClickFast()
                setOnItemClickListener?.invoke(typeKindergarten.getLinkVideo((absoluteAdapterPosition)),obj)
            }


        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            ItemKindergartenVideoBinding.inflate(
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
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: AnimalModel, newItem: AnimalModel): Boolean {
                return oldItem == newItem
            }

        }
    }

}

