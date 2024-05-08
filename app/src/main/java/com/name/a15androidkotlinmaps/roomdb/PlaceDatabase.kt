import androidx.room.Database
import androidx.room.RoomDatabase
import com.name.a15androidkotlinmaps.model.Place
import com.name.a15androidkotlinmaps.roomdb.PlaceDao


@Database(entities = [Place::class], version = 1)
abstract class PlaceDatabase : RoomDatabase() {
    abstract fun placeDao(): PlaceDao
}