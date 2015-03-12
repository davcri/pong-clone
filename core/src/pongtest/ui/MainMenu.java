package pongtest.ui;

import pongtest.Main;
import pongtest.Main.Screens;
import pongtest.utility.Tween;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainMenu implements Screen, InputProcessor {
	
	private Stage stage = new Stage();
	private TextureAtlas atlas;
	private Table table;
	private TextButton singlePlayerButton, multiplayerButton;
	private TextField ipInput;
	private Skin skin;
	private BitmapFont mainFont;
	private Action showMultiplayerMenu, hideMultiplayerMenu;
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		
		initializeActions();
		createUI();
		
		stage.addActor(ipInput);
		stage.addActor(table);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();
	}
	
	private void createUI()
	{
		atlas = new TextureAtlas(Gdx.files.internal("ui/img/buttons.pack"));
		skin = new Skin(atlas);
		mainFont = new BitmapFont(Gdx.files.internal("ui/font/kongfont.fnt"));
		mainFont.setScale(0.4f); // maybe we should reduce 
								 // the export font size ?
		
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.getDrawable("button");
		textButtonStyle.down = skin.getDrawable("button_afa");
		textButtonStyle.font = mainFont;
		//textButtonStyle.pressedOffsetX = 1;
		//textButtonStyle.pressedOffsetY = -1;
		
		singlePlayerButton = new TextButton("Single Player", textButtonStyle);		
		singlePlayerButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				Main.changeScreen(Screens.PONGGAME);
			}
		});
		
		multiplayerButton = new TextButton("Multiplayer", textButtonStyle);
		
		TextFieldStyle style = new TextFieldStyle();
		style.font = mainFont;
		style.fontColor = new Color(1, 1, 0, 1);
		style.background = skin.getDrawable("button");
		
		ipInput = new TextField("",style);
		ipInput.setTextFieldFilter(new TextFieldFilter() {
			@Override
			public boolean acceptChar(TextField textField, char c) {
				boolean accepted = false;
				
				if (! Character.isAlphabetic(c)) {
					accepted = true;
				}
							
				return accepted;
			}
		});
		
		ipInput.setPosition(300, 200);
		ipInput.setVisible(false);
		
		multiplayerButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				Gdx.app.log("button", "is visible: " + ipInput.isVisible());
				
				if(!ipInput.isVisible()) {
					stage.addAction(showMultiplayerMenu);
				}
				else {
					stage.addAction(hideMultiplayerMenu);
				}
			}
		});
				
		table = new Table();
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		table.add(singlePlayerButton);
		table.row();
		table.add(multiplayerButton);
		
		// this is needed to set the size of buttons that are inside a table
		for (Cell cell : table.getCells())
		{
			cell.minWidth(180);
		}
	}
	
	private void initializeActions() {
		
		final Tween menuTweenShow = new Tween(Interpolation.exp5Out, 0, -200, 1.0f);
		final Tween menuTweenHide = new Tween(Interpolation.exp5Out, -200, 0, 1.0f);
		
		showMultiplayerMenu = new Action() {
			@Override
			public boolean act(float delta) {
				boolean done = false;
				
				if (table.getX() >= -150)
				{
					menuTweenShow.update(delta);
					table.setX(menuTweenShow.getValue());
				}
				else
				{
					ipInput.setVisible(true);
					done = true;
				}
												
				return done;
			}
		};
		
		hideMultiplayerMenu = new Action() {
			@Override
			public boolean act(float delta) {
				boolean done = false;
				
				Gdx.app.log("initial pos", table.getX()+"");
				
				if (table.getX() <= 0)
				{
					menuTweenHide.update(delta);
					table.setX(menuTweenHide.getValue());
				}
				else
				{
					ipInput.setVisible(false);
					done = true;	
				}
												
				return done;
			}
		};
	}
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		stage.dispose();
		mainFont.dispose();
		atlas.dispose();
		skin.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}	
}
