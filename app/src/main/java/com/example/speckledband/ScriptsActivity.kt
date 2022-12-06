package com.example.speckledband

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ScriptsActivity : AppCompatActivity(){
    private lateinit var recyclerView: RecyclerView
    private lateinit var scriptList : ArrayList<Scripts>
    private lateinit var scriptsAdapter: ScriptsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actvity_skript)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Scripts"
        recyclerView = findViewById(R.id.rcScript)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        scriptList = ArrayList()
        addItems()
        scriptsAdapter = ScriptsAdapter(scriptList)
        recyclerView.adapter = scriptsAdapter

        scriptsAdapter.onItemClick = {
            val i = Intent()
            i.putExtra("script", it)
            setResult(RESULT_OK, i)
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }
        return true
    }

    private fun addItems(){
        scriptList.add(Scripts(1, "Rainbow"))
        scriptList.add(Scripts(2, "Glory to Ruslan"))
        scriptList.add(Scripts(3, "Police"))
        scriptList.add(Scripts(4, "Random color"))
        scriptList.add(Scripts(5, "Flashing"))
    }

}