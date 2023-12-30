package com.redrock;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.redrock.manager.*;
import com.redrock.sdk.AssetLoader;
import com.redrock.sdk.IPlat;
import com.redrock.sdk.IQuit;
import com.redrock.sdk.debug.Fps;
import com.redrock.sdk.layer.Layers;
import com.redrock.sdk.model.Session;
import com.redrock.sdk.model.impl.PersistentLoader;
import com.redrock.sdk.modules.generic.ModuleMessage;
import com.redrock.sdk.particle.NParticle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main extends Game implements IQuit {

	private final 	String								SYNC_KEY								= "template";

	private 		float											sclTime									= 1f;
	private 		float											minW, minH;
	private 		float											maxW, maxH;

	private 		boolean 									isWebGL									= false;
	private 		boolean										isLoading								= true;
	private 		boolean										debug										= true;
	private   	AssetLoader 							assetLoader;
	private 		Fps												fps;

	private 		Stage											stage;
	private 		InputMultiplexer 					inputMultiplexer;

	private 		Texture										bgLoading;
	private 		Texture										logoLoading;

	//Loading bar
	private 		Texture										bgLoadingBar;
	private 		Texture										loadingBar;

	private 		Batch											batch;
	private 		OrthographicCamera				camera;
	private 		Viewport									viewport;

	private 		IPlat											iPlat;
	private 		PersistentLoader<Session> pLoader;
	private			Session										session;

	private 		Layers 										layers;
	private 		ParticleMgr 							particleMgr;
	private 		PopupMgr									popupMgr;
	private 		HashMap<String, Actor>		hmElements;

	private 		MultiLangMgr							multiLangMgr;
	private 		SoundMgr									soundMgr;
	private 		SceneMgr									sceneMgr;

	private 		RemoteConfig							remoteConfig;
	private 		ModuleMessage 						moduleMessage;

	public Main(float minW, float minH, float maxW, float maxH, IPlat iPlat) {
		this.minW 	= minW;
		this.minH 	= minH;
		this.maxW 	= maxW;
		this.maxH 	= maxH;
		this.iPlat	= iPlat;
	}
	
	@Override
	public void create () {
		isWebGL = Gdx.app.getType() == Application.ApplicationType.WebGL;

		hmElements			= new HashMap<>();
		batch 					= new PolygonSpriteBatch();
		moduleMessage		= new ModuleMessage();

		pLoader = new PersistentLoader<>(Session.ofDefault(), Session.class, SYNC_KEY);
		session = pLoader.load();
		session.profile.reBalance();

		assetLoader = new AssetLoader();

		camera		= new OrthographicCamera();
		viewport 	= new ExtendViewport(minW, minH, maxW, maxH, camera);
		viewport.apply();

		stage 						= new Stage(viewport, batch);
		inputMultiplexer 	= new InputMultiplexer();
		inputMultiplexer.addProcessor(new InputAdapter() {
			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				return pointer > 0;
			}
		});
		inputMultiplexer.addProcessor(stage);
		Gdx.input.setInputProcessor(inputMultiplexer);

		layers			= new Layers();
		particleMgr = new ParticleMgr();
		soundMgr		= new SoundMgr();

		initLoading();
	}

	@Override
	public void render () {
		super.render();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (isLoading) {
			if (assetLoader.update()) {
				isLoading = false;
				assetLoader.cache();

				finishedLoading();
			}
			else
				loading();

			return;
		}

		stage.act(Gdx.graphics.getDeltaTime() * sclTime);
		stage.draw();
	}

	private void initLoading() {
		bgLoading = new Texture(Gdx.files.internal("loading/bg_loading.png"));
		bgLoading.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

		logoLoading = new Texture(Gdx.files.internal("loading/logo_loading.png"));
		logoLoading.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

		bgLoadingBar = new Texture(Gdx.files.internal("loading/bg_bar.png"));
		bgLoadingBar.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

		loadingBar = new Texture(Gdx.files.internal("loading/loading_bar.png"));
		loadingBar.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);

		viewport.update(width, height);
		viewport.apply();
	}

	@Override
	public void pause() {
		super.pause();

		pLoader.sync();
	}

	@Override
	public void dispose () {
		bgLoading.dispose();
		logoLoading.dispose();
		bgLoadingBar.dispose();
		loadingBar.dispose();

		batch.dispose();
	}

	private void loading() {
		batch.begin();
		batch.draw(bgLoading, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(logoLoading, Gdx.graphics.getWidth() / 2f - logoLoading.getWidth() / 2f, Gdx.graphics.getHeight() / 2f + logoLoading.getHeight() - 20,
				logoLoading.getWidth(), logoLoading.getHeight());

		batch.draw(bgLoadingBar,
				Gdx.graphics.getWidth() / 2f - bgLoadingBar.getWidth() / 2f, Gdx.graphics.getHeight() / 2f - bgLoadingBar.getHeight() / 2f + 20,
				bgLoadingBar.getWidth(), bgLoadingBar.getHeight(),
				0, 0,
				bgLoadingBar.getWidth(), bgLoadingBar.getHeight(),
				false, false);

		batch.draw(loadingBar,
				Gdx.graphics.getWidth() / 2f - loadingBar.getWidth() / 2f, Gdx.graphics.getHeight() / 2f - loadingBar.getHeight() / 2f + 20,
				0, 0,
				(int) (loadingBar.getWidth() * assetLoader.getProgress()), loadingBar.getHeight());

		batch.end();
	}

	private void finishedLoading() {
		remoteConfig = new RemoteConfig();

		initDefaultModules();

		particleMgr.load();

		multiLangMgr	= new MultiLangMgr();
		popupMgr			= new PopupMgr();
		sceneMgr			= new SceneMgr(this);

		boolean newbie = session.profile.newbie;
		sceneMgr.changeScene(SceneMgr.START_SCENE);
		debugFps();
	}

	private void debugFps() {
		fps = new Fps();

		if (debug)
			stage.addActor(fps);
	}

	private void initDefaultModules() {}

	public static AssetLoader asset() {
		return ((Main)Gdx.app.getApplicationListener()).assetLoader;
	}

	public static Stage getStage() {
		return ((Main)Gdx.app.getApplicationListener()).stage;
	}

	public static Session session() {
		return ((Main)Gdx.app.getApplicationListener()).session;
	}

	public static PopupMgr popupMgr() {
		return ((Main)Gdx.app.getApplicationListener()).popupMgr;
	}

	public static RemoteConfig remoteConfig() {
		return ((Main)Gdx.app.getApplicationListener()).remoteConfig;
	}

	public static MultiLangMgr multiLangMgr() {
		return ((Main)Gdx.app.getApplicationListener()).multiLangMgr;
	}

	public static NParticle getPar(String key) {
		return ((Main)Gdx.app.getApplicationListener()).particleMgr.getParticle(key);
	}

	public static SceneMgr sceneMgr() {
		return ((Main)Gdx.app.getApplicationListener()).sceneMgr;
	}

	public static SoundMgr soundMgr() {
		return ((Main)Gdx.app.getApplicationListener()).soundMgr;
	}

	public static IPlat iPlat() {
		return ((Main)Gdx.app.getApplicationListener()).iPlat;
	}

	public static void addToSysElements(String key, Actor a) {
		((Main)Gdx.app.getApplicationListener()).hmElements.put(key, a);
	}

	public static Layers layers() {
		return ((Main)Gdx.app.getApplicationListener()).layers;
	}

	public static Actor findElements(String key) {
		HashMap<String, Actor> tmps = ((Main)Gdx.app.getApplicationListener()).hmElements;
		return tmps.get(key);
	}

	public static ModuleMessage moduleMessage () { return ((Main)Gdx.app.getApplicationListener()).moduleMessage; }

	public static boolean isWebGL() {
		return ((Main) Gdx.app.getApplicationListener()).isWebGL;
	}

	public static void watchAdsReward(Runnable cbSuccess) {
		if (!session().profile.canWatchAds() || !iPlat().isVideoRewardReady()) {
			popupMgr().show(PopupMgr.ERROR_NETWORK);

			return;
		}

		iPlat().showVideoReward(success -> {
			session().profile.watchAdsCount++;

			if (success)
				if (cbSuccess != null)
					cbSuccess.run();
		});
	}

	public static void showFullscreen() {
		if (!iPlat().isFullscreenReady() || !session().profile.canShowFullscreen())
			return;

		if (isWebGL())
			iPlat().showFullscreen(
					(onShow) -> {},
					(onDone) -> {}
			);
		else
			iPlat().showFullscreen();
	}

	@Override
	public void quit() {

	}
}
