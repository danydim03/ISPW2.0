package org.example.loggers_general;

import org.example.enums.ExceptionMessagesEnum;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GeneralLogger {

    private GeneralLogger() {
        throw new IllegalStateException(ExceptionMessagesEnum.NOT_INSTANTIABLE.message);
    }

    private static final Logger logger = Logger.getLogger(GeneralLogger.class.getName());

    public static synchronized void logSevere(String msg) {
        logger.log(Level.SEVERE, msg);
    }

    public static synchronized void logWarning(String msg) {
        logger.log(Level.WARNING, msg);
    }

    public static synchronized void logInfo(String msg) {
        logger.log(Level.INFO, msg);
    }
}
