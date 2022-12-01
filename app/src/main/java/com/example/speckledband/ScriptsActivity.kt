package com.example.speckledband

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.speckledband.databinding.ActvitySkriptBinding

class ScriptsActivity : AppCompatActivity() {
    private lateinit var binding: ActvitySkriptBinding
    private val adapter = ScriptsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActvitySkriptBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Scripts"
        init()
        addItems()
        ScriptsAdapter.onItemClick = {

        }
    }

    private fun init() = with(binding){
        rcSkript.layoutManager = LinearLayoutManager(this@ScriptsActivity)
        rcSkript.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }
        return true
    }

    fun addItems(){
        adapter.addScript(Skripts(1, "Rainbow"))
        adapter.addScript(Skripts(2, "Glory to Ruslan"))
        adapter.addScript(Skripts(3, "Police"))
        adapter.addScript(Skripts(4, "Random color"))
    }

}