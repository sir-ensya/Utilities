import com.google.common.base.Charsets;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;

/**
 * @author ensyasir@gmail.com
 */
public class ConfigBase {

    private final @NotNull File dataFolder;
    private FileConfiguration newConfig = null;
    private final @NotNull File configFile;

    public ConfigBase(@NotNull File folder, @NotNull String name) {
        this.dataFolder = folder;
        dataFolder.mkdirs();
        this.configFile = new File(dataFolder, name+".yml");
    }

    @NotNull
    public FileConfiguration getConfig() {
        if (newConfig == null) {
            reloadConfig();
        }
        return newConfig;
    }

    @Nullable
    protected final Reader getTextResource(@NotNull String file) {
        final InputStream in = getResource(file);

        return in == null ? null : new InputStreamReader(in, Charsets.UTF_8);
    }

    public void reloadConfig() {
        newConfig = YamlConfiguration.loadConfiguration(configFile);

        final InputStream defConfigStream = getResource(configFile.getName());
        if (defConfigStream == null) {
            return;
        }

        newConfig.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, Charsets.UTF_8)));
    }

    public void saveConfig() {
        try {
            getConfig().save(configFile);
        } catch (IOException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not save config to " + configFile, ex);
        }
    }

    public void saveDefaultConfig() {
        if (!configFile.exists()) {
            saveResource(configFile.getName(), false);
        }
    }

    public void saveResource(@NotNull String resourcePath, boolean replace) {
        if (resourcePath.equals("")) {
            throw new IllegalArgumentException("ResourcePath cannot be null or empty");
        }

        resourcePath = resourcePath.replace('\\', '/');
        InputStream in = getResource(resourcePath);
        if (in == null) {
            throw new IllegalArgumentException("The embedded resource '" + resourcePath + "' cannot be found in Main plugin.");
        }

        File outFile = new File(dataFolder, resourcePath);
        int lastIndex = resourcePath.lastIndexOf('/');
        File outDir = new File(dataFolder, resourcePath.substring(0, Math.max(lastIndex, 0)));

        if (!outDir.exists()) {
            outDir.mkdirs();
        }

        try {
            if (!outFile.exists() || replace) {
                OutputStream out = new FileOutputStream(outFile);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.close();
                in.close();
            } else {
                Bukkit.getLogger().log(Level.WARNING, "Could not save " + outFile.getName() + " to " + outFile + " because " + outFile.getName() + " already exists.");
            }
        } catch (IOException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not save " + outFile.getName() + " to " + outFile, ex);
        }
    }

    @Nullable
    public InputStream getResource(@NotNull String filename) {
        try {
            URL url = Main.getInstance().getClass().getClassLoader().getResource(filename);

            if (url == null) {
                return null;
            }

            URLConnection connection = url.openConnection();
            connection.setUseCaches(false);
            return connection.getInputStream();
        } catch (IOException ex) {
            return null;
        }
    }
    
    public static @NotNull ConfigBase generate(@NotNull File folder, @NotNull String name) {
        return new ConfigBase(folder, name);
    }

}
