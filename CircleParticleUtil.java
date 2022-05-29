import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

/**
 * @author ensyasir@gmail.com
 */
public class CircleParticleUtil {

    private CircleParticleUtil() {
        throw new IllegalStateException();
    }

    public static void displayCircle(@NotNull Location location, @NotNull BlockFace face, float radius, double t, double tplus) {
        Vector direction = location.getDirection().normalize();
        float yaw = location.getYaw();
        if (face.getModX() == 0 && face.getModY() == 0) {
            for (double t_ = 0; t_ <= t; t_ += tplus) {
                double x = direction.getX() + radius * (float) Math.sin(t_);
                double y = direction.getY() + radius * (float) Math.cos(t_);
                location.add(x, y, 0);
                location.getWorld().spawnParticle(Particle.REDSTONE, location.getX(), location.getY(), location.getZ(), 0, 0, 0, 0, 1, new Particle.DustOptions(Color.SILVER, 5));
                location.subtract(x, y, 0);
            }
        } else if (face.getModY() == 0 && face.getModZ() == 0) {
            for (double t_ = 0; t_ <= t; t_ += tplus) {
                double y = direction.getY() + radius * (float) Math.cos(t_);
                double z = direction.getZ() + radius * (float) Math.sin(t_);
                location.add(0, y, z);
                location.getWorld().spawnParticle(Particle.REDSTONE, location.getX(), location.getY(), location.getZ(), 0, 0, 0, 0, 1, new Particle.DustOptions(Color.SILVER, 5));
                location.subtract(0, y, z);
            }
        } else if (face.getModX() == 0 && face.getModZ() == 0) {
            for (double t_ = 0; t_ <= t; t_ += tplus) {
                double x = direction.getX() + radius * (float) Math.sin(t_);
                double z = direction.getZ() + radius * (float) Math.cos(t_);
                location.add(x, 0, z);
                location.getWorld().spawnParticle(Particle.REDSTONE, location.getX(), location.getY(), location.getZ(), 0, 0, 0, 0, 1, new Particle.DustOptions(Color.SILVER, 5));
                location.subtract(x, 0, z);
            }
        }
    }

}
