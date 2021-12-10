package saund.walkingApp

import android.hardware.Sensor
import android.hardware.SensorManager

interface Communicator {
    fun passDataCom(editTextInput: String)
}