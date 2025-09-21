package net.ravinvt.webMC.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static net.ravinvt.webMC.WebMC.webserver;

public class AliveWebCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String string, String[] args) {
        if (webserver != null) {
            if (webserver.isAlive()) {
                sender.sendMessage(ChatColor.GREEN + "[ WebMC ] Web server running!" + ChatColor.RESET);
            } else {
                sender.sendMessage(ChatColor.YELLOW + "[ WebMC ] Web server isn't running" + ChatColor.RESET);
            }
        }
        else {
            sender.sendMessage(ChatColor.RED + "[ WebMC ] Web server instance is null" + ChatColor.RESET);
        }
        return true;
    }
}
