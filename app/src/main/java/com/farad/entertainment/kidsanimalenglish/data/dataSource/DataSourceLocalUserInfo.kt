package com.farad.entertainment.kidsanimalenglish.data.dataSource

import androidx.lifecycle.LiveData
import com.farad.entertainment.kidsanimalenglish.data.db.dao.UserInfoDao
import com.farad.entertainment.kidsanimalenglish.data.model.entity.UserInfoEntity
import com.farad.entertainment.kidsanimalenglish.data.model.entity.UserInfoModel
import com.farad.entertainment.kidsanimalenglish.data.model.entity.toEntity

class DataSourceLocalUserInfo(
    private val userInfoDao: UserInfoDao,
) {
    suspend fun saveUserInfo(userInfoModel: UserInfoModel) =
        userInfoDao.insert(userInfoModel.toEntity())

    suspend fun updateUserInfo(userInfoModel: UserInfoModel) =
        userInfoDao.update(userInfoModel.toEntity())


    suspend fun updateCoin(coin:String, deviceId:String) = userInfoDao.updateCoin(coin,deviceId)

    suspend fun updateList(listItem:List<String>, deviceId:String) = userInfoDao.updateList(listItem,deviceId)

    suspend fun getUserInfoList() = userInfoDao.getUserInfoList()
      fun getUserInfoLiveData(): LiveData<UserInfoEntity?> = userInfoDao.getUserInfoLiveData()
}


