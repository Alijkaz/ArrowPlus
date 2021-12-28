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
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class CrystalArrow implements Listener {
	public static ItemStack arrow;
	private final String keyValue = "CrystalArrow";
	private final NamespacedKey key = new NamespacedKey(ArrowPlus.getInt(), keyValue);

	public void createItem() {
		ItemStack itemStack = new ItemStack(Material.ARROW, 1);
		ItemMeta itemMeta = itemStack.getItemMeta();
		List<String> lore = new ArrayList<>();

		itemMeta.setDisplayName("§dEnd Crystal Arrow");
		lore.add("§5Fast, Laser Like Projectile");
		lore.add("§aPierces Mobs");
		itemMeta.setLore(lore);
		itemMeta.setCustomModelData(400);
		itemMeta.getPersistentDataContainer().set(key, PersistentDataType.DOUBLE, Math.PI);
		itemStack.setItemMeta(itemMeta);
		arrow = itemStack;

		ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(ArrowPlus.getInt(), keyValue), arrow);
		sr.shape("F  ", " E ", "   ");
		sr.setIngredient('F', new RecipeChoice.ExactChoice(FishArrow.arrow));
		sr.setIngredient('E', Material.END_CRYSTAL);
		Bukkit.getServer().addRecipe(sr);
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
			Arrow arr = (Arrow) event.getProjectile();
			arr.setGravity(false);
			arr.setShooter(event.getEntity());
			arr.setVelocity(arr.getVelocity().multiply(2));
			arr.setPierceLevel(126);
		}
	}
}
