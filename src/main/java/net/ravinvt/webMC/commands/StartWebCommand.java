package net.ravinvt.webMC.commands;

import fi.iki.elonen.NanoHTTPD;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.IOException;

import static net.ravinvt.webMC.WebMC.webserver;

public class StartWebCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String string, String[] args) {
        if (!webserver.isAlive()) {
            sender.sendMessage(ChatColor.YELLOW + "[ WebMC ] Attempting to start web server!" + ChatColor.RESET);
            try {
                webserver.start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
                sender.sendMessage(ChatColor.GREEN + "[ WebMC ] Successfully started web server!" + ChatColor.RESET);
            } catch (IOException e) {
                sender.sendMessage(ChatColor.RED + "[ WebMC ] Something went wrong, check console for more info!" + ChatColor.RESET);
                throw new RuntimeException(e);
            }
        } else {
            sender.sendMessage(ChatColor.YELLOW + "[ WebMC ] Web server is already running!" + ChatColor.RESET);
        }
        return true;
    }
}
