package com.yamil.kolibriidioma

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter



class AccentInsensitiveAdapter(
    context: Context,
    layout: Int,
    private val items: List<String>
) : ArrayAdapter<String>(context, layout, ArrayList(items)) {

    private val all = ArrayList(items)

    override fun getFilter(): Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val res = FilterResults()
            if (constraint.isNullOrBlank()) {
                res.values = all
                res.count = all.size
                return res
            }
            val q = normalize(constraint.toString())
            val filtered = all.filter { normalize(it).contains(q) }
            res.values = filtered
            res.count = filtered.size
            return res
        }
        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            clear()
            addAll(results?.values as List<String>)
            notifyDataSetChanged()
        }
        fun normalize(s: String) =
            java.text.Normalizer.normalize(s.lowercase(), java.text.Normalizer.Form.NFD)
                .replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")
    }
}

