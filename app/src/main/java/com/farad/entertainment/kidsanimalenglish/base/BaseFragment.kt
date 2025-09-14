package com.farad.entertainment.kidsanimalenglish.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.app.BaseApp
import com.farad.entertainment.kidsanimalenglish.base.navigation.base.BaseNavigator
import com.farad.entertainment.kidsanimalenglish.data.manager.NavigationManager
import com.farad.entertainment.kidsanimalenglish.ui.activity.main.MainActivity
import com.farad.entertainment.kidsanimalenglish.ui.dialog.ConfirmCancelDialog
import com.farad.entertainment.kidsanimalenglish.ui.dialog.DialogLoading
import com.farad.entertainment.kidsanimalenglish.ui.dialog.DialogLock
import com.farad.entertainment.kidsanimalenglish.ui.dialog.DialogOk
import com.farad.entertainment.kidsanimalenglish.ui.dialog.DialogWebView
import com.farad.entertainment.kidsanimalenglish.ui.dialog.MessageDialog
import com.farad.entertainment.kidsanimalenglish.utils.getColorCompat
import com.farad.entertainment.kidsanimalenglish.utils.hideKeyboard
import com.farad.entertainment.kidsanimalenglish.utils.isNetworkAvailable
import com.farad.entertainment.kidsanimalenglish.utils.isNull
import com.farad.entertainment.kidsanimalenglish.utils.lifecycleScopeDelayTryCatch
import com.farad.entertainment.kidsanimalenglish.utils.safeDismiss
import com.farad.entertainment.kidsanimalenglish.utils.safeShow
import com.farad.entertainment.kidsanimalenglish.utils.setBackGround

abstract class BaseFragment<VB : ViewBinding> : Fragment(), FragmentCreateView,
    FragmentNavigate {

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    @Suppress("PropertyName")
    var _binding: VB? = null
    private var dialogLoading: DialogLoading?=null

    protected val binding: VB
        get() {
           return _binding!!
        }
      fun showDialogLoading() {
        if (dialogLoading.isNull())
            dialogLoading = DialogLoading()

        dialogLoading?.safeShow(childFragmentManager)
    }
    fun dismissDialogLoading(){
        dialogLoading?.safeDismiss()
    }
    protected fun withBinding(action: (VB) -> Unit) {
        try {
            _binding?.let {

                    action(it)
            } ?: run {

            }
        }catch (e:Exception){
            e.fillInStackTrace()
        }

    }
    fun closeKeyboard() {
        lifecycleScopeDelayTryCatch(1){

            hideKeyboard()
        }

    }


    fun showLockDialog(openLock: () -> Unit) {
        val dialogLock = context?.let { DialogLock(it) }
        dialogLock?.setOnOpenLockListener {
            openLock()
            val dialogOk = DialogOk()

            // dialogOk.show()
        }

        dialogLock?.show( )
    }
    protected fun  showBanner(isShow:Boolean){
        (activity as? MainActivity)?.showBanner(isShow)
    }
   protected fun showDialogWebView(url: String,title: String){
        val dialog = DialogWebView()
        dialog.urlLoad=url
       dialog.title=title

        dialog.safeShow(childFragmentManager)
    }
    fun getNavigator() = activity as? BaseNavigator

      fun showDialogVideoErrorNetwork(action:()->Unit) {
          if (context?.isNetworkAvailable()==false) {
              val dialog=MessageDialog()
              dialog.setTextDialog(getString(R.string.this_video_is_not_saved_offline))
              dialog.setVisibleBtnOk(false)
              dialog.safeShow(childFragmentManager)
          }else{
              action()
          }

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
         _binding = bindingInflater.invoke(inflater, container, false)
        beforeCreateView()

        return if (_binding != null) _binding!!.root else null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBackGroundRoot(true)
        initBeforeSetup()
        showBanner(true)
        initObserveViewModel()

        withBinding {

            setup()
        }
    }


    fun setBackGroundRoot(isSet:Boolean=true){

        lifecycleScopeDelayTryCatch(200){
            if (isSet){
                binding.root.setBackGround(R.drawable.back)
                (activity as? MainActivity)?.setBackGroundRoot(true)
            }else{
                binding.root.setBackGround(0)
                context?.getColorCompat(R.color.color_background)
                    ?.let { binding.root.setBackgroundColor(it) }
                (activity as? MainActivity)?.setBackGroundRoot(false)
            }
        }

    }
    override fun initBeforeSetup() {
        //nothing
    }

    abstract fun setup()

    override fun beforeCreateView() {
    }


    override fun initObserveViewModel() {

    }

    override fun navigate(
        navDirections: NavDirections,
        pageAnimation: NavigationManager.PageAnimation
    ) {
        view?.hideKeyboard()
        getNavigator()?.navigate(navDirections, pageAnimation)
    }


    override fun navigate(id: Int, pageAnimation: NavigationManager.PageAnimation) {
        view?.hideKeyboard()
        getNavigator()?.navigate(id, pageAnimation)
    }

    override fun navigate(id: Int, bundle: Bundle, pageAnimation: NavigationManager.PageAnimation) {
        view?.hideKeyboard()
        getNavigator()?.navigate(id, bundle, pageAnimation)
    }

    fun popBackStack() {
        view?.hideKeyboard()
        runCatching {
            findNavController().popBackStack()
        }
    }
    fun showDialogMessage(message:String){
        val dialog= MessageDialog()
        dialog.setTextDialog(message)
        dialog.safeShow(childFragmentManager)
    }

    fun  showDialogConfirmCancel(title:String,textLeft:String,textRight:String,leftListener:()->Unit,rightListener:()->Unit){
        val dialog= ConfirmCancelDialog()
        dialog.setTitleDialog(title)
        dialog.setBtnTextLeft(textLeft)
        dialog.setBtnTextRight(textRight)
        dialog.setOnBtnLeftListener {
            leftListener()
        }
        dialog.setOnBtnRightListener {
            rightListener()
        }
        dialog.safeShow(childFragmentManager)
    }

    fun getBaseActivity(): BaseActivity<*>? {
        return activity as? BaseActivity<*>
    }


    fun getCoreApp(): BaseApp {
        return getBaseActivity()?.application as BaseApp
    }

    fun getDialogManager() = getBaseActivity()?.dialogManager


    override fun onDestroyView() {
        try {

            if (isNullView().not()) {
                super.onDestroyView()
                _binding = null
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    fun isNullView(): Boolean = _binding == null

}


interface FragmentCreateView {
    fun beforeCreateView()
    fun initObserveViewModel()
    fun initBeforeSetup()
}

interface FragmentNavigate {
    fun navigate(
        navDirections: NavDirections,
        pageAnimation: NavigationManager.PageAnimation = NavigationManager.PageAnimation.DEFAULT
    )

    fun navigate(
        @IdRes id: Int,
        pageAnimation: NavigationManager.PageAnimation = NavigationManager.PageAnimation.DEFAULT
    )

    fun navigate(
        @IdRes id: Int,
        bundle: Bundle,
        pageAnimation: NavigationManager.PageAnimation = NavigationManager.PageAnimation.DEFAULT
    )

}
