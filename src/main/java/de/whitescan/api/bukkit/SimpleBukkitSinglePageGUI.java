package de.whitescan.api.bukkit;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.plugin.Plugin;

import lombok.NonNull;

/**
 * 
 * @author Whitescan
 *
 */
public class SimpleBukkitSinglePageGUI extends BukkitSinglePageGUI {

	public SimpleBukkitSinglePageGUI(@NonNull Plugin plugin, @NonNull String title) {
		super(plugin, title);
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
