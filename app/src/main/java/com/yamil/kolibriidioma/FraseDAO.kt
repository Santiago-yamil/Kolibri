package com.yamil.kolibriidioma

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface FraseDao {

    @Query("SELECT * FROM frases")
    suspend fun obtenerTodas(): List<Frase>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(frase: Frase)

    // Buscar frase en español y devolver la frase completa (para traducir a otro idioma)
    @Query("SELECT * FROM frases WHERE espanol = :texto LIMIT 1")
    suspend fun buscarPorEspanol(texto: String): Frase?

    // Buscar frase en náhuatl
    @Query("SELECT * FROM frases WHERE nahuatl = :texto LIMIT 1")
    suspend fun buscarPorNahuatl(texto: String): Frase?

    // Buscar frase en inglés
    @Query("SELECT * FROM frases WHERE ingles = :texto LIMIT 1")
    suspend fun buscarPorIngles(texto: String): Frase?
}

