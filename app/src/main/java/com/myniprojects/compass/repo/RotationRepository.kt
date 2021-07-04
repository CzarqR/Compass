package com.myniprojects.compass.repo

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.myniprojects.compass.utils.toRotationInDegrees
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.PI

@Singleton
class RotationRepository @Inject constructor(
    @ApplicationContext private val context: Context
)
{
    private val sensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val accelerometerReading = FloatArray(3)
    private val magnetometerReading = FloatArray(3)
    
    private val rotationMatrix = FloatArray(9)
    private val orientationAngles = FloatArray(3)
    
    fun startObservingRotation()
    {
        sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)?.also { magneticField ->
            sensorManager.registerListener(
                sensorEventListener,
                magneticField,
                SensorManager.SENSOR_DELAY_NORMAL,
            )
        }
        
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also { accelerometer ->
            sensorManager.registerListener(
                sensorEventListener,
                accelerometer,
                SensorManager.SENSOR_DELAY_NORMAL,
            )
        }
    }
    
    fun stopObservingRotation()
    {
        sensorManager.unregisterListener(sensorEventListener)
    }
    
    private val sensorEventListener = object : SensorEventListener
    {
        override fun onSensorChanged(event: SensorEvent?)
        {
            when (event?.sensor?.type)
            {
                Sensor.TYPE_ACCELEROMETER ->
                {
                    System.arraycopy(
                        event.values,
                        0,
                        accelerometerReading,
                        0,
                        accelerometerReading.size
                    )
                    updateOrientationAngles()
                }
                Sensor.TYPE_MAGNETIC_FIELD ->
                {
                    System.arraycopy(
                        event.values,
                        0,
                        magnetometerReading,
                        0,
                        magnetometerReading.size
                    )
                    updateOrientationAngles()
                }
            }
        }
        
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int)
        {
            Timber.d("Sensor: $sensor.  Accuracy: $accuracy")
        }
    }
    
    private fun updateOrientationAngles()
    {
        SensorManager.getRotationMatrix(
            rotationMatrix,
            null,
            accelerometerReading,
            magnetometerReading
        )
        
        SensorManager.getOrientation(rotationMatrix, orientationAngles)
        
        _rotationFlow.value = (orientationAngles[0] * 180 / PI).toRotationInDegrees()
    }
    
    private val _rotationFlow: MutableStateFlow<Int> =
            MutableStateFlow(0)
    
    val rotationFlow = _rotationFlow.asStateFlow()
    
}