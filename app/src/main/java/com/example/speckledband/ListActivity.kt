package com.example.speckledband

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.speckledband.databinding.ActivityMainBinding

class ListActivity : AppCompatActivity(), RcAdapter.Listener {
    private var btAdapter: BluetoothAdapter? = null
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: RcAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

//    инициализация блютуз сервиса
    private fun init(){
        val btManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        btAdapter = btManager.adapter
        adapter = RcAdapter(this)
        binding.deviceView.layoutManager = LinearLayoutManager(this)
        binding.deviceView.adapter = adapter
        getPairedDevices()
    }

    @SuppressLint("MissingPermission")
    private fun getPairedDevices(){
        val pairedDevices: Set<BluetoothDevice>? = btAdapter?.bondedDevices
        val tempList = ArrayList<ListItem>()
        pairedDevices?.forEach{
            tempList.add(ListItem(it.name,it.address))
        }
        adapter.submitList(tempList)
    }

    companion object{
        const val DEVICE_KEY = "device_key"
    }

    override fun onClick(item: ListItem) {
        val i = Intent().apply{
            putExtra(DEVICE_KEY, item)
        }
        setResult(RESULT_OK, i)
        finish()
    }
}