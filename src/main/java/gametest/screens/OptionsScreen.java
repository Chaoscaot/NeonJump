package gametest.screens;

import gametest.Game;
import gametest.MainGame;

import java.awt.*;

public class OptionsScreen implements Screen {

    Game game = MainGame.getGame();

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0 ,0 , game.getGv().gameCanvas.getWidth(), game.getGv().gameCanvas.getHeight());
    }

    @Override
    public void renderBackground(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0 ,0 , game.getGv().backgroundCanvas.getWidth(), game.getGv().backgroundCanvas.getHeight());
    }

    @Override
    public void tick() {

    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public void loadFiles() {

    }

    @Override
    public void recalcSize() {

    }
}
