package com.example.itirium.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    lateinit var tvText: TextView
    lateinit var sensorManager: SensorManager
    lateinit var sensors: List<Sensor>
    lateinit var sensorLight: Sensor

    var listenerLight: SensorEventListener = object : SensorEventListener{
        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
            //
        }

        override fun onSensorChanged(event: SensorEvent) {
            tvText.text = event.values[0].toString()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvText = findViewById<View>(R.id.tvText) as TextView
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensors = sensorManager.getSensorList(Sensor.TYPE_ALL)
        sensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
    }

    fun onClickSensList(v: View){
        var i: Int = 1
        sensorManager.unregisterListener(listenerLight, sensorLight)
        val sb = StringBuilder()
        sb.append("Sensors count: ").append(sensors.size)
        for(sensor in sensors){
            sb.append("\n$i\nname = ").append(sensor.name)
                    .append(", type = ").append(sensor.type)
                    .append("\nvendor = ").append(sensor.vendor)
                    .append(" ,version = ").append(sensor.version)
                    .append("\nmax = ").append(sensor.maximumRange)
                    .append(", resolution = ").append(sensor.resolution)
                    .append("\n-------------------------------------------------\n")
            i++
        }
        tvText.text = sb
    }
    fun onClickSensLight(v:View){
        sensorManager.registerListener(listenerLight, sensorLight, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(listenerLight, sensorLight)
    }
}
