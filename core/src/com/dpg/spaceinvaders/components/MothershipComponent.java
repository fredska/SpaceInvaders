package com.dpg.spaceinvaders.components;

import com.badlogic.ashley.core.Component;

public class MothershipComponent implements Component {

	public static final int STATE_MOVING = 0;
	public static final int STATE_HIT = 1;
	
	public boolean direction = false;
	
	public static final float WIDTH = 1.5f;
	public static final float HEIGHT = 1.1f;

	public static final float MOVE_VELOCITY = 6f;
}
