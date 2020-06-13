package slf4jansi;

import org.fusesource.jansi.Ansi;
import org.slf4j.Logger;
import org.slf4j.spi.LocationAwareLogger;

import slf4jansi.impl.LocationAwareAnsiLogger;
import slf4jansi.impl.SimpleAnsiLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class AnsiLogger {

    private static Properties styles = DefaultStyles.asProperties();
    private static List<Runnable> configurationChangeObservers = new ArrayList<>();


    public static Logger of(Logger logger) {
        if (logger instanceof LocationAwareLogger) {
            return new LocationAwareAnsiLogger((LocationAwareLogger) logger);
        } else {
            return new SimpleAnsiLogger(logger);
        }
    }


    public static void setAnsiEnabled(boolean enabled) {
        Ansi.setEnabled(enabled);
    }

    public static boolean isAnsiEnabled() {
        return Ansi.isEnabled();
    }

    public static void setStyles(Properties styles) {
        Properties properties = DefaultStyles.asProperties();
        for (Object key : styles.keySet()) {
            properties.put(key,styles.getProperty(key.toString()));
        }
        AnsiLogger.styles = properties;
        AnsiLogger.configurationChangeObservers.forEach(Runnable::run);
    }

    public static void addStyle(String key, String value) {
        AnsiLogger.styles.put(key,value);
        AnsiLogger.configurationChangeObservers.forEach(Runnable::run);
    }

    public static Properties styles() {
        return AnsiLogger.styles;
    }

    public static void addConfigurationChangeObserver(Runnable observerMethod) {
        AnsiLogger.configurationChangeObservers.add(observerMethod);
    }
    
}