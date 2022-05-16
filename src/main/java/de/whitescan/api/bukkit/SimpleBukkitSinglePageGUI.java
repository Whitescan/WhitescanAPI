package de.whitescan.api.bukkit;

import java.util.List;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import lombok.NonNull;

/**
 *
 * @author Whitescan
 *
 */
public class SimpleBukkitSinglePageGUI extends BukkitSinglePageGUI {

	public SimpleBukkitSinglePageGUI(@NonNull Plugin plugin, @NonNull String title, @NonNull List<ItemStack> items) {
		super(plugin, title, items);
	}

	@Override
	public void onInteract(InventoryInteractEvent e) {
		e.setCancelled(true);
	}

	@Override
	public void onClick(InventoryClickEvent e) {
		e.setCancelled(true);
	}

	@Override
	public void onDrag(InventoryDragEvent e) {
		e.setCancelled(true);
	}

}
