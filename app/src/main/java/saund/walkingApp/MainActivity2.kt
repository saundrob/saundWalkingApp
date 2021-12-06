package saund.walkingApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button


class MainActivity2 : AppCompatActivity(), View.OnClickListener  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        //supportFragmentManager.beginTransaction().add(R.id.helpContainer2, helpFragment()).commit()
        val help_btn = findViewById<Button>(R.id.help_button)
        help_btn.setOnClickListener(this)
        /*val close_btn = findViewById<Button>(R.id.close_button)
        close_btn.setOnClickListener(this)*/

    }

    override fun onClick(View: View){
        if(View.id==R.id.help_button){
            supportFragmentManager.beginTransaction().replace(R.id.helpContainer1, helpFragment1()).commit()
        }
        /*if(View.id==R.id.close_button){
            supportFragmentManager.beginTransaction().remove(helpFragment()).commit()
        }*/
    }
}