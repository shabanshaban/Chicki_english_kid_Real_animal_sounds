package com.farad.entertainment.kidsanimalenglish.ui.fragment.home.painting

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BottomNavigationFragment
import com.farad.entertainment.kidsanimalenglish.data.manager.FileManager
import com.farad.entertainment.kidsanimalenglish.databinding.FragmentPaintingBinding
import com.farad.entertainment.kidsanimalenglish.ui.fragment.home.painting.dialog.BottomSheetPaintingSizePicker
import com.farad.entertainment.kidsanimalenglish.utils.animClickFast
import com.farad.entertainment.kidsanimalenglish.utils.getBitmap
import com.farad.entertainment.kidsanimalenglish.utils.getBitmapFromAssets
import com.farad.entertainment.kidsanimalenglish.utils.getPermission
import com.farad.entertainment.kidsanimalenglish.utils.invisible
import com.farad.entertainment.kidsanimalenglish.utils.safeShow
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener
import com.farad.entertainment.kidsanimalenglish.utils.visible
import kotlinx.coroutines.launch


class PaintingFragment : BottomNavigationFragment<FragmentPaintingBinding>() {

    private val args by navArgs<PaintingFragmentArgs>()

    private var drawCanvas: Canvas? = null

    private var paint: Paint? = null
    private var path: Path? = null

    private var brushColor = -56553
    private var brushSize = 30

    private var mBitmap: Bitmap? = null

    private var listPen = ArrayList<View>()
    private var historyPaint = ArrayList<LinePath>()
    private var redoList = ArrayList<LinePath>()

    private var isEraser = false

    private var totalStep = 0
    private var currentStep = 0
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPaintingBinding
        get() = FragmentPaintingBinding::inflate

    override fun setup() {
        setupBrushDrawing()
        setListenerDrawingView()
        initView()
        listener()
        initPen()
    }

    private fun setErase(eraser: Boolean) {
        isEraser = eraser
        if (isEraser) paint?.setXfermode(PorterDuffXfermode(PorterDuff.Mode.CLEAR)) else paint?.setXfermode(
            null
        )
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
                        setErase(false)
                        setPaintColor(Color.parseColor(viewSelected.tag.toString()))
                    } else {
                        setErase(true)
                    }
                }

            }
        }
    }

    private fun clearing() {
        drawCanvas?.drawColor(0, PorterDuff.Mode.CLEAR)
        binding.img.setImageBitmap(mBitmap)
    }

    private fun redoClick() {

        if (currentStep == totalStep) {

            binding.btnRedo.setImageResource(R.drawable.ic_redo)
        } else {

            binding.btnUndo.setImageResource(R.drawable.ic_undo)
            clearing()
            historyPaint.add(
                LinePath(
                    redoList[currentStep].drawPath,
                    redoList[currentStep].drawPaint
                )
            )
            currentStep++
            historyPaint.forEach {

                drawCanvas?.drawPath(it.drawPath, it.drawPaint)
                binding.img.setImageBitmap(requireNotNull(mBitmap))

            }

            if (currentStep == 0) {
                binding.btnUndo.setImageResource(R.drawable.ic_undo)
            }
        }
    }

    private fun undoClick() {
        if (currentStep == 0) {

            binding.btnUndo.setImageResource(R.drawable.ic_undo)
        } else {
            binding.btnRedo.setImageResource(R.drawable.ic_redo)
            clearing()
            currentStep--

            historyPaint.removeAt(currentStep)



            historyPaint.forEach {

                drawCanvas?.drawPath(it.drawPath, it.drawPaint)
                binding.img.setImageBitmap(requireNotNull(mBitmap))

            }




            if (currentStep == 0)
                binding.btnUndo.setImageResource(R.drawable.ic_undo)
        }
    }


    private fun setBrushSize(size: Int) {
        brushSize = size
        paint?.strokeWidth = brushSize.toFloat()
    }


    private fun showColorPicker() {
        context?.let { context ->
            ColorPickerDialogBuilder
                .with(context)
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
                    paint?.color = brushColor
                    //  setErase(false)
                }
                .setNegativeButton(
                    getString(R.string.close)
                ) { _, _ -> }
                .build()
                .show()
        }

    }

    private fun listener() {


        binding.btnSave.setOnSafeClickListener {
            it.animClickFast()
            getPermission {
                lifecycleScope.launch {

                    FileManager.saveImageToGallery(context,
                        requireNotNull(binding.drawing.getBitmap()), startSaveImage = {
                            binding.btnSave.invisible()
                        }, endSaveImage = {
                            binding.btnSave.visible()
                        })
                }
            }


        }

        binding.btnBrush.setOnSafeClickListener {
            it.animClickFast()
            val dialog = BottomSheetPaintingSizePicker()
            dialog.brushSize = brushSize
            dialog.setOnChangeSizeListener {
                setBrushSize(it)
            }
            dialog.safeShow(childFragmentManager)
        }
        binding.btnPalletColor.setOnSafeClickListener {
            it.animClickFast()
            showColorPicker()
        }
        binding.btnRedo.setOnSafeClickListener {
            it.animClickFast()
            redoClick()
        }
        binding.btnUndo.setOnSafeClickListener {
            it.animClickFast()
            undoClick()
        }
    }


    private fun initView() {
        context?.getBitmapFromAssets(args.animalModel.imagePainting)
            ?.let { imageAnimal ->
                mBitmap = imageAnimal
                binding.imgCover.setImageBitmap(mBitmap)

                binding.img.post {
                    mBitmap = Bitmap.createBitmap(
                        binding.img.measuredWidth,
                        binding.img.measuredHeight,
                        Bitmap.Config.ARGB_8888
                    )
                    mBitmap?.let { mBitmap ->
                        drawCanvas = Canvas(mBitmap)
                    }

                }
            }

    }

    private fun setPaintColor(brushColor: Int) {
        paint?.color = brushColor
    }

    private fun setupBrushDrawing() {
        paint = Paint()
        path = Path()
        paint?.isAntiAlias = true
        paint?.isDither = true
        paint?.color = brushColor
        paint?.style = Paint.Style.STROKE
        paint?.strokeJoin = Paint.Join.ROUND
        paint?.strokeCap = Paint.Cap.ROUND
        paint?.strokeWidth = brushSize.toFloat()
    }

    private class LinePath(drawPath: Path?, drawPaints: Paint?) {
        val drawPaint: Paint
        val drawPath: Path

        init {
            drawPaint = Paint(drawPaints)
            this.drawPath = Path(drawPath)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setListenerDrawingView() {

        binding.imgCover.setOnTouchListener { view, motionEvent ->

            val touchX = motionEvent.x
            val touchY = motionEvent.y

            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    path?.moveTo(touchX, touchY)
                }

                MotionEvent.ACTION_MOVE -> {
                    //"شرط گذاشتیم اگر صفحه کاملا سفید بود و هیچی برای پاک کردن نبود پاک کن کار نکنه"
                    if (isEraser && historyPaint.isEmpty()) {
                        return@setOnTouchListener false
                    }
                    path?.lineTo(touchX, touchY)
                    path?.let { path ->

                        paint?.let { paint ->
                            drawCanvas?.drawPath(path, paint)
                            binding.img.setImageBitmap(mBitmap)
                        }

                    }

                }

                MotionEvent.ACTION_UP -> {
                    //"شرط گذاشتیم اگر صفحه کاملا سفید بود و هیچی برای پاک کردن نبود پاک کن کار نکنه"
                    if (isEraser && historyPaint.isEmpty()) {
                        return@setOnTouchListener false
                    }

                    binding.btnUndo.setImageResource(R.drawable.ic_undo)
                    historyPaint.add(LinePath(path!!, paint!!))

                    totalStep = historyPaint.size
                    currentStep++

                    redoList.clear()
                    redoList.addAll(historyPaint)



                    path?.reset()

                }
            }

            true
        }

    }
}