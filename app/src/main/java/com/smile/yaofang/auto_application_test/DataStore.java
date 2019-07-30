package com.smile.yaofang.auto_application_test;

import java.util.concurrent.TimeUnit;
import android.os.SystemClock;

public class DataStore{
	private static final long COALESCE_TIME_MS = TimeUnit.SECONDS.toMillis(2);
	
	private Float mSpeed = 0.0f;
	private Float mRMP = 0.0f;
	private Float mOdo = 0.0f;
	private Integer mGear = 0;
	private Integer mBrake = 1;
	private Integer mABS = 0;
	private Integer mFuel = 0;
	
	private long mLastRMPSet;
	private long mLastSpeedSet;
	private long mLastOdoSet;
	private long mLastFuelSet;
	private long mLastGearSet;
	private long mLastBrakeSet;
	private long mLastABSSet;
	
	
	public float getSpeed(){
		synchronized (mSpeed) {
			return mSpeed;
		}		
	}		
	public float getRMP(){
		synchronized (mRMP) {
			return mRMP;
		}		
	}
	public float getOdo(){
		synchronized (mOdo) {
			return mOdo;
		}		
	}		
	public int getFuel(){
		synchronized (mFuel) {
			return mFuel;
		}		
	}
	public int getBrake(){
		synchronized (mBrake) {
			return mBrake;
		}		
	}		
	public int getGear(){
		synchronized (mGear) {
			return mGear;
		}		
	}
	public int getABS(){
		synchronized (mABS) {
			return mABS;
		}		
	}	
	
	public boolean shouldPropagateRMP(float rmp) {
        synchronized (mRMP) {
            if (SystemClock.uptimeMillis() - mLastRMPSet < COALESCE_TIME_MS) {
                return false;
            }
            mRMP = rmp;
        }
        return true;
    }
    
    public boolean shouldPropagateSpeed(float speed) {
        synchronized (mSpeed) {
            if (SystemClock.uptimeMillis() - mLastSpeedSet < COALESCE_TIME_MS) {
                return false;
            }
            mSpeed = speed;
        }
        return true;
    }
    
    public boolean shouldPropagateOdo(float odo) {
        synchronized (mSpeed) {
            if (SystemClock.uptimeMillis() - mLastOdoSet < COALESCE_TIME_MS) {
                return false;
            }
            mOdo = odo;
        }
        return true;
    }
    
    public boolean shouldPropagateFuel(int fuel) {
        synchronized (mFuel) {
            if (SystemClock.uptimeMillis() - mLastFuelSet < COALESCE_TIME_MS) {
                return false;
            }
            mFuel = fuel;
        }
        return true;
    }
    
    public boolean shouldPropagateGear(int gear) {
        synchronized (mGear) {
            if (SystemClock.uptimeMillis() - mLastGearSet < COALESCE_TIME_MS) {
                return false;
            }
            mGear = gear;
        }
        return true;
    }
    
    public boolean shouldPropagateBrake(int brake) {
        synchronized (mBrake) {
            if (SystemClock.uptimeMillis() - mLastBrakeSet < COALESCE_TIME_MS) {
                return false;
            }
            mBrake = brake;
        }
        return true;
    }
    
    public boolean shouldPropagateABS(int abs) {
        synchronized (mABS) {
            if (SystemClock.uptimeMillis() - mLastABSSet < COALESCE_TIME_MS) {
                return false;
            }
            mABS = abs;
        }
        return true;
    }
}
