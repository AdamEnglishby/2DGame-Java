package game.entities;

import game.engine.RenderManager;
import game.engine.UpdateManager;
import game.engine.interfaces.Renderable;
import game.engine.interfaces.Updateable;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class MovingRotatingSquare implements Renderable, Updateable {

    private Color color;
    private float x, y;
    private float width, height;
    private int renderableId, updateableId, z;
    private float rotationIncrement = 0, positionIncrement = 0;

    public MovingRotatingSquare(Color color, float x, float y, float width, float height) {
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
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setColor(this.color);

        AffineTransform old = graphics2D.getTransform();
        graphics2D.rotate(Math.toRadians(rotationIncrement), this.x + camera.getX(), this.y + camera.getY());
        graphics2D.fill(new Rectangle.Float(
                camera.getX() + x - this.width / 2,
                camera.getY() + y - this.height / 2,
                this.width,
                this.height)
        );
        graphics2D.setTransform(old);
    }

    @Override
    public void update() {
        this.rotationIncrement += 1;

        this.positionIncrement += 0.03;
        this.x += (float) Math.sin(this.positionIncrement);
        this.y += (float) Math.cos(this.positionIncrement);
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
