package saund.walkingApp

import android.content.ClipData
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import org.w3c.dom.Text
import android.content.Intent


class MainActivity2 : AppCompatActivity(), View.OnClickListener {
    //presetting stats vars to 0
    var steps = 0
    var calories = 0
    var distance = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        //call the goal fragment data receiver
        receiveData()

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

    //setting up intent function for goal fragment reception
    internal fun receiveData(){
        val goalTextHandler = findViewById<TextView>(R.id.goalTxt)
        val goalProgressHandler = findViewById<TextView>(R.id.goalProgress)
        if(intent.getStringExtra("key") != null) {
            goalTextHandler.text = "Your goal today: " + intent.getStringExtra("key").toString().toInt() + " steps"
            goalProgressHandler.text = "Steps until goal: " + (intent.getStringExtra("key").toString().toInt() - steps)
        }
    }

    internal fun closeActivity(){
        finish()
    }
}