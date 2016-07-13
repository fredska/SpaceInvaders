/*******************************************************************************
 * Copyright 2014 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.dpg.spaceinvaders.systems;

import java.util.Random;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.dpg.spaceinvaders.World;
import com.dpg.spaceinvaders.components.*;
import javafx.geometry.Bounds;

public class CollisionSystem extends EntitySystem {
	private ComponentMapper<BoundsComponent> bm;
	private ComponentMapper<MovementComponent> mm;
	private ComponentMapper<StateComponent> sm;
	private ComponentMapper<TransformComponent> tm;


	public static interface CollisionListener {		
		public void jump ();
		public void highJump ();
		public void hit ();
		public void coin ();
	}

	private Engine engine;
	private World world;
	private CollisionListener listener;
	private Random rand = new Random();

	private ImmutableArray<Entity> aliens;
	private ImmutableArray<Entity> missiles;
	
	public CollisionSystem(World world, CollisionListener listener) {
		this.world = world;
		this.listener = listener;
		
		bm = ComponentMapper.getFor(BoundsComponent.class);
		mm = ComponentMapper.getFor(MovementComponent.class);
		sm = ComponentMapper.getFor(StateComponent.class);
		tm = ComponentMapper.getFor(TransformComponent.class);
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		this.engine = engine;

		aliens = engine.getEntitiesFor(Family.all(AlienComponent.class, BoundsComponent.class,MovementComponent.class, TransformComponent.class, StateComponent.class).get());
		missiles = engine.getEntitiesFor(Family.all(MissileComponent.class, BoundsComponent.class, MovementComponent.class, TransformComponent.class, StateComponent.class).get());
	}
	
	@Override
	public void update(float deltaTime) {
		for(Entity missile : missiles){
			StateComponent mState = missile.getComponent(StateComponent.class);
			BoundsComponent mBounds = missile.getComponent(BoundsComponent.class);
			//Missile entities should be off screen in 10 sec guaranteed
			//TODO: Setup math to calculate time till offscreen + 50% overhead
			if(mState.time >= 2f){
				getEngine().removeEntity(missile);
			}
			if(mState.get() == MissileComponent.STATE_MOVING)
			{
				for(Entity alien : aliens){
					BoundsComponent aBounds = alien.getComponent(BoundsComponent.class);
					StateComponent aState = alien.getComponent(StateComponent.class);
					if(aState.get() == AlienComponent.STATE_MOVING && aBounds.bounds.overlaps(mBounds.bounds)){

						aState.set(AlienComponent.STATE_HIT);
						getEngine().removeEntity(missile);
						break;
					}
				}
			}
		}
	}
}
