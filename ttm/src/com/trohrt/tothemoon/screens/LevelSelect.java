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
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.trohrt.tothemoon.Constants;

public class LevelSelect implements Screen {
	
	private SpriteBatch batch;
	private Stage stage;
	private Table table;
	private Label title;

	@Override
	public void render(float delta) {
		
		// GL Stuff
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Draw stars, temporary; will have animation in future
		batch.begin();
		batch.draw(Constants.bigStar, 400, 200);
		batch.draw(Constants.bigStar, 450, 500);
		batch.draw(Constants.bigStar, 400, 700);
		batch.draw(Constants.medStar, 400, 700);
		batch.draw(Constants.medStar, 700, 600);
		batch.draw(Constants.medStar, 300, 650);
		batch.draw(Constants.smStar, 200, 400);
		batch.draw(Constants.smStar, 300, 700);
		batch.draw(Constants.smStar, 700, 700);
		batch.draw(Constants.smStar, 500, 400);
		batch.end();

		// Handle and draw the stage
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
		// Initialize batch, sprites, and stage
		batch = new SpriteBatch();
		
		stage = new Stage();

		// Allow input to stage
		Gdx.input.setInputProcessor(stage);

		table = new Table(Constants.menuSkin);
		table.setFillParent(true);

		// Difficulties
		final List list = new List(new String[] { "TUTORIAL", "EASY", "HARD" },
				Constants.menuSkin);

		// Scroll
		ScrollPane scrollPane = new ScrollPane(list, Constants.menuSkin);

		// Play button
		TextButton play = new TextButton("PLAY", Constants.menuSkin, "big");
		play.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				String selection = list.getSelection();
				//ttm.menuMusic.stop();
				System.out.println(selection);
				//((Game) Gdx.app.getApplicationListener()).setScreen(new Play());
			}

		});
		play.pad(15);

		// Back button
		TextButton back = new TextButton("BACK", Constants.menuSkin, "small");
		back.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				stage.addAction(sequence(moveTo(0, stage.getHeight(), .5f),
						run(new Runnable() {

							@Override
							public void run() {
								((Game) Gdx.app.getApplicationListener())
										.setScreen(new MainMenu());
							}

						})));
			}
		});
		back.pad(10);
		
		title = new Label("SELECT LEVEL", Constants.menuSkin, "big");
		title.setFontScale(3);

		// Add stuff to table
		table.add(new Label("SELECT LEVEL", Constants.menuSkin, "big")).colspan(3).expandX()
				.spaceBottom(50).row();
		table.add(scrollPane).uniformX().expandY().top().left();
		table.add(play).uniformX();
		table.add(back).uniformX().bottom().right();

		stage.addActor(table);

		// Transition animation
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
