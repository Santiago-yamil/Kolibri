package com.yamil.kolibriidioma

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.Normalizer

class MainActivity : BaseActivity() {

    private lateinit var fraseDao: FraseDao
    private val SPEECH_REQUEST_CODE = 100
    private lateinit var textViewTraduccion: TextView

    // Variables para idioma seleccionado
    private var idiomaOrigenSeleccionado = "Español"
    private var idiomaDestinoSeleccionado = "Nahuatl"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actymain)

        // 🔶 Conectar el botón del header naranja al Drawer
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val btnMenu = findViewById<ImageButton>(R.id.iconoSuperiorDerecha)

        btnMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        // Inicializar vistas
        textViewTraduccion = findViewById(R.id.textoTraduccion)
        val botonHablar = findViewById<Button>(R.id.botonGrabar)
        val spinnerIdiomaOrigen = findViewById<Spinner>(R.id.spinnerIdiomaOrigen)
        val spinnerIdiomaDestino = findViewById<Spinner>(R.id.spinnerIdiomaDestino)
        val input = findViewById<EditText>(R.id.input)
        val boton = findViewById<Button>(R.id.boton)

        //traducir con el boton
        boton.setOnClickListener {
            val textoIngresado = input.text.toString().trim()
            buscarTraduccion(textoIngresado)
        }

        val idiomas = listOf("Español", "Nahuatl", "Inglés")

        // Adapter para origen (con todos)
        val adapterOrigen = ArrayAdapter(this, android.R.layout.simple_spinner_item, idiomas)
        adapterOrigen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerIdiomaOrigen.adapter = adapterOrigen

        // Adapter para destino (empieza igual que origen)
        var adapterDestino = ArrayAdapter(this, android.R.layout.simple_spinner_item, idiomas)
        adapterDestino.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerIdiomaDestino.adapter = adapterDestino

        // Listener del spinner origen
        spinnerIdiomaOrigen.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                idiomaOrigenSeleccionado = parent.getItemAtPosition(position).toString()
                (view as? TextView)?.setTextColor(ContextCompat.getColor(this@MainActivity, android.R.color.black))

                // filtrar lista para destino
                val listaFiltrada = idiomas.filter { it != idiomaOrigenSeleccionado }
                adapterDestino = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, listaFiltrada)
                adapterDestino.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerIdiomaDestino.adapter = adapterDestino
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        spinnerIdiomaDestino.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                idiomaDestinoSeleccionado = parent.getItemAtPosition(position).toString()
                (view as? TextView)?.setTextColor(ContextCompat.getColor(this@MainActivity, android.R.color.black))
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // Inicializar base de datos y DAO
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "traductor-db"
        ).build()
        fraseDao = db.fraseDao()

        // Cargar frases si no hay
        CoroutineScope(Dispatchers.IO).launch {
            val fraseEjemplo = fraseDao.buscarPorEspanol("Gracias") // usa método con campo espanol para probar
            if (fraseEjemplo == null) {
                cargarFrasesIniciales(fraseDao)
            }
        }

        // Listener botón hablar
        botonHablar.setOnClickListener {
            iniciarReconocimientoVoz()
        }
    }

    fun String.normalizarTexto(): String {
        val temp = Normalizer.normalize(this, Normalizer.Form.NFD)
        return Regex("\\p{InCombiningDiacriticalMarks}+").replace(temp, "").lowercase()
    }

    private fun cargarFrasesIniciales(fraseDao: FraseDao) {
        val frases = listOf(
            Frase(espanol = "Buenos días", nahuatl = "Yatlahkah", ingles = "Good morning"),
            Frase(espanol = "Buenas tardes", nahuatl = "Yateotlak", ingles = "Good afternoon"),
            Frase(espanol = "Buenas noches", nahuatl = "Kuali youaltin", ingles = "Good night"),
            Frase(espanol = "Cómo estás", nahuatl = "Tlen tichika?", ingles = "How are you?"),
            Frase(espanol = "Gracias", nahuatl = "Tlazohcamati", ingles = "Thank you"),
            // ... resto de frases
        )

        CoroutineScope(Dispatchers.IO).launch {
            frases.forEach { fraseDao.insertar(it) }
        }
    }

    private fun iniciarReconocimientoVoz() {
        val codigoIdioma = mapearIdiomaCodigo(idiomaOrigenSeleccionado)
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, codigoIdioma)
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Te escucho...")
        }
        try {
            startActivityForResult(intent, SPEECH_REQUEST_CODE)
        } catch (e: Exception) {
            Toast.makeText(this, "No se pudo iniciar el reconocimiento de voz", Toast.LENGTH_SHORT).show()
        }
    }

    private fun mapearIdiomaCodigo(idioma: String): String {
        return when (idioma) {
            "Español" -> "es-MX"
            "Nahuatl" -> "es-MX" // no hay soporte oficial
            "Inglés" -> "en-US"
            else -> "es-MX"
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            val resultados = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val texto = resultados?.get(0) ?: ""
            buscarTraduccion(texto)
        }
    }

    private fun buscarTraduccion(texto: String) {
        val textoNormalizado = texto.normalizarTexto()

        CoroutineScope(Dispatchers.IO).launch {
            val todasFrases = fraseDao.obtenerTodas()

            val frase = todasFrases.find { frase ->
                val textoComparar = when (idiomaOrigenSeleccionado) {
                    "Español" -> frase.espanol
                    "Nahuatl" -> frase.nahuatl
                    "Inglés" -> frase.ingles
                    else -> ""
                }.normalizarTexto()

                textoComparar == textoNormalizado
            }

            val resultado = if (frase != null) {
                when (idiomaDestinoSeleccionado) {
                    "Español" -> frase.espanol
                    "Nahuatl" -> frase.nahuatl
                    "Inglés" -> frase.ingles
                    else -> "Idioma destino no válido"
                }
            } else {
                "Frase no encontrada"
            }

            withContext(Dispatchers.Main) {
                mostrarTraduccion(resultado)
            }
        }
    }

    private fun mostrarTraduccion(texto: String) {
        textViewTraduccion.text = texto
    }
}
