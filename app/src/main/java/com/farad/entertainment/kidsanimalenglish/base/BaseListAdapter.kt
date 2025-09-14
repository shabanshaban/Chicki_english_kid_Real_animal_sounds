package com.farad.entertainment.kidsanimalenglish.base

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.LinearLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.farad.entertainment.kidsanimalenglish.utils.getScreenWidth


abstract class BaseListAdapter<T, VH : RecyclerView.ViewHolder?>(diffUtil: DiffUtil.ItemCallback<T>) :
    ListAdapter<T, VH>(diffUtil) {

    override fun submitList(list: List<T>?) {
        super.submitList(list?.let { ArrayList(it) })
    }

    protected var mLastPosition = -1

     private var selectItemId = 1
      private var selectedPosition = -1
      fun selectSingedItem(id:Int,adapterPosition:Int) {
        selectItemId= id
        if (selectedPosition >= 0){
         notifyItemChanged(selectedPosition)
        }

        selectedPosition = adapterPosition
        notifyItemChanged(selectedPosition)
    }
      fun checkSelect(data:T,id:Long,adapterPosition:Int,selected:(T)->Unit,unSelected:(T)->Unit){

        if (selectItemId.toLong()==id){
            selectedPosition = adapterPosition
            selected(data)
        }else{
            unSelected(data)
        }
    }
    fun setScaleAnimation(viewToAnimate: View, position: Int) {
        if (position > mLastPosition) {
            val anim = ScaleAnimation(
                0.0f,
                1.0f,
                0.0f,
                1.0f,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
            )
            anim.duration =500L
            viewToAnimate.startAnimation(anim)
            mLastPosition = position
        }
    }
    fun numViewsToShowOnScreen(
        context: Context,
        holder: View,
        size_item: Float,
        marginLeftInt: Int = 0,
        marginRight: Int = 0,
        marginBottom: Int = 0,
        marginTop: Int = 0,
    ) {

        val width = (context.getScreenWidth() / size_item).toInt()
        val param = LinearLayout.LayoutParams( /*width*/
            width,  /*height*/
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        param.setMargins(marginLeftInt, marginTop, marginRight, marginBottom)
        holder.layoutParams = param
    }
}


