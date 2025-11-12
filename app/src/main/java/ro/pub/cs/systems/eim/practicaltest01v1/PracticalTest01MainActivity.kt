package ro.pub.cs.systems.eim.practicaltest01v1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PracticalTest01MainActivity : AppCompatActivity() {

    private lateinit var input1: EditText
    private lateinit var input2: EditText

    private var lNumber = 0
    private var rNumber = 0

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
        }

        val pressMeToo = findViewById<Button>(R.id.press_me_too)
        pressMeToo.setOnClickListener {
            rNumber++
            input2.setText(rNumber.toString())
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
}