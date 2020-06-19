package gametest.sprites;

import java.awt.*;

public interface Sprite {

    void render(Graphics2D g);

    void tick();

    int getId();

    void loadTexture();
}
