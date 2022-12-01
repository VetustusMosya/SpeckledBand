package com.example.speckledband

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.speckledband.databinding.SkriptItemBinding

class SkriptsAdapter: RecyclerView.Adapter<SkriptsAdapter.SkriptHolder>() {
    val skriptList = ArrayList<Skripts>()

    class SkriptHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = SkriptItemBinding.bind(item)
        fun bind(skript: Skripts) = with(binding){
            skriptName.text = skript.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkriptHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.skript_item, parent, false)
        return SkriptHolder(view)
    }

    override fun onBindViewHolder(holder: SkriptHolder, position: Int) {
        holder.bind(skriptList[position])
    }

    override fun getItemCount(): Int {
        return skriptList.size
    }

    fun addSkript(skript: Skripts){
        skriptList.add(skript)
//        notifyDataSetChanged()
    }
}
