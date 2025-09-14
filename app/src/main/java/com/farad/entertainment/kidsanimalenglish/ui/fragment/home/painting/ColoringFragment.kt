package com.farad.entertainment.kidsanimalenglish.ui.fragment.home.painting

import android.graphics.Bitmap
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.farad.entertainment.kidsanimalenglish.BuildConfig.APPLICATION_ID
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BottomNavigationFragment
import com.farad.entertainment.kidsanimalenglish.cv.colorImageView.photoview.ColourImageView
import com.farad.entertainment.kidsanimalenglish.data.manager.FileManager
import com.farad.entertainment.kidsanimalenglish.databinding.FragmentColoringBinding
import com.farad.entertainment.kidsanimalenglish.utils.getBitmap
import com.farad.entertainment.kidsanimalenglish.utils.getLocalBitmapUri
import com.farad.entertainment.kidsanimalenglish.utils.getPermission
import com.farad.entertainment.kidsanimalenglish.utils.invisible
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener
import com.farad.entertainment.kidsanimalenglish.utils.sharePaint
import com.farad.entertainment.kidsanimalenglish.utils.visible
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import com.farad.entertainment.kidsanimalenglish.cv.colorImageView.photoview.ImageLoaderUtil
import com.farad.entertainment.kidsanimalenglish.cv.colorImageView.photoview.PhotoViewAttacker
import com.farad.entertainment.kidsanimalenglish.utils.animClickFast
import com.farad.entertainment.kidsanimalenglish.utils.getLinkDownload
import com.farad.entertainment.kidsanimalenglish.utils.lifecycleScopeDelayTryCatch
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import com.nostra13.universalimageloader.core.assist.FailReason
import com.nostra13.universalimageloader.core.assist.QueueProcessingType
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ColoringFragment : BottomNavigationFragment<FragmentColoringBinding>() {

    private val args by navArgs<ColoringFragmentArgs>()
    var mAttacher: PhotoViewAttacker? = null

    private var listPen = ArrayList<View>()

    private var brushColor  = -56553

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentColoringBinding
        get() = FragmentColoringBinding::inflate

    override fun setup() {
        initImageLoader()
        loadLargeImage()
        initPen()
        listener()

    }

    override fun onDestroyView() {
        binding.fillImageview.clearStack()
        binding.fillImageview.clearPoints()
        binding.fillImageview.clearAnimation()
        binding.fillImageview.clearColorFilter()
        super.onDestroyView()

    }
    private fun makeTextShare( ) {
          val textShare   = StringBuilder()
        //share
        binding.btnShare.invisible()
        binding.progressShare.visible()
        lifecycleScopeDelayTryCatch(2000){
            binding.btnShare.visible()
            binding.progressShare.invisible()
        }


        textShare.append(binding.root.context.getString(R.string.share_text))
        textShare.append("\n")
      context?.getLinkDownload()?.let {

          textShare.append(it)
      }



        lifecycleScope.launch(Dispatchers.IO) {
            binding.fillImageview.getBitmap().getLocalBitmapUri(APPLICATION_ID, binding.root.context)?.let { uri ->
                context?.sharePaint(uri, textShare.toString())
            }
        }
    }

    private fun showColorPicker() {

        ColorPickerDialogBuilder
            .with(binding.root.context)
            .setTitle(getString(R.string.choose_color))
            .initialColor(brushColor)
            .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
            .density(15)
            .lightnessSliderOnly()
            .setOnColorSelectedListener {

            }
            .setPositiveButton(
                getString(R.string.save)
            ) { _, selectedColor, _ ->
                brushColor = selectedColor
               binding.fillImageview.setColor(brushColor)
            }
            .setNegativeButton(
                getString(R.string.close)
            ) { _, _ -> }
            .build()
            .show()
    }
    private fun listener(){
        binding.btnShare.setOnSafeClickListener {
         //   context?.shareApp()
            it.animClickFast()
            makeTextShare()
        }
        binding.btnSave.setOnSafeClickListener {
            it.animClickFast()
            getPermission {
                lifecycleScope.launch {
                    context?.let {context->
                        FileManager.saveImageToGallery(context,
                            requireNotNull(binding.fillImageview.getBitmap()), startSaveImage = {
                                binding.btnSave.invisible()
                            }, endSaveImage = {
                                binding.btnSave.visible()
                            })
                    }

                }
            }


        }
        binding.btnPalletColor.setOnSafeClickListener {
            it.animClickFast()
            showColorPicker()
        }

        binding.btnRedo.setOnSafeClickListener {
            it.animClickFast()
            binding.fillImageview.redo()
        }

        binding.btnUndo.setOnSafeClickListener {
            it.animClickFast()
            binding.fillImageview.undo()
        }

        binding.fillImageview.onRedoUndoListener=object :ColourImageView.OnRedoUndoListener{
            override fun onRedoUndo(undoSize: Int, redoSize: Int) {
                    if (undoSize != 0) {
                        binding.btnUndo.isEnabled = true
                        binding.btnUndo.setImageResource(R.drawable.ic_undo)
                    } else {
                        binding.btnUndo.isEnabled = false
                        binding.btnUndo.setImageResource(R.drawable.ic_undo)
                    }
                    if (redoSize != 0) {
                        binding.btnRedo.isEnabled = true
                        binding.btnRedo.setImageResource(R.drawable.ic_redo)
                    } else {
                        binding.btnRedo.isEnabled = false
                        binding.btnRedo.setImageResource(R.drawable.ic_redo)
                    }
            }

        }


    }

    private fun setPaintColor(brushColor: Int) {

        binding.fillImageview.setColor(brushColor)
    }
    private fun initPen() {
        listPen.add(binding.btnPenBlack)
        listPen.add(binding.btnPenBlue)
        listPen.add(binding.btnPenPurple)
        listPen.add(binding.btnPenGreen)
        listPen.add(binding.btnPenBrown)
        listPen.add(binding.btnPenPink)
        listPen.add(binding.btnPenRed)
        listPen.add(binding.btnPenOrange)
        listPen.add(binding.btnPenYellow)
        listPen.add(binding.btnPenEraser)


        listPen.forEach {

            it.setOnSafeClickListener { viewSelected ->
                listPen.forEach { disableView ->
                    //عقب برگشتن مداد
                    disableView.translationY = 0f
                }
                viewSelected.post {
                    //"جلو آمدن مداد انتخاب شده"
                    viewSelected.translationY -= viewSelected.measuredHeight / 6
                    if (viewSelected.tag.toString() != "eraser") {
                        setPaintColor(Color.parseColor(viewSelected.tag.toString()))
                    }
                }

            }
        }
    }
    private fun loadLargeImage() {


        showLagreImageAsynWithAllCacheOpen(
            binding.fillImageview,
            args.animalModel.imageColoring,
            object : ImageLoadingListener {
                override fun onLoadingStarted(s: String, view: View) {
                }
                override fun onLoadingFailed(
                    s: String,
                    view: View,
                    failReason: FailReason
                ) {

                }

                override fun onLoadingComplete(s: String, view: View, bitmap: Bitmap) {

                    mAttacher =
                        PhotoViewAttacker(
                            binding.fillImageview,
                            bitmap
                        )


                }

                override fun onLoadingCancelled(s: String, view: View) {

                }
            })
    }
    private fun showLagreImageAsynWithAllCacheOpen(
        imageView: ImageView,
        url: String,
        listener: ImageLoadingListener
    ) {
        ImageLoaderUtil.getInstance().displayImage(
            url, imageView,
            ImageLoaderUtil.DetailImageOptions(),
            listener
        )
    }
    private fun initImageLoader() {
        context?.let {context->
            val config: ImageLoaderConfiguration = ImageLoaderConfiguration.Builder(context)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(Md5FileNameGenerator())
                .diskCacheSize(100 * 1024 * 1024)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build()
            ImageLoaderUtil.getInstance().init(config)
        }

    }
}