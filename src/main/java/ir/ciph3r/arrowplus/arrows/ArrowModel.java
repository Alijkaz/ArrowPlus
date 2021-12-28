package ir.ciph3r.arrowplus.arrows;

import ir.ciph3r.arrowplus.ArrowPlus;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ArrowModel {
	private ItemStack arrow;
	private String displayName;
	private String metaValue;
	private List<String> lore = new ArrayList<>();
	private int customModelData;
	private NamespacedKey key;

	public ArrowModel(String displayName, String metaValue, int customModelData, String... lore) {
		this.displayName = displayName;
		this.metaValue = metaValue;
		this.customModelData = customModelData;
		this.lore.addAll(Arrays.asList(lore));
		this.key = new NamespacedKey(ArrowPlus.getInt(), metaValue);
	}

	public abstract void createItem();

	public abstract void createRecipe();

	public String getMetaValue() {
		return metaValue;
	}

	public NamespacedKey getKey() {
		return key;
	}
}
