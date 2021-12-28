package ir.ciph3r.arrowplus;

import ir.ciph3r.arrowplus.arrows.*;
import org.bukkit.plugin.java.JavaPlugin;

public final class ArrowPlus extends JavaPlugin {
	private static ArrowPlus plugin;

	@Override
	public void onEnable() {
		plugin = this;

		new BundledArrow();
		new DiamondArrow().createItem();
		new FishArrow().createItem();
		new CrystalArrow().createItem();
		new InfinityArrow().createItem();

		getServer().getPluginManager().registerEvents(new BundledArrow(), this);
		getServer().getPluginManager().registerEvents(new DiamondArrow(), this);
		getServer().getPluginManager().registerEvents(new FishArrow(), this);
		getServer().getPluginManager().registerEvents(new CrystalArrow(), this);
		getServer().getPluginManager().registerEvents(new InfinityArrow(), this);
	}

	@Override
	public void onDisable() {
		plugin = null;
	}

	public static ArrowPlus getInt() {
		return plugin;
	}
}
