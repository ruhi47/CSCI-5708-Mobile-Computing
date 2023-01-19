package com.example.pilloclock.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pilloclock.constants.DBConstants
import com.example.pilloclock.data.dao.NotificationDao
import com.example.pilloclock.data.dao.PillDao
import com.example.pilloclock.data.dao.UserDao
import com.example.pilloclock.data.entity.Notification
import com.example.pilloclock.data.entity.Pill
import com.example.pilloclock.data.entity.User

@Database(entities = [User::class, Notification::class, Pill::class], version = 7, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun notificationDao(): NotificationDao
    abstract fun pillDao(): PillDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            var tempInstance = INSTANCE;
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DBConstants.DB_NAME
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
                INSTANCE = instance
                return instance
            }
        }
    }
}