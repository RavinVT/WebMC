package net.ravinvt.webMC;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static net.ravinvt.webMC.WebMC.logger;
import static net.ravinvt.webMC.WebMC.plugin;

public class Utils {
    public static String loadFileFromDataFolder(String filename) {
        File file = new File(plugin.getDataFolder(), filename);
        if (!file.exists()) return "File not found: " + filename;

        try {
            return Files.readString(file.toPath(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return "Error reading file: " + filename;
        }
    }

    public static void saveResourceIfNotExists(String resourcePath, String outputName) {
        File outFile = new File(plugin.getDataFolder(), outputName);

        if (!outFile.exists()) {
            outFile.getParentFile().mkdirs();
            try (InputStream in = plugin.getResource(resourcePath)) {
                if (in == null) {
                    logger.warning("Missing resource in JAR: " + resourcePath);
                    return;
                }
                Files.copy(in, outFile.toPath());
                logger.info("Saved default " + outputName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
