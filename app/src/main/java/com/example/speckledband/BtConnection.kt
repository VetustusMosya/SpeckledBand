package com.example.speckledband

import android.bluetooth.BluetoothAdapter
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
        } catch (i: IOException) {
        }
    }
}