package com.example.speckledband

import android.bluetooth.BluetoothSocket
import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

class RecievTread(val bSocket: BluetoothSocket, val listener: RecievTread.Listener) : Thread() {

    var inStream: InputStream? = null
    var outStream: OutputStream? = null

    init {
        try {
            inStream = bSocket.inputStream
        } catch (i: IOException){

        }

        try {
            outStream = bSocket.outputStream
        } catch (i: IOException){

        }
    }

    override fun run() {
        val buf = ByteArray(256)
        while (true){
            try {
                val size = inStream?.read(buf)
                val massage = String(buf, 0, size!!)
                listener.getState(massage)
            } catch (i: IOException){
                listener.onReceive("Lost connection")
                break
            }
        }
    }

    fun sendMassage(byteArray: ByteArray){
        try {
            outStream?.write(byteArray)
        } catch (i: IOException){

        }
    }

    interface Listener {
        fun onReceive(message: String)
        fun getState(status: String)
    }
}