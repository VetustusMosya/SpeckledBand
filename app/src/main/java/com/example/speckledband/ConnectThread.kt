package com.example.speckledband

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import java.io.IOException
import java.util.*

@SuppressLint("MissingPermission")
class ConnectThread(private val device: BluetoothDevice, val listener: RecievTread.Listener) : Thread() {
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
                listener.onReceive("Connecting...")
                mSocket?.connect()
                listener.onReceive( "Connected :)")
                rThread = RecievTread(mSocket!!, listener)
                rThread.start()
            } catch (i: IOException) {
                listener.onReceive( "Not connected. Repeat")
                closeConnection()
            }
    }

    fun closeConnection(){
        try {
            mSocket?.close()
        } catch (i:IOException){
        }
    }
}