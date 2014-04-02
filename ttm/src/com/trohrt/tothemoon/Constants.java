package com.trohrt.tothemoon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Constants {

	public static Sprite bigStar, medStar, smStar;

	public static Music menuMusic, inGameMusic, gameOverMusic;

	public static Skin menuSkin;

	public static void loadMenu() {

		menuSkin = new Skin(Gdx.files.internal("data/ui/menuSkin.json"),
				new TextureAtlas("data/ui/atlas.pack"));

		bigStar = new Sprite(new Texture("data/ui/star_64.png"));

		medStar = new Sprite(new Texture("data/ui/star_32.png"));

		smStar = new Sprite(new Texture("data/ui/star_16.png"));
		
		menuMusic = Gdx.audio.newMusic(Gdx.files
				.internal("data/sfx/main_menu.wav"));
	}
}
