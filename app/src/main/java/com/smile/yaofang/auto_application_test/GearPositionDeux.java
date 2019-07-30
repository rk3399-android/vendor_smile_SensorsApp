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
import android.support.car.hardware.CarSensorEvent;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class GearPositionDeux extends LinearLayout{
	
	public static final int POSITION_1 = 0;
    public static final int POSITION_2 = 1;
    public static final int POSITION_3 = 2;
    
    @IntDef({POSITION_1 , POSITION_2,
            POSITION_3})
    public @interface GearUn {}
	
	private static final float UNSELECTED_BUTTON_ALPHA = 0.5f;
    private static final float SELECTED_BUTTON_ALPHA = 1.0f;

	private ImageView Button1;
	private ImageView Button2;
	private ImageView Button3;
	
    private final Map<ImageView, Pair<Drawable, Drawable>> mMap = new HashMap<>();
    private final Map<ImageView, Integer> mControlMap = new HashMap<>();
    
    public GearPositionDeux(Context context) {
        super(context);
        init();
    }

    public GearPositionDeux(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GearPositionDeux(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    
    private void init() {
        inflate(getContext(), R.layout.gear_position_deux, this);
    }
    
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Resources res = getResources();

        setOrientation(VERTICAL);

		Button1 = (ImageView) findViewById(R.id.quatre_off);
        Button2 = (ImageView) findViewById(R.id.cinq_off);
        Button3 = (ImageView) findViewById(R.id.six_off);

        Drawable directionOn1 = res.getDrawable(R.drawable.quatre_on);
        Drawable directionOn2 = res.getDrawable(R.drawable.cinq_on);
		Drawable directionOn3 = res.getDrawable(R.drawable.six_on);

        Drawable directionOff1 = res.getDrawable(R.drawable.quatre_off);
        Drawable directionOff2 = res.getDrawable(R.drawable.cinq_off);
		Drawable directionOff3 = res.getDrawable(R.drawable.six_off);

        mMap.put(Button1, new Pair(directionOn1, directionOff1));
        mMap.put(Button2, new Pair(directionOn2, directionOff2));
		mMap.put(Button3, new Pair(directionOn3, directionOff3));

        mControlMap.put(Button1, POSITION_1);
        mControlMap.put(Button2, POSITION_2);
		mControlMap.put(Button3, POSITION_3);
		

        
    }
    
    public void setGearPosition(int GearV){
		switch(GearV){
			case CarSensorEvent.GEAR_FOURTH :
				Button1.setImageDrawable(mMap.get(Button1).first);
				Button1.setAlpha(SELECTED_BUTTON_ALPHA);
				Button2.setImageDrawable(mMap.get(Button2).second);
				Button2.setAlpha(UNSELECTED_BUTTON_ALPHA);
				Button3.setImageDrawable(mMap.get(Button3).second);
				Button3.setAlpha(UNSELECTED_BUTTON_ALPHA);
				break;
			case CarSensorEvent.GEAR_FIFTH:
				Button1.setImageDrawable(mMap.get(Button1).second);
				Button1.setAlpha(UNSELECTED_BUTTON_ALPHA);
				Button2.setImageDrawable(mMap.get(Button2).first);
				Button2.setAlpha(SELECTED_BUTTON_ALPHA);
				Button3.setImageDrawable(mMap.get(Button3).second);
				Button3.setAlpha(UNSELECTED_BUTTON_ALPHA);
				break;
			case CarSensorEvent.GEAR_SIXTH:	
				Button1.setImageDrawable(mMap.get(Button1).second);
				Button1.setAlpha(UNSELECTED_BUTTON_ALPHA);
				Button2.setImageDrawable(mMap.get(Button2).second);
				Button2.setAlpha(UNSELECTED_BUTTON_ALPHA);
				Button3.setImageDrawable(mMap.get(Button3).first);
				Button3.setAlpha(SELECTED_BUTTON_ALPHA);
				break;
		}
	}
	
	public void clearGearPositionDeux(){	
		Button1.setImageDrawable(mMap.get(Button1).second);
		Button1.setAlpha(UNSELECTED_BUTTON_ALPHA);
		Button2.setImageDrawable(mMap.get(Button2).second);
		Button2.setAlpha(UNSELECTED_BUTTON_ALPHA);
		Button3.setImageDrawable(mMap.get(Button3).second);
		Button3.setAlpha(UNSELECTED_BUTTON_ALPHA);
	}	
}	
