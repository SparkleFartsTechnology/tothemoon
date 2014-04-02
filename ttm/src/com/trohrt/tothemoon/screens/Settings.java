package com.trohrt.tothemoon.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.trohrt.tothemoon.Constants;
import com.trohrt.tothemoon.ttm;

public class Settings implements Screen {
	
	private SpriteBatch batch;
	private Stage stage;
	private Table table;

	@Override
	public void render(float delta) {
		// GL stuff
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Draw stars, temporary; will have animation in future
		batch.begin();
		// TODO
		batch.end();
		
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// Mainly for desktop; no image distortion/stretch on resize
		stage.setViewport(width, height, false);
		table.invalidateHierarchy();
	}

	@Override
	public void show() {
		
		batch = new SpriteBatch();
		
		stage = new Stage();

		// Input to stage
		Gdx.input.setInputProcessor(stage);

		// Add skin to table and set table to fill screen
		table = new Table(Constants.menuSkin);
		table.setFillParent(true);

		// Back button
		final TextButton back = new TextButton("BACK", Constants.menuSkin);
		back.pad(10);

		// Input
		ClickListener buttonHandler = new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (event.getListenerActor() == back) {

					Gdx.app.getPreferences(ttm.TITLE).flush();

					Gdx.app.log(ttm.TITLE, "settings saved");

					stage.addAction(sequence(moveTo(0, stage.getHeight(), .5f),
							run(new Runnable() {

								@Override
								public void run() {
									((Game) Gdx.app.getApplicationListener())
											.setScreen(new MainMenu());
								}
							})));
				}
			}
		};

		// Add back to listener
		back.addListener(buttonHandler);

		// Add stuff to table
		table.add(new Label("SETTINGS", Constants.menuSkin, "big")).spaceBottom(50)
				.colspan(3).expandX().row();
		table.add();
		table.add().row();
		table.row();
		table.add(back).bottom().right();

		stage.addActor(table);

		// Tween transition
		stage.addAction(sequence(moveTo(0, stage.getHeight()),
				moveTo(0, 0, .5f)));
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
		stage.dispose();
	}

}
