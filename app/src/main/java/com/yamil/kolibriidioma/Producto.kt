package com.yamil.kolibriidioma

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Producto(
    val imagenes: MutableList<String>,
    val nombre: String,
    val precio: Double,
    val descripcion: String
) : Parcelable

