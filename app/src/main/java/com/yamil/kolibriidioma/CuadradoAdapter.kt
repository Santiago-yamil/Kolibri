package com.yamil.kolibriidioma

import android.content.Context
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView



class CuadradoAdapter(private val lista: List<ItemCuadrado>) :
    RecyclerView.Adapter<CuadradoAdapter.CuadradoViewHolder>() {

    class CuadradoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagen: ImageView = itemView.findViewById(R.id.imageView)
        val texto: TextView = itemView.findViewById(R.id.textView)
        val traduccion: TextView = itemView.findViewById(R.id.textEs)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CuadradoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cuadrado, parent, false)
        return CuadradoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CuadradoViewHolder, position: Int) {
        val item = lista[position]
        holder.texto.text = item.es
        holder.traduccion.text = item.traduccion


        val context = holder.itemView.context
        val resId = context.resources.getIdentifier(item.imagen, "drawable", context.packageName)
        holder.imagen.setImageResource(resId)

        holder.itemView.setOnClickListener {
            mostrarDialogo(context, item)
        }
    }

    private fun mostrarDialogo(context: Context, item: ItemCuadrado) {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_item, null)

        val imageView = view.findViewById<ImageView>(R.id.dialogImage)
        val textoEs = view.findViewById<TextView>(R.id.dialogEs)
        val textoTraduccion = view.findViewById<TextView>(R.id.dialogTraduccion)
        val descripcion = view.findViewById<TextView>(R.id.dialogDescripcion)
        val icono = view.findViewById<ImageView>(R.id.dialogIcon)

        // Asignar contenido
        val resId = context.resources.getIdentifier(item.imagen, "drawable", context.packageName)
        imageView.setImageResource(resId)
        textoEs.text = item.es
        textoTraduccion.text = item.traduccion
        descripcion.text = item.descripcion
        icono.setImageResource(R.drawable.herling)

        icono.setOnClickListener {
            reproducirSonido(it.context, item.audio)
        }



        AlertDialog.Builder(context)
            .setView(view)
            .setCancelable(true)
            .show()
    }

    fun reproducirSonido(context: Context, audioName: String) {
        val resId = context.resources.getIdentifier(audioName, "raw", context.packageName)

        if (resId != 0) {
            val mediaPlayer = MediaPlayer.create(context, resId)
            mediaPlayer?.start()
            mediaPlayer.setOnCompletionListener {
                it.release()
            }
        } else {
            android.widget.Toast.makeText(context, "Audio no encontrado: $audioName", android.widget.Toast.LENGTH_SHORT).show()
        }
    }



    override fun getItemCount(): Int = lista.size
}
