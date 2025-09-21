package net.ravinvt.webMC;

import fi.iki.elonen.NanoHTTPD;
import net.ravinvt.webMC.commands.AliveWebCommand;
import net.ravinvt.webMC.commands.StartWebCommand;
import net.ravinvt.webMC.commands.StopWebCommand;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;

import static net.ravinvt.webMC.Utils.saveResourceIfNotExists;

public final class WebMC extends JavaPlugin {
    public static JavaPlugin plugin;
    public static Logger logger;
    public static SimpleWebServer webserver;
    public static YamlConfiguration config;
    public static File basepath;
    public static File htmlpath;
    private static final Integer version = 1;
    public static Map<String, UUID> pendingLinks = new HashMap<>();
    public static YamlConfiguration rules;

    @Override
    public void onEnable() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }
        basepath = getDataFolder();

        htmlpath = new File(basepath, "html");
        logger = getLogger();
        plugin = this;
        File config_file = new File(basepath, "config.yml");
        if (!config_file.exists()) {
            saveDefaultConfig();
            logger.config("New config created from the default config!");
        }
        config = YamlConfiguration.loadConfiguration(config_file);
        if (config.getInt("version") < version) {
            logger.info("Heads up, current config seems to be behind the current version, this may not affect anything but it is recommended to update your config, todo so copy the contents of your config and then delete it!");
        }

        saveResourceIfNotExists("rules.yml", "rules.yml");
        saveResourceIfNotExists("html/home.html", "html/home.html");
        saveResourceIfNotExists("html/link.html", "html/link.html");

        File datapath = new File(basepath, "data");
        if (!datapath.exists()) {
            datapath.mkdirs();
        }

        File rulespath = new File(basepath, "rules.yml");
        rules = YamlConfiguration.loadConfiguration(rulespath);

        if (rules.getInt("version") < version) {
            logger.warning("Heads up, routes are outdated, this might not be an issue!");
        }

        logger.info("Loading WebMC");
        try {
            webserver = new SimpleWebServer(config.getInt("port"));
            webserver.start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
            logger.info("Started WebMC on port: " + webserver.getListeningPort());
        } catch (IOException e) {
            logger.severe("Failed to start web server: " + e.getMessage());
            e.printStackTrace();
        }

        logger.info("Starting WebMC health checker");
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            if (webserver != null) {
                logger.info("Web server alive: " + webserver.isAlive());
            } else {
                logger.info("Web server instance is null!");
            }
        }, 0L, 30 * 60 * 20L);
        logger.info("Started WebMC health checker");

        logger.info("Loading commands");
        Objects.requireNonNull(getCommand("alive-web")).setExecutor(new AliveWebCommand());
        Objects.requireNonNull(getCommand("start-web")).setExecutor(new StartWebCommand());
        Objects.requireNonNull(getCommand("stop-web")).setExecutor(new StopWebCommand());
        logger.info("Loaded commands");
    }

    @Override
    public void onDisable() {
        logger.info("Stopping WebMC");
        if (webserver != null) {
            webserver.stop();
            logger.info("Web server stopped");
        }
    }
}
