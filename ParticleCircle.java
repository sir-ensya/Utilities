import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

/**
 * @author ensyasir@gmail.com
 */
@Getter
@Setter
public class ParticleCircle {

    private @NotNull Particle particle;
    private int count;
    private @NotNull Particle.DustOptions options;

    public ParticleCircle(@NotNull Particle particle, int count, @NotNull Particle.DustOptions options) {
        this.particle = particle;
        this.count = count;
        this.options = options;
    }

    public void display(@NotNull Location location, @NotNull BlockFace face, float radius, double t, double tplus) {
        Vector direction = location.getDirection().normalize();
        float yaw = location.getYaw();
        if (face.getModX() == 0 && face.getModY() == 0) {
            for (double t_ = tplus; t_ <= t; t_ += tplus) {
                double x = direction.getX() + radius * (float) Math.sin(t_);
                double y = direction.getY() + radius * (float) Math.cos(t_);
                location.add(x, y, 0);
                location.getWorld().spawnParticle(particle, location.getX(), location.getY(), location.getZ(), count, options);
                location.subtract(x, y, 0);
            }
        } else if (face.getModY() == 0 && face.getModZ() == 0) {
            for (double t_ = tplus; t_ <= t; t_ += tplus) {
                double y = direction.getY() + radius * (float) Math.cos(t_);
                double z = direction.getZ() + radius * (float) Math.sin(t_);
                location.add(0, y, z);
                location.getWorld().spawnParticle(particle, location.getX(), location.getY(), location.getZ(), count, options);
                location.subtract(0, y, z);
            }
        } else if (face.getModX() == 0 && face.getModZ() == 0) {
            for (double t_ = tplus; t_ <= t; t_ += tplus) {
                double x = direction.getX() + radius * (float) Math.sin(t_);
                double z = direction.getZ() + radius * (float) Math.cos(t_);
                location.add(x, 0, z);
                location.getWorld().spawnParticle(particle, location.getX(), location.getY(), location.getZ(), count, options);
                location.subtract(x, 0, z);
            }
        }
    }

}
