package game;

import game.engine.GameManager;
import game.entities.*;

import java.awt.*;

public class Game {

    public static void main(String[] args) {
        new GameManager();
    }

    public static void init() {

        GameManager.setFramerate(60);

        new MovingSquare(Color.MAGENTA, 0, 0, 64, 64);
        new MovingSquare(Color.GREEN, 64, 48, 64, 64);

        new RotatingSquare(Color.WHITE, 64, -64, 32, 32);
        new RotatingSquare(Color.RED, 128, 96, 72, 16);

        new MovingRotatingSquare(Color.BLUE, -64, -64, 16, 32);
        new MovingRotatingSquare(Color.YELLOW, -128, -32, 32, 32);

        new StaticSquare(Color.CYAN, -64, 64, 16, 16);
        new StaticSquare(Color.GRAY, -32, 16, 32, 32);

        for(int x = -8; x <= 8; x++) {
            for(int y = -8; y <= 8; y++) {
                if(y == 0 && x == 0) {
                    new StaticSquare(Color.RED, x * 16, y * 16, 1, 1).setZ(Integer.MAX_VALUE);
                } else {
                    new StaticSquare(Color.WHITE, x * 16, y * 16, 1, 1).setZ(Integer.MAX_VALUE);
                }
            }
        }

    }

}
