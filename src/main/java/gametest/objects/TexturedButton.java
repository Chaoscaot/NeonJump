package gametest.objects;

import gametest.Game;
import gametest.MainGame;
import gametest.input.MouseInput;
import gametest.sprites.Sprite;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TexturedButton implements Sprite {

    private double sx, sy, swidht, sheigth;
    private int x, y, widht, heigth;
    Rectangle rect;
    Font f;
    Color textC;
    String text;
    TexturedButton activeButton;
    boolean active;
    Runnable press;
    boolean pressed;
    Game game = MainGame.getGame();
    int fontSize;
    BufferedImage img;

    public TexturedButton(int x, int y, int widht, int heigth, Font f, BufferedImage img, Color textC, String text, Runnable press, TexturedButton activeButton) {
        sx = (double)x / 800;
        sy = (double)y / 600;
        swidht = (double)widht / 800;
        sheigth = (double)heigth / 600;
        this.x = (int) (Math.ceil(game.gv.gameCanvas.getWidth()*sx));
        this.y = (int) (Math.ceil(game.gv.gameCanvas.getHeight()*sy));
        this.widht = (int) (Math.ceil(game.gv.gameCanvas.getWidth()*swidht));
        this.heigth = (int) (Math.ceil(game.gv.gameCanvas.getHeight()*sheigth));
        this.rect = new Rectangle(x, y, widht, heigth);
        this.f = f;
        this.img = img;
        this.textC = textC;
        this.text = text;
        this.activeButton = activeButton;
        this.press = press;
    }

    public TexturedButton(int x, int y, int widht, int heigth, Font f, BufferedImage img, Color textC, String text) {
        this(x, y, widht, heigth, f, img, textC, text, null, null);
    }

    @Override
    public void render(Graphics2D g) {
        if(activeButton == null) {
            renderSelf(g);
        }else {
            if(active) {
                activeButton.render(g);
            }else {
                renderSelf(g);
            }
        }

    }

    private void renderSelf(Graphics2D g) {
        g.drawImage(img, x, y, widht, heigth, null);
        g.setColor(textC);
        f = new Font(f.getName(), f.getStyle(), fontSize);
        FontMetrics fm = g.getFontMetrics(f);
        g.setFont(f);
        int textX = (widht - fm.stringWidth(text)) / 2 + x;
        int textY = ((heigth - fm.getHeight())/2) + fm.getAscent() + y;
        g.drawString(text, textX, textY);
    }

    @Override
    public void tick() {
        active = rect.intersects(new Rectangle(MouseInput.x, MouseInput.y, 1, 1));
        if (press != null) {
            if(active && MouseInput.wasReleased(1)) {
                if(!pressed) {
                    press.run();
                    pressed = true;
                }
            }else {
                pressed = false;
            }
            if(MouseInput.wasReleased(2)) {
                System.out.println(this);
            }
        }
        if(activeButton != null) activeButton.tick();
        recalcSize();
        this.rect = new Rectangle(x, y, widht, heigth);
        int fSize = (widht + heigth)/8;
        FontMetrics fm = game.gv.gameCanvas.getFontMetrics(new Font(f.getName(), f.getStyle(), fSize));
        while (fm.stringWidth(text) >= widht-widht/10) {
            fSize-=1;
            fm = game.gv.gameCanvas.getFontMetrics(new Font(f.getName(), f.getStyle(), fSize));
        }
        fontSize = fSize;
    }

    private void recalcSize () {
        double frameWidth = game.gv.gameCanvas.getWidth();
        double frameHeight = game.gv.gameCanvas.getHeight();
        this.x = (int) (Math.ceil(frameWidth*sx));
        this.y = (int) (Math.ceil(frameHeight*sy));
        this.widht = (int) (Math.ceil(frameWidth*swidht));
        this.heigth = (int) (Math.ceil(frameHeight*sheigth));
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public void loadTexture() {

    }

    public void setText(String text) {

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidht() {
        return widht;
    }

    public void setWidht(int widht) {
        this.widht = widht;
    }

    public int getHeigth() {
        return heigth;
    }

    public void setHeigth(int heigth) {
        this.heigth = heigth;
    }

    @Override
    public String toString() {
        return "Button{" +
                "sx=" + sx +
                ", sy=" + sy +
                ", swidht=" + swidht +
                ", sheigth=" + sheigth +
                ", x=" + x +
                ", y=" + y +
                ", widht=" + widht +
                ", heigth=" + heigth +
                ", rect=" + rect +
                ", f=" + f +
                ", img=" + img +
                ", textC=" + textC +
                ", text='" + text + '\'' +
                ", activeButton=" + activeButton +
                ", active=" + active +
                ", press=" + press +
                ", pressed=" + pressed +
                ", game=" + game +
                '}';
    }

}
