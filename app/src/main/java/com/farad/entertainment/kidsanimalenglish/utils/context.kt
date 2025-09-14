package com.farad.entertainment.kidsanimalenglish.utils


import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Display
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.drawable.toBitmap
import androidx.core.hardware.display.DisplayManagerCompat
import androidx.fragment.app.Fragment
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.MarketName
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.Locale

fun Context.setLocaleApp(): Context {
    var language = "fa"
    checkLanguage(farsi = {
        language = "fa"
    }, english = {
        language = "en"
    })
    val locale = Locale(language)
    Locale.setDefault(locale)
    val configuration = resources.configuration
    configuration.setLocale(locale)
    configuration.setLayoutDirection(locale)
    return createConfigurationContext(configuration)
}

fun Context.subscribeToChannel() {
    var youtubeIntent: Intent? = null

    youtubeIntent = Intent(Intent.ACTION_VIEW)
    val youtubeURL = "https://www.youtube.com/@JafarMazareei"
    try {

        youtubeIntent.setPackage(PACKAGE_NAME_YOUTUBE)
        youtubeIntent.setData(Uri.parse(youtubeURL))
        startActivity(youtubeIntent)

    } catch (e: Exception) {
        e.fillInStackTrace()

        try {
            youtubeIntent = Intent(Intent.ACTION_VIEW)
            youtubeIntent.setData(Uri.parse(youtubeURL))
            startActivity(youtubeIntent)
        }catch (e:Exception){
            e.fillInStackTrace()
        }

    }

}

fun Context.intentToTelegram() {
    val i = Intent(Intent.ACTION_VIEW)
    if (isInitApp(PACKAGE_TELEGRAM)) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("https://t.me/Jafarmazareei")
        i.setPackage(PACKAGE_TELEGRAM)
        startActivity(intent)
    } else {
        Toast.makeText(
            applicationContext,
            "Telegram is not installed on your phone",
            Toast.LENGTH_SHORT
        )
            .show()
    }
}

fun Context.intentToWhatsapp() {
    if (PHONE_WHATSAPP.isNotEmpty()) {
        if (isInitApp(PACKAGE_WHATSAPP)) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://api.whatsapp.com/send?phone=$PHONE_WHATSAPP")
            intent.setPackage(PACKAGE_WHATSAPP)
            startActivity(intent)
        } else {
            Toast.makeText(
                applicationContext,
                "WhatsApp is not installed on your phone",
                Toast.LENGTH_SHORT
            )
                .show()
        }
    } else {
        toast(getString(R.string.the_mobile_number_is_empty))
    }

}

fun Context.intentToInstagram() {
    if (isInitApp(PACKAGE_INSTAGRAM)) {
        val uri = Uri.parse(PAGE_INSTAGRAM_LINK)
        val likeIng = Intent(Intent.ACTION_VIEW, uri)
        likeIng.setPackage(PACKAGE_INSTAGRAM)

        try {
            startActivity(likeIng)
        } catch (e: ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(PAGE_INSTAGRAM_LINK)))
        }
    }

}

fun Context.getPackageInfo(nameApp: String) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        packageManager?.getPackageInfo(nameApp, PackageManager.PackageInfoFlags.of(0))
    } else {
        packageManager?.getPackageInfo(nameApp, 0)
    }
}

fun Context.isInitApp(nameApp: String): Boolean {

    return try {
        getPackageInfo(nameApp)
        true
    } catch (e: Exception) {
        false
    }
}

fun Context.getDrawableCompat(@DrawableRes drawableId: Int): Drawable {
    return AppCompatResources.getDrawable(this, drawableId)!!
}

fun Context.getBitmapFromAssets(fileName: String): Bitmap? {
    return try {
        val assetManager = resources.assets
        val inputStream = assetManager.open(fileName)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        inputStream.close()
        bitmap
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }


}

fun Context.getFromAssets(fileName: String): String {
    var result = ""
    try {
        val inputReader = InputStreamReader(resources.assets.open(fileName))
        val bufReader = BufferedReader(inputReader)
        var line: String

        while (bufReader.readLine().also { line = it } != null) {
            if (line.trim { it <= ' ' } == "") continue
            result += line + "\r\n"
        }
        return result
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return result
}

@SuppressLint("DiscouragedApi")
fun Context.getStringByName(nameString: String): String {
    val idString = resources.getIdentifier(nameString, "string", packageName)
    return getString(if (idString == 0) R.string.app_name else idString)

}

@SuppressLint("DiscouragedApi")
fun Context.getMusicInRawByName(nameString: String): Int? {
    val idSound = resources.getIdentifier(
        nameString,
        "raw",
        packageName
    )
    return if (idSound > 0) {
        idSound
    } else {
        null
    }

}

@SuppressLint("DiscouragedApi")
fun Context.getImageDrawableByName(nameImage: String): Int {
    return resources.getIdentifier(nameImage, "drawable", packageName)
}

fun Context.getColorCompatStateList(@ColorRes id: Int): ColorStateList {
    return AppCompatResources.getColorStateList(this, id)
}

fun Context.getColorCompat(color: Int) = ContextCompat.getColor(this, color)

@ColorInt
fun Context.getColorCompatAttr(@AttrRes color: Int, alpha: Int = 255): Int {
    val mColorByAttr = HashMap<Int, Int>()
    return mColorByAttr.getOrPut(color) {
        try {
            val colorTemp = TypedValue()
            theme.resolveAttribute(color, colorTemp, true)
            ColorUtils.setAlphaComponent(colorTemp.data, alpha)
        } catch (e: Exception) {
            ContextCompat.getColor(this, android.R.color.white)
        }
    }
}

fun Context.getHexColorCompatAttr(@AttrRes color: Int, alpha: Int = 255) =
    String.format("#%06X", 0xFFFFFF and getColorCompatAttr(color, alpha))


fun Fragment.getDrawableCompat(@DrawableRes id: Int): Drawable {
    return AppCompatResources.getDrawable(requireContext(), id)!!
}

fun Activity.getDrawableCompat(@DrawableRes id: Int): Drawable {
    return AppCompatResources.getDrawable(this, id)!!
}

fun Fragment.getBitmapCompat(@DrawableRes id: Int): Bitmap {
    return getDrawableCompat(id).toBitmap()
}

fun Activity.getBitmapCompat(@DrawableRes id: Int): Bitmap {
    return getDrawableCompat(id).toBitmap()
}

inline val Context.displayWidth: Int
    get() = resources.displayMetrics.widthPixels

inline val Context.displayHeight: Int
    get() = resources.displayMetrics.heightPixels

fun Context?.toast(text: String, duration: Int = Toast.LENGTH_SHORT) =
    this?.let { Toast.makeText(it, text, duration).show() }

fun Context?.toast(@StringRes textId: Int, duration: Int = Toast.LENGTH_LONG) =
    this?.let { Toast.makeText(it, textId, duration).show() }

fun Context.call(phone: String) {
    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
    startActivity(intent)
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

@SuppressLint("MissingPermission")
@Suppress("DEPRECATION")
fun Context.isNetworkAvailable(): Boolean {
    var result = true
    runCatching {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }
    }.onFailure {
        result = true
    }
    return result
}

fun Resources.getRawUri(@RawRes rawRes: Int?): String? {
    if (rawRes.isNull())
        return null
    return "%s://%s/%s/%s".format(
        ContentResolver.SCHEME_ANDROID_RESOURCE, this.getResourcePackageName(rawRes!!),
        this.getResourceTypeName(rawRes), this.getResourceEntryName(rawRes)
    )
}

fun Context.openUrl(url: String) {
    try {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(i)
    } catch (ignored: ActivityNotFoundException) {
        toast(getString(R.string.can_not_open_url))
    }
}

fun Context.sharePaint(uri: Uri, textShare: String) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "image/*"
    intent.putExtra(
        Intent.EXTRA_STREAM, uri
    )
    intent.putExtra(Intent.EXTRA_TEXT, textShare)
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    startActivity(Intent.createChooser(intent, "send my paint ghooghooli"))
}

fun Context.goMarketPage() {
    when (MARKET_NAME) {
        MarketName.MYKET -> {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse("myket://details?id=$packageName"))
            intent.setPackage("ir.mservices.market")
            startActivity(intent)
        }

        MarketName.CAFE_BAZAAR -> {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse("bazaar://details?id=$packageName"))
            intent.setPackage("com.farsitel.bazaar")
            startActivity(intent)
        }

        MarketName.GOOGLE_PLY -> {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=$packageName")
                )
            )
        }

        else -> {}


    }

}


fun Context.goToMainApps() {

    if (isNetworkAvailable()) {
        when (MARKET_NAME) {


            MarketName.GOOGLE_PLY -> {

                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=$packageName")
                    )
                )
            }


            MarketName.GALAXY_STORE -> {


                if (isInitApp(PACKAGE_NAME_GALAXY)) {

                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://apps.samsung.com/appquery/AppRating.as?appId=$packageName")
                        )
                    )
                }
            }

            MarketName.CAFE_BAZAAR -> {
                if (isInitApp(PACKAGE_NAME_BAZAAR)) {
                    val intent = Intent(Intent.ACTION_EDIT)
                    intent.data = Uri.parse("bazaar://details?id=$packageName")
                    intent.setPackage("com.farsitel.bazaar")
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                } else {
                    toast(getString(R.string.not_init_app))
                }
            }

            MarketName.MYKET -> {
                if (isInitApp(PACKAGE_NAME_MYKET)) {
                    val url = "myket://comment?id=$packageName"
                    val intent = Intent()
                    intent.action = Intent.ACTION_VIEW
                    intent.data = Uri.parse(url)
                    startActivity(intent)

                } else {
                    toast(getString(R.string.not_init_app))
                }
            }

            else -> {

            }
        }
    } else {
        toast(getString(R.string.error_connect_net))
    }

}


fun Context.getLinkDownload(): String {
    var shareText = ""
    shareText += when (MARKET_NAME) {

        MarketName.GOOGLE_PLY -> {
            "\nhttps://play.google.com/store/apps/details?id=$packageName"
        }

        MarketName.GALAXY_STORE -> {
            "\nhttps://apps.samsung.com/appquery/appDetail.as?appId=$packageName"
            ""
        }

        MarketName.CAFE_BAZAAR -> {
            "\nhttps://cafebazaar.ir/app/$packageName"
        }

        MarketName.MYKET -> {
            "\nhttps://myket.ir/app/$packageName"
        }

        else -> {

        }

    }
    return shareText
}


fun Context.shareText(text: String = "", shareTitle: String = "") {

    val sendIntent = Intent()
    sendIntent.action = Intent.ACTION_SEND
    sendIntent.putExtra(
        Intent.EXTRA_TEXT,
        text + "\n\n" + getString(R.string.share_text) + "\n\n" + getLinkDownload()
    )
    sendIntent.type = "text/plain"
    startActivity(Intent.createChooser(sendIntent, shareTitle))
}

fun Context.invitedFriend(text: String = "", shareTitle: String = "") {

    val sendIntent = Intent()
    sendIntent.action = Intent.ACTION_SEND
    sendIntent.putExtra(
        Intent.EXTRA_TEXT,
        text + "\n\n"  + " " + "\n\n\n" + " " + "\n\n" + "GET IT ON GOOGLE PLAY \n\n" + "\n\n\n" + getLinkDownload() + "\n\n\n" + "Share this link with your friends because spreading the love and care for animals is something we can all take pride in, then earn coins to unlock more items—because every share makes a difference!"
    )
    sendIntent.type = "text/plain"
    startActivity(Intent.createChooser(sendIntent, shareTitle))
}


fun Context.copyToClipboard(text: String, message: String = "Copy Clipboard") {
    val clipboard = ContextCompat.getSystemService(this, ClipboardManager::class.java)
    clipboard?.setPrimaryClip(ClipData.newPlainText("", text))
    if (message.isNotEmpty())
        toast(message)
}

fun Context.getLocalizedResources(desiredLocale: Locale): Resources {
    var conf: Configuration = resources.configuration
    conf = Configuration(conf)
    conf.setLocale(desiredLocale)
    val localizedContext = createConfigurationContext(conf)
    return localizedContext.resources
}

fun Context.isPermissionGrantedForMediaLocationAccess(): Boolean {
    val result: Int =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_MEDIA_LOCATION
            )
        } else {
            return true
        }
    return result == PackageManager.PERMISSION_GRANTED
}


fun Context.getHexColorResCompat(@ColorRes color: Int) =
    String.format("#%06X", 0xFFFFFF and getColorCompat(color))

fun Context.getHexColorCompat(@ColorInt color: Int) =
    String.format("#%06X", 0xFFFFFF and color)


fun Context.getScreenWidth(): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

        val defaultDisplay =
            DisplayManagerCompat.getInstance(this).getDisplay(Display.DEFAULT_DISPLAY)
        val displayContext = this.createDisplayContext(defaultDisplay!!)

        displayContext.resources.displayMetrics.widthPixels
    } else {
        val windowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val displayMetrics = DisplayMetrics()
        @Suppress("DEPRECATION")
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        displayMetrics.widthPixels
    }
}

fun Context.getScreenHeight(): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

        val defaultDisplay =
            DisplayManagerCompat.getInstance(this).getDisplay(Display.DEFAULT_DISPLAY)
        val displayContext = this.createDisplayContext(defaultDisplay!!)

        displayContext.resources.displayMetrics.heightPixels
    } else {
        val windowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val displayMetrics = DisplayMetrics()
        @Suppress("DEPRECATION")
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        displayMetrics.heightPixels
    }
}

fun Context.getInstallerPackageName(): String? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        packageManager.getInstallSourceInfo(packageName).installingPackageName
    } else {
        @Suppress("DEPRECATION")
        packageManager.getInstallerPackageName(packageName)
    }
}


