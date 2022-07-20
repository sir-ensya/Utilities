import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Class created by: ensyasir@gmail.com
 * Do not change. Please ༼ つ ◕_◕ ༽つ
 */
public class ItemBuilder {

    /**
     * Current itemstack
     */
    private final ItemStack itemStack;

    public ItemBuilder(@NotNull Material material) {
        itemStack = new ItemStack(material);
    }

    public ItemBuilder(@NotNull Material material, int amount) {
        itemStack = new ItemStack(material, amount);
    }

    public ItemBuilder(@NotNull ItemStack clone) {
        itemStack = new ItemStack(clone);
    }

    /**
     * @return ItemMeta of itemStack
     */
    public ItemMeta getItemMeta() {
        return itemStack.getItemMeta();
    }
    
    /**
     * Setting custom model data of item meta
     * @param data custom model data
     * @return this with new custom model data
     */
    public ItemBuilder setCustomModelData(Integer data) {
        getItemMeta().setCustomModelData(data);
        return this;
    }

    /**
     * Setting lore of item meta
     * @return this with new lore
     */
    public ItemBuilder setLore(String... lore){
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> texts = new ArrayList<>();
        Arrays.stream(lore).filter(Objects::nonNull).forEach(text -> texts.add(ChatUtil.tacc(text)));
        itemMeta.setLore(texts);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Adding flag to item meta
     * @return this with new item flags
     */
    public ItemBuilder addFlags(ItemFlag... flags){
        ItemMeta itemMeta = itemStack.getItemMeta();
        Arrays.stream(flags).filter(Objects::nonNull).forEach(itemMeta::addItemFlags);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Adding attribute to item meta
     * @return this with new lore
     */
    public ItemBuilder addAttribute(Attribute attribute, AttributeModifier attributeModifier){
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addAttributeModifier(attribute, attributeModifier);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Setting item meta's color if itemstack is leather armor
     * @return this with new leather armor color
     */
    public ItemBuilder setLeatherArmorColor(Color color) {
        if (!(itemStack.getItemMeta() instanceof LeatherArmorMeta itemMeta)) return this;
        itemMeta.setColor(color);
        setItemMeta(itemMeta);
        return this;
    }

    /**
     * Setting item meta unbreakable or breakable
     * @return this with unbreakable or breakable
     */
    public ItemBuilder setUnbreakable(boolean unbreakable) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setUnbreakable(unbreakable);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Setting amount of item stack
     * @return this with new amount
     */
    public ItemBuilder setAmount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    /**
     * Setting new item meta of item stack
     * @return this with new item meta
     */
    public ItemBuilder setItemMeta(ItemMeta itemMeta) {
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Setting new display name of item meta
     * @return this with new display name
     */
    public ItemBuilder setDisplayName(String displayname) {
        ItemMeta itemMeta = getItemMeta();
        itemMeta.setDisplayName(ChatUtil.tacc(displayname));
        setItemMeta(itemMeta);
        return this;
    }

    /**
     * Adding new enchantment to item meta
     * @return this with new enchantment
     */
    public ItemBuilder addEnchant(Enchantment enchantment, int level) {
        ItemMeta itemMeta = getItemMeta();
        itemMeta.addEnchant(enchantment, level, true);
        setItemMeta(itemMeta);
        return this;
    }

    /**
     * Adding new enchantment to item meta
     * @return this with new enchantment
     */
    public ItemBuilder addEnchant(Enchantment enchantment, int level, boolean ignoreLevelRestriction) {
        ItemMeta itemMeta = getItemMeta();
        itemMeta.addEnchant(enchantment, level, ignoreLevelRestriction);
        setItemMeta(itemMeta);
        return this;
    }

    /**
     * Storing a metadata value in persistent data container
     * @return this with new value in persistent data container
     */
    public <T, Z> ItemBuilder pdcSet(@NotNull NamespacedKey key, @NotNull PersistentDataType<T, Z> type, @NotNull Z value) {
        ItemMeta itemMeta = getItemMeta();
        itemMeta.getPersistentDataContainer().set(key, type, value);
        setItemMeta(itemMeta);
        return this;
    }

    /**
     * Removing a key in persistent data container
     * @return this with new persistent data container without defined key
     */
    public ItemBuilder pdcRemove(@NotNull NamespacedKey key) {
        ItemMeta itemMeta = getItemMeta();
        itemMeta.getPersistentDataContainer().remove(key);
        setItemMeta(itemMeta);
        return this;
    }

    /**
     * Checking persistent data container has key with defined persistent data type
     * @return true if persistent data container have defined key and data type. Otherwise returns false
     */
    public <T, Z> boolean pdcHas(@NotNull NamespacedKey key, @NotNull PersistentDataType<T, Z> type) {
        return getItemMeta().getPersistentDataContainer().has(key, type);
    }

    /**
     * Getting metadata value that is stored on the persistent data container
     * @return metadata value matches
     */
    public <T, Z> @Nullable Z pdcGet(@NotNull NamespacedKey key, @NotNull PersistentDataType<T, Z> type) {
        return getItemMeta().getPersistentDataContainer().get(key, type);
    }

    /**
     * Builds item stack
     * @return built item stack
     */
    public ItemStack build() {
        return itemStack;
    }

}
