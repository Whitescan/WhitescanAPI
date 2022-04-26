package de.whitescan.api.bukkit;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;

/**
 * 
 * @author Whitescan
 *
 */
public interface InventoryActionHandler {

	/**
	 * This is the root for {@link #onClick(InventoryClickEvent)} and {@link #onDrag(InventoryDragEvent)}
	 * 
	 * @param e
	 */
	public void onInteract(InventoryInteractEvent e);

	/**
	 * This is being called when the inventory has been clicked.
	 * 
	 * See also {@link #onDrag(InventoryDragEvent)}
	 * 
	 * @param e
	 */
	public void onClick(InventoryClickEvent e);

	/**
	 * This is being called when when someone attempts to drag items from or to this inventory.
	 * 
	 * See also {@link #onClick(InventoryClickEvent)}
	 * 
	 * @param e
	 */
	public void onDrag(InventoryDragEvent e);

}
