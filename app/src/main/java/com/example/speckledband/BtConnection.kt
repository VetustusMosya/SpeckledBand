package com.example.speckledband

import android.bluetooth.BluetoothAdapter
import android.webkit.ConsoleMessage

class BtConnection(private val adapter: BluetoothAdapter) {
    lateinit var cThread: ConnectThread

    fun connect(mac: String){
        if (adapter.isEnabled && mac.isNotEmpty()) {
            val device = adapter.getRemoteDevice((mac))
            device.let {
                cThread = ConnectThread(it)
                cThread.start()
            }
        }
    }

    fun sendMassage(message: String){
        cThread.rThread.sendMassage(message.toByteArray())
    }
}