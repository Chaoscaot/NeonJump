package gametest;

import gametest.input.KeyInput;
import gametest.input.MouseInput;
import gametest.screens.MenuScreen;
import gametest.screens.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game {

    Gameloop renderLoop;
    Gameloop tickLoop;

    Screen currentScreen;

    public JFrame frame;
    public GameView gv;

    public int HEIGHT, WIDTH;
    boolean debug = true;

    GraphicsDevice gd;

    public Game(int width, int height) {
        //Asaigning Vars
        this.HEIGHT = height;
        this.WIDTH = width;
    }

    public void start() {
        //Frame
        gv = new GameView(WIDTH, HEIGHT, true);
        frame = new JFrame("GameTest");
        frame.setSize(WIDTH, HEIGHT);
        frame.setResizable(true);
        frame.requestFocus(FocusEvent.Cause.ACTIVATION);
        frame.setVisible(true);
        frame.setContentPane(gv);
        frame.invalidate();
        gv.gameCanvas.addKeyListener(new KeyInput());
        gv.gameCanvas.addMouseListener(new MouseInput());
        gv.gameCanvas.addMouseMotionListener(new MouseInput());
        gv.backgroundCanvas.addKeyListener(new KeyInput());
        gv.backgroundCanvas.addMouseListener(new MouseInput());
        gv.backgroundCanvas.addMouseMotionListener(new MouseInput());
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Exiting Game!");
                tickLoop.stopLoop();
                renderLoop.stopLoop();
                System.exit(0);
            }
        });
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                //currentScreen.recalcSize();
            }
        });
        frame.setLocationRelativeTo(null);

        //Loops
        setScreen(new MenuScreen());
        tickLoop = new Gameloop(this, 60, () -> tick(), "TickLoop");
        tickLoop.startLoop();
        renderLoop = new Gameloop(this, 60, () -> renderView(), "RenderLoop");
        renderLoop.startLoop();
        gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    }

    public void tick() {
        currentScreen.tick();
        if(KeyInput.wasReleased(KeyEvent.VK_F4)) {
            if (!debug) {
                frame.setMenuBar(null);
                debug = true;
            }else {
                MenuBar bar = new MenuBar();
                Menu m1 = new Menu("Debug");
                MenuItem mi1 = new MenuItem("Set Render Loop Target");
                mi1.addActionListener(e -> {
                    try {
                        int i = Integer.parseInt(JOptionPane.showInputDialog(frame, "Give Number", "Loop Target", JOptionPane.QUESTION_MESSAGE));
                        renderLoop.setTarget(i);
                    } catch (NumberFormatException e1) {
                        JOptionPane.showMessageDialog(frame, "Please Enter a Valid Number!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });
                MenuItem mi2 = new MenuItem("Set Tick Loop Target");
                mi2.addActionListener(e -> {
                    try {
                        int i = Integer.parseInt(JOptionPane.showInputDialog(frame, "Give Number", "Tick Target", JOptionPane.QUESTION_MESSAGE));
                        tickLoop.setTarget(i);
                    } catch (NumberFormatException e1) {
                        JOptionPane.showMessageDialog(frame, "Please Enter a Valid Number!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });
                MenuItem mi3 = new MenuItem("Toggle Fps/Tick Counter");
                mi3.addActionListener(e -> {
                    renderLoop.setDebug(!renderLoop.isDebug());
                    tickLoop.setDebug(!tickLoop.isDebug());
                });
                m1.add(mi1);
                m1.add(mi2);
                m1.addSeparator();
                m1.add(mi3);
                bar.add(m1);
                frame.setMenuBar(bar);
                debug = false;
            }
        }
        if(KeyInput.wasPressed(KeyEvent.VK_F11)) {
            toggleFullscreen();
        }
        KeyInput.update();
        MouseInput.update();
    }

    private void render() {
        BufferStrategy bs = gv.gameCanvas.getBufferStrategy();
        if(bs == null) {
            gv.gameCanvas.createBufferStrategy(3);
            return;
        }

        Graphics2D g = (Graphics2D) bs.getDrawGraphics();

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        currentScreen.render(g);

        if(!debug) {
            g.setColor(Color.GREEN);
            g.setFont(new Font("Arial", Font.PLAIN, 40));
            FontMetrics fm = g.getFontMetrics(new Font("Arial", Font.PLAIN, 40));
            int Sx = (MainGame.getGame().getGv().getBackgroundCanvas().getWidth() - MainGame.getGame().getGv().getGameCanvas().getWidth()) / 2;
            int Sy = (MainGame.getGame().getGv().getBackgroundCanvas().getHeight() - MainGame.getGame().getGv().getGameCanvas().getHeight()) / 2;
            g.drawString(Integer.toString(renderLoop.getIps()),0- Sx ,fm.getAscent() - Sy);
        }

        g.dispose();
        bs.show();
    }

    private void renderBackground() {
        BufferStrategy bs = gv.backgroundCanvas.getBufferStrategy();
        if(bs == null) {
            gv.backgroundCanvas.createBufferStrategy(3);
            return;
        }

        Graphics2D g = (Graphics2D) bs.getDrawGraphics();

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        currentScreen.renderBackground(g);

        if(!debug) {
            g.setColor(Color.GREEN);
            g.setFont(new Font("Arial", Font.PLAIN, 40));
            FontMetrics fm = g.getFontMetrics(new Font("Arial", Font.PLAIN, 40));
            g.drawString(Integer.toString(renderLoop.getIps()), 0 ,fm.getAscent());
        }

        g.dispose();
        bs.show();
    }

    private void renderView() {
        renderBackground();
        render();
    }

    public void setScreen(Screen screen) {
        System.out.println("new Screen: " + screen.getClass().getName());
        this.currentScreen = screen;
        currentScreen.loadFiles();
    }

    public void toggleFullscreen() {
        if(gd.getFullScreenWindow() == null) {
            gd.setFullScreenWindow(frame);
        }else {
            gd.setFullScreenWindow(null);
        }
    }

    public static BufferedImage scale(BufferedImage sbi, int imageType, int dWidth, int dHeight, double fWidth, double fHeight) {
        BufferedImage dbi = null;
        if(sbi != null) {
            dbi = new BufferedImage(dWidth, dHeight, imageType);
            Graphics2D g = dbi.createGraphics();
            AffineTransform at = AffineTransform.getScaleInstance(fWidth, fHeight);
            g.drawRenderedImage(sbi, at);
        }
        return dbi;
    }

    public GameView getGv() {
        return gv;
    }
}
