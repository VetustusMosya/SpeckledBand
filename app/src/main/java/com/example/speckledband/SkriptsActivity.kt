package com.example.speckledband

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.speckledband.databinding.ActivityMainBinding
import com.example.speckledband.databinding.ActvitySkriptBinding

class SkriptsActivity : AppCompatActivity() {
    private lateinit var binding: ActvitySkriptBinding
    private val adapter = SkriptsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActvitySkriptBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Scripts"
        init()
        val script = Skripts(12, "First mother Facker")
        adapter.addSkript(script)
    }

    private fun init() = with(binding){
        rcSkript.layoutManager = LinearLayoutManager(this@SkriptsActivity)
        rcSkript.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }
        return true
    }
}