package game.entities;

import game.engine.RenderManager;
import game.engine.UpdateManager;
import game.engine.interfaces.Renderable;
import game.engine.interfaces.Updateable;

import javax.swing.*;
import java.awt.*;

public class MovingSquare implements Renderable, Updateable {

    private Color color;
    private float x, y;
    private float width, height;
    private int renderableId, updateableId, z;
    private float increment = 0;

    public MovingSquare(Color color, float x, float y, float width, float height) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.renderableId = RenderManager.registerRenderable(this);
        this.updateableId = UpdateManager.registerUpdateable(this);
        this.z = 1;
    }

    @Override
    public void render(Graphics graphics, JComponent canvas, Camera camera) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setColor(this.color);
        graphics2D.fill(new Rectangle.Float(
                camera.getX() + x - this.width / 2,
                camera.getY() + y - this.height / 2,
                this.width,
                this.height)
        );
    }

    @Override
    public void update() {
        this.increment += 0.01;
        this.x += (float) Math.sin(this.increment);
        this.y += (float) Math.cos(this.increment);
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
