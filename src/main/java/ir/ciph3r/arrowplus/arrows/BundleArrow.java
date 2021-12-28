package ir.ciph3r.arrowplus.arrows;

import ir.ciph3r.arrowplus.ArrowPlus;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BundleArrow implements Listener {
	public static ItemStack arrow;
	private final String keyValue = "BundleArrow";
	private final NamespacedKey key = new NamespacedKey(ArrowPlus.getInt(), keyValue);

	public void createItem() {
		ItemStack itemStack = new ItemStack(Material.ARROW, 1);
		ItemMeta itemMeta = itemStack.getItemMeta();
		List<String> lore = new ArrayList<>();

		itemMeta.setDisplayName("§eBundle Of Arrows");
		lore.add("§5Fires Multiple Arrows");
		itemMeta.setLore(lore);
		itemMeta.setCustomModelData(100);
		itemMeta.getPersistentDataContainer().set(key,PersistentDataType.DOUBLE, Math.PI);
		itemStack.setItemMeta(itemMeta);
		arrow = itemStack;

		ShapelessRecipe sr = new ShapelessRecipe(new NamespacedKey(ArrowPlus.getInt(), keyValue), arrow);
		sr.addIngredient(9, Material.ARROW);
		Bukkit.getServer().addRecipe(sr);
	}

	@EventHandler
	public void onLaunch(EntityShootBowEvent event) {
		if (!(event.getEntity() instanceof Player)) return;
		if (event.getConsumable() == null) return;
		if (event.getConsumable().getItemMeta() == null) return;

		ItemStack itemStack = event.getConsumable();
		ItemMeta itemMeta = itemStack.getItemMeta();
		Random random = new Random();
		PersistentDataContainer container = itemMeta.getPersistentDataContainer();

		if (container.has(key, PersistentDataType.DOUBLE)) {
			Arrow arrow = (Arrow) event.getProjectile();

			for (int i = 0; i < 4; i++) {
				Arrow arr = event.getEntity().getWorld().spawn(event.getEntity().getEyeLocation(), Arrow.class);
				arr.setShooter(event.getEntity());
				arr.setVelocity(arrow.getVelocity().rotateAroundY(Math.toRadians(random.nextInt(15 + 1 - (-15)) + (-15))));
			}
		}
	}
}
