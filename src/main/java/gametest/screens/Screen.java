package gametest.screens;

import javax.swing.*;
import java.awt.*;

public interface Screen {

    void render(Graphics2D g);

    void renderBackground(Graphics2D g);

    void tick();

    int getId();

    void loadFiles();

    void recalcSize();

}
