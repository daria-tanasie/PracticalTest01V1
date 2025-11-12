package ro.pub.cs.systems.eim.practicaltest01v1

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class PracticalTest01SecondaryActivity : AppCompatActivity() {
    private lateinit var input1: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_practical_test01_secondary)

        val in1 = intent.getIntExtra("INPUT1", 0)
        val in2 = intent.getIntExtra("INPUT2", 0)
        val sum = in2 + in1
        input1 = findViewById(R.id.input21)
        input1.setText(sum.toString())

        val ok = findViewById<Button>(R.id.ok)
        ok.setOnClickListener {
            setResult(RESULT_OK, intent)
            finish()
        }

        val cancel = findViewById<Button>(R.id.cancel)
        cancel.setOnClickListener {
            setResult(RESULT_CANCELED, intent)
            finish()
        }
    }
}