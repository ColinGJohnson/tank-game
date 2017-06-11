package com.mygdx.tanks.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.tanks.TankGame;

public class DesktopLauncher {
	public static void main (String[] arg) {

        // set game to desktop mode
        TankGame.platformResolver = new DesktopResolver();

        // configure game window
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 720;
		config.fullscreen = true;
		config.resizable = false;
		config.title = "LibGDX Tank Game";

        // create new game instance with window configuration
		new LwjglApplication(new TankGame(), config);
	} // main method
} // Desktop Launcher
