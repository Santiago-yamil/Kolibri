package com.yamil.kolibriidioma

import android.content.Context
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject


class TercerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tercer)

        val categoria = intent.getStringExtra("categoria")



        val iconoSuperiorDerecha = findViewById<ImageButton>(R.id.iconoSuperiorDerecha)
        iconoSuperiorDerecha.setOnClickListener {
            finish() // Cierra la actividad actual
        }
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        val jsonString = leerJSONDesdeAssets(this, "datos.json")
        val jsonObject = JSONObject(jsonString)
        val saludosArray = jsonObject.getJSONArray(categoria)
        val gson = Gson()
        val tipoLista = object : TypeToken<List<ItemCuadrado>>() {}.type
        val lista: List<ItemCuadrado> = gson.fromJson(saludosArray.toString(), tipoLista)

        recyclerView.adapter = CuadradoAdapter(lista)
    }

    fun leerJSONDesdeAssets(context: Context, archivo: String): String {
        return context.assets.open(archivo).bufferedReader().use { it.readText() }
    }
}