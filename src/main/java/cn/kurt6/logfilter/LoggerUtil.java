package cn.kurt6.logfilter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;

import java.lang.reflect.Method;

public class LoggerUtil {

    private static boolean useReflection = false;
    private static Method addFilterMethod = null;
    private static Method removeFilterMethod = null;

    static {
        try {
            Class<?> loggerClass = Logger.class;
            addFilterMethod = loggerClass.getMethod("addFilter", AbstractFilter.class);
            removeFilterMethod = loggerClass.getMethod("removeFilter", AbstractFilter.class);
        } catch (NoSuchMethodException e) {
            useReflection = true;
        }
    }

    public static boolean addFilter(AbstractFilter filter) {
        try {
            Logger rootLogger = (Logger) LogManager.getRootLogger();

            if (!useReflection && addFilterMethod != null) {
                addFilterMethod.invoke(rootLogger, filter);
                return true;
            } else {
                Configuration config = rootLogger.getContext().getConfiguration();
                LoggerConfig rootConfig = config.getLoggerConfig("");
                rootConfig.addFilter(filter);
                rootLogger.getContext().updateLoggers();
                return true;
            }
        } catch (Exception e) {
            System.err.println("Failed to add log filter: " + e.getMessage());
            return false;
        }
    }

    public static boolean removeFilter(AbstractFilter filter) {
        try {
            Logger rootLogger = (Logger) LogManager.getRootLogger();

            if (!useReflection && removeFilterMethod != null) {
                removeFilterMethod.invoke(rootLogger, filter);
                return true;
            } else {
                Configuration config = rootLogger.getContext().getConfiguration();
                LoggerConfig rootConfig = config.getLoggerConfig("");
                rootConfig.removeFilter(filter);
                rootLogger.getContext().updateLoggers();
                return true;
            }
        } catch (Exception e) {
            System.err.println("Failed to remove log filter: " + e.getMessage());
            return false;
        }
    }
}