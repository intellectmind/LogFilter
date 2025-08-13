package cn.kurt6.logfilter;

import org.bukkit.plugin.java.JavaPlugin;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.apache.logging.log4j.core.LogEvent;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;

import static org.bukkit.Bukkit.getLogger;

public class LogFilter extends JavaPlugin {

    private static LogFilter instance;
    private CustomLogFilter logFilter;

    @Override
    public void onEnable() {
        // bStats
        int pluginId = 26904;
        cn.kurt6.logfilter.bStats.Metrics metrics = new cn.kurt6.logfilter.bStats.Metrics(this, pluginId);

        instance = this;
        saveDefaultConfig();
        getCommand("logfilter").setExecutor(new LogFilterCommand(this));
        setupLogFilter();
        getLogger().info("LogFilter plugin enabled!");
    }

    @Override
    public void onDisable() {
        if (logFilter != null && !LoggerUtil.removeFilter(logFilter)) {
            getLogger().warning("Failed to properly remove log filter");
        }
        getLogger().info("LogFilter plugin disabled!");
    }

    private void setupLogFilter() {
        List<String> filterPatterns = getConfig().getStringList("filter-patterns");
        if (filterPatterns.isEmpty()) {
            getLogger().warning("No filter patterns found, plugin will not filter any logs");
            return;
        }

        logFilter = new CustomLogFilter(filterPatterns);
        if (LoggerUtil.addFilter(logFilter)) {
            getLogger().info("Loaded " + filterPatterns.size() + " log filter rules");
        } else {
            getLogger().severe("Failed to setup log filter!");
        }
    }

    public static LogFilter getInstance() {
        return instance;
    }

    public void reloadFilter() {
        if (logFilter != null) {
            LoggerUtil.removeFilter(logFilter);
        }
        reloadConfig();
        setupLogFilter();
        getLogger().info("Log filter reloaded!");
    }
}

class CustomLogFilter extends AbstractFilter {
    private final List<Pattern> filterPatterns;

    public CustomLogFilter(List<String> patterns) {
        this.filterPatterns = new ArrayList<>();
        for (String patternStr : patterns) {
            try {
                filterPatterns.add(Pattern.compile(patternStr));
            } catch (Exception e) {
                getLogger().severe("Invalid regular expression: " + patternStr + " - " + e.getMessage());
            }
        }
    }

    @Override
    public Result filter(LogEvent event) {
        if (event == null || event.getMessage() == null) {
            return Result.NEUTRAL;
        }
        String message = event.getMessage().getFormattedMessage();
        for (Pattern pattern : filterPatterns) {
            if (pattern.matcher(message).find()) {
                return Result.DENY;
            }
        }
        return Result.NEUTRAL;
    }
}