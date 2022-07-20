import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class InventoryEditor {

    private @Nullable InventoryHolder owner = null;

    @Nullable
    public InventoryHolder getOwner() {
        return owner;
    }

    public InventoryEditor setOwner(@Nullable InventoryHolder owner) {
        this.owner = owner;
        return this;
    }

    private @Nullable InventoryType type = null;

    @Nullable
    public InventoryType getType() {
        return type;
    }

    public InventoryEditor setType(@Nullable InventoryType type) {
        this.type = type;
        if (type != null) {
            this.size = type.getDefaultSize();
            if (title.isEmpty()) {
                title = type.getDefaultTitle();
            }
        }
        return this;
    }

    private int size = 9;

    public int getSize() {
        if (type != null) {
            size = type.getDefaultSize();
        }
        return size;
    }

    public InventoryEditor setSize(int size) {
        if (size % 9 != 0) return null;
        this.size = size;
        return this;
    }

    private @NotNull String title = "";

    @NotNull
    public String getTitle() {
        if (type != null && title.isEmpty()) {
            title = type.getDefaultTitle();
        }
        return title;
    }

    public InventoryEditor setTitle(@NotNull String title) {
        this.title = title;
        return this;
    }

    private Inventory inventory;

    @NotNull
    public Inventory getInventory() {
        if (inventory == null) {
            if (type != null) {
                if (title.isEmpty()) {
                    return Bukkit.createInventory(owner, type);
                } else {
                    return Bukkit.createInventory(owner, type, title);
                }
            } else {
                return Bukkit.createInventory(owner, size, title);
            }
        }
        return inventory;
    }

    public ItemStack getItem(int index) {
        return inventory.getItem(index);
    }

    public InventoryEditor setItem(int index, ItemStack itemStack) {
        inventory.setItem(index, itemStack);
        return this;
    }

    public void openDefinitely(@NotNull HumanEntity humanEntity) {
        humanEntity.closeInventory();
        open(humanEntity);
    }

    public void open(@NotNull HumanEntity humanEntity) {
        humanEntity.openInventory(inventory);
    }

}
