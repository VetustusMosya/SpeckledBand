package com.example.speckledband

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class ScriptsAdapter(private val scriptList:ArrayList<Scripts>):
    RecyclerView.Adapter<ScriptsAdapter.ScriptHolder>() {

    var onItemClick : ((Scripts) -> Unit)? = null

    class ScriptHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.skriptName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScriptHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.skript_item, parent, false)
        return ScriptHolder(view)
    }

    override fun onBindViewHolder(holder: ScriptHolder, position: Int) {
        val script = scriptList[position]
        holder.textView.text = script.name
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(script)
        }
    }

    override fun getItemCount(): Int {
        return scriptList.size
    }
}
