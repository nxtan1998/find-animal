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
  private Image settingButton;
  private Group startButton, modeButton;

  public StartScene() {
    this.gParent = new Group();
    this.gParent.setSize(Main.getStage().getWidth(), Main.getStage().getHeight());

    Main.layers().init(SceneMgr.START_SCENE, Main.getStage().getWidth(), Main.getStage().getHeight());

    this.initUIElements();
    this.initEventListeners();
  }

  private void initUIElements(){
    Image background = new Image(Main.asset().getTG("background"));
    Image logo = new Image(Main.asset().getTG("logo"));
    this.settingButton = new Image(Main.asset().getTG("setting_btn"));

    this.gParent.addActor(background);
    this.gParent.addActor(logo);
    this.gParent.addActor(settingButton);

    logo.setPosition(58,871);
    settingButton.setPosition(605,1152);

    this.initButtons();
  }

  private void initButtons(){
    this.startButton = new Group();
    this.modeButton = new Group();

    Image greenButtonShape = new Image(Main.asset().getTG("btn_green"));
    Image startText = new Image(Main.asset().getTG("Start"));
    Image orangeButtonShape = new Image(Main.asset().getTG("btn_orange"));
    Image modeText = new Image(Main.asset().getTG("Mode"));

    this.startButton.addActor(greenButtonShape);
    this.startButton.addActor(startText);
    this.modeButton.addActor(orangeButtonShape);
    this.modeButton.addActor(modeText);

    this.startButton.setSize(greenButtonShape.getWidth(), greenButtonShape.getHeight());
    this.modeButton.setSize(orangeButtonShape.getWidth(), orangeButtonShape.getHeight());

    startText.setPosition((startButton.getWidth() - startText.getWidth())/2,
            (startButton.getHeight() - startText.getHeight())/2);
    modeText.setPosition((modeButton.getWidth() - modeText.getWidth())/2,
            (modeButton.getHeight() - modeText.getHeight())/2);

    this.startButton.setPosition(231, 496);
    this.modeButton.setPosition(231, 331);

    this.gParent.addActor(this.startButton);
    this.gParent.addActor(this.modeButton);
  }

  private void initEventListeners(){
     this.settingButton.addListener(new ClickListener(){
      public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);

        System.out.println("clicked settingButton");
      }
    });

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
  public void show() {
    super.show();

    Main.layers().activeLayersBy(SceneMgr.START_SCENE);
    Main.getStage().addActor(gParent);
  }

  @Override
  public void resize(int width, int height) {
    super.resize(width, height);

    CENTER_X = Main.getStage().getWidth() / 2;
    CENTER_Y = Main.getStage().getHeight() / 2;

    gParent.setSize(Main.getStage().getWidth(), Main.getStage().getHeight());
  }
}
