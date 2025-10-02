package com.yamil.kolibriidioma.recursos_cuatro

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.yamil.kolibriidioma.Producto
import com.yamil.kolibriidioma.R

class ProductoAdapter(private val lista: List<Producto>) :
    RecyclerView.Adapter<ProductoAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombre: TextView = itemView.findViewById(R.id.textView)
        val imagen: ImageView = itemView.findViewById(R.id.imageView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            // ⬇️ Inflamos EL LAYOUT DEL ÍTEM, no el de la activity
            .inflate(R.layout.activity_market, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto = lista[position]
        holder.nombre.text = producto.nombre


        val url = producto.imagenes.firstOrNull()
        holder.imagen.load(url) {
            placeholder(R.drawable.book)  // usa un drawable que SÍ exista
            error(R.drawable.micro)       // idem
        }
    }

    override fun getItemCount(): Int = lista.size
}



