package com.example.speckledband

import android.bluetooth.BluetoothManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.speckledband.databinding.ActivityControlBinding
import com.nvt.color.ColorPickerDialog

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
                    btConnection.sendMassage("1")
            }
            btnCooseColor.setOnClickListener {
                val colorPicker = ColorPickerDialog(
                    this@ControlActivity,
                    rememberColor!!, // color init
                    false, // true is show alpha
                    object : ColorPickerDialog.OnColorPickerListener {
                        override fun onCancel(dialog: ColorPickerDialog?) {
                            // handle click button Cancel
                        }
                        override fun onOk(dialog: ColorPickerDialog?, colorPicker: Int) {
                            rememberColor = colorPicker
                            binding.btnCooseColor.setBackgroundColor(colorPicker)
                            val color = colorPicker.toUInt().toString(16)
                            btConnection.sendMassage("0x${ color.substring(2, color.length) }")
                        }
                    })
                colorPicker.show()
            }
            btnSkript.setOnClickListener {
                val skripts = Intent(this@ControlActivity,SkriptsActivity::class.java)
                startActivity(skripts)
            }
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
            } else {
                binding.btnOn.isEnabled = false
                binding.btnOff.isEnabled = false
                binding.btnCooseColor.isEnabled = false
            }
        }
    }

    override fun getState(status: String) {
        runOnUiThread {
            binding.textView2.text = status
        }
    }
}

