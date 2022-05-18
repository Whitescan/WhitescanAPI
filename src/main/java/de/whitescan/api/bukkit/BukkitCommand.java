package de.whitescan.api.bukkit;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.md_5.bungee.api.chat.TextComponent;

/**
 *
 * @author Whitescan
 *
 */
@AllArgsConstructor
public abstract class BukkitCommand implements CommandExecutor, TabExecutor {

	@Getter
	private String permission;

	@Getter
	private TextComponent noPermissionMessage;

	protected boolean hasPermission(CommandSender sender) {
		return getPermission() == null || sender.hasPermission(getPermission());
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (hasPermission(sender)) {
			executeCommand(sender, label, args);

		} else {
			sender.spigot().sendMessage(getNoPermissionMessage());
		}

		return true;

	}

	protected abstract void executeCommand(CommandSender sender, String label, String[] args);

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

		if (sender instanceof Player actor && hasPermission(sender)) {
			return tabComplete(actor, label, args);
		}

		return new ArrayList<>();

	}

	protected abstract List<String> tabComplete(Player actor, String label, String[] args);

}
