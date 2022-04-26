package de.whitescan.api.bukkit;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.ItemStack;

import lombok.AllArgsConstructor;

/**
 * 
 * @author Whitescan
 *
 */
@AllArgsConstructor
public class InfoInventoryActionHandler implements InventoryActionHandler {

	private ItemStack nextPage;
	private ItemStack previousPage;

	private BukkitGUI bukkitGUI;

	@Override
	public void onInteract(InventoryInteractEvent e) {
		e.setCancelled(true);
	}

	@Override
	public void onClick(InventoryClickEvent e) {
		e.setCancelled(true);

		final ItemStack currentItem = e.getCurrentItem();

		final Player actor = (Player) e.getWhoClicked();

		int currentPage = bukkitGUI.getCurrentPage(actor);

		if (nextPage.equals(currentItem) && currentPage < bukkitGUI.getPages().size() - 1) {
			currentPage++;
			actor.openInventory(bukkitGUI.getPages().get(currentPage));

		} else if (previousPage.equals(currentItem) && currentPage > 0) {
			currentPage--;
			actor.openInventory(bukkitGUI.getPages().get(currentPage));
		}

	}

	@Override
	public void onDrag(InventoryDragEvent e) {
		e.setCancelled(true);
	}

}
