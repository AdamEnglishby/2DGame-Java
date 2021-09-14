package game.engine;

public class InputManager {

    private static boolean[] keys;

    InputManager() {
        keys = new boolean[65535];
    }

    public static boolean isKeyDown(int keyCode) {
        return keys[keyCode];
    }

    static void keyDown(int key) {
        keys[key] = true;
    }

    static void keyUp(int key) {
        keys[key] = false;
    }

}
