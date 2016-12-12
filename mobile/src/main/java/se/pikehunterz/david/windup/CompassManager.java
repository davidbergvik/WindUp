package se.pikehunterz.david.windup;

import android.hardware.SensorManager;

import android.hardware.Sensor;

import android.hardware.SensorEvent;

import android.hardware.SensorEventListener;
import android.text.Layout;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;


import static android.content.Context.SENSOR_SERVICE;

/**
 * Created by David on 2016-12-12.
 */

public class CompassManager {
    private SensorManager mSensorManager;
    private float currentDegree;
    private RelativeLayout compassLayout;
    public CompassManager(SensorManager sManager, RelativeLayout compassLayout){
        this.mSensorManager = sManager;
        this.compassLayout = compassLayout;
    }
    public float sensorChanged(SensorEvent event) {
        float degree = Math.round(event.values[0]);
        currentDegree = -degree;
        return currentDegree;
    }
    public float getCurrentDegree(){
        return this.currentDegree;
    }
}
