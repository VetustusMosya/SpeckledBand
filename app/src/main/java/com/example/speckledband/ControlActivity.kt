package com.example.speckledband

import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SeekBar
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.speckledband.databinding.ActivityControlBinding
import com.github.dhaval2404.colorpicker.ColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape

//Log.d("MyLog", "$script")

class ControlActivity : AppCompatActivity(), RecievTread.Listener {
    private lateinit var binding: ActivityControlBinding
    private lateinit var actListLauncher: ActivityResultLauncher<Intent>
    lateinit var btConnection: BtConnection
    private var listItem: ListItem? = null
    private var rememberMac: String? = "00:21:13:00:60:87"
    private var rememberColor: Int? = null
    private var pref : SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityControlBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onListResult()
        init()
        pref = getSharedPreferences("table", Context.MODE_PRIVATE)
        rememberColor = pref?.getInt("color", -1)!!
        binding.seekBar4.progress = pref?.getInt("brightness", 40)!!

        val scriptLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){  result: ActivityResult ->
            if(result.resultCode == RESULT_OK){
                val script = result.data?.getParcelableExtra<Scripts>("script")
                val scId = script?.id
                btConnection.sendMassage("s$scId")
                binding.informText.text = "Script: ${script?.name}"
            }
        }

        binding.apply {
            btnOn.setOnClickListener{
                btConnection.sendMassage("${seekBar4.progress}")
                binding.btnOn.isEnabled = false
                binding.btnOff.isEnabled = true
                binding.seekBar4.visibility = View.VISIBLE
                binding.informText.text = "Band is on"
                binding.informLayout.setBackgroundColor(rememberColor!!)
            }
            btnOff.setOnClickListener{
                btConnection.sendMassage("t0")
                binding.informText.text = "Band is off"
                binding.btnOn.isEnabled = true
                binding.btnOff.isEnabled = false
                binding.informLayout.setBackgroundColor(Color.parseColor("#2F2F2F"))
            }
            btnCooseColor.setOnClickListener {
                ColorPickerDialog
                    .Builder(this@ControlActivity)
                    .setTitle("Choose your color")
                    .setColorShape(ColorShape.CIRCLE)
                    .setDefaultColor(rememberColor!!)
                    .setPositiveButton("Ok")
                    .setNegativeButton("Cancel")
                    .setColorListener { color, colorHex ->
                        rememberColor = color
                        binding.informLayout.setBackgroundColor(color)
                        binding.informText.text = "Color: $colorHex"
                        btConnection.sendMassage("0x${colorHex.slice(1..6)}")
                        saveData(color, "color")
                    }
                    .show()
            }
            btnSkript.setOnClickListener {
                scriptLauncher.launch(Intent(this@ControlActivity, ScriptsActivity::class.java))
            }
            seekBar4.setOnSeekBarChangeListener( object : SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(seek: SeekBar?, p1: Int, p2: Boolean) {

                }
                override fun onStartTrackingTouch(seek: SeekBar?) {

                }
                override fun onStopTrackingTouch(seek: SeekBar?) {
                    btConnection.sendMassage("${seek?.progress}")
                    Log.d("MyLog", "${seek?.progress}")
                    saveData(seek?.progress!!, "brightness")
                }
            })
        }
    }

    private fun init(){
        val btManager = getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
        val btAdapter = btManager.adapter
        btConnection = BtConnection(btAdapter, this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.control_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.id_list){
            actListLauncher.launch(Intent(this, ListActivity::class.java))
        } else if(item.itemId == R.id.id_connect) {
            listItem.let {
                if (it?.mac != null) {
                    btConnection.connect(it.mac)
                } else {
                    btConnection.connect(rememberMac!!)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onListResult(){
        actListLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == RESULT_OK){
                listItem = it.data?.getSerializableExtra(ListActivity.DEVICE_KEY) as ListItem
            }
        }

    }

    override fun onReceive(message: String) {
        runOnUiThread{
            binding.textView.text = message
            if(message == "Connected :)"){
                binding.btnOn.isEnabled = true
                binding.btnCooseColor.isEnabled = true
                binding.btnSkript.isEnabled = true
            } else {
                binding.btnOn.isEnabled = false
                binding.btnOff.isEnabled = false
                binding.btnCooseColor.isEnabled = false
                binding.btnSkript.isEnabled = false
                binding.seekBar4.visibility = View.INVISIBLE
            }
        }
    }

    fun saveData(value: Int, key: String){
        val editor = pref?.edit()
        editor?.putInt(key, value)
        editor?.apply()
    }

    override fun getState(status: String) {
        runOnUiThread {
        }
    }
}

