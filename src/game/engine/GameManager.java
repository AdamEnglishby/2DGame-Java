package game.engine;

import game.Game;

import javax.swing.*;
import java.awt.*;

public class GameManager {

    private static JFrame frame;
    private static RenderManager.RenderingPanel renderingPanel;

    public GameManager() {

        // GAMESTATE UPDATING
        UpdateManager updateManager = new UpdateManager();

        // INPUT UPDATING
        InputManager inputManager = new InputManager();

        // SCREEN UPDATING
        RenderManager renderManager = new RenderManager();
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame();
            frame.setSize(1280, 720);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            GameManager.renderingPanel = new RenderManager.RenderingPanel(60, renderManager);
            GameManager.renderingPanel.setIgnoreRepaint(true);
            GameManager.renderingPanel.setLayout(null);
            GameManager.renderingPanel.addKeyListener(renderingPanel);
            GameManager.renderingPanel.setFocusable(true);

            Toolkit.getDefaultToolkit().setDynamicLayout(false);

            frame.add(renderingPanel);

            // frame.setResizable(false);

            // frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            // frame.setUndecorated(true);

            frame.setVisible(true);

            /* Graphics2D graphics2D = (Graphics2D) GameManager.renderingPanel.getGraphics();
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            graphics2D.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE); */

            updateManager.start();

            Game.init();
        });
    }

    public static JFrame getFrame() {
        return GameManager.frame;
    }

    public static void setFramerate(int newFramerate) {
        try {
            GameManager.renderingPanel.setFramerate(newFramerate);
        } catch(InterruptedException e) {
            ErrorHandler.logError(ErrorHandler.ErrorLevel.CRITICAL, e, "Failed to set new framerate");
        } catch (NullPointerException e) {
            ErrorHandler.logError(ErrorHandler.ErrorLevel.CRITICAL, e, "Cannot update framerate until window has been created!");
        }
    }

    public static void setTitle(String title) {
        GameManager.frame.setTitle(title);
    }

}
