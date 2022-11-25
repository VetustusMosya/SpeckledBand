package com.example.speckledband

import android.Manifest
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import java.io.IOException
import java.util.*

class ConnectThread(private val device: BluetoothDevice) : Thread() {
    val uuid = "00001101-0000-1000-8000-00805F9B34FB"
    var mSocket: BluetoothSocket? = null
    lateinit var rThread: RecievTread

    init {
        try {
            mSocket = device.createRfcommSocketToServiceRecord(UUID.fromString(uuid))
        } catch (i:IOException){
        }
    }

    override fun run() {
        try {
            Log.d("MyLog", "Connecting...")
            mSocket?.connect()
            Log.d("MyLog", "Connected")
            rThread = RecievTread(mSocket!!)
            rThread.start()
        } catch (i:IOException){
            Log.d("MyLog", "Not connected")
            closeConnection()
        }
    }

    fun closeConnection(){
        try {
            mSocket?.close()
            Log.d("MyLog", "closeConnected")
        } catch (i:IOException){
        }
    }
}