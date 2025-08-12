package cn.kurt6.logfilter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class LogFilterCommand implements CommandExecutor, TabCompleter {

    private final LogFilter plugin;

    public LogFilterCommand(LogFilter plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("logfilter.admin")) {
            sender.sendMessage("§cYou don't have permission to use this command!");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage("§6LogFilter commands:");
            sender.sendMessage("§a/logfilter reload §7- Reload the configuration");
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            try {
                plugin.reloadFilter();
                sender.sendMessage("§aLog filter configuration reloaded!");
            } catch (Exception e) {
                sender.sendMessage("§cError occurred while reloading config: §f" + e.getMessage());
                plugin.getLogger().severe("Reload failed: " + e.getMessage());
            }
            return true;
        }

        sender.sendMessage("§cUnknown command. Use /logfilter for help.");
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();

        if (!sender.hasPermission("logfilter.admin")) {
            return completions;
        }

        if (args.length == 1) {
            completions.add("reload");
        }

        if (args.length > 0) {
            String input = args[args.length - 1].toLowerCase();
            completions.removeIf(s -> !s.toLowerCase().startsWith(input));
        }

        return completions;
    }
}