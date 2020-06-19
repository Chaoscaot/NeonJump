package gametest.objects;

import gametest.Game;
import gametest.MainGame;
import gametest.sprites.Sprite;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;

public class Text implements Sprite {

    private double sx, sy;
    private int x, y;
    String text;
    Color c;
    Font f;
    Game game = MainGame.getGame();
    int fontSize, fontScale;
    boolean halo;

    public Text(int x, int y, String text, Color c, Font f, int fontScale, boolean halo) {
        sx = (double)x / 800;
        sy = (double)y / 600;
        this.x = x;
        this.y = y;
        this.text = text;
        this.c = c;
        this.f = f;
        this.fontSize = f.getSize();
        this.fontScale = fontScale;
        this.halo = halo;
    }

    @Override
    public void render(Graphics2D g) {
        if(halo) {
            f = new Font(f.getName(), f.getStyle(), fontSize);
            FontMetrics fm = g.getFontMetrics(f);
            g.setFont(f);
            int textX = x - (fm.stringWidth(text)) / 2;
            int textY = y - (( fm.getHeight())/2) + fm.getAscent();
            FontRenderContext frc = g.getFontRenderContext();
            GlyphVector gv = f.createGlyphVector(frc, text);

            g.setColor(Color.RED);
            Shape shape = gv.getOutline();
            g.setColor(c);
            g.drawGlyphVector(gv, textX, textY);
        }else {
            g.setColor(c);
            f = new Font(f.getName(), f.getStyle(), fontSize);
            FontMetrics fm = g.getFontMetrics(f);
            g.setFont(f);
            int textX = x - (fm.stringWidth(text)) / 2;
            int textY = y - (( fm.getHeight())/2) + fm.getAscent();
            g.drawString(text, textX, textY);
        }
    }

    @Override
    public void tick() {
        recalcSize();
        int fSize = (game.gv.gameCanvas.getWidth() + game.gv.gameCanvas.getHeight())/fontScale;
        FontMetrics fm = game.gv.gameCanvas.getFontMetrics(new Font(f.getName(), f.getStyle(), fSize));
        while (fm.stringWidth(text) >= game.gv.gameCanvas.getWidth()-game.gv.gameCanvas.getWidth()/20) {
            fSize-=1;
            fm = game.gv.gameCanvas.getFontMetrics(new Font(f.getName(), f.getStyle(), fSize));
        }
        fontSize = fSize;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public void loadTexture() {

    }

    private void recalcSize () {
        double frameWidth = game.gv.gameCanvas.getWidth();
        double frameHeight = game.gv.gameCanvas.getHeight();
        this.x = (int) (Math.ceil(frameWidth*sx));
        this.y = (int) (Math.ceil(frameHeight*sy));
    }
}
