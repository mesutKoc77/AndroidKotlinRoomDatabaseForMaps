package com.name.a15androidkotlinmaps.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.name.a15androidkotlinmaps.model.Place


@Dao //verilere erisim objemiz yani ilgili database de bulunan verilere erisecegimiz nesne
interface PlaceDao {

    @Query ("SELECT * FROM Place")
    fun getAll(): List<Place>

    @Insert
    fun insert (place: Place)
    @Delete
    fun delete (place: Place)






}