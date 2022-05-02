package de.whitescan.api.bukkit;

import java.util.HashMap;
import java.util.Map;

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
import org.bukkit.plugin.Plugin;

import de.whitescan.api.utils.ChatUtils;
import lombok.Getter;
import lombok.NonNull;

/**
 * 
 * @author Whitescan
 *
 */
public abstract class BukkitGUI implements Listener {

	// Settings

	@Getter
	private String title;

	// Runtime

	@Getter
	private Map<Player, Inventory> open = new HashMap<>();

	public BukkitGUI(@NonNull Plugin plugin, @NonNull String title) {
		this.title = ChatUtils.translateHexCodes(title, true);
		Bukkit.getPluginManager().registerEvents(this, plugin);
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
		if (e.getPlayer() instanceof Player actor && e.getView().getTitle().startsWith(getTitle())) {
			getOpen().remove(actor);
			onClose(e);
		}
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
	
	public abstract void open(Player player);
	
	public void onClose(InventoryCloseEvent e) {
		
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
