package game.engine;

import game.entities.Camera;
import game.engine.interfaces.Renderable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RenderManager {

    private static ArrayList<Renderable> renderables, postRenderables;
    private Camera activeCamera;
    private BufferStrategy bufferStrategy;

    private int fpsTotal, fps;
    private long startTimeFps, startTimeTpf;
    private float tpf;

    RenderManager() {
        RenderManager.renderables = new ArrayList<>();
        RenderManager.postRenderables = new ArrayList<>();
        this.activeCamera = new Camera(0, 0);
        this.fps = 0;
        this.tpf = 0;
        this.startTimeFps = System.currentTimeMillis();
        this.startTimeTpf = System.currentTimeMillis();
    }

    private void render(Graphics graphics, JComponent canvas) {

        // set start time for measuring tpf
        this.startTimeTpf = System.nanoTime();

        Graphics2D graphics2D = (Graphics2D) graphics;

        // draw background
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // calculate screen ratio to set viewport properly
        float ratio = Math.min(Math.max((float) canvas.getWidth() / canvas.getHeight(), 1), 2);

        // transform to allow for camera
        AffineTransform transform = new AffineTransform();
        transform.translate((float) canvas.getWidth() / 2, (float) canvas.getHeight() / 2);
        transform.scale(activeCamera.getZ() + ratio, activeCamera.getZ() + ratio);

        AffineTransform old = graphics2D.getTransform();

        graphics2D.transform(transform);

        renderables.stream()
                .sorted(Comparator.comparingInt(Renderable::getZ))
                .forEach(r -> r.render(graphics, canvas, this.activeCamera));

        graphics2D.setTransform(old);

        postRenderables.stream()
                .sorted(Comparator.comparingInt(Renderable::getZ))
                .forEach(r -> r.render(graphics, canvas, this.activeCamera));

        graphics2D.dispose();
        graphics.dispose();



        this.tpf = ((float) (System.nanoTime() - this.startTimeTpf) / 1000000);
        GameManager.setTitle("FPS: " + this.fps + ", TPF: " + this.tpf + "ms");

        if (this.startTimeFps < System.currentTimeMillis() - 1000) {
            this.fps = this.fpsTotal;
            this.startTimeFps = System.currentTimeMillis();
            this.fpsTotal = 0;
        } else this.fpsTotal++;
    }

    public static int registerRenderable(Renderable renderable) {
        renderables.add(renderable);
        return 0; // TODO: unique ID?
    }

    public static int registerPostRenderable(Renderable renderable) {
        postRenderables.add(renderable);
        return 0; // TODO: unique ID?
    }

    static class RenderingPanel extends JPanel implements KeyListener {

        private Thread t;
        private ScheduledExecutorService scheduledExecutorService;

        private RenderManager renderManager;

        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
            InputManager.keyDown(e.getKeyCode());
        }

        @Override
        public void keyReleased(KeyEvent e) {
            InputManager.keyUp(e.getKeyCode());
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            renderManager.render(graphics, this);
        }

        RenderingPanel(int initialUpdatesPerSecond, RenderManager renderManager) {
            this.setDoubleBuffered(true);

            this.t = new Thread(this::repaint);
            this.scheduledExecutorService = Executors.newScheduledThreadPool(1);
            this.scheduledExecutorService.scheduleAtFixedRate(t, 0, 1000000 / initialUpdatesPerSecond, TimeUnit.MICROSECONDS);

            this.renderManager = renderManager;
        }

        void setFramerate(int newFramerate) throws InterruptedException {
            this.t.join();
            this.scheduledExecutorService.shutdownNow();
            this.t = new Thread(this::repaint);
            this.scheduledExecutorService = Executors.newScheduledThreadPool(1);
            this.scheduledExecutorService.scheduleAtFixedRate(t, 0, 1000000 / newFramerate, TimeUnit.MICROSECONDS);
        }

    }

}
