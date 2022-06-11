import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ensyasir@gmail.com
 */
@Getter
@Setter
public class BossBarStabilizer implements Cloneable {

    private @NotNull String title;

    private @NotNull BarColor color;

    private @NotNull BarStyle style;

    private final @NotNull Set<@NotNull BarFlag> flags;

    public void addFlag(@NotNull BarFlag flag) {
        flags.add(flag);
    }

    public void removeFlag(@NotNull BarFlag flag) {
        flags.remove(flag);
    }

    public boolean hasFlag(@NotNull BarFlag flag) {
        return flags.contains(flag);
    }

    private final @NotNull BossBar bossBar;

    public void reloadBossBarFromData() {
        bossBar.setTitle(title);
        bossBar.setColor(color);
        bossBar.setStyle(style);
        Arrays.stream(BarFlag.values()).filter(bossBar::hasFlag).toList().forEach(bossBar::removeFlag);
        flags.forEach(bossBar::addFlag);
    }

    public BossBarStabilizer(@NotNull String title, @NotNull BarColor color, @NotNull BarStyle style) {
        this.title = ChatUtil.tacc(title);
        this.color = color;
        this.style = style;
        this.flags = new HashSet<>();
        this.bossBar = Bukkit.createBossBar(title, color, style);
    }

    public BossBarStabilizer(@NotNull String title, @NotNull BarColor color, @NotNull BarStyle style, @NotNull BarFlag... flags) {
        this.title = ChatUtil.tacc(title);
        this.color = color;
        this.style = style;
        this.flags = Set.of(flags);
        this.bossBar = Bukkit.createBossBar(title, color, style);
        this.flags.forEach(bossBar::addFlag);
    }

    public BossBarStabilizer(@NotNull BossBar bossBar) {
        this.title = ChatUtil.tacc(bossBar.getTitle());
        this.color = bossBar.getColor();
        this.style = bossBar.getStyle();
        this.flags = new HashSet<>(Arrays.stream(BarFlag.values()).filter(bossBar::hasFlag).toList());
        this.bossBar = bossBar;
    }

    @Override
    public BossBarStabilizer clone() {
        try {
            return (BossBarStabilizer) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

}
