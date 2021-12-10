package saund.walkingApp

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import kotlinx.android.synthetic.main.activity_main2.*
import android.view.ViewGroup





class MainActivity2 : AppCompatActivity(), View.OnClickListener, SensorEventListener, Communicator {
    //Goal txt Fragment
    var FragB = goalTxtContainer()

    //presetting stats vars to 0
    var steps = 0
    var calories = 0
    var distance = 0

    //sensor vars
    var running = false
    val sensorManager: SensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        //start goalTxt Fragment
        supportFragmentManager.beginTransaction().replace(R.id.goalTxtContainer, goalTxtContainer())
            .commit()

        //add buttons from xml to main
        val help_btn = findViewById<Button>(R.id.help_button)
        help_btn.setOnClickListener(this)
        val goal_btn = findViewById<Button>(R.id.goal_button)
        goal_btn.setOnClickListener(this)
        val sign_in = findViewById<Button>(R.id.sign_in_button)
        sign_in.setOnClickListener(this)

        //add text lines from xml to main
        val stepsCounter = findViewById<TextView>(R.id.stepsToday)
        val distanceCounter = findViewById<TextView>(R.id.distanceToday)
        val calorieCounter = findViewById<TextView>(R.id.caloriesToday)

        //preset the stats to 0
        stepsCounter.text = "Steps: " + steps
        distanceCounter.text = "Distance: " + distance + " meters"
        calorieCounter.text = "Approx. calories burned: " + calories
    }


    //set button actions
    override fun onClick(View: View) {
        if (View.id == R.id.help_button) {
            supportFragmentManager.beginTransaction().replace(R.id.helpContainer1, helpFragment1())
                .commit()
        }
        if (View.id == R.id.goal_button) {
            supportFragmentManager.beginTransaction().replace(R.id.goalContainer, goalFragment())
                .commit()
            //val parent = parent as ViewGroup
        }
        if(View.id == R.id.sign_in_button){
            //start sign in activity
            val intent = Intent (this, sign_in_activity::class.java).apply{
                putExtra("yo", "yep")
            }
            startActivity(intent)
        }
    }


    //close fragment function
    internal fun closeGoalTxtFragment() {
        supportFragmentManager.findFragmentById(R.id.goalTxtContainer)
            ?.let { supportFragmentManager.beginTransaction().remove(it).commit() }
    }


    //sensor codes
    override fun onResume() {
        super.onResume()
        running = true
        //swap statements to see functionality on emulator
        val stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        //val stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(this,stepSensor,SensorManager.SENSOR_DELAY_UI)
    }

    override fun onPause(){
        super.onPause()
        running = false
        sensorManager.unregisterListener(this)
    }
    //functions for motion sensors
    override fun onSensorChanged(event: SensorEvent) {
        if (running) {
            val stepsCounter = findViewById<TextView>(R.id.stepsToday)
            //swap statements to see functionality on emulator
            steps = event.values[0].toInt()
            //steps = steps + 1
            stepsCounter.text = "Steps: " + steps
            distanceToday.text  = "Distance: "+(steps*0.762).toInt().toString()+" meters"
            caloriesToday.text = "Approx. calories burned: "+(steps*.04).toInt().toString()
            FragB.updateToGo(steps)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    //frag to frag data function
    override fun passDataCom(editTextInput: String) {
        val bundle = Bundle()
        bundle.putString("message", editTextInput)
        bundle.putString("message2", steps.toString())
        val transaction = supportFragmentManager.beginTransaction()

        FragB.arguments = bundle
        transaction.replace(R.id.goalTxtContainer, FragB)
        transaction.commit()
    }
}