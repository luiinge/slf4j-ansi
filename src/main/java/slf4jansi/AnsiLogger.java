/**
 * @author Luis Iñesta Gelabert - luiinge@gmail.com
 */
package slf4jansi;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.fusesource.jansi.Ansi;
import org.slf4j.Logger;
import org.slf4j.spi.LocationAwareLogger;
import slf4jansi.impl.DefaultStyles;
import slf4jansi.impl.LocationAwareAnsiLogger;
import slf4jansi.impl.SimpleAnsiLogger;

/**
 * This class is the entry point for obtaining Ansi-enchanced {@link Logger} instances,
 * as well as initial configuration.
 */
public class AnsiLogger {

    private static Properties styles = DefaultStyles.asProperties();
    private static List<Runnable> configurationChangeObservers = new ArrayList<>();


    private AnsiLogger() {
        /*avoid instantiation*/
    }


    /**
     * Wraps the given logger with the Ansi-enhanced logger
     * @param logger The original logger
     */
    public static Logger of(Logger logger) {
        if (logger instanceof LocationAwareLogger) {
            return new LocationAwareAnsiLogger((LocationAwareLogger) logger);
        } else {
            return new SimpleAnsiLogger(logger);
        }
    }


    /**
     * Enable/disable the Ansi capabilities
     */
    public static void setAnsiEnabled(boolean enabled) {
        Ansi.setEnabled(enabled);
    }


    /**
     * @return Whether the Ansi capabilities are enabled
     */
    public static boolean isAnsiEnabled() {
        return Ansi.isEnabled();
    }


    /**
     * Set the Ansi styles used
     */
    public static void setStyles(Properties styles) {
        Properties properties = DefaultStyles.asProperties();
        for (Object key : styles.keySet()) {
            properties.put(key,styles.getProperty(key.toString()));
        }
        AnsiLogger.styles = properties;
        AnsiLogger.configurationChangeObservers.forEach(Runnable::run);
    }


    /**
     * Add a new Ansi style
     */
    public static void addStyle(String key, String value) {
        AnsiLogger.styles.put(key,value);
        AnsiLogger.configurationChangeObservers.forEach(Runnable::run);
    }


    /**
     * @return The current Ansi styles
     */
    public static Properties styles() {
        return AnsiLogger.styles;
    }


    /**
     * Add a configuration change observer, that will be invoked each time the
     * styles change.
     */
    public static void addConfigurationChangeObserver(Runnable observerMethod) {
        AnsiLogger.configurationChangeObservers.add(observerMethod);
    }

}