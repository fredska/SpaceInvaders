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

	private float accelX = 0.0f;
	private World world;

	private ComponentMapper<AlienComponent> am;
	private ComponentMapper<StateComponent> sm;
	private ComponentMapper<TransformComponent> tm;
	private ComponentMapper<MovementComponent> mm;

	private float timeSinceDirectionChange = 0.0f;

	public AlienSystem(World world) {
		super(family);

		this.world = world;
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

		timeSinceDirectionChange += deltaTime;

		if (state.get() != DefenderComponent.STATE_HIT) {
			float direction = alien.direction ? 1 : -1;
			mov.velocity.x = direction * AlienComponent.MOVE_VELOCITY;
		}

		final float AlienBoundLimit = AlienComponent.WIDTH;
		if ((timeSinceDirectionChange > 1.5f)
				&& (t.pos.x - AlienBoundLimit < 0 || t.pos.x > World.WORLD_WIDTH - AlienBoundLimit)) {
			alien.direction = !alien.direction;
			t.pos.y -= 0.5f;
			timeSinceDirectionChange = 0f;
		}

	}

}
