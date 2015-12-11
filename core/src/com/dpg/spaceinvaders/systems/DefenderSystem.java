package com.dpg.spaceinvaders.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.dpg.spaceinvaders.World;
import com.dpg.spaceinvaders.components.DefenderComponent;
import com.dpg.spaceinvaders.components.MovementComponent;
import com.dpg.spaceinvaders.components.StateComponent;
import com.dpg.spaceinvaders.components.TransformComponent;

public class DefenderSystem extends IteratingSystem{
	private static final Family family = Family.all(DefenderComponent.class,
			StateComponent.class,
			TransformComponent.class,
			MovementComponent.class).get();
	
	private float accelX = 0.0f;
	private World world;
	
	private ComponentMapper<DefenderComponent> dm;
	private ComponentMapper<StateComponent> sm;
	private ComponentMapper<TransformComponent> tm;
	private ComponentMapper<MovementComponent> mm;
	
	public DefenderSystem(World world){
		super(family);
		
		this.world = world;
		dm = ComponentMapper.getFor(DefenderComponent.class);
		sm = ComponentMapper.getFor(StateComponent.class);
		tm = ComponentMapper.getFor(TransformComponent.class);
		mm = ComponentMapper.getFor(MovementComponent.class);
	}
	
	public void setAccelX(float accelX){
		this.accelX = accelX;
	}
	
	@Override
	public void update(float deltaTime){
		super.update(deltaTime);
		accelX = 0.0f;
	}
	
	@Override
	public void processEntity(Entity entity, float delta){
		TransformComponent t = tm.get(entity);
		StateComponent state = sm.get(entity);
		MovementComponent mov = mm.get(entity);
		DefenderComponent def = dm.get(entity);
		
		if(state.get() == DefenderComponent.STATE_HIT){
			world.state = World.WORLD_STATE_GAME_OVER;
		}
		
		if(state.get() != DefenderComponent.STATE_HIT){
			mov.velocity.x = -accelX / 10.0f * DefenderComponent.MOVE_VELOCITY;
		}
		final float defenderBoundLimit = DefenderComponent.WIDTH / 2f;
		if(t.pos.x - defenderBoundLimit < 0){
			t.pos.x = defenderBoundLimit;
		}
		
		if(t.pos.x > World.WORLD_WIDTH - defenderBoundLimit){
			t.pos.x = World.WORLD_WIDTH - defenderBoundLimit;
		}
		
	}
}
