package com.smile.yaofang.auto_application_test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;

import android.content.Intent;
import android.content.ServiceConnection;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;
import android.os.Handler;
import android.os.IBinder;
import android.view.LayoutInflater;



public class MainActivity extends AppCompatActivity {

	private static final String TAG = "Activity";
	SensorController mSensorController;
	Panel mPanel;
	private View mContainer;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        mContainer = inflater.inflate(R.layout.activity_main, null);
        setContentView(mContainer);
        mSensorController = new SensorController();
		mPanel = new Panel(mContainer);
		Intent intent = new Intent(this, SensorController.class);
		
		if (!bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)) {
            Log.e(TAG, "Failed to connect to SensorController.");
        }
		
    }
    
    
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            mSensorController = ((SensorController.LocalBinder) service).getService();
            final Context context = MainActivity.this;

            final Runnable r = new Runnable() {
                @Override
                public void run() {
                    // Once the hvac controller has refreshed its values from the vehicle,
                    // bind all the values.
                    mPanel.updateSensorController(mSensorController);
                }
            };

           if (mSensorController != null) {
                mSensorController.requestRefresh(r, new Handler(context.getMainLooper()));
           }
        }

        @Override
        public void onServiceDisconnected(ComponentName className) {
            mSensorController = null;
            
            //TODO: b/29126575 reconnect to controller if it is restarted
        }
    };
}
