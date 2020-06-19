package gametest.screens;

import gametest.Game;
import gametest.MainGame;
import gametest.objects.TexturedButton;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OptionsScreen implements Screen {

    Game game = MainGame.getGame();
    List<TexturedButton> buttons;
    BufferedImage buttonimg, buttonimg2;

    public OptionsScreen() {
        buttons = new ArrayList<>();

        loadFiles();
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0 ,0 , game.getGv().gameCanvas.getWidth(), game.getGv().gameCanvas.getHeight());
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).render(g);
        }
    }

    @Override
    public void renderBackground(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0 ,0 , game.getGv().backgroundCanvas.getWidth(), game.getGv().backgroundCanvas.getHeight());
    }

    @Override
    public void tick() {
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).tick();
        }
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public void loadFiles() {
        try {
            buttonimg = ImageIO.read(new File("src/main/resources/textures/button_not.png"));
            buttonimg2 = ImageIO.read(new File("src/main/resources/textures/button_yes.png"));
            buttons.add(new TexturedButton(0, 540, 150, 50, new Font("Arial", Font.PLAIN, 38), buttonimg, Color.BLACK, "Back", () ->
                    game.setScreen(new MenuScreen())
                    , new TexturedButton(0, 540, 150, 50, new Font("Arial", Font.PLAIN, 38), buttonimg2, Color.BLACK, "Back")));
            System.out.println("buttons = " + buttons);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void recalcSize() {

    }
}
