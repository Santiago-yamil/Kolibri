package com.yamil.kolibriidioma

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import android.content.Context
import java.util.Locale

import android.content.Intent


class Idioma : BaseActivity() {

    private var idiomaSeleccionado: String = "es" // default

    override fun currentDestId(): Int = R.id.nav_idioma

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.idioma)

        // Mapa display ↔ code
        val opciones = listOf(
            "Español Latinoamérica" to "es",
            "English" to "en",
            "Português" to "pt",
            "Nahuatl" to "nah"
        )
        val displayToCode = opciones.toMap()
        val codeToDisplay = opciones.associate { it.second to it.first }

        val autoPais = findViewById<AutoCompleteTextView>(R.id.autoPais)
        val btnAceptar = findViewById<Button>(R.id.btnAceptar)

        // Set actual guardado
        val langGuardado = LocaleManager.currentLanguage(this)
        idiomaSeleccionado = langGuardado
        autoPais.setText(codeToDisplay[langGuardado] ?: "Español Latinoamérica", false)

        // Adapter
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            opciones.map { it.first }
        )
        autoPais.setAdapter(adapter)

        // Selección
        autoPais.setOnItemClickListener { parent, _, position, _ ->
            val seleccionado = parent.getItemAtPosition(position).toString()
            idiomaSeleccionado = displayToCode[seleccionado] ?: "es"
            Toast.makeText(this, "Elegiste: $seleccionado", Toast.LENGTH_SHORT).show()
        }

        // Aplicar cambio
        btnAceptar.setOnClickListener {
            LocaleManager.persistLanguage(applicationContext, idiomaSeleccionado)

            // Trae el nuevo locale en BaseActivity.attachBaseContext
            // y reinicia el flujo para refrescar todo el árbol de vistas
            val intent = Intent(this, MainActivityDrawer::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or
                        Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
            startActivity(intent)
            overridePendingTransition(0, 0)
            finish()
        }
    }
}

