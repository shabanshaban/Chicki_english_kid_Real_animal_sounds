package com.farad.entertainment.kidsanimalenglish.base

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

abstract class BaseDao<T> {

    /**
     * Insert an object in the database.
     *
     * @param obj the object to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(obj: T)

    /**
     * Insert an array of objects in the database.
     *
     * @param obj the objects to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(vararg obj: T)

    /**
     * Insert an list of objects in the database.
     *
     * @param obj the objects to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(obj: List<T>)

    /**
     * Update an object from the database.
     *
     * @param obj the object to be updated
     */

    @Update
    abstract suspend fun update(obj: T)

    /**
     * Update an array of objects in the database.
     *
     * @param obj the objects to be inserted.
     */
    @Update
    abstract suspend fun update(vararg obj: T)
    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun updateAll(obj: List<T>)

    /**
     * Delete an object from the database
     *
     * @param obj the object to be deleted
     */
    @Delete
    abstract suspend fun delete(obj: T)

    /**
     * Delete an object list from the database
     *
     * @param obj the object to be deleted
     */
    @Delete
    abstract suspend fun deleteAll(obj: List<T>)


    suspend fun safeInsert(obj: T?) {
        if (obj != null)
            insert(obj)
    }

    suspend fun safeDelete(obj: T?) {
        if (obj != null)
            delete(obj)
    }
}

