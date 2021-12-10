package saund.walkingApp

import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.fitness.HistoryClient
import com.google.android.gms.fitness.data.DataPoint
import com.google.android.gms.fitness.data.DataSource
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.DataType.AGGREGATE_STEP_COUNT_DELTA
import com.google.android.gms.fitness.data.Field
import com.google.android.gms.fitness.request.DataReadRequest
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.concurrent.TimeUnit
import java.util.jar.Manifest


class MainActivity2 : AppCompatActivity(), View.OnClickListener, SensorEventListener, Communicator {
    //presetting stats vars to 0
    var steps = 0
    var calories = 0
    var distance = 0
    var strStep: String = "0"
    var requestCode = 1
    val TAG = "MyMessage"

    //sensor vars
    var running = false
    var sensorManager:SensorManager?=null


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        //start goalTxt Fragment
        supportFragmentManager.beginTransaction().replace(R.id.goalTxtContainer, goalTxtContainer()).commit()

        //sensor setup
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

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
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACTIVITY_RECOGNITION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACTIVITY_RECOGNITION),
                requestCode)

            // Permission is not granted
        }

        // Read the data that's been collected throughout the past week.
        val endTime = LocalDateTime.now().atZone(ZoneId.systemDefault())
        val startTime = endTime.minusWeeks(1)
        Log.i(TAG, "Range Start: $startTime")
        Log.i(TAG, "Range End: $endTime")

        val readRequest =
            DataReadRequest.Builder()
                // The data request can specify multiple data types to return,
                // effectively combining multiple data queries into one call.
                // This example demonstrates aggregating only one data type.
                .aggregate(DataType.AGGREGATE_STEP_COUNT_DELTA)
                // Analogous to a "Group By" in SQL, defines how data should be
                // aggregated.
                // bucketByTime allows for a time span, whereas bucketBySession allows
                // bucketing by <a href="/fit/android/using-sessions">sessions</a>.
                .bucketByTime(1, TimeUnit.DAYS)
                .setTimeRange(startTime.toEpochSecond(), endTime.toEpochSecond(), TimeUnit.SECONDS)
                .build()


        //val readRequest = DataReadRequest.Builder()
        //    .aggregate(DataType.AGGREGATE_CALORIES_EXPENDED)
                //    .bucketByActivityType(1, TimeUnit.SECONDS)
            //.setTimeRange(startTime.toEpochSecond(), endTime.toEpochSecond(), TimeUnit.SECONDS)
           // .build()


        val dataSource = DataSource.Builder()
            .setAppPackageName(this)
            .setDataType(DataType.TYPE_STEP_COUNT_DELTA)
            .setStreamName("$TAG - step count")
            .setType(DataSource.TYPE_RAW)
            .build()

        val stepCountDelta = 950
        val dataPoint =
            DataPoint.builder(dataSource)
                .setField(Field.FIELD_STEPS, stepCountDelta)
                .setTimeInterval(startTime.toEpochSecond(), endTime.toEpochSecond(), TimeUnit.SECONDS)
                .build()
        strStep = "950"


        stepsCounter.text = "Steps: " + steps




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
            //stepsCounter = findViewById<TextView>(R.id.stepsToday)

           // stepsCounter.text ="Steps: " + steps
            // strStep = event!!.values[0].toString()
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
