package com.yamil.kolibriidioma

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import coil.load

class DetailProductActivity : AppCompatActivity() {

    private var currentIndex = 0  // índice de la imagen actual

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_product)

        // Producto simulado
        val producto = Producto(
            imagenes = mutableListOf(
                "https://picsum.photos/600/400?random=1",
                "https://picsum.photos/600/400?random=2",
                "https://picsum.photos/600/400?random=3"
            ),
            nombre = "Cuitlacoche",
            precio = 85.0,
            descripcion = "Hongo de maíz comestible, considerado un manjar ancestral."
        )

        // Referencias
        val imgProducto = findViewById<ImageView>(R.id.imgProducto)
        val btnPrev = findViewById<Button>(R.id.btnPrev)
        val btnNext = findViewById<Button>(R.id.btnNext)
        val tvNombre = findViewById<TextView>(R.id.tvNombre)
        val tvPrecio = findViewById<TextView>(R.id.tvPrecio)
        val tvDescripcion = findViewById<TextView>(R.id.tvDescripcion)

        // Mostrar primera imagen
        imgProducto.load(producto.imagenes[currentIndex]) { crossfade(true) }

        // Mostrar datos del producto
        tvNombre.text = producto.nombre
        tvPrecio.text = "$${producto.precio}"
        tvDescripcion.text = producto.descripcion

        // Botón anterior
        btnPrev.setOnClickListener {
            if (currentIndex > 0) {
                currentIndex--
            } else {
                currentIndex = producto.imagenes.size - 1 // volver al final
            }
            imgProducto.load(producto.imagenes[currentIndex]) { crossfade(true) }
        }

        // Botón siguiente
        btnNext.setOnClickListener {
            if (currentIndex < producto.imagenes.size - 1) {
                currentIndex++
            } else {
                currentIndex = 0 // volver al inicio
            }
            imgProducto.load(producto.imagenes[currentIndex]) { crossfade(true) }
        }
    }
}
