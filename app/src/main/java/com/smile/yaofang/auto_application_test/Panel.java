package com.smile.yaofang.auto_application_test;

import android.support.car.hardware.CarSensorEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.animation.ObjectAnimator;
import android.util.Log;

public class Panel {
	private static final String TAG = "Panel";
	SensorController mSensorController = new SensorController();
	private View mContainer;
	private ImageView mRpm;
	private ImageView mSpeed;
	private TextView mOdo;
	private static float precedentRpm = 0;
	private static float precedentSpeed = 0;
	private GearPosition mGearPosition;
	private GearPositionUn mGearPositionUn;
	private GearPositionDeux mGearPositionDeux;
	private AbsFuelBrake mAbsFuelBrake;
	private GearPositionController mGearPositionController;
	private AbsFuelBrakeController mAbsFuelBrakeController;

	
	public Panel(View container){
		mContainer = container;
		mSpeed = (ImageView) mContainer.findViewById(R.id.aiguille);
		mGearPosition = (GearPosition) mContainer.findViewById(R.id.gearposition);
		mGearPositionUn = (GearPositionUn) mContainer.findViewById(R.id.gearpositionun);
		mGearPositionDeux = (GearPositionDeux) mContainer.findViewById(R.id.gearpositiondeux);
		mAbsFuelBrake = (AbsFuelBrake) mContainer.findViewById(R.id.absfuelbrake);
		mOdo = (TextView) mContainer.findViewById(R.id.odo);
		mRpm = (ImageView) mContainer.findViewById(R.id.aiguille_rpm);
	}
	
	public void updateSensorController(SensorController controller){
		mSensorController = controller;
		mGearPositionController = new GearPositionController(mGearPosition, mGearPositionUn, mGearPositionDeux, mSensorController);
		mAbsFuelBrakeController = new AbsFuelBrakeController(mAbsFuelBrake, mSensorController);
		mSensorController.registerCallback(mCallbacks);
	}
	private final SensorController.Callback mCallbacks
            = new SensorController.Callback() {	
				
            public void onSpeedChange(float speedV){
				Log.e(TAG, "Speed" +speedV);
				ObjectAnimator animationSpeed = ObjectAnimator.ofFloat(mSpeed, "rotation", precedentSpeed, speedV);
				precedentSpeed = speedV;
				animationSpeed.setDuration(2000);
				animationSpeed.start();
			}
			public void	onRMPChange(float rpmV){
				Log.e(TAG, "rpmV" +rpmV);
				rpmV = rpmV*0.03f;
				ObjectAnimator animationRpm = ObjectAnimator.ofFloat(mRpm, "rotation", precedentRpm, rpmV);
				precedentRpm = rpmV;
				animationRpm.setDuration(2000);
				animationRpm.start();
				
			}
			public void onOdoChange(float odoV){
				mOdo.setText(Float.toString(odoV));
			}
			
	};	           
}		
