package com.yamil.kolibriidioma

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SegundoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_menu)

        val btnSalir = findViewById<ImageButton>(R.id.btnSalir) // ❌ Error si no está en el XML
        val btnSaludos = findViewById<Button>(R.id.btnSaludos)
        val btnNumeros = findViewById<Button>(R.id.btnNumeros)
        val btnAlfabeto = findViewById<Button>(R.id.btnAlfabeto)
        val btnFamilia = findViewById<Button>(R.id.btnFamilia)
        val btnComercio = findViewById<Button>(R.id.btnComercio)
        val btnAnimales = findViewById<Button>(R.id.btnAnimales)
        val btnCostumbres = findViewById<Button>(R.id.btnCostumbres)

        btnSalir.setOnClickListener {
           finish() // Cierra la actividad actual
        }

        btnSaludos.setOnClickListener {
            newActivity("saludos")
        }

        btnNumeros.setOnClickListener {
            newActivity("numeros")
        }

        btnAlfabeto.setOnClickListener {
            newActivity("alfabeto")
        }

        btnFamilia.setOnClickListener {
            newActivity("familia")
        }

        btnComercio.setOnClickListener {
            newActivity("comercio")
        }

        btnAnimales.setOnClickListener {
            newActivity("animales")
        }

        btnCostumbres.setOnClickListener {
            newActivity("colores")
        }



    }
    private fun newActivity(name : String){
        val intent = Intent(this, TercerActivity::class.java)
        intent.putExtra("categoria",name)
        startActivity(intent)
    }

}