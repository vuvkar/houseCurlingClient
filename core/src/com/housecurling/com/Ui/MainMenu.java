package com.housecurling.com.Ui;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.housecurling.com.Constants;
import com.housecurling.com.HouseCurling;
import com.housecurling.com.Systems.PhysicSystem;

public class MainMenu implements Screen{

    private Stage stage;
    private TextureAtlas atlas;

    private Skin skin;
    private Table table;
    private TextButton buttonPlay;
    private BitmapFont white, black;
    private HouseCurling houseCurling;
    public Label gameOver;

    @Override
    public void show() {
        houseCurling.physicSystem.state=0;
        stage = new Stage();

        Gdx.input.setInputProcessor(stage);

        atlas = new TextureAtlas("ui/button.pack");
        skin = new Skin(atlas);
        // skin = new Skin(Gdx.files.internal(Constants.SKIN));

        table = new Table(skin);
        table.setBounds(0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        white = new BitmapFont(Gdx.files.internal("Fonts/white.fnt"),false);
        black = new BitmapFont(Gdx.files.internal("Fonts/black.fnt"),false);

        LabelStyle labelStyle = new LabelStyle();
        labelStyle.font = white;
        gameOver = new Label("Game Over", labelStyle);


        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("5895d300cba9841eabab607b");
        // textButtonStyle.down = skin.getDrawable("button.down");
        textButtonStyle.pressedOffsetX = 1;
        textButtonStyle.pressedOffsetY = -1;
        textButtonStyle.font = black;
        // buttonExit.pad(20);

        buttonPlay = new TextButton("Play", textButtonStyle);
        buttonPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                houseCurling.physicSystem.state=1;
            }
        });

        table.add(buttonPlay);
        gameOver.setPosition(Gdx.graphics.getWidth() / 2f - 100, 50);
        gameOver.setVisible(false);
        gameOver.setSize(200, 50);
        table.addActor(gameOver);
        stage.addActor(table);

    }

    public MainMenu(HouseCurling houseCurling) {
        this.houseCurling = houseCurling;
    }

    @Override
    public void render(float delta) {
        if(houseCurling.physicSystem.state==0){
            stage.act(delta);
            stage.draw();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        atlas.dispose();
        skin.dispose();
        white.dispose();
        black.dispose();
    }

}