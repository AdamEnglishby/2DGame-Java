package game.engine.interfaces;

import game.entities.Camera;

import javax.swing.*;
import java.awt.*;

public interface Renderable {

    void render(Graphics graphics, JComponent canvas, Camera camera);

    void setZ(int z);

    int getZ();

    int getRenderableId();

}
