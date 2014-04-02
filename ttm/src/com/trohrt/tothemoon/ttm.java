package com.trohrt.tothemoon;

import com.badlogic.gdx.Game;
import com.trohrt.tothemoon.screens.Splash;

public class ttm extends Game {

	public static final String TITLE = "To The Moon!";
	public static final String VERSION = "0.0.1";

	@Override
	public void create() {
		
		// Initialize splash screen
		setScreen(new Splash());
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

}
