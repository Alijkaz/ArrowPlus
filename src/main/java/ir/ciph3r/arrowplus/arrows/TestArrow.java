package ir.ciph3r.arrowplus.arrows;

import ir.ciph3r.arrowplus.ArrowPlus;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;

public class TestArrow extends ArrowModel implements Listener {
	public TestArrow() {
		super("§eBundle Of Arrows","BundleArrow",100, "§5Fires Multiple Arrows");
	}
	public static ItemStack arrow;

	@Override
	public void createItem() {

	}

	@Override
	public void createRecipe() {
		ShapelessRecipe sr = new ShapelessRecipe(new NamespacedKey(ArrowPlus.getInt(), getMetaValue()), getArrow());
		sr.addIngredient(9, Material.ARROW);
		Bukkit.getServer().addRecipe(sr);
	}

//	@EventHandler
//	public void onLaunch(EntityShootBowEvent event) {
//		if (event.isCancelled()) return;
//		if (!(event.getEntity() instanceof Player) || event.getConsumable() == null || event.getConsumable().getItemMeta() == null) return;
//
//		ItemStack itemStack = event.getConsumable();
//		ItemMeta itemMeta = itemStack.getItemMeta();
//		Arrow arrow = (Arrow) event.getProjectile();
//		PersistentDataContainer container = itemMeta.getPersistentDataContainer();
//		double yRotation = Math.toRadians(new Random().nextInt(15 + 1 - (-15)) + (-15));
//		double zRotation = Math.toRadians(new Random().nextInt(5 + 1 - (-5)) + (-5));
//		Vector vector = arrow.getVelocity().setY(yRotation).setZ(zRotation);
//
//		if (container.has(getKey(), PersistentDataType.DOUBLE)) {
//			for (int i = 0; i < 4; i++) {
//				Arrow arr = event.getEntity().getWorld().spawn(event.getEntity().getEyeLocation(), Arrow.class);
//				arr.setShooter(event.getEntity());
//				arr.setDamage(arr.getDamage() / 2);
//				arr.setVelocity(vector);
//			}
//		}
//	}
}
