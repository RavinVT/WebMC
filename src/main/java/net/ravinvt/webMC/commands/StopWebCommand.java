package net.ravinvt.webMC.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

import static net.ravinvt.webMC.WebMC.webserver;

public class StopWebCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String string, String[] args) {
        if (webserver.isAlive()) {
            sender.sendMessage(ChatColor.YELLOW + "[ WebMC ] Attempting to stop web server!" + ChatColor.RESET);
            webserver.stop();
            if (!webserver.isAlive()) {
                sender.sendMessage(ChatColor.GREEN + "[ WebMC ] Successfully stopped web server!" + ChatColor.RESET);
                sender.sendMessage("[ WebMC ] You can always restart it by using \"/startweb\"");
            } else {
                sender.sendMessage(ChatColor.RED + "[ WebMC ] Something went wrong file stopping the server!" + ChatColor.RESET);
            }
        } else {
            sender.sendMessage(ChatColor.YELLOW + "[ WebMC ] Web server is already offline!" + ChatColor.RESET);
        }
        return true;
    }
}
