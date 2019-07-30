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

public class GearPosition extends LinearLayout{
	
	public static final int POSITION_P = 0;
    public static final int POSITION_R = 1;
    public static final int POSITION_N = 2;
    public static final int POSITION_D = 3;
    
    @IntDef({POSITION_P , POSITION_R,
            POSITION_N, POSITION_D})
    public @interface Gear {}
	
	private static final float UNSELECTED_BUTTON_ALPHA = 0.5f;
    private static final float SELECTED_BUTTON_ALPHA = 1.0f;

	private ImageView ButtonP;
	private ImageView ButtonR;
	private ImageView ButtonN;
	private ImageView ButtonD;
	
    private final Map<ImageView, Pair<Drawable, Drawable>> mMap = new HashMap<>();
    private final Map<ImageView, Integer> mControlMap = new HashMap<>();
    
    public GearPosition(Context context) {
        super(context);
        init();
    }

    public GearPosition(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GearPosition(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    
    private void init() {
        inflate(getContext(), R.layout.gear_position, this);
    }
    
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Resources res = getResources();

        setOrientation(VERTICAL);

		ButtonP = (ImageView) findViewById(R.id.p_on);
        ButtonR = (ImageView) findViewById(R.id.r_off);
        ButtonN = (ImageView) findViewById(R.id.n_off);
        ButtonD = (ImageView) findViewById(R.id.d_off);

        Drawable directionOn1 = res.getDrawable(R.drawable.p_on);
        Drawable directionOn2 = res.getDrawable(R.drawable.r_on);
		Drawable directionOn3 = res.getDrawable(R.drawable.n_on);
		Drawable directionOn4 = res.getDrawable(R.drawable.d_on);

        Drawable directionOff1 = res.getDrawable(R.drawable.p_off);
        Drawable directionOff2 = res.getDrawable(R.drawable.r_off);
		Drawable directionOff3 = res.getDrawable(R.drawable.n_off);
		Drawable directionOff4 = res.getDrawable(R.drawable.d_off);

        mMap.put(ButtonP, new Pair(directionOn1, directionOff1));
        mMap.put(ButtonR, new Pair(directionOn2, directionOff2));
		mMap.put(ButtonN, new Pair(directionOn3, directionOff3));
		mMap.put(ButtonD, new Pair(directionOn4, directionOff4));

        mControlMap.put(ButtonP, POSITION_P);
        mControlMap.put(ButtonR, POSITION_R);
		mControlMap.put(ButtonN, POSITION_N);
		mControlMap.put(ButtonD, POSITION_D);

        
    }
    
    public void setGearPosition(int GearV){
		switch(GearV){
			case CarSensorEvent.GEAR_REVERSE:
				ButtonP.setImageDrawable(mMap.get(ButtonP).second);
				ButtonP.setAlpha(UNSELECTED_BUTTON_ALPHA);
				ButtonR.setImageDrawable(mMap.get(ButtonR).first);
				ButtonR.setAlpha(SELECTED_BUTTON_ALPHA);
				ButtonN.setImageDrawable(mMap.get(ButtonN).second);
				ButtonN.setAlpha(UNSELECTED_BUTTON_ALPHA);
				ButtonD.setImageDrawable(mMap.get(ButtonD).second);
				ButtonD.setAlpha(UNSELECTED_BUTTON_ALPHA);
				break;
			case CarSensorEvent.GEAR_NEUTRAL:
				ButtonP.setImageDrawable(mMap.get(ButtonP).second);
				ButtonP.setAlpha(UNSELECTED_BUTTON_ALPHA);
				ButtonR.setImageDrawable(mMap.get(ButtonR).second);
				ButtonR.setAlpha(UNSELECTED_BUTTON_ALPHA);
				ButtonN.setImageDrawable(mMap.get(ButtonN).first);
				ButtonN.setAlpha(SELECTED_BUTTON_ALPHA);
				ButtonD.setImageDrawable(mMap.get(ButtonD).second);
				ButtonD.setAlpha(UNSELECTED_BUTTON_ALPHA);
				break;
			case CarSensorEvent.GEAR_DRIVE:	
				ButtonP.setImageDrawable(mMap.get(ButtonP).second);
				ButtonP.setAlpha(UNSELECTED_BUTTON_ALPHA);
				ButtonR.setImageDrawable(mMap.get(ButtonR).second);
				ButtonR.setAlpha(UNSELECTED_BUTTON_ALPHA);
				ButtonN.setImageDrawable(mMap.get(ButtonN).second);
				ButtonN.setAlpha(UNSELECTED_BUTTON_ALPHA);
				ButtonD.setImageDrawable(mMap.get(ButtonD).first);
				ButtonD.setAlpha(SELECTED_BUTTON_ALPHA);
				break;
			case CarSensorEvent.GEAR_PARK:	
				ButtonP.setImageDrawable(mMap.get(ButtonP).first);
				ButtonP.setAlpha(SELECTED_BUTTON_ALPHA);
				ButtonR.setImageDrawable(mMap.get(ButtonR).second);
				ButtonR.setAlpha(UNSELECTED_BUTTON_ALPHA);
				ButtonN.setImageDrawable(mMap.get(ButtonN).second);
				ButtonN.setAlpha(UNSELECTED_BUTTON_ALPHA);
				ButtonD.setImageDrawable(mMap.get(ButtonD).second);
				ButtonD.setAlpha(UNSELECTED_BUTTON_ALPHA);
				break;
		}
	}
	
	public void clearGearPosition(){
		ButtonP.setImageDrawable(mMap.get(ButtonP).second);
		ButtonP.setAlpha(UNSELECTED_BUTTON_ALPHA);
		ButtonR.setImageDrawable(mMap.get(ButtonR).second);
		ButtonR.setAlpha(UNSELECTED_BUTTON_ALPHA);
		ButtonN.setImageDrawable(mMap.get(ButtonN).second);
		ButtonN.setAlpha(UNSELECTED_BUTTON_ALPHA);
		ButtonD.setImageDrawable(mMap.get(ButtonD).second);
		ButtonD.setAlpha(UNSELECTED_BUTTON_ALPHA);	
	}	
}	
