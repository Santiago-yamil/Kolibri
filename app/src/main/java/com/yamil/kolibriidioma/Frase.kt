package com.yamil.kolibriidioma

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "frases")
data class Frase(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val espanol: String,
    val nahuatl: String,
    val ingles: String
)


