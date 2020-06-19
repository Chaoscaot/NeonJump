package gametest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GameView extends JComponent {

    int pWidth, pHeight;
    boolean scale = false;
    public GameCanvas gameCanvas;
    public GameCanvas backgroundCanvas;
    double scaleDim = 1.0;

    public GameView(int pWidth, int pHeight) {
        this.pWidth = pWidth;
        this.pHeight = pHeight;

        init();
    }

    public GameView(int pWidth, int pHeight, boolean scale) {
        this.pWidth = pWidth;
        this.pHeight = pHeight;
        this.scale = scale;

        init();
    }

    private void init() {
        gameCanvas = new GameCanvas();
        backgroundCanvas = new GameCanvas();
        add(gameCanvas);
        add(backgroundCanvas);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Component component = e.getComponent();
                if(scale) {
                    scaleDim = Math.min((double) component.getWidth() / pWidth, (double)component.getHeight() / pHeight);
                    gameCanvas.setSize((int)(pWidth * scaleDim), (int)(pHeight * scaleDim));
                    gameCanvas.setScaleDim(scaleDim);
                }
                gameCanvas.setLocation(component.getWidth()/2-gameCanvas.getWidth()/2, component.getHeight()/2-gameCanvas.getHeight()/2);
                backgroundCanvas.setSize(component.getSize());
            }
        });
    }

    public void setScale(boolean scale) {
        this.scale = scale;
    }

    @Override
    protected void paintChildren(Graphics g) {}

    public GameCanvas getGameCanvas() {
        return gameCanvas;
    }

    public GameCanvas getBackgroundCanvas() {
        return backgroundCanvas;
    }
}
