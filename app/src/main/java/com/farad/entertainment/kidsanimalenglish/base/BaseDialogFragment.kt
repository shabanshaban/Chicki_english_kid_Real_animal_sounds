package com.farad.entertainment.kidsanimalenglish.base

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.activity.ComponentDialog
import androidx.activity.OnBackPressedCallback
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.utils.isNull
import com.farad.entertainment.kidsanimalenglish.utils.safeDismiss


@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseDialogFragment<VB : ViewBinding> : AppCompatDialogFragment() {

    private var _binding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    fun getBaseActivity(): BaseActivity<*>? = activity as? BaseActivity<*>

    var isCanceledOnTouchOutside = false
    private var themeCustom = 0

    fun getNavigator() = findNavController()
    private var onDismissListener: (() -> Unit)? = null
    fun onDismissListener(listener: () -> Unit) {
        onDismissListener = listener
    }

    protected var onItemBackPressedListener: (() -> Unit)? = null
    fun setOnBackPressedListener(listener: () -> Unit) {
        onItemBackPressedListener = listener
    }

    @Suppress("UNCHECKED_CAST")
    protected val binding: VB
        get() = _binding as VB

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {


        val dialog = object : ComponentDialog(
            requireContext(),
            getThem()
        ) {

        }

        return dialog.apply {
            setCanceledOnTouchOutside(isCanceledOnTouchOutside)
            setCancelable(isCancelable)
            window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        }


    }

    fun setThem(@StyleRes them: Int) {
        themeCustom = them
    }

    private fun getThem(): Int {
        return when {
            themeCustom != 0 -> {
                themeCustom
            }

            else -> {
                R.style.Theme_Dialog2
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        showsDialog = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = bindingInflater.invoke(inflater, container, false)

        return requireNotNull(_binding).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        (dialog as? ComponentDialog)?.apply {

         /*   window?.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )*/

            onBackPressedDispatcher.addCallback(
                viewLifecycleOwner,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        if (onItemBackPressedListener.isNull())
                            safeDismiss()
                        else onItemBackPressedListener?.invoke()
                    }
                })
        }


        initBeforeSetup()
        setup()
    }

    fun isNullView(): Boolean = _binding == null

    open fun initBeforeSetup() {
        //nothing
    }

    abstract fun setup()


    override fun onDestroyView() {
        super.onDestroyView()
        onDismissListener?.invoke()
        _binding = null
    }
}