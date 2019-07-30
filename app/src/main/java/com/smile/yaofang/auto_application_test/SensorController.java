package com.smile.yaofang.auto_application_test;

import android.os.Handler;
import android.os.IBinder;
import android.os.Binder;
import android.support.car.Car;
import android.support.car.CarConnectionCallback;
import android.support.car.CarNotConnectedException;
import android.support.car.hardware.CarSensorManager;
import android.support.car.hardware.CarSensorEvent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;

import android.app.Service;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;

public class SensorController extends Service {
	private static final String TAG = "SensorController";
	private CarSensorManager mCarSensorManager;
	private Car mCar;
	private DataStore mDataStore = new DataStore();
	private Object mSensorManagerReady = new Object();

	public class LocalBinder extends Binder {
        SensorController getService() {
            return SensorController.this;
        }
    }
	
	 public static abstract class Callback {

        public void	onRMPChange(float rmpV){
		}
		public void onSpeedChange(float speedV){
		}
		public void	onFuelChange(int levelV){
		}
		public void onOdoChange(float odoV){
		}
		public void onBrakeChange(int brakeV){
		}
		public void	onGearChange(int GearV){
		}
		public void onABSChange(int absV){
		}	
    }
	
    private final Binder mBinder = new LocalBinder();
    private List<Callback> mCallbacks = new ArrayList<>();
   
	public void registerCallback(Callback callback) {
        synchronized (mCallbacks) {
            mCallbacks.add(callback);
        }
    }

    public void unregisterCallback(Callback callback) {
        synchronized (mCallbacks) {
            mCallbacks.remove(callback);
        }
    }
   
	private final CarConnectionCallback mCarConnectionCallback =
            new CarConnectionCallback() {
         @Override
        public void onConnected(Car car) {
			synchronized (mSensorManagerReady) {
				Log.d(TAG, "Connected to Car Service");
				try {
					mCarSensorManager = (CarSensorManager) mCar.getCarManager(Car.SENSOR_SERVICE);
					mCarSensorManager.addListener(mCarSensorListener,
                        CarSensorManager.SENSOR_TYPE_CAR_SPEED,
                        CarSensorManager.SENSOR_RATE_NORMAL);
					mCarSensorManager.addListener(mCarSensorListener,
                        CarSensorManager.SENSOR_TYPE_RPM,
                        CarSensorManager.SENSOR_RATE_NORMAL);
					mCarSensorManager.addListener(mCarSensorListener,
                        CarSensorManager.SENSOR_TYPE_ODOMETER,
                        CarSensorManager.SENSOR_RATE_NORMAL);
					mCarSensorManager.addListener(mCarSensorListener,
                        CarSensorManager.SENSOR_TYPE_FUEL_LEVEL,
                        CarSensorManager.SENSOR_RATE_NORMAL);
					mCarSensorManager.addListener(mCarSensorListener,
                        CarSensorManager.SENSOR_TYPE_PARKING_BRAKE,
                        CarSensorManager.SENSOR_RATE_NORMAL);
					mCarSensorManager.addListener(mCarSensorListener,
                        CarSensorManager.SENSOR_TYPE_GEAR,
                        CarSensorManager.SENSOR_RATE_NORMAL); 
                    mCarSensorManager.addListener(mCarSensorListener,
                        CarSensorManager.SENSOR_TYPE_ABS_ACTIVE,
                        CarSensorManager.SENSOR_RATE_NORMAL);              
					mSensorManagerReady.notifyAll(); 
				} catch (CarNotConnectedException e) {
					Log.e(TAG, "Car is not connected!", e);
				}
			}	
        }

        @Override
        public void onDisconnected(Car car) {
            Log.d(TAG, "Disconnect from Car Service");
        }
    };
    
    private final CarSensorManager.OnSensorChangedListener mCarSensorListener =
            new CarSensorManager.OnSensorChangedListener() {
                @Override
                public void onSensorChanged(CarSensorManager manager, CarSensorEvent event) {
                   //Log.e(TAG, "New car sensor event: " + event.sensorType);
                   switch(event.sensorType){
						case CarSensorManager.SENSOR_TYPE_CAR_SPEED:
							Log.e(TAG, "SENSOR_TYPE_CAR_SPEED");
							CarSensorEvent.CarSpeedData carspeeddata = event.getCarSpeedData();
							handleSpeedUpdate(carspeeddata.carSpeed);
							break;
						case CarSensorManager.SENSOR_TYPE_RPM:
							Log.e(TAG, "SENSOR_TYPE_RPM");
							CarSensorEvent.RpmData rpmdata = event.getRpmData();
							handleRMPUpdate(rpmdata.rpm);
							break;
						case CarSensorManager.SENSOR_TYPE_ODOMETER:
							Log.e(TAG, "SENSOR_TYPE_ODOMETER");
							CarSensorEvent.OdometerData odometerdata = event.getOdometerData();
							handleOdoUpdate(odometerdata.kms);
							break;
						case CarSensorManager.SENSOR_TYPE_FUEL_LEVEL:
							Log.e(TAG, "SENSOR_TYPE_FUEL_LEVEL");
							CarSensorEvent.FuelLevelData fuelleveldata = event.getFuelLevelData();
							Log.e(TAG, "fuel "+fuelleveldata.lowFuelWarning);
							int fuelVal = fuelleveldata.lowFuelWarning ? 1 : 0;
							handleFuelUpdate(fuelVal);
							break;
						case CarSensorManager.SENSOR_TYPE_PARKING_BRAKE:
							Log.e(TAG, "SENSOR_TYPE_PARKING_BRAKE");
							CarSensorEvent.ParkingBrakeData parkingbrakedata = event.getParkingBrakeData();
							int brakeVal = parkingbrakedata.isEngaged ? 1 : 0;
							handleBrakeUpdate(brakeVal);
							break;
						case CarSensorManager.SENSOR_TYPE_GEAR:
							Log.e(TAG, "SENSOR_TYPE_GEAR");
							CarSensorEvent.GearData geardata = event.getGearData();
							handleGearUpdate(geardata.gear);
							break;
						 case CarSensorManager.SENSOR_TYPE_ABS_ACTIVE:
							Log.e(TAG, "SENSOR_TYPE_ABS_ACTIVE");
							CarSensorEvent.CarAbsActiveData absdata = event.getCarAbsActiveData();
							int absVal = absdata.absIsActive ? 1 : 0;
							handleABSUpdate(absVal); 
							break;	
						default:
							Log.d(TAG, "Unknowed Sensor Event" + event.sensorType);	
					}
				}	
            };
       
    @Override
    public void onCreate() {
		Log.e(TAG, "onCreate");
        super.onCreate();
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_AUTOMOTIVE)) {
            mCar = Car.createCar(this, mCarConnectionCallback);
            mCar.connect();
        }
    }
    @Override
	public void onDestroy() {
        super.onDestroy();
        if (mCar != null) {
            mCar.disconnect();
        }
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }
	@Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }  
    
    private void handleSpeedUpdate(float speed){
		boolean shouldPropagate = mDataStore.shouldPropagateSpeed(speed);
		if (shouldPropagate) {
            synchronized (mCallbacks) {
                for (int i = 0; i < mCallbacks.size(); i++) {
                    mCallbacks.get(i).onSpeedChange(speed);
                }
            }
        }
	}
	
	private void handleRMPUpdate(float rmp){
		boolean shouldPropagate = mDataStore.shouldPropagateRMP(rmp);
		if (shouldPropagate) {
            synchronized (mCallbacks) {
                for (int i = 0; i < mCallbacks.size(); i++) {
                    mCallbacks.get(i).onRMPChange(rmp);
                }
            }
        }
	}
	
	private void handleOdoUpdate(float odo){
		boolean shouldPropagate = mDataStore.shouldPropagateOdo(odo);
		if (shouldPropagate) {
            synchronized (mCallbacks) {
                for (int i = 0; i < mCallbacks.size(); i++) {
                    mCallbacks.get(i).onOdoChange(odo);
                }
            }
        }
	}
	
	private void handleFuelUpdate(int fuel){
		boolean shouldPropagate = mDataStore.shouldPropagateFuel(fuel);
		if (shouldPropagate) {
            synchronized (mCallbacks) {
                for (int i = 0; i < mCallbacks.size(); i++) {
                    mCallbacks.get(i).onFuelChange(fuel);
                }
            }
        }
	}
	
	private void handleGearUpdate(int gear){
		boolean shouldPropagate = mDataStore.shouldPropagateGear(gear);
		if (shouldPropagate) {
            synchronized (mCallbacks) {
                for (int i = 0; i < mCallbacks.size(); i++) {
                    mCallbacks.get(i).onGearChange(gear);
                }
            }
        }
	}	
	
	private void handleBrakeUpdate(int brake){
		boolean shouldPropagate = mDataStore.shouldPropagateBrake(brake);
		if (shouldPropagate) {
            synchronized (mCallbacks) {
                for (int i = 0; i < mCallbacks.size(); i++) {
                    mCallbacks.get(i).onBrakeChange(brake);
                }
            }
        }
	}
	
	private void handleABSUpdate(int abs){
		boolean shouldPropagate = mDataStore.shouldPropagateABS(abs);
		if (shouldPropagate) {
            synchronized (mCallbacks) {
                for (int i = 0; i < mCallbacks.size(); i++) {
                    mCallbacks.get(i).onABSChange(abs);
                }
            }
        }
	}

	public void requestRefresh(final Runnable r, final Handler h) {
		Log.e(TAG, "requestRefresh");
        final AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... unused) {
                synchronized (mSensorManagerReady) {
                    while (mCarSensorManager == null) {
                        try {
                            mSensorManagerReady.wait();
                        } catch (InterruptedException e) {
                            // We got interrupted so we might be shutting down.
                            return null;
                        }
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                h.post(r);
            }
        };
        task.execute();
    }
    
	public float getRMP(){
		return mDataStore.getRMP();
	}
	public float getSpeed(){
		return mDataStore.getSpeed();
	}
	public float getOdo(){
		return mDataStore.getOdo();
	}
	public int getFuel(){
		return mDataStore.getFuel();
	}
	public int getBrake(){
		return mDataStore.getBrake();
	}
	public int getGear(){
		return mDataStore.getGear();
	}
	public int getABS(){
		return mDataStore.getABS();
	}
		     
}	
