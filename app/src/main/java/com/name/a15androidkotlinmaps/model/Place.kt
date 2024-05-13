package com.name.a15androidkotlinmaps.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

//we create our Object. yani biz recyler view de ismini gosterecegimiz ve tiklandiginda kendisinsden ayrintili bilgi alacagimiz Yerlerimizin ki buna biz nesne
//diyoruz, nelere hangi ozelliklere saahip olmasini ve hangi degiskenleri barindrimsini istedigimizi burda soylicegz. Yerimizin ismi, enlem, boylami olsun diyecegiz.
@Entity // (tableName = "PlaceDataBase") database adi.
class Place (

    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "latitude")
    var latitude: Double,
    @ColumnInfo(name = "longitude")
    var longitude: Double

    ) :Serializable

{
    @PrimaryKey(autoGenerate = true)
    var id=0

}