package de.whitescan.api.bukkit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import de.whitescan.api.utils.ChatUtils;
import lombok.Getter;
import lombok.NonNull;

/**
 * 
 * @author Whitescan
 *
 */
public class BukkitGUI implements Listener {

	// Settings

	@Getter
	private String title;

	@Getter
	private List<Inventory> pages = new ArrayList<>();

	@Getter
	private Map<Integer, ItemStack> navbar = new HashMap<>();

	@Getter
	private InventoryActionHandler handler;

	// Runtime

	@Getter
	private Map<Player, Inventory> open = new HashMap<>();

	public BukkitGUI(@NonNull Plugin plugin, @NonNull String title, InventoryActionHandler handler, List<ItemStack> items) {
		this.title = ChatUtils.translateHexCodes(title, true);
		this.handler = handler;
		Bukkit.getPluginManager().registerEvents(this, plugin);
		calculatePages(items);
	}

	private void calculatePages(List<ItemStack> items) {

		Inventory page = getBlankPage();

		for (int item = 0; item < items.size(); item++) {

			if (page.firstEmpty() == 46) {
				getPages().add(page);
				page = getBlankPage();
				page.addItem(items.get(item));

			} else {
				page.addItem(items.get(item));
			}

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
		}

		return counter;

	}

	public void closeAll() {
		for (Player p : getOpen().keySet())
			p.closeInventory();
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onQuit(PlayerQuitEvent e) {
		open.remove(e.getPlayer());
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onInventoryOpen(InventoryOpenEvent e) {
		if (e.getPlayer() instanceof Player actor && e.getView().getTitle().startsWith(getTitle()))
			getOpen().put(actor, e.getInventory());
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onInventoryClose(InventoryCloseEvent e) {
		if (e.getPlayer() instanceof Player actor && e.getView().getTitle().startsWith(getTitle()))
			getOpen().remove(actor);
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = false)
	public void onInventoryInteract(InventoryInteractEvent e) {
		if (e.getWhoClicked() instanceof Player actor && e.getView().getTitle().startsWith(getTitle()))
			getHandler().onInteract(e);
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = false)
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player actor && e.getView().getTitle().startsWith(getTitle()))
			getHandler().onClick(e);
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = false)
	public void onInventoryDrag(InventoryDragEvent e) {
		if (e.getWhoClicked() instanceof Player actor && e.getView().getTitle().startsWith(getTitle()))
			getHandler().onDrag(e);
	}
}
