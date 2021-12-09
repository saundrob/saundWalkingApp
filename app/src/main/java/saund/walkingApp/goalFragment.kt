package saund.walkingApp

import android.content.ClipData
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import org.w3c.dom.Text

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [goalFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class goalFragment : Fragment(), View.OnClickListener {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_goal, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment goalFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            goalFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //getting buttons from xml
        val back_btn = getView()?.findViewById<Button>(R.id.back_button) as Button
        back_btn.setOnClickListener(this)
        val confirm_btn = getView()?.findViewById<Button>(R.id.confirm_button) as Button
        confirm_btn.setOnClickListener(this)

    }
    override fun onClick(View: View) {
        //setting button actions
        if(View.id==R.id.back_button){
            getActivity()?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit();
        }
        if(View.id==R.id.confirm_button){
            //saving goal here
            val goalTyped = view?.findViewById<TextView>(R.id.goalInput)
            val goalOkay = goalTyped?.text
            val intent = Intent(activity, MainActivity2::class.java)
            intent.putExtra("key", (goalOkay).toString())
            if((goalOkay).toString().toInt() <= 10000000 && (goalOkay).toString().toInt() >= 1) {
                //call function receive data here
                (activity as MainActivity2).closeActivity()
                startActivity(intent)
                getActivity()?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit();
            }
            else{
                val goalTxtMsg = view?.findViewById<TextView>(R.id.enterGoal)
                goalTxtMsg?.text = "Enter your goal in steps:\nRange: [1,10000000]"
            }
        }
    }
}