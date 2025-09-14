package com.farad.entertainment.kidsanimalenglish.data.apiService

import android.content.Context
import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.farad.entertainment.kidsanimalenglish.data.manager.SharedPreferencesManager
import com.farad.entertainment.kidsanimalenglish.data.model.DataDialog
import com.farad.entertainment.kidsanimalenglish.data.model.SignModel
import com.farad.entertainment.kidsanimalenglish.data.repository.GameRepository
import com.farad.entertainment.kidsanimalenglish.utils.APP_NUMBER
import com.farad.entertainment.kidsanimalenglish.utils.INVITECODE
import com.farad.entertainment.kidsanimalenglish.utils.getAndroidIdUser
import com.farad.entertainment.kidsanimalenglish.utils.isNotNull
import com.farad.entertainment.kidsanimalenglish.utils.isNull
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

class ApiService(
    private val sharedPre: SharedPreferencesManager,
    private val context: Context,
    val repository: GameRepository
) {
    private val cachePrefsExpireTime = 86400
    private fun getDataDialog(action: (DataDialog) -> Unit) {
        AndroidNetworking.post("https://www.faradteam.ir/jafarMazareei/adService.php?action=_readAd")
            .addBodyParameter("params_ID", APP_NUMBER.toString())
            .setTag("test")
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONArray(object : JSONArrayRequestListener {
                override fun onResponse(response: JSONArray) {
                    try {

                        Log.e("shaban", "onResponse: "+response )
                        val jsonObject = response.getJSONObject(0)
                        sharedPre.dataDialogExist = jsonObject.toString()
                        action(jsonObject.toData())
                        saveDataLocal(System.currentTimeMillis(), jsonObject)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }

                override fun onError(error: ANError) {

                }
            })
    }


    private fun saveDataLocal(time: Long, jsonObject: JSONObject?) {
        sharedPre.timeWhen = time
        if (jsonObject.isNull()) {
            sharedPre.dataDialogExist = ""
        } else {
            sharedPre.dataDialogExist = jsonObject.toString()
        }

    }

    private fun readFromCache(): DataDialog? {
        val timeWhen = sharedPre.timeWhen

        val now = System.currentTimeMillis()

        if ((now - timeWhen) > (cachePrefsExpireTime * 1000)) {
            saveDataLocal(now, null)
        }


        return sharedPre.dataDialogExist.toJSONObject()
    }

    fun readData(action: (DataDialog) -> Unit) {

        if (sharedPre.dataDialogExist.isEmpty()) {
            getDataDialog {
                action(it)
            }
        } else {

            if (readFromCache().isNull()) {
                getDataDialog {
                    action(it)
                }
            } else {

                readFromCache()?.let { action(it) }
            }
        }

    }


    private fun String.toJSONObject(): DataDialog? {
        return try {

            JSONObject(this).toData()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun JSONObject.toData(): DataDialog {
        val text = getString("text")
        val imageAddress = getString("imageAddress")
        val link = getString("link")
        val imageAdVersion = getInt("imageAdVersion")
        val bgColor = getString("bgColor")
        val textColor = getString("textColor")

        return DataDialog(text, imageAddress, link, imageAdVersion, bgColor, textColor)
    }


    private fun JSONObject.toModel(action: (SignModel) -> Unit) {

        try {
            val data = this.getJSONObject("data")
            val id = data.getString("id")
            val deviceId = data.getString("device_id")
            val parentId = data.getString("parent_id")
            val coin = data.getString("coin")
            val subUserCount = data.getString("subuser_count")
            val createdAt = data.getString("created_at")
            val updatedAt = data.getString("updated_at")

            val signModel = SignModel(
                id,
                deviceId,
                parentId,
                coin,
                subUserCount,
                createdAt,
                updatedAt
            )
            action(signModel)
        } catch (e: Exception) {

            e.fillInStackTrace()
        }


    }

    fun signUp() {
        AndroidNetworking.post("https://androidkurd.ir/referral/userInfo.php")
            .addBodyParameter("device_id", context.getAndroidIdUser())
            .addBodyParameter("parent_id", "0")
            .setTag("signUp")
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    try {
                        sharedPre.isSignUp = true
                        response.toModel { model ->
                            sharedPre.subUserCount = model.superuserCount
                            sharedPre.inviteCode = model.id
                            sharedPre.coinCount = model.coin
                            INVITECODE = sharedPre.inviteCode
                            CoroutineScope(Dispatchers.IO).launch {
                                repository.updateCoin(model.coin, context.getAndroidIdUser())
                            }


                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }

                override fun onError(error: ANError) {
                }
            })
    }
    fun splitStringToArray(listItem: String): List<String> {
        return listItem.split(",")
            .filter { it.isNotEmpty() }  // حذف عناصر خالی
            .map { it  }  // تبدیل هر عنصر به عدد صحیح
    }
    fun updateItem(listItem:String,action: (List<String>) -> Unit) {
        AndroidNetworking.post("https://androidkurd.ir/referral/updateitem.php")
            .addBodyParameter("device_id", context.getAndroidIdUser())
            .addBodyParameter("listItem", listItem)
            .setTag("signUp")
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    try {
                        val data = response.getJSONObject("data")
                        val listItemServer = data.getString("listItem")

                        action( splitStringToArray(listItemServer))

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }

                override fun onError(error: ANError) {
                }
            })
    }

    fun inviteFriend(inviteCode: String,error:()->Unit,success:()->Unit) {
        AndroidNetworking.post("https://androidkurd.ir/referral/userInfo.php")
            .addBodyParameter("device_id", context.getAndroidIdUser())
            .addBodyParameter("parent_id", inviteCode.lowercase())
            .setTag("signUp")
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    try {
                        response.toModel { model ->

                            sharedPre.subUserCount = model.superuserCount
                            sharedPre.coinCount = model.coin
                            sharedPre.inviteCode = model.id
                            CoroutineScope(Dispatchers.IO).launch {
                                repository.updateCoin(model.coin, context.getAndroidIdUser())
                            }

                        }
                    }catch (e:Exception){
                        e.fillInStackTrace()
                    }


                    try {
                        val errors = response.getJSONObject("errors")

                        val message = errors.getString("parent_id")
                        if(errors.isNotNull()){

                            error()
                        }


                    } catch (e: Exception) {

                        success()
                        e.printStackTrace()
                    }

                }

                override fun onError(error: ANError) {
                }
            })
    }

    fun updateCoin(coin: String, progressDismiss:()->Unit) {
        AndroidNetworking.post("https://androidkurd.ir/referral/userInfo.php")
            .addBodyParameter("device_id", context.getAndroidIdUser())
            .addBodyParameter("parent_id", "0")
            .addBodyParameter("coin", coin)
            .setTag("signUp")
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {

                    try {
                        response.toModel { model ->
                            sharedPre.subUserCount = model.superuserCount
                            sharedPre.inviteCode = model.id
                            CoroutineScope(Dispatchers.IO).launch {
                                sharedPre.coinCount = model.coin
                                repository.updateCoin(model.coin, context.getAndroidIdUser())
                            }

                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    progressDismiss()
                }

                override fun onError(error: ANError) {
                }
            })
    }

}




