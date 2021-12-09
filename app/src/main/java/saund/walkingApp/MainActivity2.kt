package saund.walkingApp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.widget.Toast


class MainActivity2 : AppCompatActivity(), View.OnClickListener, SensorEventListener, Communicator {
    //presetting stats vars to 0
    var steps = 0
    var calories = 0
    var distance = 0
    var strStep: String = "0"

    //sensor vars
    var running = false
    var sensorManager:SensorManager?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        //start goalTxt Fragment
        supportFragmentManager.beginTransaction().replace(R.id.goalTxtContainer, goalTxtContainer()).commit()

        //sensor setup
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        //add buttons from xml to main
        val help_btn = findViewById<Button>(R.id.help_button)
        help_btn.setOnClickListener(this)
        val goal_btn = findViewById<Button>(R.id.goal_button)
        goal_btn.setOnClickListener(this)

        //add text lines from xml to main
        val stepsCounter = findViewById<TextView>(R.id.stepsToday)
        val distanceCounter = findViewById<TextView>(R.id.distanceToday)
        val calorieCounter = findViewById<TextView>(R.id.caloriesToday)

        //preset the stats to 0
        stepsCounter.text="Steps: " + steps
        distanceCounter.text = "Distance: " + distance + " meters"
        calorieCounter.text = "Approx. calories burned: " + calories
    }

    //set button actions
    override fun onClick(View: View){
        if(View.id==R.id.help_button){
            supportFragmentManager.beginTransaction().replace(R.id.helpContainer1, helpFragment1()).commit()
        }
        if(View.id==R.id.goal_button){
            supportFragmentManager.beginTransaction().replace(R.id.goalContainer, goalFragment()).commit()
        }
    }

    //close fragment function
    internal fun closeGoalTxtFragment(){
        supportFragmentManager.findFragmentById(R.id.goalTxtContainer)
            ?.let { supportFragmentManager.beginTransaction().remove(it).commit() }
    }

    //sensor codes
    override fun onResume() {
        super.onResume()
        running = true
        var stepsSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        if(stepsSensor==null){
            Toast.makeText(this, "No step sensor found!", Toast.LENGTH_SHORT).show()
        }
        else{
            sensorManager?.registerListener(this, stepsSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        running = false
        sensorManager?.unregisterListener(this)
    }
    //functions for motion sensors
    override fun onSensorChanged(event: SensorEvent?) {
        if(running){
            val stepsCounter = findViewById<TextView>(R.id.stepsToday)

            stepsCounter.text ="Steps: " + event!!.values[0]
             strStep = event!!.values[0].toString()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        TODO("Not yet implemented")
    }

    //frag to frag data function
    override fun passDataCom(editTextInput: String) {
        val bundle = Bundle()
        bundle.putString("message", editTextInput)
        bundle.putString("message2", strStep)
        val transaction = supportFragmentManager.beginTransaction()
        var FragB = goalTxtContainer()
        FragB.arguments = bundle
        transaction.replace(R.id.goalTxtContainer, FragB)
        transaction.commit()
    }
}