package ir.ciph3r.arrowplus.arrows;

import ir.ciph3r.arrowplus.ArrowPlus;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ArrowModel implements Listener {
    @Getter private static ItemStack arrow;
    @Getter private final String keyValue;
    @Getter private final NamespacedKey key;

    @Getter @Setter private static boolean isRecipeAdded = false;
    @Getter private final String name;
    @Getter private final List<String> loreList = new ArrayList<>();
    @Getter private final int modelData;


    public ArrowModel(String keyValue, String name, int modelData, String... lore) {
        this.keyValue = keyValue;
        this.key = new NamespacedKey(ArrowPlus.getInt(), this.keyValue);
        this.name = name;
        loreList.addAll(Arrays.asList(lore));
        this.modelData = modelData;
        createItem();
        addRecipe();
    }

    private void createItem() {
        ItemStack itemStack = new ItemStack(Material.ARROW, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(this.name);
        itemMeta.setLore(loreList);
        itemMeta.setCustomModelData(100);
        itemMeta.getPersistentDataContainer().set(key, PersistentDataType.DOUBLE, Math.PI);
        itemStack.setItemMeta(itemMeta);
        arrow = itemStack;
    }

    @EventHandler
    public void onLaunch(EntityShootBowEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        if (event.getConsumable() == null) return;
        if (event.getConsumable().getItemMeta() == null) return;

        ItemStack itemStack = event.getConsumable();
        ItemMeta itemMeta = itemStack.getItemMeta();
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();

        if (container.has(key, PersistentDataType.DOUBLE)) {
            event.getProjectile().setMetadata(keyValue, new FixedMetadataValue(ArrowPlus.getInt(), event.getProjectile()));
            onBowShoot(event);
        }
    }

    private void addRecipe() {
        if(isRecipeAdded()) return;

        ShapelessRecipe sr = new ShapelessRecipe(new NamespacedKey(ArrowPlus.getInt(), getKeyValue()), getArrow());
        sr.addIngredient(9, Material.ARROW);
        Bukkit.getServer().addRecipe(sr);

        setRecipeAdded(true);
    }

    /**
     * Add recipe to your Arrow
     */
    public abstract Recipe arrowRecipe();

    /**
     * Your custom actions on EntityBowShootEvent
     */
    public void onBowShoot(EntityShootBowEvent e) {}


}
