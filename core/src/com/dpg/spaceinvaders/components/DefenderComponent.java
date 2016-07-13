package com.dpg.spaceinvaders.components;

import com.badlogic.ashley.core.Component;

public class DefenderComponent implements Component{
	public static final int STATE_NONE = 0;
	public static final int STATE_MOVE_LEFT = 1;
	public static final int STATE_MOVE_RIGHT = 2;
	
	public static final int STATE_HIT = 3;
	
	
	public static final float WIDTH = 2.0f;
	public static final float HEIGHT = 1.5f;

	public static final float MOVE_VELOCITY = 4.5f;

	/* Lower value is faster rate of fire */
	public static final float RATE_OF_FIRE = 0.25f;

}
