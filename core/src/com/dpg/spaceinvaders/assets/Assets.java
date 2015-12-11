package com.dpg.spaceinvaders.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dpg.spaceinvaders.components.AlienComponent;
import com.dpg.spaceinvaders.components.DefenderComponent;
import com.dpg.spaceinvaders.systems.RenderingSystem;

public class Assets {

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
		
		Pixmap bulletPix = new Pixmap(80, 50, Format.RGB888);
		bulletPix.setColor(Color.RED);
		bulletPix.drawRectangle(0, 0, 40, 30);
		static_Bullet = new Texture(bulletPix);
	}
}
