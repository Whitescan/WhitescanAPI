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
import lombok.Setter;

/**
 * 
 * @author Whitescan
 *
 */
public abstract class BukkitMultiPageGUI implements Listener {

	// Settings

	@Getter
	private String title;

	@Getter
	private List<Inventory> pages = new ArrayList<>();

	@Getter
	@Setter
	private Map<Integer, ItemStack> navbar = new HashMap<>();

	// Runtime

	@Getter
	private Map<Player, Inventory> open = new HashMap<>();

	public BukkitMultiPageGUI(@NonNull Plugin plugin, @NonNull String title, @NonNull List<ItemStack> items) {
		this.title = ChatUtils.translateHexCodes(title, true);
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	public BukkitMultiPageGUI(@NonNull Plugin plugin, @NonNull String title, @NonNull List<ItemStack> items,
			@NonNull Map<Integer, ItemStack> navbar) {
		this.title = ChatUtils.translateHexCodes(title, true);
		this.navbar = navbar;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	protected void calculatePages(List<ItemStack> items) {

		Inventory page = getBlankPage();

		int slot = 0;

		for (ItemStack item : items) {

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
			onInteract(e);
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = false)
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player actor && e.getView().getTitle().startsWith(getTitle()))
			onClick(e);
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = false)
	public void onInventoryDrag(InventoryDragEvent e) {
		if (e.getWhoClicked() instanceof Player actor && e.getView().getTitle().startsWith(getTitle()))
			onDrag(e);
	}

	/**
	 * This is the root for {@link #onClick(InventoryClickEvent)} and {@link #onDrag(InventoryDragEvent)}
	 * 
	 * @param e
	 */
	public abstract void onInteract(InventoryInteractEvent e);

	/**
	 * This is being called when the inventory has been clicked.
	 * 
	 * See also {@link #onDrag(InventoryDragEvent)}
	 * 
	 * @param e
	 */
	public abstract void onClick(InventoryClickEvent e);

	/**
	 * This is being called when when someone attempts to drag items from or to this inventory.
	 * 
	 * See also {@link #onClick(InventoryClickEvent)}
	 * 
	 * @param e
	 */
	public abstract void onDrag(InventoryDragEvent e);
}
