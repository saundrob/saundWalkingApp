package saund.walkingApp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [goalTxtContainer.newInstance] factory method to
 * create an instance of this fragment.
 */
class goalTxtContainer : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    //update the goal counter
    override fun onResume() {
        super.onResume()
        if(arguments?.getString("message")!=null) {
            val goalTextHandler = view?.findViewById<TextView>(R.id.goalTxt)
            val goalProgressHandler = view?.findViewById<TextView>(R.id.goalProgress)
            val strIntGoal = arguments?.getString("message")
            val strIntSteps = arguments?.getString("message2")
            val planInt = strIntGoal?.toInt()
            val soFarInt = strIntSteps?.toInt()
            val toGo: Int = soFarInt?.let { planInt?.minus(it) } ?: 0
            val str = "Steps until goal: " + toGo

            goalTextHandler?.text = "Your goal today: " + strIntGoal?.toInt() + " steps"
            goalProgressHandler?.text = str
        }
        else{
            System.out.println("no message")
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v =  inflater.inflate(R.layout.fragment_goal_txt_container, container, false)
        return v
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment goalTxtContainer.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            goalTxtContainer().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}