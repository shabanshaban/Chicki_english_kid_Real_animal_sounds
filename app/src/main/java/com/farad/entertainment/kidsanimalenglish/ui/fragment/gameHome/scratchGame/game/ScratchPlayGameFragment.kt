package com.farad.entertainment.kidsanimalenglish.ui.fragment.gameHome.scratchGame.game

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BottomNavigationFragment
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.GameLevel
import com.farad.entertainment.kidsanimalenglish.data.manager.SharedPreferencesManager
import com.farad.entertainment.kidsanimalenglish.data.model.entity.ScratchGame
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.StateGameScratch
import com.farad.entertainment.kidsanimalenglish.data.model.getSoundFalseRandom
import com.farad.entertainment.kidsanimalenglish.databinding.FragmentScratchPlayGameBinding
import com.farad.entertainment.kidsanimalenglish.ui.dialog.DialogInfoScratch
import com.farad.entertainment.kidsanimalenglish.ui.fragment.gameHome.scratchGame.vm.ScratchGameViewModel
import com.farad.entertainment.kidsanimalenglish.utils.SIZE_ITEM
import com.farad.entertainment.kidsanimalenglish.utils.gone
import com.farad.entertainment.kidsanimalenglish.utils.lifecycleScopeDelayTryCatch
import com.farad.entertainment.kidsanimalenglish.utils.playSoundMediaPlayer
import com.farad.entertainment.kidsanimalenglish.utils.safeDismiss
import com.farad.entertainment.kidsanimalenglish.utils.safeShow
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener
import com.farad.entertainment.kidsanimalenglish.utils.setTextColorCompat
import com.farad.entertainment.kidsanimalenglish.utils.visible
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Timer
import java.util.TimerTask

    class ScratchPlayGameFragment : BottomNavigationFragment<FragmentScratchPlayGameBinding>() {

        private val args by navArgs<ScratchPlayGameFragmentArgs>()

        private var drawCanvas: Canvas? = null

        private var paint: Paint? = null
        private var path: Path? = null


        private var mBitmap: Bitmap? = null

        private var mediaPlayer: MediaPlayer? = null


        private val listBtn = ArrayList<AppCompatTextView>()


        private var timer: Timer? = null

        private var counterTimer = 30

        private var counterLevel = 0

        private var stepScore = 0

        private var isPlayGame = false
        private var isPlayGameCountdown = false
        private var isFinishEraser = false
        private val sharedPreferencesManager: SharedPreferencesManager by inject()

        private val listItem = ArrayList<ScratchGame>()
        private val viewModel by viewModel<ScratchGameViewModel>()

        private var scratchGame: ScratchGame? = null
        override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentScratchPlayGameBinding
            get() = FragmentScratchPlayGameBinding::inflate

        override fun setup() {
            initMediaPlayer()
            initView()
            createBitmapCanvas()
            clearBitmap()
            startTimer()
            getData()
            listener()
        }


        private fun checkLevel(): Int {
            return when (sharedPreferencesManager.levelScratch) {
                GameLevel.Easy -> {
                    50
                }

                GameLevel.Medium -> {
                    30
                }

                GameLevel.Hard -> {
                    20
                }

                else -> {
                    30
                }
            }
        }

        private fun isLockBtn(isLock: Boolean) {

            listBtn.forEach {
                it.isEnabled = isLock
            }
            binding.imageBitmap.isEnabled = isLock
            binding.imageAnimal.isEnabled = isLock
        }

        private fun update(isSuccess: Boolean) {
            if ((counterLevel + 1) != listItem.size) {
                scratchGame?.let {
                    if (isSuccess) {
                        it.stageGame = StateGameScratch.IMAGE_ANIMAL

                    } else {
                        it.stageGame = StateGameScratch.IsFalse
                    }
                    it.rating = binding.ratingStar.rating
                    viewModel.updateScratchGame(it)

                    val model = listItem[counterLevel + 1]
                    model.stageGame = StateGameScratch.IS_OPEN
                    viewModel.updateScratchGame(model)
                }
            } else {
                popBackStack()
            }

        }

        private fun listener() {
            listBtn.forEach { textView ->
                textView.setOnSafeClickListener {
                    var answer = ""
                    scratchGame?.let {
                        answer = it.answer

                    }
                    if (textView.text.toString().uppercase() == answer.uppercase()) {
                        listBtn.forEach {
                            it.setBackgroundResource(R.drawable.bg_border_2)
                        }
                        textView.setBackgroundResource(R.drawable.bg_ripple_shape_chose_3)
                        textView.setTextColorCompat(R.color.white)
                        mediaPlayer?.playSoundMediaPlayer(context, scratchGame?.soundAnimal)
                        showDialogInfo(true)
                    } else {
                        binding.ratingStar.rating=0f
                        mediaPlayer?.playSoundMediaPlayer(context, getSoundFalseRandom())
                        listBtn.forEach {
                            it.setBackgroundResource(R.drawable.bg_border_2)
                        }
                        listBtn.filter {
                            it.text.toString().uppercase() == answer.uppercase()
                        }.also {
                            val textViewSelected = it.firstOrNull()
                            textViewSelected?.setBackgroundResource(R.drawable.bg_ripple_shape_chose_3)
                            textViewSelected?.setTextColorCompat(R.color.white)
                        }
                        textView.setBackgroundResource(R.drawable.bg_ripple_shape_chose_2)
                        textView.setTextColorCompat(R.color.white)
                        showDialogInfo(false)

                    }

                    timer?.cancel()
                }
            }
        }

        private fun getData() {
            lifecycleScope.launch {
                viewModel.getScratchGameList().let {
                    listItem.clear()
                    listItem.addAll(it)
                }
            }
        }

        private fun nextLevel() {
            binding.imageBitmapView.visible()
            isLockBtn(true)
            createBitmapCanvas()
            val number = counterLevel + 1
            setData(listItem[number])
            mediaPlayer?.pause()
            counterTimer = 30
            binding.ratingStar.rating = 5f
            binding.tvTimer.text = counterTimer.toString()
            startTimer()
            listBtn.forEach {
                it.setBackgroundResource(R.drawable.bg_border_2)
                it.setTextColorCompat(R.color.black)
            }


        }

        private fun showDialogInfo(isSuccess: Boolean) {
            isLockBtn(false)
            update(isSuccess)
            lifecycleScope.launch {
                binding.imageBitmap.setImageResource(0)


                timer?.cancel()
                if (isSuccess)
                    sharedPreferencesManager.scoreScratch += binding.ratingStar.rating.toLong()
                lifecycleScopeDelayTryCatch(1500){
                    if (isNullView().not()) {
                        val dialog = DialogInfoScratch()
                        dialog.setOnBackPressedListener { }
                        dialog.setOnNextBtnListener {
                            dialog.safeDismiss()
                            nextLevel()
                        }
                        dialog.isCancelable = false
                        dialog.isCanceledOnTouchOutside = false
                        dialog.isSuccess = isSuccess
                        dialog.rating = binding.ratingStar.rating
                        dialog.safeShow(childFragmentManager)
                    }
                }

            }

        }

        override fun onStart() {
            if (isPlayGame)
                startTimer()
            super.onStart()

        }

        override fun onPause() {
            timer?.cancel()
            mediaPlayer?.pause()
            super.onPause()

        }

        override fun onDestroyView() {
            super.onDestroyView()
            timer?.cancel()
            timer?.purge()
            timer = null
            mediaPlayer?.release()
            mediaPlayer = null
        }

        private fun startTimer() {
            timer?.cancel()
            timer = Timer()
            timer?.schedule(object : TimerTask() {
                override fun run() {
                    lifecycleScope.launch {


                        if (isNullView().not()) {
                            if (counterTimer > 0) {
                                isPlayGame = true
                                counterTimer--
                                binding.tvTimer.text = counterTimer.toString()
                                if (counterTimer < 11&&isPlayGameCountdown.not()){
                                    mediaPlayer?.playSoundMediaPlayer(binding.root.context, R.raw.countdown)

                                    isPlayGameCountdown=true
                                }



                            } else {
                                binding.ratingStar.rating = 0f
                                isFinishEraser = true
                                isPlayGame = false
                                mediaPlayer?.pause()
                                timer?.cancel()
                                checkStateGame()


                            }
                        }
                    }

                }

            }, 0, 1000)
        }

        private fun checkStateGame() {
            showDialogInfo(false)
            listBtn.forEach {
                if (it.text == scratchGame?.answer) {
                    it.setBackgroundResource(R.drawable.bg_border_orange)
                } else {
                    it.setBackgroundResource(R.drawable.bg_border_2)
                }
            }
        }

        private fun playScratch() {
            if (mediaPlayer?.isPlaying == false) {
                mediaPlayer?.playSoundMediaPlayer(
                    binding.root.context,
                    R.raw.scratch
                )
            }
        }

        private fun initMediaPlayer() {
            mediaPlayer = MediaPlayer.create(binding.root.context, R.raw.scratch)
        }

        @SuppressLint("ClickableViewAccessibility")
        private fun clearBitmap() {
            binding.imageBitmap.setOnTouchListener { view, motionEvent ->
                if (!isFinishEraser) {
                    val touchX = motionEvent.x
                    val touchY = motionEvent.y

                    when (motionEvent.action) {
                        MotionEvent.ACTION_DOWN -> {
                            path?.moveTo(touchX, touchY)
                            initMediaPlayer()

                        }

                        MotionEvent.ACTION_MOVE -> {
                            stepScore++
                            path?.lineTo(touchX, touchY)
                            path?.let { path ->
                                paint?.let { paint ->
                                    drawCanvas?.drawPath(path, paint)
                                    binding.imageBitmap.setImageBitmap(mBitmap)
                                }
                            }

                            playScratch()

                            checkScore(stepScore)
                        }

                        MotionEvent.ACTION_UP -> {
                            path?.reset()
                        }
                    }
                }
                true
            }

        }

        private fun checkScore(stepScore: Int) {


            when (stepScore) {

                25 -> {
                    binding.ratingStar.rating = 4.5f
                }

                50 -> {
                    binding.ratingStar.rating = 4f
                }

                75 -> {
                    binding.ratingStar.rating = 3.5f
                }

                100 -> {
                    binding.ratingStar.rating = 3f
                }

                120 -> {
                    binding.ratingStar.rating = 2.5f
                }

                150 -> {
                    binding.ratingStar.rating = 2f
                }

                170 -> {
                    binding.ratingStar.rating = 1f
                    isFinishEraser = true
                }

            }

        }

        private fun createBitmapCanvas() {

            lifecycleScopeDelayTryCatch(300){
                binding.imageBitmap.post {
                    lifecycleScopeDelayTryCatch(300){
                        isPlayGameCountdown=false
                        stepScore=0
                        mBitmap=null
                        path?.reset()
                        paint?.reset()
                        path=null
                        paint=null
                        isFinishEraser = false
                        setupBrushDrawing()
                        //        "ایجاد بیت مپ با رنگ ساده"
                        mBitmap = Bitmap.createBitmap(
                            binding.imageBitmap.measuredWidth,
                            binding.imageBitmap.measuredHeight,
                            Bitmap.Config.ARGB_8888
                        )
                        mBitmap?.eraseColor(Color.parseColor("#bdbcc2"))
                        mBitmap?.let {

                            drawCanvas = Canvas(it)
                            binding.imageBitmap.setImageBitmap(it)
                            binding.imageBitmapView.gone()
                        }
                    }



                }

            }


        }

        private fun setData(it: ScratchGame) {
            scratchGame = it
            val numberLevel = it.id + 1
            counterLevel = it.id.toInt()
            binding.tvNumberLevel.text =  "$numberLevel/$SIZE_ITEM"
            binding.imageAnimal.setImageResource(it.image)
            binding.tvWord1.text = it.listWord[0]
            binding.tvWord2.text = it.listWord[1]
            binding.tvWord3.text = it.listWord[2]
            binding.tvWord4.text = it.listWord[3]
        }

        private fun initView() {
            listBtn.add(binding.tvWord1)
            listBtn.add(binding.tvWord2)
            listBtn.add(binding.tvWord3)
            listBtn.add(binding.tvWord4)

            setData(args.dataScratch)
        }

        private fun setupBrushDrawing() {
            paint = Paint()
            path = Path()
            paint?.isAntiAlias = true
            paint?.isDither = true
            paint?.style = Paint.Style.STROKE
            paint?.strokeJoin = Paint.Join.ROUND
            paint?.strokeCap = Paint.Cap.ROUND
            paint?.strokeWidth = checkLevel().toFloat()
            paint?.setXfermode(PorterDuffXfermode(PorterDuff.Mode.CLEAR))
        }


    }