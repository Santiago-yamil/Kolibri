package com.yamil.kolibriidioma.recursos_cuatro

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.core.view.GravityCompat
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yamil.kolibriidioma.DrawerBaseActivity
import com.yamil.kolibriidioma.Producto
import com.yamil.kolibriidioma.R

class ComprasActivityDrawer : DrawerBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compras)

        WindowCompat.setDecorFitsSystemWindows(window, true)
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                )

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2) // cuadrícula de 2 columnas
        recyclerView.adapter = ProductoAdapter(productos)

        // 🔶 Conectar el botón del header naranja al Drawer
        val btnMenu = findViewById<ImageButton>(R.id.iconoSuperiorDerecha)
        btnMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }
    override fun currentDestId(): Int = R.id.nav_tianguis

    companion object{
        val productos: MutableList<Producto> = mutableListOf(
            Producto(
                imagenes = mutableListOf("https://www.cmabastos.es/images/blog/Cuitlacoche.jpg", "https://www.mercadoflotante.com/blog/wp-content/uploads/2021/09/Cuitlacoche.jpeg"),
                nombre = "Cuitlacoche",
                precio = 85.0,
                descripcion = "Hongo de maíz comestible, considerado un manjar ancestral.",

                ),
            Producto(
                imagenes = mutableListOf("https://imag.bonviveur.com/semillas-de-amaranto-en-un-cuenco.jpg", "https://content.cuerpomente.com/medio/2022/02/16/como-cocinar-amaranto_e6089591_1200x1200.jpg"),
                nombre = "Huautli (Amaranto)",
                precio = 60.0,
                descripcion = "Semilla sagrada en la época mexica, rica en proteínas."
            ),
            Producto(
                imagenes = mutableListOf("https://www.mexicoenmicocina.com/wp-content/uploads/2019/06/Receta-de-tepache-de-pina-2.jpg", "https://www.hazteveg.com/img/recipes/full/202204/R14-90066.jpg"),
                nombre = "Tepache",
                precio = 45.0,
                descripcion = "Bebida fermentada de piña y piloncillo, refrescante y tradicional."
            ),
            Producto(
                imagenes = mutableListOf("https://upload.wikimedia.org/wikipedia/commons/c/c7/Chilate.jpg", "https://i.blogs.es/49ff12/1366_2000-1/840_560.jpg"),
                nombre = "Chilate",
                precio = 50.0,
                descripcion = "Bebida a base de cacao, arroz y canela, muy energética."
            ),
            Producto(
                imagenes = mutableListOf("https://www.xocolatlmexica.com/img/blog/xocolatl-en-agua.jpg", "https://mapasgourmet.com/wp-content/uploads/2015/10/img_3780.jpg"),
                nombre = "Xocolatl",
                precio = 120.0,
                descripcion = "La bebida original de cacao espumoso, consumida por los mexicas."
            )
        )
    }
}
