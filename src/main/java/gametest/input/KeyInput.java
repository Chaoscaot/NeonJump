package gametest.input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    private final static boolean[] keys = new boolean[256];
    private final static boolean[] lastkeys = new boolean[256];

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    public static void update() {
        for (int i = 0; i < keys.length; i++)
            lastkeys[i] = keys[i];
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
}
