package com.redrock.scenes;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.redrock.Main;
import com.redrock.coreGame.views.components.CardView;
import com.redrock.manager.SceneMgr;

public class GamePlayScene extends ScreenAdapter {
  private final Group gParent;
  private Image settingButton;

  public GamePlayScene() {
    Main.layers().init(SceneMgr.GAMEPLAY_SCENE, Main.getStage().getWidth(), Main.getStage().getHeight());

    this.gParent = new Group();
    this.gParent.setSize(Main.getStage().getWidth(), Main.getStage().getHeight());

    this.initUIElements();
    this.initEventListeners();

    Array<CardView> cards = new Array<>();
    for(int i = 0; i < 48; i++){
      CardView card = new CardView(0);
      cards.add(card);
      this.gParent.addActor(card);
      card.setPosition(60 + card.getWidth()*(i%6), 134 + card.getHeight()*(i/6));
    }
  }

  private void initUIElements(){
    Image background = new Image(Main.asset().getTG("background"));
    this.settingButton = new Image(Main.asset().getTG("setting_btn"));

    this.gParent.addActor(background);
    this.gParent.addActor(settingButton);

    settingButton.setPosition(605,1152);
  }

  private void initEventListeners(){
    this.settingButton.addListener(new ClickListener(){
      public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);

        System.out.println("clicked settingButton");
      }
    });
  }

  @Override
  public void show() {
    super.show();

    Main.layers().activeLayersBy(SceneMgr.GAMEPLAY_SCENE);
    Main.getStage().addActor(this.gParent);
  }
}
