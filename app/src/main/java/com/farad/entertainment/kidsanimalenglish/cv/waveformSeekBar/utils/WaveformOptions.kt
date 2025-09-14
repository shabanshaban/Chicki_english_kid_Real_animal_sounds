package com.farad.entertainment.kidsanimalenglish.cv.waveformSeekBar.utils

import android.content.Context
import android.content.res.Resources
import android.net.Uri
//import linc.com.amplituda.Amplituda
//import linc.com.amplituda.AmplitudaProcessingOutput
//import linc.com.amplituda.exceptions.AmplitudaException

internal object WaveformOptions {

    @JvmStatic
    fun getSampleFrom(context: Context, pathOrUrl: String, onSuccess: (IntArray) -> Unit) {

        // handleAmplitudaOutput(am(context).processAudio(pathOrUrl), onSuccess)
    }

    @JvmStatic
    fun getSampleFrom(context: Context, resource: Int, onSuccess: (IntArray) -> Unit) {
        handleAmplitudaOutput(context,resource, onSuccess)
    }

    @JvmStatic
    fun getSampleFrom(context: Context, uri: Uri, onSuccess: (IntArray) -> Unit) {
        // handleAmplitudaOutput(Amplituda(context).processAudio(context.uriToFile(uri)), onSuccess)
    }

    private fun handleAmplitudaOutput(
        context: Context,
        resource: Int,
        onSuccess: (IntArray) -> Unit
    ) {

        readBytesFromRaw(context.resources,resource)?.let {
            val listIntByte=ArrayList<Int>()
            it.forEach {byte ->
                listIntByte.add(byte.toInt())
            }
            onSuccess(listIntByte.toTypedArray().toIntArray())
        }

    }
    fun readBytesFromRaw(resources: Resources, rawResourceId: Int): ByteArray? {
        return try {
            val inputStream = resources.openRawResource(rawResourceId)
            val byteArray = ByteArray(inputStream.available())
            inputStream.read(byteArray)
            byteArray
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
