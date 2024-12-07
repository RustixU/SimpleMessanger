package rut.miit.simplemessanger.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import rut.miit.simplemessanger.converters.TypeConverter
import rut.miit.simplemessanger.dao.CharacterDao
import rut.miit.simplemessanger.entity.Character

@Database(entities = [Character::class], version = 2)
@TypeConverters(TypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun characterDao(): CharacterDao

    companion object DatabaseProvider {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "characters_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}