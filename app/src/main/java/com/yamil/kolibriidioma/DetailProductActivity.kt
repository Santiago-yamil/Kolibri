package com.yamil.kolibriidioma

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import coil.load
import coil.transform.RoundedCornersTransformation

class DetailProductActivity : AppCompatActivity() {

    private var currentIndex = 0  // índice de la imagen actual
    private lateinit var producto: Producto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_product)

        WindowCompat.setDecorFitsSystemWindows(window, true)
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                )

        // 1) Recibir el producto desde el Intent
        producto = intent.getParcelableExtra("producto")
            ?: error("No se recibió el producto en el Intent")

        // 2) Referencias
        val imgProducto = findViewById<ImageView>(R.id.imgProducto)
        val btnPrev = findViewById<Button>(R.id.btnPrev)
        val btnNext = findViewById<Button>(R.id.btnNext)
        val tvNombre = findViewById<TextView>(R.id.tvNombre)
        val tvPrecio = findViewById<TextView>(R.id.tvPrecio)
        val tvDescripcion = findViewById<TextView>(R.id.tvDescripcion)

        // 3) Pinta datos
        tvNombre.text = producto.nombre
        tvPrecio.text = "$${producto.precio}"
        tvDescripcion.text = producto.descripcion

        fun loadAt(index: Int) {
            val url = producto.imagenes.getOrNull(index)
            imgProducto.load(url) {
                crossfade(true)
                placeholder(R.drawable.book)
                error(R.drawable.book)
                transformations(RoundedCornersTransformation(8f))
            }
        }

        loadAt(currentIndex)

        // 4) Navegación entre imágenes
        btnPrev.setOnClickListener {
            currentIndex = if (currentIndex > 0) currentIndex - 1 else producto.imagenes.lastIndex
            loadAt(currentIndex)
        }
        btnNext.setOnClickListener {
            currentIndex = if (currentIndex < producto.imagenes.lastIndex) currentIndex + 1 else 0
            loadAt(currentIndex)
        }
    }
}

