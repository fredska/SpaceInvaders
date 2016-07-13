package com.dpg.spaceinvaders.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.dpg.spaceinvaders.SpaceInvadersGame;
import com.dpg.spaceinvaders.World;
import com.dpg.spaceinvaders.components.*;
import com.dpg.spaceinvaders.systems.AlienSystem;
import com.dpg.spaceinvaders.systems.AnimationSystem;
import com.dpg.spaceinvaders.systems.BoundsSystem;
import com.dpg.spaceinvaders.systems.CameraSystem;
import com.dpg.spaceinvaders.systems.CollisionSystem;
import com.dpg.spaceinvaders.systems.CollisionSystem.CollisionListener;
import com.dpg.spaceinvaders.systems.DefenderSystem;
import com.dpg.spaceinvaders.systems.MovementSystem;
import com.dpg.spaceinvaders.systems.RenderingSystem;
import com.dpg.spaceinvaders.systems.StateSystem;

public class MainGameScreen extends ScreenAdapter {

	static final int GAME_READY = 0;
	static final int GAME_RUNNING = 1;
	static final int GAME_PAUSED = 2;
	static final int GAME_LEVEL_END = 3;
	static final int GAME_OVER = 4;

	SpaceInvadersGame game;

	OrthographicCamera guiCam;
	Vector3 touchPoint;
	World world;
	CollisionListener collisionListener;

	int lastScore;
	String scoreString;

	Engine engine;

	private int state;

	public MainGameScreen(SpaceInvadersGame game) {
		this.game = game;

		state = GAME_RUNNING;
		guiCam = new OrthographicCamera(640, 480);
		guiCam.position.set(640 / 2, 480 / 2, 0);
		touchPoint = new Vector3();
		collisionListener = new CollisionListener() {

			@Override
			public void jump() {
			}

			@Override
			public void hit() {
			}

			@Override
			public void highJump() {
			}

			@Override
			public void coin() {
			}
		};

		engine = new Engine();
		world = new World(engine);

		engine.addSystem(new DefenderSystem(world));
		engine.addSystem(new AlienSystem(world));
		engine.addSystem(new CameraSystem());
		engine.addSystem(new MovementSystem());
		engine.addSystem(new BoundsSystem());
		engine.addSystem(new StateSystem());
		engine.addSystem(new AnimationSystem());
		engine.addSystem(new CollisionSystem(world, collisionListener));
		engine.addSystem(new RenderingSystem(game.batcher));

		world.create();

		lastScore = 0;
		scoreString = "SCORE: 0";

		resumeSystems();
	}

	public void update(float deltaTime) {
		if (deltaTime > 0.1f)
			deltaTime = 0.1f;

		engine.update(deltaTime);

		switch (state) {
		case GAME_READY:
			updateReady();
			break;
		case GAME_RUNNING:
			updateRunning(deltaTime);
			break;
		case GAME_PAUSED:
			// updatePaused();
			break;
		case GAME_LEVEL_END:
			// updateLevelEnd();
			break;
		case GAME_OVER:
			// updateGameOver();
			break;
		}
	}

	private void updateReady() {
		if (Gdx.input.justTouched()) {
			state = GAME_RUNNING;
			resumeSystems();
		}
	}

	private void updateRunning(float deltaTime) {
		// Implement Pause button check here
		/* See ashley-jumper -> GameScreen -> line 155 */

		ApplicationType appType = Gdx.app.getType();

		float accelX = 0.0f;
		boolean fireMissile = false;
		if (appType == ApplicationType.Android || appType == ApplicationType.iOS) {
			accelX = Gdx.input.getAccelerometerX();
			if(Gdx.input.isTouched()){
				fireMissile = true;
			}
		} else {
			if (Gdx.input.isKeyPressed(Keys.DPAD_LEFT)) {
				accelX = 5f;
			}
			if (Gdx.input.isKeyPressed(Keys.DPAD_RIGHT)) {
				accelX = -5f;
			}
			if(Gdx.input.isKeyPressed(Keys.F)){
				fireMissile = true;
			}
		}

		engine.getSystem(DefenderSystem.class).setAccelX(accelX);
		engine.getSystem(DefenderSystem.class).setIsFiring(fireMissile);

		//Check if all aliens are gone
		ImmutableArray<Entity> aliens = engine.getEntitiesFor(Family.all(AlienComponent.class, BoundsComponent.class,MovementComponent.class, TransformComponent.class, StateComponent.class).get());
		if(aliens.size() == 0){
			world.state = World.WORLD_STATE_GAME_OVER;
		}
		if (world.state == World.WORLD_STATE_GAME_OVER) {
			state = GAME_OVER;
			pauseSystems();
		}
	}

	private void drawUI() {
		guiCam.update();
		game.batcher.setProjectionMatrix(guiCam.combined);
		game.batcher.begin();
		switch (state) {
		case GAME_READY:
			// presentReady();
			break;
		case GAME_RUNNING:
			// presentRunning();
			break;
		case GAME_PAUSED:
			// presentPaused();
			break;
		case GAME_LEVEL_END:
			// presentLevelEnd();
			break;
		case GAME_OVER:
			// presentGameOver();
			break;
		}
		game.batcher.end();
	}

	private void pauseSystems() {
		// engine.getSystem(BobSystem.class).setProcessing(false);
		// engine.getSystem(SquirrelSystem.class).setProcessing(false);
		// engine.getSystem(PlatformSystem.class).setProcessing(false);
		// engine.getSystem(GravitySystem.class).setProcessing(false);
		engine.getSystem(MovementSystem.class).setProcessing(false);
		engine.getSystem(BoundsSystem.class).setProcessing(false);
		engine.getSystem(StateSystem.class).setProcessing(false);
		engine.getSystem(AnimationSystem.class).setProcessing(false);
		engine.getSystem(CollisionSystem.class).setProcessing(false);
		engine.getSystem(DefenderSystem.class).setProcessing(false);
		engine.getSystem(AlienSystem.class).setProcessing(false);
	}

	private void resumeSystems() {
		// engine.getSystem(BobSystem.class).setProcessing(true);
		// engine.getSystem(SquirrelSystem.class).setProcessing(true);
		// engine.getSystem(PlatformSystem.class).setProcessing(true);
		// engine.getSystem(GravitySystem.class).setProcessing(true);
		engine.getSystem(MovementSystem.class).setProcessing(true);
		engine.getSystem(BoundsSystem.class).setProcessing(true);
		engine.getSystem(StateSystem.class).setProcessing(true);
		engine.getSystem(AnimationSystem.class).setProcessing(true);
		engine.getSystem(CollisionSystem.class).setProcessing(true);
		engine.getSystem(DefenderSystem.class).setProcessing(true);
		engine.getSystem(AlienSystem.class).setProcessing(true);
	}

	@Override
	public void render(float delta) {
		update(delta);
		drawUI();
	}

}
