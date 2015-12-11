package com.dpg.spaceinvaders.components;

import com.badlogic.ashley.core.Component;

public class AlienComponent implements Component{
	
	public static final int STATE_MOVING = 0;
	public static final int STATE_LAND = 1;
	public static final int STATE_HIT = 2;
	
	// False for moving to the left; true for right
	public boolean direction = false; 
	
	public static final float WIDTH = 1f;
	public static final float HEIGHT = 1f;

	public static final float MOVE_VELOCITY = 3f;

	
}
