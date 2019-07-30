package com.smile.yaofang.auto_application_test;

import android.support.car.hardware.CarSensorManager;
import android.support.car.hardware.CarSensorEvent;
import android.util.SparseIntArray;

public class AbsFuelBrakeController {

    private final AbsFuelBrake mAbsFuelBrake;
    private final SensorController mSensorController;
	
	public AbsFuelBrakeController(AbsFuelBrake absfuelbrake, SensorController controller) {
        mAbsFuelBrake = absfuelbrake;
        mSensorController = controller;
        initialize();
    }
    private void initialize() {
        mSensorController.registerCallback(mCallback);
    }
    
    private final SensorController.Callback mCallback = new SensorController.Callback() {
		public void	onFuelChange(int levelV){
			mAbsFuelBrake.setFuel(levelV);
		}
		public void onABSChange(int absV){
			mAbsFuelBrake.setAbs(absV);
		}
		public void onBrakeChange(int brakeV){
			mAbsFuelBrake.setBrake(brakeV);
		}	
	};
}
