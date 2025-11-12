package ro.pub.cs.systems.eim.practicaltest01v1

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PracticalTest01MainActivity : AppCompatActivity() {

    private lateinit var input1: EditText
    private lateinit var input2: EditText

    private var lNumber = 0
    private var rNumber = 0
    private val intentFilter = IntentFilter()

    private val messageBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                Log.d("Message", it.action.toString())
                Log.d("Message", it.getStringExtra("broadcast_receiver_extra").toString())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_practical_test01_main)

        input1 = findViewById(R.id.input1)
        input2 = findViewById(R.id.input2)
        input1.setText("0")
        input2.setText("0")

        val pressMe = findViewById<Button>(R.id.press_me)
        pressMe.setOnClickListener {
            lNumber++
            input1.setText(lNumber.toString())
            startServiceIf(lNumber, rNumber)
        }

        val pressMeToo = findViewById<Button>(R.id.press_me_too)
        pressMeToo.setOnClickListener {
            rNumber++
            input2.setText(rNumber.toString())
            startServiceIf(lNumber, rNumber)
        }

        val activityResultsLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                Toast.makeText(this, "The activity returned with result OK", Toast.LENGTH_LONG)
                    .show()
            } else if (result.resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "The activity returned with result CANCELED", Toast.LENGTH_LONG)
                    .show()
            }
        }

        val navigateToSecondaryActivityButton = findViewById<Button>(R.id.nav)
        navigateToSecondaryActivityButton.setOnClickListener {
            val intent = Intent(this, PracticalTest01SecondaryActivity::class.java)
            intent.putExtra("INPUT1", Integer.valueOf(input1.text.toString()))
            intent.putExtra("INPUT2", Integer.valueOf(input2.text.toString()))
            activityResultsLauncher.launch(intent)
        }

        Constants.actionTypes.forEach { action ->
            intentFilter.addAction(action)
        }
    }


    private fun startServiceIf(lNumber: Int, rNumber: Int) {
        if (lNumber + rNumber > 5) {
            val intent = Intent(applicationContext, PracticalTest01Service::class.java).apply {
                putExtra("INPUT1", lNumber)
                putExtra("INPUT2", rNumber)
            }
            Log.d("tagBau", "bSABSasbjbsASsbasbAJSBa")
            applicationContext.startService(intent)
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("INPUT1", input1.text.toString())
        outState.putString("INPUT2", input2.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState.containsKey("INPUT1") && savedInstanceState.containsKey("INPUT2")) {
            input1.setText(savedInstanceState.getString("INPUT1"))
            input2.setText(savedInstanceState.getString("INPUT2"))
            lNumber = Integer.valueOf(input1.text.toString())
            rNumber = Integer.valueOf(input2.text.toString())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(messageBroadcastReceiver, intentFilter, RECEIVER_EXPORTED)
        } else {
            registerReceiver(messageBroadcastReceiver, intentFilter)
        }
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(messageBroadcastReceiver)
    }

    override fun onDestroy() {
        val intent = Intent(applicationContext, PracticalTest01Service::class.java)
        applicationContext.stopService(intent)
        super.onDestroy()
    }
}