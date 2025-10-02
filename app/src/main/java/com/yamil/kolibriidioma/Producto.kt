package com.yamil.kolibriidioma

data class Producto(
    val imagenes: MutableList<String>,    // ruta o URL de la imagen
    val nombre: String,
    val precio: Double,
    val descripcion: String
)
