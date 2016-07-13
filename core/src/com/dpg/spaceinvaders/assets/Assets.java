package com.dpg.spaceinvaders.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dpg.spaceinvaders.components.AlienComponent;
import com.dpg.spaceinvaders.components.DefenderComponent;
import com.dpg.spaceinvaders.components.MissileComponent;
import com.dpg.spaceinvaders.systems.RenderingSystem;

public class Assets {

	public static Texture aExplosionTex;
	public static Animation alienExplosion;

	public static Texture background;
	public static TextureRegion backgroundRegion;

	public static Texture static_Defender;
	public static Texture static_Alien;
	public static Texture static_Bullet;
	public static Texture loadTexture(String file) {
		return new Texture(Gdx.files.internal(file));
	}

	public static void load() {
		Pixmap defenderPix = new Pixmap((int)(DefenderComponent.WIDTH / RenderingSystem.PIXELS_TO_METRES),
				(int)(DefenderComponent.HEIGHT / RenderingSystem.PIXELS_TO_METRES), Format.RGB888);
		defenderPix.setColor(Color.BLUE);
		defenderPix.drawRectangle(0, 0, (int)(DefenderComponent.WIDTH / RenderingSystem.PIXELS_TO_METRES),
				(int)(DefenderComponent.HEIGHT / RenderingSystem.PIXELS_TO_METRES));
		
		static_Defender = new Texture(defenderPix);

		Pixmap alienPix = new Pixmap((int)(AlienComponent.WIDTH / RenderingSystem.PIXELS_TO_METRES),
				(int)(AlienComponent.HEIGHT / RenderingSystem.PIXELS_TO_METRES), Format.RGB888);
		alienPix.setColor(Color.RED);
		alienPix.drawRectangle(0, 0, (int)(AlienComponent.WIDTH / RenderingSystem.PIXELS_TO_METRES),
				(int)(AlienComponent.HEIGHT / RenderingSystem.PIXELS_TO_METRES));
		static_Alien = new Texture(alienPix);
		
		Pixmap bulletPix = new Pixmap((int)(MissileComponent.WIDTH / RenderingSystem.PIXELS_TO_METRES),
				(int)(MissileComponent.HEIGHT / RenderingSystem.PIXELS_TO_METRES), Format.RGB888);
		bulletPix.setColor(Color.CYAN);
		bulletPix.drawRectangle(0, 0, (int)(MissileComponent.WIDTH / RenderingSystem.PIXELS_TO_METRES),
				(int)(MissileComponent.HEIGHT / RenderingSystem.PIXELS_TO_METRES));
		static_Bullet = new Texture(bulletPix);

		aExplosionTex = loadTexture("task04a_explosion_transparent.png");

		TextureRegion[] alienExplosionTR = new TextureRegion[16];
		for(int y = 0; y < 4; y++){
			for(int x = 0; x < 4; x++){
				alienExplosionTR[x + (y*4)] = new TextureRegion(aExplosionTex, x * 64, y * 64, 64, 64);
			}
		}
		alienExplosion = new Animation(0.1f, alienExplosionTR);
		alienExplosion.setPlayMode(Animation.PlayMode.NORMAL);
	}
}
