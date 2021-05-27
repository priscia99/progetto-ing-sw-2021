package it.polimi.ingsw_old.utils;
import java.util.Date;
import java.util.Objects;
import java.util.logging.*;

public class CustomLogger {
    static private Logger customLogger;

    static private void initLogger(){
        customLogger = Logger.getLogger(CustomLogger.class.getName());
        customLogger.setUseParentHandlers(false);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new SimpleFormatter() {

            // sample output: [2021-04-04 23:48:24] [INFO   ] test
            private static final String format = "[%1$tF %1$tT] [%2$-7s] %3$s %n";

            @Override
            public synchronized String format(LogRecord logRecord) {
                return String.format(format,
                        new Date(logRecord.getMillis()),
                        logRecord.getLevel().getLocalizedName(),
                        logRecord.getMessage()
                );
            }
        });
        customLogger.addHandler(handler);
        customLogger.setLevel(Level.ALL);
    }

    static public Logger getLogger(){
        if(Objects.isNull(customLogger)) initLogger();
        return customLogger;
    }
}
