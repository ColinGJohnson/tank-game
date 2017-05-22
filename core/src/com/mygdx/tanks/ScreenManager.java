package com.mygdx.tanks;

import com.badlogic.gdx.Screen;

import java.util.HashMap;

/**
 * Created by colin on 22-May-17.
 */

public class ScreenManager {

    // reference to main class
    private TankGame game;

    // game screens
    private static MenuScreen menuScreen;
    private static PlayScreen playScreen;
    private HashMap<GameState, Screen> screens;

    public enum GameState {
        Menu,
        Play;
    } // GameState

    public ScreenManager(TankGame game) {
        this.game = game;

        menuScreen = new MenuScreen(game);
        playScreen = new PlayScreen(game);

        screens = new HashMap<GameState, Screen>();
        screens.put(GameState.Menu, menuScreen);
        screens.put(GameState.Play, playScreen);
    } // ScreenManager Constructor

    public HashMap<GameState, Screen> getScreens() {
        return screens;
    } // getScreens
} // ScreenManager
