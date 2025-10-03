package com.yamil.kolibriidioma

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.Spinner
import androidx.core.view.GravityCompat
import androidx.core.view.WindowCompat

class SegundoActivityDrawer : DrawerBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_menu)



        WindowCompat.setDecorFitsSystemWindows(window, true)
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                )

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
        val spinnerIdiomaOrigen = findViewById<Spinner>(R.id.spinnerIdiomaOrigen)

// Opciones fijas
        val opciones = listOf("Nahuatl")

// Adaptador para el Spinner
        val adapter = ArrayAdapter(
            this,
            R.layout.spinner_item, // diseño simple por defecto
            opciones
        )

// Diseño de los items desplegados
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

// Asignar al Spinner
        spinnerIdiomaOrigen.adapter = adapter

// Listener (opcional, si solo quieres que muestre lo elegido, ni lo pongas)
        spinnerIdiomaOrigen.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                // Esto solo cambia lo que se ve, ya no haces nada más
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }


        btnSaludos.setOnClickListener { newActivity("saludos") }
        btnNumeros.setOnClickListener { newActivity("numeros") }
        btnAlfabeto.setOnClickListener { newActivity("alfabeto") }
        btnFamilia.setOnClickListener { newActivity("familia") }
        btnComercio.setOnClickListener { newActivity("comercio") }
        btnAnimales.setOnClickListener { newActivity("animales") }
        btnCostumbres.setOnClickListener { newActivity("colores") }
    }

    override fun currentDestId(): Int = R.id.nav_dicc

    private fun newActivity(name: String) {
        val intent = Intent(this, TercerActivity::class.java)
        intent.putExtra("categoria", name)
        startActivity(intent)
    }
}