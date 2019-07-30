package com.smile.yaofang.auto_application_test;

import android.support.car.hardware.CarSensorManager;
import android.support.car.hardware.CarSensorEvent;
import android.util.SparseIntArray;
import android.util.Log;


public class GearPositionController {
	private static final String TAG = "Gear";
    private final GearPosition mGearPosition;
    private final GearPositionUn mGearPositionUn;
    private final GearPositionDeux mGearPositionDeux;
    private final SensorController mSensorController;
	
	public GearPositionController(GearPosition gearposition, GearPositionUn gearpositionun, GearPositionDeux gearpositiondeux, SensorController controller) {
        mGearPosition = gearposition;
        mGearPositionUn = gearpositionun;
        mGearPositionDeux = gearpositiondeux;
        mSensorController = controller;
        initialize();
    }
    private void initialize() {
        mSensorController.registerCallback(mCallback);
    }
    
    private final SensorController.Callback mCallback = new SensorController.Callback() {
		public void	onGearChange(int GearV){
			Log.e(TAG, "position"+GearV);
			switch(GearV){
				case CarSensorEvent.GEAR_REVERSE:
					mGearPosition.setGearPosition(GearV);
					mGearPositionUn.clearGearPositionUn();
					mGearPositionDeux.clearGearPositionDeux();
					break;
				case CarSensorEvent.GEAR_NEUTRAL:
					mGearPosition.setGearPosition(GearV);
					mGearPositionUn.clearGearPositionUn();
					mGearPositionDeux.clearGearPositionDeux();
					break;
				case CarSensorEvent.GEAR_DRIVE:
					mGearPosition.setGearPosition(GearV);
					mGearPositionUn.clearGearPositionUn();
					mGearPositionDeux.clearGearPositionDeux();
					break;
				case CarSensorEvent.GEAR_PARK:
					mGearPosition.setGearPosition(GearV);
					mGearPositionUn.clearGearPositionUn();
					mGearPositionDeux.clearGearPositionDeux();
					break;
				case CarSensorEvent.GEAR_FIRST:
					mGearPositionUn.setGearPosition(GearV);
					mGearPosition.clearGearPosition();
					mGearPositionDeux.clearGearPositionDeux();
					break;
				case CarSensorEvent.GEAR_SECOND:
					mGearPositionUn.setGearPosition(GearV);
					mGearPosition.clearGearPosition();
					mGearPositionDeux.clearGearPositionDeux();
					break;					
				case CarSensorEvent.GEAR_THIRD:
					mGearPositionUn.setGearPosition(GearV);
					mGearPosition.clearGearPosition();
					mGearPositionDeux.clearGearPositionDeux();
					break;	
				case CarSensorEvent.GEAR_FOURTH:
					mGearPositionDeux.setGearPosition(GearV);
					mGearPosition.clearGearPosition();
					mGearPositionUn.clearGearPositionUn();
					break;
				case CarSensorEvent.GEAR_FIFTH:
					mGearPositionDeux.setGearPosition(GearV);
					mGearPosition.clearGearPosition();
					mGearPositionUn.clearGearPositionUn();
					break;					
				case CarSensorEvent.GEAR_SIXTH:
					mGearPositionDeux.setGearPosition(GearV);
					mGearPosition.clearGearPosition();
					mGearPositionUn.clearGearPositionUn();
					break;			
			}
		}		
	};
}
