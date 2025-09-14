package com.farad.entertainment.kidsanimalenglish.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.farad.entertainment.kidsanimalenglish.base.BaseDao
import com.farad.entertainment.kidsanimalenglish.data.model.entity.UserInfoEntity


@Dao
abstract class UserInfoDao : BaseDao<UserInfoEntity>() {
    @Query(" SELECT * FROM tbl_UserInfo  ")
    abstract fun getUserInfoLiveData(): LiveData<UserInfoEntity?>

    @Query(" SELECT * FROM tbl_UserInfo  ")
    abstract suspend fun getUserInfoList(): UserInfoEntity

    @Query("UPDATE tbl_UserInfo SET Coin = :coin  WHERE id = :devideId")
    abstract suspend fun updateCoin(coin:String,devideId:String)


    @Query("UPDATE tbl_UserInfo SET listItem = :listItem  WHERE id = :devideId")
    abstract suspend fun updateList(listItem:List<String>,devideId:String)
}

