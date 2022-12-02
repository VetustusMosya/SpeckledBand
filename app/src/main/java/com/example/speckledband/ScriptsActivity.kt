package com.example.speckledband

import android.bluetooth.BluetoothManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ScriptsActivity : AppCompatActivity(), RecievTread.Listener{
    lateinit var btConnection: BtConnection
    private lateinit var recyclerView: RecyclerView
    private lateinit var scriptList : ArrayList<Scripts>
    private lateinit var scriptsAdapter: ScriptsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actvity_skript)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Scripts"
        init()

        recyclerView = findViewById(R.id.rcScript)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        scriptList = ArrayList()
        addItems()
        scriptsAdapter = ScriptsAdapter(scriptList)
        recyclerView.adapter = scriptsAdapter

        scriptsAdapter.onItemClick = {

        }
    }

    private fun init(){
        val btManager = getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
        val btAdapter = btManager.adapter
        btConnection = BtConnection(btAdapter, this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }
        return true
    }

    fun addItems(){
        scriptList.add(Scripts(1, "Rainbow"))
        scriptList.add(Scripts(2, "Glory to Ruslan"))
        scriptList.add(Scripts(3, "Police"))
        scriptList.add(Scripts(4, "Random color"))
    }

    override fun onReceive(message: String) {
        TODO("Not yet implemented")
    }

    override fun getState(status: String) {
        TODO("Not yet implemented")
    }

}