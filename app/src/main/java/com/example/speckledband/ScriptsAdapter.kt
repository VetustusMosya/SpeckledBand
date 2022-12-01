package com.example.speckledband

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.speckledband.databinding.SkriptItemBinding

class ScriptsAdapter: RecyclerView.Adapter<ScriptsAdapter.ScriptHolder>() {
    val scriptList = ArrayList<Skripts>()
    lateinit var btConnection: BtConnection

    var onItemClick : ((Skripts) -> Unit)? = null

    class ScriptHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = SkriptItemBinding.bind(item)
        fun bind(script: Skripts) = with(binding){
            skriptName.text = script.name
            itemView.setOnClickListener {

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScriptHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.skript_item, parent, false)
        return ScriptHolder(view)
    }

    override fun onBindViewHolder(holder: ScriptHolder, position: Int) {
        holder.bind(scriptList[position])
        val script = scriptList[position]
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(script)
//            btConnection.sendMassage("s")
        }
    }

    override fun getItemCount(): Int {
        return scriptList.size
    }

    fun addScript(script: Skripts){
        scriptList.add(script)
//        notifyDataSetChanged()
    }


}
