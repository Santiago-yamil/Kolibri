package com.yamil.kolibriidioma

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import android.content.Context
import java.util.Locale

class Idioma : ComponentActivity() {

    private var idiomaSeleccionado: String = "es" // por defecto español

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.idioma)

        val paises = listOf(
            "Español Latinoamerica",
            "Inglish",
            "Portugues",
            "Popoluca",
            "Huasteco",
            "Yucateco",
            "Peruano",
            "Mixteco",
            "Nahuatl"
        )

        val autoPais = findViewById<AutoCompleteTextView>(R.id.autoPais)
        val btnAceptar = findViewById<Button>(R.id.btnAceptar)

        // Adaptador del desplegable
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, paises)
        autoPais.setAdapter(adapter)

        // Cuando se selecciona un item del desplegable
        autoPais.setOnItemClickListener { parent, _, position, _ ->
            val seleccionado = parent.getItemAtPosition(position).toString()
            Toast.makeText(this, "Elegiste: $seleccionado", Toast.LENGTH_SHORT).show()

            // Mapear la opción a un código de idioma válido (ISO)
            idiomaSeleccionado = when (seleccionado) {
                "Español Latinoamerica" -> "es"
                "Inglish" -> "en"
                "Portugues" -> "pt"
                "Nahuatl" -> "nah"
                else -> "es" // fallback
            }
        }

        // Botón para aplicar el cambio
        btnAceptar.setOnClickListener {
            val nuevoContexto = cambiarIdioma(this, idiomaSeleccionado)
            recreate() // recarga la Activity con el nuevo idioma
        }
    }

    fun cambiarIdioma(context: Context, languageCode: String): Context {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = context.resources.configuration
        config.setLocale(locale)

        return context.createConfigurationContext(config)
    }
}
