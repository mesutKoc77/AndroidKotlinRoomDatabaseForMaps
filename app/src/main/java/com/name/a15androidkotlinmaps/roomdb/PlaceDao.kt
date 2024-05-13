package com.name.a15androidkotlinmaps.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.name.a15androidkotlinmaps.model.Place
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable


@Dao //verilere erisim objemiz yani ilgili database de bulunan verilere erisecegimiz nesne
interface PlaceDao {

    @Query ("SELECT * FROM Place")
    fun getAll(): Flowable <List<Place>>
    @Insert
    fun insert (place: Place)  :Completable
    @Delete
    fun delete (place: Place) : Completable

    /*
    @Query ("SELECT * FROM Place WHERE id= :id")
    fun getAll(id: String): List<Place>
     */






}