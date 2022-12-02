package com.example.speckledband

import android.bluetooth.BluetoothAdapter
import android.util.Log
import java.io.IOException

class BtConnection(private val adapter: BluetoothAdapter, val listener: RecievTread.Listener) {
    lateinit var cThread: ConnectThread

    fun connect(mac: String ){
        if (adapter.isEnabled && mac.isNotEmpty()) {
            val device = adapter.getRemoteDevice((mac))
            device.let {
                cThread = ConnectThread(it, listener)
                cThread.start()
            }
        }
    }

    fun sendMassage(message: String){
        try {
            cThread.rThread.sendMassage(message.toByteArray())
            Log.d("MyLog", "Scented")
        } catch (i: IOException) {
            Log.d("MyLog", "Err in sendMassage")
        }
    }
}