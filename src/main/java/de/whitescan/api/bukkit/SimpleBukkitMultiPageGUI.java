package de.whitescan.api.bukkit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import de.whitescan.api.utils.BukkitUtils;
import de.whitescan.api.utils.BukkitUtils.SkinMethod;
import lombok.NonNull;

/**
 * 
 * @author Whitescan
 *
 */
public class SimpleBukkitMultiPageGUI extends BukkitMultiPageGUI {

	private ItemStack previousPage;

	private ItemStack nextPage;

	public SimpleBukkitMultiPageGUI(@NonNull Plugin plugin, @NonNull String title, @NonNull List<ItemStack> items) {
		super(plugin, title);
		this.nextPage = BukkitUtils.createHead(SkinMethod.BASE64,
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGFhMTg3ZmVkZTg4ZGUwMDJjYmQ5MzA1NzVlYjdiYTQ4ZDNiMWEwNmQ5NjFiZGM1MzU4MDA3NTBhZjc2NDkyNiJ9fX0=",
				"§6§lNext Page");
		this.previousPage = BukkitUtils.createHead(SkinMethod.BASE64,
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjZkYWI3MjcxZjRmZjA0ZDU0NDAyMTkwNjdhMTA5YjVjMGMxZDFlMDFlYzYwMmMwMDIwNDc2ZjdlYjYxMjE4MCJ9fX0=",
				"§6§lPrivious Page");

		Map<Integer, ItemStack> navbar = new HashMap<Integer, ItemStack>();
		navbar.put(45, previousPage);
		navbar.put(53, nextPage);
		setNavbar(navbar);

		calculatePages(items);

	}

	public SimpleBukkitMultiPageGUI(@NonNull Plugin plugin, @NonNull String title, @NonNull List<ItemStack> items,
			@NonNull ItemStack nextPage, @NonNull ItemStack previousPage) {
		super(plugin, title);
		this.nextPage = nextPage;
		this.previousPage = previousPage;

		Map<Integer, ItemStack> navbar = new HashMap<Integer, ItemStack>();
		navbar.put(45, previousPage);
		navbar.put(53, nextPage);
		setNavbar(navbar);

		calculatePages(items);

	}

	@Override
	public void open(Player player) {
		openPage(player, 0);
	}

	@Override
	public void onInteract(InventoryInteractEvent e) {
		e.setCancelled(true);
	}

	@Override
	public void onClick(InventoryClickEvent e) {
		e.setCancelled(true);

		final ItemStack currentItem = e.getCurrentItem();

		final Player actor = (Player) e.getWhoClicked();

		int currentPage = getCurrentPage(actor);

		if (nextPage.equals(currentItem) && currentPage < getPages().size() - 1) {
			openPage(actor, currentPage + 1);

		} else if (previousPage.equals(currentItem) && currentPage > 0) {
			openPage(actor, currentPage - 1);
		}

	}

	@Override
	public void onDrag(InventoryDragEvent e) {
		e.setCancelled(true);
	}

}
