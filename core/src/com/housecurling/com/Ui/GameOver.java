package com.housecurling.com.Ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.housecurling.com.HouseCurling;

public class GameOver implements Screen{

    private Stage stage;
    private TextureAtlas atlas;

    private Skin skin;
    private Table table;
    private TextButton buttonReplay;
    private BitmapFont white, black;
    private Label label;
    private HouseCurling houseCurling;

    boolean isEnded = false;


    public GameOver (HouseCurling houseCurling) {
        this.houseCurling = houseCurling;
    }

    @Override
    public void show() {
        stage = new Stage();

        Gdx.input.setInputProcessor(stage);

        //label = new Label(null, new Skin());

        atlas = new TextureAtlas("ui/button.pack");
        skin = new Skin(atlas);

        table = new Table(skin);
        table.setBounds(0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        white = new BitmapFont(Gdx.files.internal("Fonts/white.fnt"),false);
        black = new BitmapFont(Gdx.files.internal("Fonts/black.fnt"),false);

        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("5895d300cba9841eabab607b");
        textButtonStyle.pressedOffsetX = 1;
        textButtonStyle.pressedOffsetY = -1;
        textButtonStyle.font = black;

        buttonReplay = new TextButton("Play", textButtonStyle);
        buttonReplay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                houseCurling.physicSystem.state=1;
            }
        });

        //table.add(label);
        table.row();
        table.add(buttonReplay);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        if(houseCurling.physicSystem.state==2 || houseCurling.physicSystem.state==3){
            String text;
            if(houseCurling.physicSystem.state==2){
                                text="YOU WON!!!";
                                houseCurling.menu.gameOver.setText(text);
                                houseCurling.menu.gameOver.setVisible(true);
                houseCurling.physicSystem.state=0;
            }
            else {
                houseCurling.menu.gameOver.setVisible(true);
            }
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

    }

}