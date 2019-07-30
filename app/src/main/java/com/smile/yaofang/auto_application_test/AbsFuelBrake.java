package com.smile.yaofang.auto_application_test;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.animation.ObjectAnimator;
import android.view.animation.Animation;
import android.support.car.hardware.CarSensorEvent;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class AbsFuelBrake extends LinearLayout{
	
	public static final int POSITION_1 = 0;
    public static final int POSITION_2 = 1;
    public static final int POSITION_3 = 2;
	
	private static final float UNSELECTED_BUTTON_ALPHA = 0.5f;
    private static final float SELECTED_BUTTON_ALPHA = 1.0f;

	private ImageView Button1;
	private ImageView Button2;
	private ImageView Button3;
	
    private final Map<ImageView, Pair<Drawable, Drawable>> mMap = new HashMap<>();
    private final Map<ImageView, Integer> mControlMap = new HashMap<>();
    
    public AbsFuelBrake(Context context) {
        super(context);
        init();
    }

    public AbsFuelBrake(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AbsFuelBrake(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    
    private void init() {
        inflate(getContext(), R.layout.abs_fuel_brake, this);
    }
    
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Resources res = getResources();

        setOrientation(HORIZONTAL);

		Button1 = (ImageView) findViewById(R.id.abs_off);
        Button2 = (ImageView) findViewById(R.id.fuel_off);
        Button3 = (ImageView) findViewById(R.id.brake_on);

        Drawable directionOn1 = res.getDrawable(R.drawable.abs_on);
        Drawable directionOn2 = res.getDrawable(R.drawable.fuel_on);
		Drawable directionOn3 = res.getDrawable(R.drawable.brake_on);

        Drawable directionOff1 = res.getDrawable(R.drawable.abs_off);
        Drawable directionOff2 = res.getDrawable(R.drawable.fuel_off);
		Drawable directionOff3 = res.getDrawable(R.drawable.brake_off);

        mMap.put(Button1, new Pair(directionOn1, directionOff1));
        mMap.put(Button2, new Pair(directionOn2, directionOff2));
		mMap.put(Button3, new Pair(directionOn3, directionOff3));

        mControlMap.put(Button1, POSITION_1);
        mControlMap.put(Button2, POSITION_2);
		mControlMap.put(Button3, POSITION_3);
        
    }
    
    public void setAbs(int absV){
		if(absV == 1){			
			Button1.setImageDrawable(mMap.get(Button1).first);
			Button1.setAlpha(SELECTED_BUTTON_ALPHA);
		}
		if(absV == 0){
			Button1.setImageDrawable(mMap.get(Button1).second);
			Button1.setAlpha(SELECTED_BUTTON_ALPHA);
		}				
	}
    public void	setFuel(int levelV){
		if(levelV == 1){		
			Button2.setImageDrawable(mMap.get(Button2).first);
			ObjectAnimator animationFuel = ObjectAnimator.ofFloat(Button2, "alpha", 1f, 0f);
			Button2.setTag(animationFuel);
			animationFuel.setRepeatMode(ObjectAnimator.RESTART);
			animationFuel.setRepeatCount(Animation.INFINITE);
			animationFuel.setDuration(2000);
			animationFuel.start();
		}
		if(levelV == 0){
			ObjectAnimator animator = (ObjectAnimator) Button2.getTag();
			if (animator !=null){
				animator.cancel();
			}	
			Button2.setImageDrawable(mMap.get(Button2).second);
			Button2.setAlpha(SELECTED_BUTTON_ALPHA);	
		}		
	}
	public void setBrake(int brakeV){
		if(brakeV == 1){			
			Button3.setImageDrawable(mMap.get(Button3).first);
			Button3.setAlpha(SELECTED_BUTTON_ALPHA);
		}
		if(brakeV == 0){
			Button3.setImageDrawable(mMap.get(Button3).second);
			Button3.setAlpha(SELECTED_BUTTON_ALPHA);		
		}	
	}
	
}	
