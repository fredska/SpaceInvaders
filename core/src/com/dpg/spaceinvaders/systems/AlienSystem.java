package com.dpg.spaceinvaders.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.dpg.spaceinvaders.World;
import com.dpg.spaceinvaders.components.AlienComponent;
import com.dpg.spaceinvaders.components.DefenderComponent;
import com.dpg.spaceinvaders.components.MovementComponent;
import com.dpg.spaceinvaders.components.StateComponent;
import com.dpg.spaceinvaders.components.TransformComponent;

public class AlienSystem extends IteratingSystem {

	private static final Family family = Family
			.all(AlienComponent.class, StateComponent.class, TransformComponent.class, MovementComponent.class).get();

//	private float accelX = 0.0f;
//	private World world;

	private ComponentMapper<AlienComponent> am;
	private ComponentMapper<StateComponent> sm;
	private ComponentMapper<TransformComponent> tm;
	private ComponentMapper<MovementComponent> mm;


	public AlienSystem(World world) {
		super(family);

//		this.world = world;
		am = ComponentMapper.getFor(AlienComponent.class);
		sm = ComponentMapper.getFor(StateComponent.class);
		tm = ComponentMapper.getFor(TransformComponent.class);
		mm = ComponentMapper.getFor(MovementComponent.class);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		TransformComponent t = tm.get(entity);
		StateComponent state = sm.get(entity);
		MovementComponent mov = mm.get(entity);
		AlienComponent alien = am.get(entity);

		alien.timeSinceBorderCollision += deltaTime;

		if(state.get() == AlienComponent.STATE_MOVING) {
			float direction = alien.direction ? 1 : -1;
			mov.velocity.x = direction * AlienComponent.MOVE_VELOCITY;
			final float AlienBoundLimit = AlienComponent.WIDTH;
			if ((alien.timeSinceBorderCollision >= 1 / AlienComponent.MOVE_VELOCITY)
					&& (t.pos.x - AlienBoundLimit < 0 || t.pos.x > World.WORLD_WIDTH - AlienBoundLimit)) {

				alien.direction = !alien.direction;
				t.pos.y -= 0.5f;
				alien.timeSinceBorderCollision = 0f;
			}
		}
		if(state.get() == AlienComponent.STATE_HIT){
			mov.velocity.x = 0f;
			alien.timeToDeathAnimFinish += deltaTime;
			if(alien.timeToDeathAnimFinish >= AlienComponent.DEATH_ANIMATION_LENGTH){
				getEngine().removeEntity(entity);
			}
		}

	}

}
