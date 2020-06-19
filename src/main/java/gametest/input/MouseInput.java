package gametest.input;

import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter {
    private final static boolean[] keys = new boolean[12];
    private final static boolean[] lastkeys = new boolean[12];

    public static int x, y, lastX, lastY;
    public static boolean moving;

    @Override
    public void mousePressed(MouseEvent e) {
        keys[e.getButton()] = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        keys[e.getButton()] = false;
    }

    public static void update() {
        for (int i = 0; i < keys.length; i++)
            lastkeys[i] = keys[i];
        lastX = x;
        lastY = y;
    }

    public static boolean isKeyDown(int keycode) {
        return keys[keycode];
    }

    public static boolean wasPressed(int keycode) {
        return isKeyDown(keycode) && !lastkeys[keycode];
    }

    public static boolean wasReleased(int keycode) {
        return !isKeyDown(keycode) && lastkeys[keycode];
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        moving = x != lastX || y != lastY;
    }
}
