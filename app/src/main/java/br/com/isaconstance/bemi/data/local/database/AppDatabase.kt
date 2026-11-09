package br.com.isaconstance.bemi.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.isaconstance.bemi.data.local.dao.ContatoDao
import br.com.isaconstance.bemi.data.local.entity.ContatoEntity

@Database(
    entities = [ContatoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun contatoDao(): ContatoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "bemi.db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}