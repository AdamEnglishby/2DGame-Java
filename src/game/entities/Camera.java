package game.entities;

import game.engine.InputManager;
import game.engine.UpdateManager;
import game.engine.interfaces.Updateable;
import game.engine.util.Vector3f;

import java.awt.event.KeyEvent;

public class Camera implements Updateable {

    private float x, y;
    private float z;
    private Vector3f velocity;

    private int updateableId;

    public Camera(int x, int y) {
        this.x = x;
        this.y = y;
        this.z = 1;
        this.velocity = new Vector3f(0, 0, 0);
        this.updateableId = UpdateManager.registerUpdateable(this);
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getZ() {
        return this.z;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(float z) {
        this.z = z;
    }

    @Override
    public void update() {
        if (InputManager.isKeyDown(65)) {
            this.velocity.x += 1;
        }
        if (InputManager.isKeyDown(68)) {
            this.velocity.x -= 1;
        }
        if (InputManager.isKeyDown(87)) {
            this.velocity.y += 1;
        }
        if (InputManager.isKeyDown(83)) {
            this.velocity.y -= 1;
        }
        if (InputManager.isKeyDown(KeyEvent.VK_EQUALS)) {
            this.velocity.z += 0.01;
        }
        if (InputManager.isKeyDown(KeyEvent.VK_MINUS)) {
            this.velocity.z -= 0.01;
        }

        this.x += this.velocity.x;
        this.y += this.velocity.y;
        this.z += this.velocity.z;

        this.velocity.x /= 1.2;
        this.velocity.y /= 1.2;
        this.velocity.z /= 1.2;
    }

    @Override
    public int getUpdateableId() {
        return this.updateableId;
    }
}
