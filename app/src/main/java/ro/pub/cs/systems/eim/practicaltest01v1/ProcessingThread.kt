package ro.pub.cs.systems.eim.practicaltest01v1

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.util.Random
import android.os.Process
import java.lang.Math.sqrt
import java.util.Date

class ProcessingThread(private val context: Context, firstNumber: Int, secondNumber: Int) :
    Thread() {
    private var isRunning = true

    private val random = Random()

    private  val arithmeticMean: Double
    private val geometricMean: Double

    init {
        arithmeticMean = (firstNumber + secondNumber).toDouble() / 2
        geometricMean = kotlin.math.sqrt((firstNumber * secondNumber).toDouble())
    }


    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    override fun run() {
        Log.d(
            "Thread_Process",
            "Thread has started! PID: " + Process.myPid() + " TID: " + Process.myTid()
        )
        while (isRunning) {
            sendMessage()
            sleep()
        }
        Log.d("Thread_Process", "Thread has stopped!")
    }

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    private fun sendMessage() {
        val intent = Intent()
        intent.setAction(Constants.actionTypes[random.nextInt(Constants.actionTypes.size)])
        intent.putExtra("broadcast_receiver_extra",
            Date(System.currentTimeMillis()).toString() + " " + arithmeticMean + " " + geometricMean
        )
        context.sendBroadcast(intent)
    }

    private fun sleep() {
        try {
            sleep(1000)
        } catch (interruptedException: InterruptedException) {
            interruptedException.printStackTrace()
        }
    }

    fun stopThread() {
        isRunning = false
    }
}