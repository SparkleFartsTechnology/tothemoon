package com.trohrt.tothemoon.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.trohrt.tothemoon.Constants;
import com.trohrt.tothemoon.ttm;
import com.trohrt.tothemoon.tween.ActorAccessor;
import com.trohrt.tothemoon.tween.SpriteAccessor;

public class MainMenu implements Screen {

	private SpriteBatch batch;
	private Stage stage;
	private Table table;
	private TweenManager tweenManager;

	@Override
	public void render(float delta) {
		
		// GL stuff
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Draw stars, temporary; will have animation in future
		batch.begin();
		batch.draw(Constants.bigStar, 200, 400);
		batch.draw(Constants.bigStar, 950, 50);
		batch.draw(Constants.bigStar, 850, 500);
		batch.draw(Constants.medStar, 900, 250);
		batch.draw(Constants.medStar, 700, 600);
		batch.draw(Constants.medStar, 300, 650);
		batch.draw(Constants.smStar, 800, 100);
		batch.draw(Constants.smStar, 100, 100);
		batch.draw(Constants.smStar, 600, 400);
		batch.draw(Constants.smStar, 50, 600);
		batch.end();

		// Handle and draw the stage
		stage.act(delta);
		stage.draw();

		// Update tween for animation
		tweenManager.update(delta);

	}

	@Override
	public void resize(int width, int height) {
		// Mainly for desktop; no image distortion/stretch on resize
		stage.setViewport(width, height, false);
		table.invalidateHierarchy();
	}

	@Override
	public void show() {
		
		// Initialize batch, sprites, and stage
		batch = new SpriteBatch();

		stage = new Stage();

		// Allow input to stage
		Gdx.input.setInputProcessor(stage);

		table = new Table(Constants.menuSkin);
		table.setFillParent(true);

		// Title
		Label heading = new Label(ttm.TITLE, Constants.menuSkin, "big");
		heading.setFontScale(2);

		// Play button
		TextButton buttonPlay = new TextButton("PLAY", Constants.menuSkin, "big");
		buttonPlay.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				stage.addAction(sequence(moveTo(0, -stage.getHeight(), .5f),
						run(new Runnable() {

							@Override
							public void run() {
								((Game) Gdx.app.getApplicationListener())
										.setScreen(new LevelSelect());
							}
						})));
			}
		});

		buttonPlay.pad(15);

		// Settings button
		TextButton buttonSettings = new TextButton("SETTINGS", Constants.menuSkin);
		buttonSettings.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				stage.addAction(sequence(moveTo(0, -stage.getHeight(), .5f),
						run(new Runnable() {

							@Override
							public void run() {
								((Game) Gdx.app.getApplicationListener())
										.setScreen(new Settings());
							}
						})));
			}
		});
		buttonSettings.pad(15);

		// Exit button
		TextButton buttonExit = new TextButton("EXIT", Constants.menuSkin, "big");
		buttonExit.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				Timeline.createParallel()
						.beginParallel()
						.push(Tween.to(table, ActorAccessor.ALPHA, .75f)
								.target(0))
						.push(Tween.to(table, ActorAccessor.Y, .75f)
								.target(table.getY() - 50)
								.setCallback(new TweenCallback() {

									@Override
									public void onEvent(int type,
											BaseTween<?> source) {
										Gdx.app.exit();
									}
								})).end().start(tweenManager);
			}
		});
		buttonExit.pad(15);

		// Add stuff to the table
		table.add(heading).spaceBottom(100).row();
		table.add(buttonPlay).spaceBottom(15).row();
		table.add(buttonSettings).spaceBottom(15).row();
		table.add(buttonExit);
		stage.addActor(table);

		tweenManager = new TweenManager();
		Tween.registerAccessor(Actor.class, new ActorAccessor());
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		
		// Tween stuff for button fade in animation
		Tween.set(Constants.bigStar, SpriteAccessor.ALPHA).target(0).start(tweenManager);
		Tween.to(Constants.bigStar, SpriteAccessor.ALPHA, 1F).target(1).repeatYoyo(1, 1).delay(5)
				.start(tweenManager);
		Tween.set(Constants.medStar, SpriteAccessor.ALPHA).target(0).start(tweenManager);
		Tween.to(Constants.medStar, SpriteAccessor.ALPHA, 2F).target(1).repeatYoyo(1, 1).delay(4)
		.start(tweenManager);
		Tween.set(Constants.smStar, SpriteAccessor.ALPHA).target(0).start(tweenManager);
		Tween.to(Constants.smStar, SpriteAccessor.ALPHA, 3F).target(1).repeatYoyo(1, 1).delay(3)
		.start(tweenManager);

		// Tween stuff for flashing title colors
		Timeline.createSequence()
				.beginSequence()
				.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(0, 0, 1))
				.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(0, 1, 0))
				.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 0, 0))
				.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 1, 0))
				.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(0, 1, 1))
				.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 0, 1))
				.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 1, 1))
				.end().repeat(Tween.INFINITY, 0).start(tweenManager);

		// Tween stuff for button slide animation
		Timeline.createSequence()
				.beginSequence()
				.push(Tween.set(buttonPlay, ActorAccessor.ALPHA).target(0))
				.push(Tween.set(buttonSettings, ActorAccessor.ALPHA).target(0))
				.push(Tween.set(buttonExit, ActorAccessor.ALPHA).target(0))
				.push(Tween.from(heading, ActorAccessor.ALPHA, .25f).target(0))
				.push(Tween.to(buttonPlay, ActorAccessor.ALPHA, .25f).target(1))
				.push(Tween.to(buttonSettings, ActorAccessor.ALPHA, .25f)
						.target(1))
				.push(Tween.to(buttonExit, ActorAccessor.ALPHA, .25f).target(1))
				.end().start(tweenManager);

		// Tween stuff for table animation
		Tween.from(table, ActorAccessor.ALPHA, .75f).target(0)
				.start(tweenManager);
		Tween.from(table, ActorAccessor.Y, .75f)
				.target(Gdx.graphics.getHeight() / 8).start(tweenManager);

		// Update tween
		tweenManager.update(Gdx.graphics.getDeltaTime());
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
		stage.dispose();
	}
}
