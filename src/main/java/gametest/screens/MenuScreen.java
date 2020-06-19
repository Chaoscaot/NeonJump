package gametest.screens;

import gametest.Game;
import gametest.MainGame;
import gametest.objects.Button;
import gametest.objects.Text;
import gametest.objects.TexturedButton;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MenuScreen implements Screen {

    List<TexturedButton> buttons;
    Text text;
    Game game = MainGame.getGame();
    BufferedImage Backgroundimg, buttonimg, buttonimg2;

    public MenuScreen() {
        buttons = new ArrayList<>();

        loadFiles();
    }

    @Override
    public void render(Graphics2D g) {
        g.fillRect(0,0, game.getGv().gameCanvas.getWidth(), game.getGv().gameCanvas.getHeight());
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).render(g);
        }
        text.render(g);
    }

    @Override
    public void renderBackground(Graphics2D g) {
        g.fillRect(0,0, game.getGv().backgroundCanvas.getWidth(), game.getGv().backgroundCanvas.getHeight());
    }

    @Override
    public void tick() {
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).tick();
        }
        text.tick();
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
            buttons.add(new TexturedButton(250, 200, 300, 50, new Font("Arial", Font.PLAIN, 38), buttonimg, Color.BLACK, "Play", new Runnable() {
                @Override
                public void run() {
                    System.out.println("Pressed!");
                }
            }, new TexturedButton(250, 200, 300, 50, new Font("Arial", Font.PLAIN, 38), buttonimg2, Color.BLACK, "Play")));
            buttons.add(new TexturedButton(250, 400, 300, 50, new Font("Arial", Font.PLAIN, 38), buttonimg, Color.BLACK, "Options", new Runnable() {
                @Override
                public void run() {
                    game.setScreen(new OptionsScreen());
                }
            }, new TexturedButton(250, 400, 300, 50, new Font("Arial", Font.PLAIN, 38), buttonimg2, Color.BLACK, "Options")));
            buttons.add(new TexturedButton(250, 500, 300, 50, new Font("Arial", Font.PLAIN, 38), buttonimg, Color.BLACK, "Quit", new Runnable() {
                @Override
                public void run() {
                    System.exit(0);
                }
            }, new TexturedButton(250, 500, 300, 50, new Font("Arial", Font.PLAIN, 38), buttonimg2, Color.BLACK, "Quit")));
            buttons.add(new TexturedButton(250, 300, 300, 50, new Font("Arial", Font.PLAIN, 38), buttonimg, Color.BLACK, "Level Builder", new Runnable() {
                @Override
                public void run() {
                    System.exit(0);
                }
            }, new TexturedButton(250, 300, 300, 50, new Font("Arial", Font.PLAIN, 38), buttonimg2, Color.BLACK, "Level Builder")));
            text = new Text(400, 100, "Neonjump", new Color(255, 0, 255), new Font("Arial", Font.PLAIN, 38), 20, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void recalcSize() {

    }
}
