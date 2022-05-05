import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Objects;

/**
 * @author ensyasir@gmail.com
 */
public class StringLocation {

    private String worldName;
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;

    public StringLocation(@NotNull String text) throws InvalidPropertiesFormatException {
        if (isStringLocation(text)) {
            throw new InvalidPropertiesFormatException("Can't read location keys and values while trying to create string location!");
        }
        HashMap<String, String> keyAndValues = new HashMap<>();
        for (String split : text.split("")) {
            String[] keyAndValue = split.split(":");
            keyAndValues.put(keyAndValue[0], keyAndValue[1]);
        }
        try {
            Bukkit.getWorld(keyAndValues.get("world"));
        } catch (NullPointerException exception) {
            throw new NullPointerException("Can't found world while trying to create string location!");
        }

        try {
            this.worldName = Objects.requireNonNull(Bukkit.getWorld(keyAndValues.get("worldName")), "Defined world is null!").getName();
            this.x = Double.parseDouble(keyAndValues.get("x"));
            this.y = Double.parseDouble(keyAndValues.get("y"));
            this.z = Double.parseDouble(keyAndValues.get("z"));
            this.yaw = keyAndValues.containsKey("yaw") ? Float.parseFloat(keyAndValues.get("yaw")) : 0;
            this.pitch = keyAndValues.containsKey("pitch") ? Float.parseFloat(keyAndValues.get("pitch")) : 0;
        } catch (NumberFormatException exception) {
            throw new NumberFormatException("Invalid location value numbers while trying to create string location!");
        }

    }

    public StringLocation(@NotNull Location location) {
        this.worldName = Objects.requireNonNull(location.getWorld()).getName();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
    }

    public StringLocation(String worldName, double x, double y, double z, float yaw, float pitch) {
        this.worldName = worldName;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public StringLocation(String worldName, double x, double y, double z) {
        this.worldName = worldName;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = 0;
        this.pitch = 0;
    }

    public static boolean isStringLocation(@NotNull String textLocation) {
        if (textLocation.split(",").length < 3) return false;
        for (String s : List.of("x", "y", "z", "yaw", "pitch")) {
            if (!textLocation.contains(s)) return false;
        }
        return true;
    }

    public @Nullable Location toLocation() {
        if (Bukkit.getWorld(worldName) == null) return null;
        return new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
    }
    
    public StringLocation setWorldName(@NotNull World world) {
        this.worldName = world.getName();
        return this;
    }
    
    public StringLocation setX(double x) {
        this.x = x;
        return this;
    }
    
    public StringLocation setY(double y) {
        this.y = y;
        return this;
    }
    
    public StringLocation setZ(double z) {
        this.z = z;
        return this;
    }
    
    public StringLocation setYaw(float yaw) {
        this.yaw = yaw;
        return this;
    }
    
    public StringLocation setPitch(float pitch) {
        this.pitch = pitch;
        return this;
    }

    public String getWorldName() {
        return worldName;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

}
