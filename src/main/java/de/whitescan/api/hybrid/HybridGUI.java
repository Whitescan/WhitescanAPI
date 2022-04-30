package de.whitescan.api.hybrid;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.geysermc.floodgate.api.FloodgateApi;

import de.whitescan.api.bedrock.BedrockGUI;
import de.whitescan.api.bukkit.BukkitGUI;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

/**
 * 
 * @author Whitescan
 *
 */
@AllArgsConstructor
public class HybridGUI {

	public static final FloodgateApi FLOODGATE_API = FloodgateApi.getInstance();

	@Getter
	@NonNull
	private BukkitGUI bukkitGUI;

	@Getter
	@NonNull
	private BedrockGUI bedrockGUI;

	public void open(Player player) {

		if (FLOODGATE_API.isFloodgatePlayer(player.getUniqueId())) {
			bedrockGUI.open(FLOODGATE_API.getPlayer(player.getUniqueId()));
		} else {
			bukkitGUI.open(player);
		}

	}

	public void close(Player player) {
		if (FLOODGATE_API.isFloodgatePlayer(player.getUniqueId())) {
//			if (bedrockGUI.getOpen().contains(player))
			// TODO Close Bedrock GUI?
		} else {
			if (bukkitGUI.getOpen().containsKey(player))
				player.closeInventory();
		}
	}

	public void closeAll() {
		for (Player player : Bukkit.getOnlinePlayers())
			close(player);
	}

}
