package com.yamil.kolibriidioma

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Frase::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun fraseDao(): FraseDao
}
