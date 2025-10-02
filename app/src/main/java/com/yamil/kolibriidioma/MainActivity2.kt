package com.yamil.kolibriidioma

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.Normalizer

class MainActivity2 : BaseActivity() {

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

        val iconoSuperiorDerecha = findViewById<ImageButton>(R.id.iconoSuperiorDerecha)
        iconoSuperiorDerecha.setOnClickListener {
            val intent = Intent(this, SegundoActivity::class.java)
            startActivity(intent)
        }

        // Inicializar vistas
        textViewTraduccion = findViewById(R.id.textoTraduccion)
        val botonHablar = findViewById<Button>(R.id.botonGrabar)
        val spinnerIdiomaOrigen = findViewById<Spinner>(R.id.spinnerIdiomaOrigen)
        val spinnerIdiomaDestino = findViewById<Spinner>(R.id.spinnerIdiomaDestino)

        // Configurar spinners de idiomas
        val idiomas = listOf("Español", "Nahuatl", "Inglés")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, idiomas)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerIdiomaOrigen.adapter = adapter
        spinnerIdiomaDestino.adapter = adapter

        spinnerIdiomaOrigen.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                idiomaOrigenSeleccionado = parent.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        spinnerIdiomaDestino.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                idiomaDestinoSeleccionado = parent.getItemAtPosition(position).toString()
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

        // Listener botón
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
            Frase(espanol = "Buenos tardes", nahuatl = "Yateotlak", ingles = "Good afternoon"),
            Frase(espanol = "Buenos noches", nahuatl = "Kuali youaltin", ingles = "Good night"),
            Frase(espanol = "Cómo estás", nahuatl = "Tlen tichika?", ingles = "How are you?"),
            Frase(espanol = "Gracias", nahuatl = "Tlazohcamati", ingles = "Thank you"),
            Frase(espanol = "Por favor", nahuatl = "Nimitstlatlaz", ingles = "Please"),
            Frase(espanol = "Hasta mañana", nahuatl = "Mostla", ingles = "See you tomorrow"),
            Frase(espanol = "Adios", nahuatl = "Timoittazqueh", ingles = "Goodbye"),
            Frase(espanol = "Mi nombre es", nahuatl = "Notoka"  , ingles = "My name is"),
            Frase(espanol = "Perdón", nahuatl = "Nimitstlazotla", ingles = "Sorry"),
            Frase(espanol = "Quiero hacer negocios contigo", nahuatl = "Nimitznequi ticatepan tlamachiliztli", ingles = "I want to do business with you"),
            Frase(espanol = "Necesito hablar con el jefe", nahuatl = "Nimitznequi tlahtoa iuan tlatoani", ingles = "I need to speak with the boss"),
            Frase(espanol = "Cuál es el precio", nahuatl = "Tlen yehuatl tlamaniliztli?", ingles = "What is the price?"),
            Frase(espanol = "Estoy interesado en este producto", nahuatl = "Nimitznequi inin tlamantli", ingles = "I am interested in this product"),
            Frase(espanol = "Necesitaba una compra", nahuatl = "Tlakoualistli nechpiyaya", ingles = "I needed to make a purchase"),
            Frase(espanol = "Queremos colaborar juntos", nahuatl = "Ticchihua tlachihualiztli huehuepan", ingles = "We want to collaborate together"),
            Frase(espanol = "Dónde firmamos", nahuatl = "Campa tiquintzani", ingles = "Where do we sign?"),
            Frase(espanol = "Necesitamos una reunión", nahuatl = "Nimitznequi tlamachiliztli huan tlachinolli", ingles = "We need a meeting"),
            Frase(espanol = "Sali a hacer una compra", nahuatl = "Ekauili niyawi tlakoualistli", ingles = "I went out to make a purchase"),
            Frase(espanol = "El trabajador del mercado vende", nahuatl = "Tlasemananki tiankistli namaka", ingles = "The market worker sells")
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
            "Nahuatl" -> "es-MX" // No hay soporte oficial para náhuatl en reconocimiento de voz, usar español
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
            // Buscamos todas las frases (podrías optimizar en DAO para traer solo las del idioma origen)
            val todasFrases = fraseDao.obtenerTodas() // Debes crear este método para traer todo

            // Buscamos la frase que coincida normalizando el texto
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