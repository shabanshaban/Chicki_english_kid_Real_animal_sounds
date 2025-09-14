package com.farad.entertainment.kidsanimalenglish.ui.dialog

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.farad.entertainment.kidsanimalenglish.base.BaseDialogFragment
import com.farad.entertainment.kidsanimalenglish.databinding.DialogWebviewBinding
import com.farad.entertainment.kidsanimalenglish.utils.safeDismiss
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener
import com.farad.entertainment.kidsanimalenglish.utils.visibleOrGone


class DialogWebView() : BaseDialogFragment<DialogWebviewBinding>(), Parcelable {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DialogWebviewBinding
        get() = DialogWebviewBinding::inflate
    var urlLoad = ""
    var title = ""
    private var onPaymentListener: (() -> Unit)? = null

    constructor(parcel: Parcel) : this() {
        urlLoad = parcel.readString().toString()
    }

    fun setOnPaymentListener(listener: (() -> Unit)) {
        onPaymentListener = listener
    }

    init {
        setThem(android.R.style.Theme_Translucent_NoTitleBar)
    }

    override fun setup() {
        if (title.isNotEmpty()) {
            binding.tvTitle.text = title
        }
        initWebView()
        binding.imageClose.setOnSafeClickListener {
            safeDismiss()
        }

        setOnBackPressedListener {

            this.safeDismiss()

        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        binding.apply {
            webView.apply {
                settings.javaScriptEnabled = true
                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                settings.builtInZoomControls = false
                settings.domStorageEnabled = true
                settings.databaseEnabled = true
                webViewClient = WebViewClient()
                isVerticalScrollBarEnabled = true
                isHorizontalScrollBarEnabled = true


                settings.cacheMode = WebSettings.LOAD_NO_CACHE

                //settings.blockNetworkImage = true
                //  settings.loadsImagesAutomatically = true
                //settings.setGeolocationEnabled(false)
                // settings.setNeedInitialFocus(false)
                // settings.defaultFontSize =  16.sp


                webChromeClient = object : WebChromeClient() {
                    override fun onProgressChanged(view: WebView?, newProgress: Int) {
                        webViewLoading.visibleOrGone(newProgress != 100)
                        webViewLoading.progress = newProgress
                        binding.webView.visibleOrGone(newProgress == 100, true)

                        if (newProgress == 100) {
                            view?.evaluateJavascript("(function() { return document.documentElement.outerHTML; })();") {
                                if (it.contains("پیگیری")) {
                                    onPaymentListener?.invoke()
                                }
                            }
                        }
                    }
                }
                loadUrl(urlLoad)
            }
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(urlLoad)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DialogWebView> {
        override fun createFromParcel(parcel: Parcel): DialogWebView {
            return DialogWebView(parcel)
        }

        override fun newArray(size: Int): Array<DialogWebView?> {
            return arrayOfNulls(size)
        }
    }

}