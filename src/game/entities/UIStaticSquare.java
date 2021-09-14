package game.entities;

import game.engine.RenderManager;
import game.engine.interfaces.Renderable;

import javax.swing.*;
import java.awt.*;

public class UIStaticSquare implements Renderable {

    private Color color;
    private float x, y;
    private float width, height;
    private int renderableId, z;

    public UIStaticSquare(Color color, float x, float y, float width, float height) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.renderableId = RenderManager.registerPostRenderable(this);
        this.z = 0;
    }

    @Override
    public void render(Graphics graphics, JComponent canvas, Camera camera) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setColor(this.color);
        graphics2D.fill(new Rectangle.Float(
                x,
                y,
                this.width,
                this.height)
        );
    }

    @Override
    public void setZ(int z) {
        this.z = z;
    }

    @Override
    public int getZ() {
        return this.z;
    }

    @Override
    public int getRenderableId() {
        return renderableId;
    }

}
