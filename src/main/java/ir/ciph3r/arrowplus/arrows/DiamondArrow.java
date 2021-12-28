package ir.ciph3r.arrowplus.arrows;

import ir.ciph3r.arrowplus.ArrowPlus;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class DiamondArrow implements Listener {
	public static ItemStack arrow;
	private final String keyValue = "DiamondArrow";
	private final NamespacedKey key = new NamespacedKey(ArrowPlus.getInt(), "DiamondArrow");
	private static final List<UUID> deadEntities = new ArrayList<>();
	private static final List<ItemStack> lootItems = new ArrayList<>();

	public void createItem() {
		ItemStack itemStack = new ItemStack(Material.ARROW, 1);
		ItemMeta itemMeta = itemStack.getItemMeta();
		List<String> lore = new ArrayList<>();

		itemMeta.setDisplayName("§bDiamond Arrow");
		lore.add("§5Killed Mobs Will Drop");
		lore.add("§5Diamonds And Gold");
		itemMeta.setLore(lore);
		itemMeta.setCustomModelData(200);
		itemMeta.getPersistentDataContainer().set(key, PersistentDataType.DOUBLE, Math.PI);
		itemStack.setItemMeta(itemMeta);
		arrow = itemStack;

		ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(ArrowPlus.getInt(), keyValue), arrow);
		sr.shape("XXX", "XAX", "XXX");
		sr.setIngredient('X', Material.DIAMOND);
		sr.setIngredient('A', new RecipeChoice.ExactChoice(TestArrow.arrow));
		Bukkit.getServer().addRecipe(sr);

		lootItems.add(new ItemStack(Material.DIAMOND, 1));
		lootItems.add(new ItemStack(Material.GOLD_INGOT, 4));
		lootItems.add(new ItemStack(Material.ENDER_PEARL, 1));
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
		}
	}

	@EventHandler
	public void onDeath(EntityDeathEvent event) {
		if (event.getEntity() instanceof Player) return;
		if (!(deadEntities.contains(event.getEntity().getUniqueId()))) return;
		Random random = new Random();

		event.getDrops().add(lootItems.get(random.nextInt(lootItems.size())));
		deadEntities.remove(event.getEntity().getUniqueId());
	}

	@EventHandler
	public void onDMG(EntityDamageByEntityEvent event) {
		if (!(event.getDamager().hasMetadata(keyValue))) return;
		if (event.getEntity() instanceof Player) return;
		if (!(event.getEntity() instanceof LivingEntity)) return;

		LivingEntity entity = (LivingEntity) event.getEntity();
		double newHealth = entity.getHealth() - event.getDamage();

		if (newHealth <= 0) {
			deadEntities.add(entity.getUniqueId());
		}
	}
}
