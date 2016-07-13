package com.dpg.spaceinvaders;

import java.util.Random;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.dpg.spaceinvaders.assets.Assets;
import com.dpg.spaceinvaders.components.AlienComponent;
import com.dpg.spaceinvaders.components.BoundsComponent;
import com.dpg.spaceinvaders.components.CameraComponent;
import com.dpg.spaceinvaders.components.DefenderComponent;
import com.dpg.spaceinvaders.components.MovementComponent;
import com.dpg.spaceinvaders.components.StateComponent;
import com.dpg.spaceinvaders.components.TextureComponent;
import com.dpg.spaceinvaders.components.TransformComponent;
import com.dpg.spaceinvaders.systems.RenderingSystem;

public class World {

	public static final float WORLD_WIDTH = 10;
	public static final float WORLD_HEIGHT = 30;
	public static final int WORLD_STATE_RUNNING = 0;
	public static final int WORLD_STATE_NEXT_LEVEL = 1;
	public static final int WORLD_STATE_GAME_OVER = 2;
	
	public final Random rand;
	
	public float score;
	public int state;
	
	private Engine engine;
	
	public World(Engine engine){
		this.engine = engine;
		this.rand = new Random();
	}
	
	public void create(){
		Entity player = createPlayer();
		createCamera(player);
		
		for(int y = 0; y < 10; y++){
			for(int x = 0; x < 8; x++){
				createAlien(new Vector2(x + 1, 14f - (y*0.5f)), true);
			}
		}
		
		this.state = WORLD_STATE_RUNNING;
	}
	
	private void createCamera(Entity target) {
		Entity entity = new Entity();
		
		CameraComponent camera = new CameraComponent();
		camera.camera = engine.getSystem(RenderingSystem.class).getCamera();
		camera.target = target;
		
		entity.add(camera);
		
		engine.addEntity(entity);
	}
	
	private Entity createPlayer(){
		Entity entity = new Entity();
		
//		AnimationComponent animation = new AnimationComponent();
		DefenderComponent defender = new DefenderComponent();
		BoundsComponent bounds = new BoundsComponent();
		MovementComponent movement = new MovementComponent();
		TransformComponent position = new TransformComponent();
		StateComponent state = new StateComponent();
		TextureComponent texture = new TextureComponent();
		
		//Implement Animation here
		
		bounds.bounds.width = DefenderComponent.WIDTH;
		bounds.bounds.height = DefenderComponent.HEIGHT;
		
		position.pos.set(5.0f, 1.0f, 0f);
		
		texture.region = new TextureRegion(Assets.static_Defender);
		
		state.set(DefenderComponent.STATE_NONE);
		
//		entity.add(animation);
		entity.add(defender);
		entity.add(bounds);
		entity.add(movement);
		entity.add(position);
		entity.add(state);
		entity.add(texture);
		
		engine.addEntity(entity);
		
		return entity;
	}
	
	private Entity createAlien(Vector2 pos, boolean direction){
		Entity entity = new Entity();
		
//		AnimationComponent animation = new AnimationComponent();
		AlienComponent alien = new AlienComponent();
		BoundsComponent bounds = new BoundsComponent();
		MovementComponent movement = new MovementComponent();
		TransformComponent position = new TransformComponent();
		StateComponent state = new StateComponent();
		TextureComponent texture = new TextureComponent();
		
		//Implement Animation here
		
		alien.direction = direction;
		
		bounds.bounds.width = DefenderComponent.WIDTH;
		bounds.bounds.height = DefenderComponent.HEIGHT;
		
		position.pos.set(pos.x, pos.y, 0f);
		
		texture.region = new TextureRegion(Assets.static_Alien);
		
		state.set(DefenderComponent.STATE_NONE);
		
//		entity.add(animation);
		entity.add(alien);
		entity.add(bounds);
		entity.add(movement);
		entity.add(position);
		entity.add(state);
		entity.add(texture);
		
		engine.addEntity(entity);
		
		return entity;
	}


	
}
