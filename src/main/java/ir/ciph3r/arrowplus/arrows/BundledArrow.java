package ir.ciph3r.arrowplus.arrows;

import ir.ciph3r.arrowplus.ArrowPlus;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Arrow;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ShapelessRecipe;

import java.util.Random;

public class BundledArrow extends ArrowModel{
    public BundledArrow() {
        super("BundleArrow", "§eBundle Of Arrows", 100, "§5Fires Multiple Arrows");
    }

    @Override
    public ShapelessRecipe arrowRecipe() {
        return null;
    }

    @Override
    public void onBowShoot(EntityShootBowEvent event) {
        Random random = new Random();

        Arrow arrow = (Arrow) event.getProjectile();

        for (int i = 0; i < 4; i++) {
            Arrow arr = event.getEntity().getWorld().spawn(event.getEntity().getEyeLocation(), Arrow.class);
            arr.setShooter(event.getEntity());
            arr.setVelocity(arrow.getVelocity().rotateAroundY(Math.toRadians(random.nextInt(15 + 1 - (-15)) + (-15))));
        }
    }
}
