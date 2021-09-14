package game.engine;

import java.time.LocalDateTime;

public class ErrorHandler {

    public static void logError(String info) {
        logError(ErrorLevel.INFO, null, info);
    }

    public static void logError(ErrorLevel level, String info) {
        logError(level, null, info);
    }

    public static void logError(ErrorLevel level, Exception e, String info) {
        if(e == null) {
            System.out.println(LocalDateTime.now() + " [" + level + "] " + info);
        } else {
            System.out.println(LocalDateTime.now() + " [" + level + "] " + info);
        }
        if(level == ErrorLevel.CRITICAL) {
            System.exit(0);
        }
    }

    public enum ErrorLevel {
        CRITICAL,
        ERROR,
        WARNING,
        INFO,
        DEBUG
    }

}
