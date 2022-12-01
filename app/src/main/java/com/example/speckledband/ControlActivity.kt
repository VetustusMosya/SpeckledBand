package com.example.speckledband

import android.bluetooth.BluetoothManager
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SeekBar
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.speckledband.databinding.ActivityControlBinding
import com.github.dhaval2404.colorpicker.ColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape

//import com.github.dhaval2404.colorpicker.listener.ColorListener
//import com.github.dhaval2404.colorpicker.model.ColorShape
//import com.nvt.color.ColorPickerDialog


class ControlActivity : AppCompatActivity(), RecievTread.Listener {
    private lateinit var binding: ActivityControlBinding
    private lateinit var actListLauncher: ActivityResultLauncher<Intent>
    lateinit var btConnection: BtConnection
    private var listItem: ListItem? = null
    private var rememberMac: String? = "00:21:13:00:60:87"
    private var rememberColor: Int? = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityControlBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onListResult()
        init()

        binding.apply {
            btnOff.setOnClickListener{
                    btConnection.sendMassage("0")
            }
            btnOn.setOnClickListener{
                    val bri = seekBar4.progress
                    btConnection.sendMassage("$bri")
            }
            btnCooseColor.setOnClickListener {
                ColorPickerDialog
                    .Builder(this@ControlActivity)   // Pass Activity Instance
                    .setTitle("Choose your color")    // Default "Choose Color"
                    .setColorShape(ColorShape.CIRCLE)// Default ColorShape.CIRCLE
                    .setDefaultColor(rememberColor!!)
                    .setPositiveButton("Ok")
                    .setNegativeButton("Cancel")// Pass Default Color
                    .setColorListener { color, colorHex ->
                        rememberColor = color
                        binding.btnCooseColor.setBackgroundColor(color)
                        btConnection.sendMassage("0x${colorHex.slice(1..6)}")
                    }
                    .show()
            }
            btnSkript.setOnClickListener {
                val skripts = Intent(this@ControlActivity,ScriptsActivity::class.java)
                startActivity(skripts)
            }
            seekBar4.setOnSeekBarChangeListener( object : SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {}
                override fun onStartTrackingTouch(p0: SeekBar?) {}
                override fun onStopTrackingTouch(seek: SeekBar?) {
                    if (seek != null) {
                        btConnection.sendMassage("${seek.progress}")
                    }
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
                binding.btnOff.isEnabled = true
                binding.btnCooseColor.isEnabled = true
                binding.seekBar4.visibility = View.VISIBLE
            } else {
                binding.btnOn.isEnabled = false
                binding.btnOff.isEnabled = false
                binding.btnCooseColor.isEnabled = false
                binding.seekBar4.visibility = View.INVISIBLE
            }
        }
    }

    override fun getState(status: String) {
        runOnUiThread {
            binding.textView2.text = status
        }
    }
}

