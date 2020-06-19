package gametest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MainGame {

    public static Game game;

    public static void main(String[] args) {
        game = new Game(800, 600);
        game.start();
    }

    public static Game getGame() {
        return game;
    }
}
