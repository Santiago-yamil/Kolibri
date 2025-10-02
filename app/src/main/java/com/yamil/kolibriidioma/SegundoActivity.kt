package com.yamil.kolibriidioma

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.core.view.GravityCompat

class SegundoActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_menu)

        // 🔶 Conectar el botón del header naranja al Drawer
        val btnMenu = findViewById<ImageButton>(R.id.iconoSuperiorDerecha)
        btnMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        // Botones del menú
        val btnSaludos = findViewById<Button>(R.id.btnSaludos)
        val btnNumeros = findViewById<Button>(R.id.btnNumeros)
        val btnAlfabeto = findViewById<Button>(R.id.btnAlfabeto)
        val btnFamilia = findViewById<Button>(R.id.btnFamilia)
        val btnComercio = findViewById<Button>(R.id.btnComercio)
        val btnAnimales = findViewById<Button>(R.id.btnAnimales)
        val btnCostumbres = findViewById<Button>(R.id.btnCostumbres)

        btnSaludos.setOnClickListener { newActivity("saludos") }
        btnNumeros.setOnClickListener { newActivity("numeros") }
        btnAlfabeto.setOnClickListener { newActivity("alfabeto") }
        btnFamilia.setOnClickListener { newActivity("familia") }
        btnComercio.setOnClickListener { newActivity("comercio") }
        btnAnimales.setOnClickListener { newActivity("animales") }
        btnCostumbres.setOnClickListener { newActivity("colores") }
    }

    private fun newActivity(name: String) {
        val intent = Intent(this, TercerActivity::class.java)
        intent.putExtra("categoria", name)
        startActivity(intent)
    }
}