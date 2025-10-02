package com.yamil.kolibriidioma

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity

class SelectionLanguageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection_language)

        val radioGroup: RadioGroup = findViewById(R.id.radioGroupOptions)


        val sharedPref = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val lastOption = sharedPref.getString("selected_option", null)

        if (lastOption != null) {

            when (lastOption) {
                "Opción 1" -> radioGroup.check(R.id.opcionNahuatl)
                "Opción 2" -> radioGroup.check(R.id.opcionEspanol)
                "Opción 3" -> radioGroup.check(R.id.opcionIngles)
            }
        }

        // Detectar cuando cambia la selección
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val selectedRadioButton = findViewById<RadioButton>(checkedId)
            val selectedText = selectedRadioButton.text.toString()

            // Guardar ajuste
            val editor = sharedPref.edit()
            editor.putString("selected_option", selectedText)
            editor.apply()

            // Ir a la pantalla principal
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("selected_option", selectedText)
            startActivity(intent)

            //finish() // Opcional, para que no regrese a esta pantalla
        }
    }
}
