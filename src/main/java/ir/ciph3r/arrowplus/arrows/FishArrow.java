package ir.ciph3r.arrowplus.arrows;

import ir.ciph3r.arrowplus.ArrowPlus;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
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

public class FishArrow implements Listener {
	public static ItemStack arrow;
	private final String keyValue = "FishArrow";
	private final NamespacedKey key = new NamespacedKey(ArrowPlus.getInt(), keyValue);
	private static final List<EntityType> fishTypes = new ArrayList<>();

	public void createItem() {
		ItemStack itemStack = new ItemStack(Material.ARROW, 1);
		ItemMeta itemMeta = itemStack.getItemMeta();
		List<String> lore = new ArrayList<>();

		itemMeta.setDisplayName("§eFish Arrow");
		lore.add("§5When Hitting A Mob");
		lore.add("§5It Turns The Mob To A Variant Of A Fish");
		itemMeta.setLore(lore);
		itemMeta.setCustomModelData(500);
		itemMeta.getPersistentDataContainer().set(key, PersistentDataType.DOUBLE, Math.PI);
		itemStack.setItemMeta(itemMeta);
		arrow = itemStack;

		ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(ArrowPlus.getInt(), keyValue), arrow);
		sr.shape("SCS", "CAC", "SCS");
		sr.setIngredient('S', Material.SALMON);
		sr.setIngredient('C', Material.COD);
		sr.setIngredient('A', new RecipeChoice.ExactChoice(BundleArrow.arrow));
		Bukkit.getServer().addRecipe(sr);

		fishTypes.add(EntityType.AXOLOTL);
		fishTypes.add(EntityType.COD);
		fishTypes.add(EntityType.DOLPHIN);
		fishTypes.add(EntityType.PUFFERFISH);
		fishTypes.add(EntityType.SALMON);
		fishTypes.add(EntityType.TROPICAL_FISH);
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
	public void onHit(ProjectileHitEvent event) {
		if (event.getHitEntity() instanceof Player || event.getHitEntity() instanceof Wither ||  event.getHitEntity() instanceof EnderDragon) return;
		if (!(event.getEntity().hasMetadata(keyValue))) return;
		if (event.getHitEntity() instanceof Player) return;
		if(event.getHitEntity() == null) return;

		Entity entity = event.getHitEntity();
		event.getEntity().remove();
		entity.getWorld().spawnParticle(Particle.CLOUD,entity.getLocation(),10);
		entity.remove();
		entity.getWorld().spawnEntity(entity.getLocation(), fishTypes.get(new Random().nextInt(fishTypes.size())));
	}
}
