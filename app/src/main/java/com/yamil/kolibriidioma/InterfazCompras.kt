package com.yamil.kolibriidioma

import android.content.Context
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject


class InterfazCompras:BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compras)

        val categoria = intent.getStringExtra("categoria")

    val iconoSuperiorDerecha = findViewById<ImageButton>(R.id.iconoSuperiorDerecha)
    iconoSuperiorDerecha.setOnClickListener {
        finish() // Cierra la actividad actual
    }



    val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
    recyclerView.layoutManager = GridLayoutManager(this, 2)


    val tipoLista = object : TypeToken<List<ItemCuadrado>>() {}.type
    //val lista: List<ItemCuadrado> = gson.fromJson(saludosArray.toString(), tipoLista)

   // recyclerView.adapter = CuadradoAdapter(lista)
}






}