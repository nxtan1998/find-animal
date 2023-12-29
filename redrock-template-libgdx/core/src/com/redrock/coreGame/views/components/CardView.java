package com.redrock.coreGame.views.components;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.redrock.Main;
import com.redrock.manager.SceneMgr;

public class CardView extends Group {
    private int id;
    private int idString;
    private Image backImg, shapeImg;

    public CardView(int id){
        this.id = id;

        this.backImg = new Image(Main.asset().getTG("cxl_4"));
        this.shapeImg = new Image(Main.asset().getTG(this.getIdString(id)));

        this.setSize(this.backImg.getWidth(), this.backImg.getHeight());

        this.backImg.setOrigin(Align.center);
        this.shapeImg.setOrigin(Align.center);
        this.setOrigin(Align.center);

        this.addActor(this.backImg);
        this.addActor(this.shapeImg);

        this.shapeImg.setPosition((this.getWidth() - this.shapeImg.getWidth())/2,
                (this.getHeight() - this.shapeImg.getHeight())/2);

        this.addingListener();
    }

    private void addingListener(){
        this.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                displayMatchCardAnimation();
            }
        });
    }

//    startButton.addListener(new ClickListener(){
//        public void clicked(InputEvent event, float x, float y) {
//            super.clicked(event, x, y);
//
//            System.out.println("clicked startButton");
//            Main.sceneMgr().changeScene(SceneMgr.GAMEPLAY_SCENE);
//        }
//    });

    public void changeId(int id){
        this.id = id;
        this.shapeImg.setDrawable(new TextureRegionDrawable(Main.asset().getTG("theme_0_1")));
    }

    public void displayMatchCardAnimation(){
        this.addAction(Actions.parallel(
                Actions.alpha(0, 0.5f, Interpolation.fastSlow),
                Actions.scaleTo(1.3f, 1.3f, 0.5f, Interpolation.fastSlow)
        ));
    }

    private String getIdString(int id){
        return "theme_0_" + id;
    }
}
