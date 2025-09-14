package com.farad.entertainment.kidsanimalenglish.ui.fragment.home.puzzle

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.SoundPool
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.animation.addListener
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.farad.entertainment.kidsanimalenglish.base.BottomNavigationFragment
import com.farad.entertainment.kidsanimalenglish.base.OnBackPressed
import com.farad.entertainment.kidsanimalenglish.data.manager.SharedPreferencesManager
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.GameLevel
import com.farad.entertainment.kidsanimalenglish.databinding.FragmentPuzzleBinding
import com.farad.entertainment.kidsanimalenglish.ui.dialog.DialogMedal
import com.farad.entertainment.kidsanimalenglish.ui.dialog.DialogName
import com.farad.entertainment.kidsanimalenglish.ui.dialog.DialogSelectLevel
import com.farad.entertainment.kidsanimalenglish.utils.animClickFast
import com.farad.entertainment.kidsanimalenglish.utils.getBitmap
import com.farad.entertainment.kidsanimalenglish.utils.getScreenWidth
import com.farad.entertainment.kidsanimalenglish.utils.gone
import com.farad.entertainment.kidsanimalenglish.utils.loadSoundPool
import com.farad.entertainment.kidsanimalenglish.utils.playSound
import com.farad.entertainment.kidsanimalenglish.utils.safeDismiss
import com.farad.entertainment.kidsanimalenglish.utils.safeShow
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener
import com.farad.entertainment.kidsanimalenglish.utils.startTimer
import com.farad.entertainment.kidsanimalenglish.utils.toFormatTime
import com.farad.entertainment.kidsanimalenglish.utils.visible
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.util.Timer
import kotlin.math.floor
import kotlin.math.roundToInt

open class PuzzleFragment : BottomNavigationFragment<FragmentPuzzleBinding>(), OnBackPressed {

    private var numCols = 3
    private var numRows = 3
    private val spacing = 2 // spacing between blocks

    private var soundPool: SoundPool? = null

    private var soundResult = 0
    private var soundClaps = 0
    private var soundMove = 0
    private val itemsRotations = ArrayList<Int>()
    private val listImage = ArrayList<ImageView>()


    private var _mBitmap: Bitmap? = null
    private val mBitmap: Bitmap
        get() = requireNotNull(_mBitmap)

    private var timer: Timer? = null
    private var timerCount = 0

    private val args by navArgs<PuzzleFragmentArgs>()
    private var gameLevel = GameLevel.Easy

    private val sharedPreferencesManager: SharedPreferencesManager by inject()

    private var numberStep = 0
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPuzzleBinding
        get() = FragmentPuzzleBinding::inflate

    override fun setup() {
        initBitmap()
        checkPanel()
        initSoundPool()
        listener()
    }


    private fun backDialog() {
        resetGame()
        binding.panelGame.gone()
        binding.panelFirst.visible()
        binding.imagePuzzle.visible()
        binding.imageAnimal.visible()
    }


    override fun onBackPressedCompact() {
        if (binding.panelGame.isVisible) {
            backDialog()
        } else {
            super.onBackPressedCompact()

        }
    }

    private fun showDialogMedal(shakeAnimation: Boolean = false) {
        val dialogMedal = DialogMedal()
        dialogMedal.setOnBackPressedListener {
            if (binding.panelGame.isVisible)
                backDialog()
            dialogMedal.safeDismiss()
        }
        dialogMedal.isAnimation = shakeAnimation
        dialogMedal.timeGameNow = timerCount
        dialogMedal.countMoving = numberStep

        dialogMedal.safeShow(childFragmentManager)
    }

    private fun showDialogName() {
        if (sharedPreferencesManager.userName?.isEmpty() == true) {
            val dialog = DialogName()
            dialog.setOnBackPressedListener {
                if (binding.panelGame.isVisible)
                    backDialog()
                dialog.safeDismiss()
            }
            dialog.isCancelable = false
            dialog.isCanceledOnTouchOutside = false
            dialog.onSaveNameListener {
                sharedPreferencesManager.userName = it
            }
            dialog.safeShow(childFragmentManager)
        }
    }

    override fun onStop() {
        super.onStop()
        timer?.cancel()
    }


    private fun startTimer() {
        timer = Timer()
        timer?.startTimer(delay = 0, period = 1000) {
            if (isNullView().not()) {
                timerCount += 1000
                lifecycleScope.launch {
                    binding.txtTime.text = timerCount.toLong().toFormatTime()
                }
            }
        }
    }

    private fun listener() {
        binding.btnRecords.setOnSafeClickListener {
            showDialogMedal()
        }
        binding.imagePuzzle.setOnSafeClickListener {
            resetGame()
            binding.imageAnimal.gone()
            binding.imagePuzzle.gone()
            binding.panelFirst.gone()
            binding.panelGame.visible()
            startGame()

        }
        binding.btnLevel.setOnSafeClickListener {
            it.animClickFast()
            dialogSelectLevel()
        }
    }

    private fun dialogSelectLevel() {
        val dialogSelectLevel = DialogSelectLevel()
        dialogSelectLevel.onSelectItemClickListener { gameLevel ->
            this.gameLevel = gameLevel
            binding.textLevel.text = getString(gameLevel.value)
            when (gameLevel) {
                GameLevel.Easy -> {
                    numCols = 3
                    numRows = 3
                }

                GameLevel.Medium -> {
                    numCols = 4
                    numRows = 4
                }

                GameLevel.Hard -> {
                    numCols = 5
                    numRows = 5
                }

                else -> {
                    numCols = 5
                    numRows = 5
                }
            }
        }
        dialogSelectLevel.safeShow(childFragmentManager)
    }

    private fun checkPanel() {
        binding.panelFirst.visible()
        binding.panelGame.gone()
    }

    private fun initBitmap() {
        binding.textLevel.text = getString(GameLevel.Easy.value)
        binding.imagePuzzle.setImageResource(args.animalModel.bigImage)
        binding.imageAnimal.setImageResource(args.animalModel.bigImage)
        binding.imagePuzzle.post {
            try {
                _mBitmap = binding.imagePuzzle.getBitmap()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    private fun initSoundPool() {
        soundPool = SoundPool.Builder().setMaxStreams(128).build()

        soundPool?.let {
            soundResult = it.loadSoundPool(context, "snd_result.mp3")
            soundClaps = it.loadSoundPool(context, "claps.mp3")
            soundMove = it.loadSoundPool(context, "snd_move.mp3")

        }
    }

    private fun playSoundPool(idSoundPool: Int) {
        soundPool?.playSound(idSoundPool)
    }


    private fun resetGame() {
        initBitmap()
        timer?.cancel()
        timerCount = 0
        numberStep = 0
        binding.frameGame.removeAllViews()
        listImage.clear()
        itemsRotations.clear()

    }

    private fun checkItem() {
        for (i in listImage.indices) {
            if (itemsRotations[i].toFloat() / 360 != (itemsRotations[i] / 360).toFloat()
                    .roundToInt().toFloat()
            ) {
                return
            }
        }

        soundPool?.playSound(soundClaps)

        when (gameLevel) {
            GameLevel.Easy -> {

                if (numberStep < sharedPreferencesManager.recordMovementLevel1) {
                    sharedPreferencesManager.recordMovementLevel1 = numberStep.toLong()
                } else {
                    if (sharedPreferencesManager.recordMovementLevel1 == 0L) {
                        sharedPreferencesManager.recordMovementLevel1 = numberStep.toLong()
                    }
                }

                //time
                if (timerCount < sharedPreferencesManager.recordTimeLevel1) {
                    sharedPreferencesManager.recordTimeLevel1 = timerCount.toLong()
                } else {
                    if (sharedPreferencesManager.recordTimeLevel1 == 0L) {
                        sharedPreferencesManager.recordTimeLevel1 = timerCount.toLong()
                    }
                }
            }

            GameLevel.Medium -> {


                if (numberStep < sharedPreferencesManager.recordMovementLevel2) {
                    sharedPreferencesManager.recordMovementLevel2 = numberStep.toLong()
                } else {
                    if (sharedPreferencesManager.recordMovementLevel2 == 0L) {
                        sharedPreferencesManager.recordMovementLevel2 = numberStep.toLong()
                    }
                }

                if (timerCount < sharedPreferencesManager.recordTimeLevel2) {
                    sharedPreferencesManager.recordTimeLevel2 = timerCount.toLong()
                } else {
                    if (sharedPreferencesManager.recordTimeLevel2 == 0L) {
                        sharedPreferencesManager.recordTimeLevel2 = timerCount.toLong()
                    }
                }
            }

            GameLevel.Hard -> {
                if (numberStep < sharedPreferencesManager.recordMovementLevel3) {
                    sharedPreferencesManager.recordMovementLevel3 = numberStep.toLong()
                } else {
                    if (sharedPreferencesManager.recordMovementLevel3 == 0L) {
                        sharedPreferencesManager.recordMovementLevel3 = numberStep.toLong()
                    }
                }

                if (timerCount < sharedPreferencesManager.recordTimeLevel3) {
                    sharedPreferencesManager.recordTimeLevel3 = timerCount.toLong()
                } else {
                    if (sharedPreferencesManager.recordTimeLevel3 == 0L) {
                        sharedPreferencesManager.recordTimeLevel3 = timerCount.toLong()
                    }
                }
            }

            else -> {}
        }


        showDialogMedal(true)
        showDialogName()
        resetGame()
    }

    private fun startGame() {
        binding.frameGame.post {
            numberStep = 0
            startTimer()
            binding.frameGame.removeAllViews()
            context?.apply {

                val screenWidth = getScreenWidth().coerceAtMost(getScreenWidth())
                val screenHeight = getScreenWidth().coerceAtMost(getScreenWidth())
                binding.frameGame.layoutParams.width = getScreenWidth()
                binding.frameGame.layoutParams.height = getScreenWidth()

                val itemSize = floor(
                    ((screenWidth - (numCols - 1) * spacing) / numCols).coerceAtMost((screenHeight - (numRows - 1) * spacing) / numRows)
                        .toDouble()
                ).toInt()

                val startX = (screenWidth - itemSize * numCols - (numCols - 1) * spacing) / 2
                val startY = (screenWidth - itemSize * numCols - (numCols - 1) * spacing) / 2
                binding.imageAnimal.post {
                    // scale matrix
                    val matrix = Matrix()
                    matrix.setScale(
                        (numCols * itemSize).toFloat() / mBitmap.width,
                        (numRows * itemSize).toFloat() / mBitmap.height
                    )


                    // bitmap scaled
                    val bitmapScaled =
                        Bitmap.createBitmap(
                            mBitmap,
                            0,
                            0,
                            mBitmap.width,
                            mBitmap.height,
                            matrix,
                            true
                        )


                    // add items
                    var xPos = 0f
                    var yPos = 0f

                    for (i in 0 until (numRows * numCols)) {
                        val item = ImageView(context)
                        item.isClickable = true
                        item.layoutParams = ViewGroup.LayoutParams(itemSize, itemSize)
                        item.setImageBitmap(
                            Bitmap.createBitmap(
                                bitmapScaled,
                                (xPos * itemSize).toInt(),
                                (yPos * itemSize).toInt(),
                                itemSize,
                                itemSize
                            )
                        )

                        item.x = (startX + xPos * itemSize + xPos * spacing)
                        item.y = (startY + yPos * itemSize + yPos * spacing)


                        // random rotation
                        itemsRotations.add(((Math.random() * 3).roundToInt() * 90))
                        item.rotation = itemsRotations[i].toFloat()
                        binding.frameGame.addView(item)
                        binding.frameGame.gravity = Gravity.TOP

                        listImage.add(item)

                        xPos++
                        if (xPos.toInt() == numCols) {
                            xPos = 0f
                            yPos++
                        }


                        item.setOnSafeClickListener {

                            val listAnim = ArrayList<Animator>()


                            var anim = AnimatorSet()
                            anim.playTogether(
                                ObjectAnimator.ofFloat(item, View.SCALE_X, 0.5f),
                                ObjectAnimator.ofFloat(item, View.SCALE_Y, 0.5f)
                            )
                            listAnim.add(anim)


                            // rotate
                            val currentItem = listImage.indexOf(item)
                            itemsRotations[currentItem] = itemsRotations[currentItem] + 90
                            listAnim.add(
                                ObjectAnimator.ofFloat(
                                    item,
                                    View.ROTATION,
                                    itemsRotations[currentItem].toFloat()
                                )
                            )



                            anim = AnimatorSet()
                            anim.playTogether(
                                ObjectAnimator.ofFloat(item, View.SCALE_X, 1f),
                                ObjectAnimator.ofFloat(item, View.SCALE_Y, 1f)
                            )
                            listAnim.add(anim)

                            // animation

                            anim = AnimatorSet()
                            anim.playSequentially(listAnim)
                            anim.setDuration(50)
                            anim.start()
                            anim.addListener(onEnd = {
                                checkItem()
                            })
                            playSoundPool(soundMove)
                            numberStep++
                        }
                    }

                }
            }


        }


    }
}