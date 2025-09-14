package com.farad.entertainment.kidsanimalenglish.data.manager

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import coil.imageLoader
import coil.request.ImageRequest
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.utils.sinaLog
import com.farad.entertainment.kidsanimalenglish.utils.toast
import java.io.*
import java.nio.file.Files
import java.nio.file.Paths
import java.text.SimpleDateFormat
import java.util.*


@Suppress("unused")
class FileManager {

    companion object {
        private const val GALLERY_FOLDER = "mr_rooster"


        fun getUriFromFile(file: File, context: Context, appId: String): Uri {
            // val authority = BuildConfig.APPLICATION_ID + ".provider"
            val authority = "$appId.provider"
            try {
                return FileProvider.getUriForFile(context, authority, file)
            } catch (e: Exception) {
                try {
                    // Note: Periodically clear this cache
                    val cacheFolder = File(context.cacheDir, "CROP_LIB_CACHE")
                    val cacheLocation = File(cacheFolder, file.name)
                    var input: InputStream? = null
                    var output: OutputStream? = null
                    try {
                        input = FileInputStream(file)
                        output = FileOutputStream(cacheLocation) // appending output stream
                        input.copyTo(output)

                        return FileProvider.getUriForFile(context, authority, cacheLocation)
                    } catch (e: Exception) {
                        val path = "content://$authority/files/my_images/"

                        if (Build.VERSION.SDK_INT >= 26) {
                            Files.createDirectories(Paths.get(path))
                        } else {
                            val directory = File(path)
                            if (!directory.exists()) directory.mkdirs()
                        }

                        return Uri.parse("$path${file.name}")
                    } finally {
                        input?.close()
                        output?.close()
                    }
                } catch (e: Exception) {
                    if (Build.VERSION.SDK_INT < 29) {
                        val cacheDir = context.externalCacheDir
                        cacheDir?.let {
                            try {
                                return Uri.fromFile(File(cacheDir.path, file.absolutePath))
                            } catch (e: Exception) {
                                sinaLog(e.message.toString())
                            }
                        }
                    }
                    // If nothing else work we try

                    return Uri.fromFile(file)
                }
            }
        }

        fun getFilePathFromUri(context: Context, uri: Uri, uniqueName: Boolean): String =
            if (uri.path?.contains("file://") == true) uri.path!!
            else getFileFromContentUri(context, uri, uniqueName).path


        suspend fun saveImageToGalleryByUrl(
            context: Context,
            imageUrl: String,
            startSaveImage: suspend () -> Unit,
            endSaveImage: suspend (Boolean) -> Unit,
        ) {

            val request: ImageRequest = ImageRequest.Builder(context).data(imageUrl).build()
            val bitmap = context.imageLoader.execute(request).drawable?.toBitmap()
            if (bitmap == null) {
                context.toast(context.getString(R.string.failed_saved_in_gallery))
                endSaveImage(false)
                return
            }
            saveImageToGallery(context, bitmap, startSaveImage, endSaveImage)
        }


        suspend fun saveImageToGallery(
            contextM: Context?,
            bitmap: Bitmap,
            startSaveImage: suspend () -> Unit,
            endSaveImage: suspend (Boolean) -> Unit,
        ) {

            contextM?.let {context->
                startSaveImage()
                val name = "${UUID.randomUUID()}.png"
                var fos: OutputStream? = null
                val folder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                    File(Environment.DIRECTORY_PICTURES + File.separator + GALLERY_FOLDER)
                else
                    File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).path + File.separator + GALLERY_FOLDER)


                if (folder.exists().not()) {
                    folder.mkdirs()
                }

                val imageFile = File(folder.path, name)

                runCatching {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        context.contentResolver?.also { resolver ->
                            val contentValues = ContentValues().apply {
                                put(MediaStore.MediaColumns.DISPLAY_NAME, name)
                                put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
                                put(
                                    MediaStore.MediaColumns.RELATIVE_PATH,
                                    folder.path
                                )
                            }
                            val imageUri =
                                resolver.insert(
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                    contentValues
                                ) ?: throw IOException("Failed to create new MediaStore record.")
                            fos = imageUri.let { resolver.openOutputStream(it) }
                        }
                    } else {

                        fos = FileOutputStream(imageFile)
                    }
                    fos?.use {
                        if (!bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)) {
                            throw IOException("Failed to save bitmap.")
                        }
                        context.toast(context.getString(R.string.successfully_saved_in_gallery))
                        MediaScannerConnection.scanFile(
                            context,
                            arrayOf(imageFile.toString()),
                            null,
                            null
                        )
                    }
                    endSaveImage(true)

                }.onFailure {
                    fos?.close()
                    context.toast(it.message.toString())
                    endSaveImage(false)
                }
            }

        }
    }
}

/*fun getProfileCropOptions(): CropImageOptions {
    return CropImageOptions().apply {
        cropShape = CropImageView.CropShape.OVAL
        cornerShape = CropImageView.CropCornerShape.OVAL
        aspectRatioX = 1
        aspectRatioY = 1
        minCropResultWidth = 200
        minCropResultHeight = 200
        fixAspectRatio = true
        guidelines = CropImageView.Guidelines.ON_TOUCH
    }
}

fun getRectangleCropOptions(): CropImageOptions {
    return CropImageOptions().apply {
        cropShape = CropImageView.CropShape.RECTANGLE
        cornerShape = CropImageView.CropCornerShape.RECTANGLE
        aspectRatioX = 1
        aspectRatioY = 1
        minCropResultWidth = 200
        minCropResultHeight = 200
        fixAspectRatio = true
        guidelines = CropImageView.Guidelines.ON_TOUCH
    }
}*/


private fun getFileFromContentUri(context: Context, contentUri: Uri, uniqueName: Boolean): File {
    // Preparing Temp file name
    val fileExtension = getFileExtension(context, contentUri) ?: ""
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val fileName = ("temp_file_" + if (uniqueName) timeStamp else "") + ".$fileExtension"
    // Creating Temp file
    val tempFile = File(context.cacheDir, fileName)
    tempFile.createNewFile()
    // Initialize streams
    var oStream: FileOutputStream? = null
    var inputStream: InputStream? = null

    try {
        oStream = FileOutputStream(tempFile)
        inputStream = context.contentResolver.openInputStream(contentUri)

        inputStream?.let { copy(inputStream, oStream) }
        oStream.flush()
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        // Close streams
        inputStream?.close()
        oStream?.close()
    }

    return tempFile
}
fun Context.createDirectoryInInternalStorage(directoryName: String): File {
        val directory = File(this.filesDir, directoryName)
        if (!directory.exists()) {
                directory.mkdirs()
             }
        return directory
     }
private fun getDisc(): File {
    val file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
    return File(file, "YOUR_ALBUM_NAME")
}
private fun getFileExtension(context: Context, uri: Uri): String? =
    if (uri.scheme == ContentResolver.SCHEME_CONTENT) MimeTypeMap.getSingleton()
        .getExtensionFromMimeType(context.contentResolver.getType(uri))
    else uri.path?.let { MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(File(it)).toString()) }

@Throws(IOException::class)
private fun copy(source: InputStream, target: OutputStream) {
    val buf = ByteArray(8192)
    var length: Int
    while (source.read(buf).also { length = it } > 0) {
        target.write(buf, 0, length)
    }
}



