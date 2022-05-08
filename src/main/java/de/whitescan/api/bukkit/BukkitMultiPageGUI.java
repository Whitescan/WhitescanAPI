package de.whitescan.api.bukkit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * 
 * @author Whitescan
 *
 */
public abstract class BukkitMultiPageGUI extends BukkitGUI implements Listener {

	// Settings

	@Getter
	private List<Inventory> pages = new ArrayList<>();

	@Getter
	@Setter
	private Map<Integer, ItemStack> navbar = new HashMap<>();

	public BukkitMultiPageGUI(@NonNull Plugin plugin, @NonNull String title, @NonNull List<ItemStack> items) {
		super(plugin, title, items);
	}

	@Override
	public void populate() {
		
		getPages().clear();

		Inventory page = getBlankPage();

		int slot = 0;

		for (ItemStack item : getItems()) {

			if (slot == 45) {
				getPages().add(page);
				page = getBlankPage();
				slot = 0;

			}

			page.setItem(slot++, item);

		}

		getPages().add(page);

	}

	/**
	 * Create a blank page only containing the navigation bar
	 * 
	 * @return
	 */
	private Inventory getBlankPage() {

		Inventory page = Bukkit.createInventory(null, 54, getTitle());

		for (Entry<Integer, ItemStack> entry : getNavbar().entrySet()) {
			page.setItem(entry.getKey(), entry.getValue());
		}

		return page;

	}

	public void openPage(Player player, int page) {
		Inventory inv = getPages().get(page);
		player.openInventory(inv);
	}

	public int getCurrentPage(Player player) {

		Inventory inventory = getOpen().get(player);

		int counter = 0;

		for (Inventory page : getPages()) {

			if (page.equals(inventory))
				break;

			counter++;

		}

		return counter;

	}
	
	@Override
	public void open(Player player) {
		openPage(player, 0);
	}

}
