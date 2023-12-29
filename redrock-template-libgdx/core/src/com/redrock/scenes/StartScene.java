package com.redrock.scenes;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.redrock.Main;
import com.redrock.manager.SceneMgr;

public class StartScene extends ScreenAdapter {

  public static float CENTER_X = Main.getStage().getWidth()/2;
  public static float CENTER_Y = Main.getStage().getHeight()/2;

  private final Group gParent;

  public StartScene() {
    this.gParent = new Group();
    this.gParent.setSize(Main.getStage().getWidth(), Main.getStage().getHeight());

    Main.layers().init(SceneMgr.START_SCENE, Main.getStage().getWidth(), Main.getStage().getHeight());
  }

  @Override
  public void show() {
    super.show();

    Main.layers().activeLayersBy(SceneMgr.START_SCENE);
    Main.getStage().addActor(gParent);


    Image background = new Image(Main.asset().getTG("background"));
    this.gParent.addActor(background);
    Image logo = new Image(Main.asset().getTG("logo"));
    this.gParent.addActor(logo);
    logo.setPosition(58,871);

    Image settingButton = new Image(Main.asset().getTG("setting_btn"));
    this.gParent.addActor(settingButton);

    settingButton.setPosition(605,1152);

    settingButton.addListener(new ClickListener(){
      public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);

        System.out.println("clicked settingButton");
      }
    });

    Group startButton = new Group();
    Image greenButtonShape = new Image(Main.asset().getTG("btn_green"));
    Image startText = new Image(Main.asset().getTG("Start"));
    startButton.addActor(greenButtonShape);
    startButton.addActor(startText);
    this.gParent.addActor(startButton);

    startButton.setSize(greenButtonShape.getWidth(), greenButtonShape.getHeight());
    startButton.debug();

    startText.setPosition((startButton.getWidth() - startText.getWidth())/2,
            (startButton.getHeight() - startText.getHeight())/2);

    startButton.setPosition(231, 496);

    Group modeButton = new Group();
    Image orangeButtonShape = new Image(Main.asset().getTG("btn_orange"));
    Image modeText = new Image(Main.asset().getTG("Mode"));
    modeButton.addActor(orangeButtonShape);
    modeButton.addActor(modeText);
    this.gParent.addActor(modeButton);

    modeButton.setSize(greenButtonShape.getWidth(), greenButtonShape.getHeight());
    modeButton.debug();

    modeText.setPosition((startButton.getWidth() - startText.getWidth())/2,
            (startButton.getHeight() - startText.getHeight())/2);

    modeButton.setPosition(231, 331);



    startButton.addListener(new ClickListener(){
      public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);

        System.out.println("clicked startButton");
        Main.sceneMgr().changeScene(SceneMgr.GAMEPLAY_SCENE);
      }
    });

    modeButton.addListener(new ClickListener(){
      public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);

        System.out.println("clicked modeButton");
      }
    });


  }

  @Override
  public void render(float delta) {
    super.render(delta);

  }

  @Override
  public void resize(int width, int height) {
    super.resize(width, height);

    CENTER_X = Main.getStage().getWidth() / 2;
    CENTER_Y = Main.getStage().getHeight() / 2;

    gParent.setSize(Main.getStage().getWidth(), Main.getStage().getHeight());
  }
}
