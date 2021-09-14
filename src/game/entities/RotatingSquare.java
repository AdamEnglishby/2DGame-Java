package game.entities;

import game.engine.RenderManager;
import game.engine.UpdateManager;
import game.engine.interfaces.Renderable;
import game.engine.interfaces.Updateable;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class RotatingSquare implements Renderable, Updateable {

    private Color color;
    private float x, y;
    private float width, height;
    private int renderableId, updateableId, z;
    private float increment = 0;

    public RotatingSquare(Color color, float x, float y, float width, float height) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.renderableId = RenderManager.registerRenderable(this);
        this.updateableId = UpdateManager.registerUpdateable(this);
        this.z = 0;
    }

    @Override
    public void render(Graphics graphics, JComponent canvas, Camera camera) {
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.setColor(this.color);

        AffineTransform old = g2d.getTransform();
        g2d.rotate(Math.toRadians(increment), this.x + camera.getX(), this.y + camera.getY());
        g2d.fill(new Rectangle.Float(
                camera.getX() + x - this.width / 2,
                camera.getY() + y - this.height / 2,
                this.width,
                this.height)
        );
        g2d.setTransform(old);
    }

    @Override
    public void update() {
        this.increment += 2;
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

    @Override
    public int getUpdateableId() {
        return updateableId;
    }
}
