package com.trohrt.tothemoon.screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.trohrt.tothemoon.Constants;
import com.trohrt.tothemoon.tween.SpriteAccessor;

public class Splash implements Screen {

	private SpriteBatch batch;
	private TweenManager tweenManager;
	private Music splashMusic;
	private Sprite splash;

	@Override
	public void render(float delta) {

		// GL stuff
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Draw splash centered
		batch.begin();
		batch.setColor(splash.getColor());
		batch.draw(splash,
				Gdx.graphics.getWidth() / 2f - splash.getRegionWidth() / 2f,
				Gdx.graphics.getHeight() / 2f - splash.getRegionHeight() / 2f,
				splash.getRegionWidth(), splash.getRegionHeight());
		batch.end();

		// Update tween
		tweenManager.update(delta);
	}

	@Override
	public void resize(int width, int height) {

		// For desktop; no distorted resize
		batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
	}

	@Override
	public void show() {

		// Create + play music
		splashMusic = Gdx.audio.newMusic(Gdx.files
				.internal("data/sfx/splash.wav"));
		splashMusic.play();

		// Init tween stuff
		tweenManager = new TweenManager();
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());

		// Batch + sprites
		batch = new SpriteBatch();

		splash = new Sprite(new Texture("data/ui/splash.png"));

		// Fade in, fade out
		Tween.set(splash, SpriteAccessor.ALPHA).target(0).start(tweenManager);
		Tween.to(splash, SpriteAccessor.ALPHA, 2.5F).target(1).repeatYoyo(1, 1)
				.setCallback(new TweenCallback() {
					@Override
					public void onEvent(int type, BaseTween<?> source) {

						// Load menu assets into memory
						Constants.loadMenu();

						((Game) Gdx.app.getApplicationListener())
								.setScreen(new MainMenu());

						// Start menu music
						Constants.menuMusic.setLooping(true);
						Constants.menuMusic.play();

					}
				}).start(tweenManager);

		tweenManager.update(Float.MIN_VALUE);

	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		batch.dispose();
		splashMusic.dispose();
		splash.getTexture().dispose();
	}

}