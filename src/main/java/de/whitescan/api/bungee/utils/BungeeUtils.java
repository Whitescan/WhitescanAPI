package de.whitescan.api.bungee.utils;

import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 *
 * @author Whitescan
 *
 */
public class BungeeUtils {

	/**
	 * Tab complete player names
	 *
	 * @param partial - the input that is already written
	 * @return a list of players matching the partial
	 */
	public static List<String> getProxyPlayers(String partial) {

		List<String> suggestions = new ArrayList<>();

		int lastSpaceIndex = partial.lastIndexOf(' ');

		if (lastSpaceIndex >= 0)
			partial = partial.substring(lastSpaceIndex + 1);

		for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
			if (p.getName().toLowerCase().startsWith(partial))
				suggestions.add(p.getName());
		}

		return suggestions;
	}

}
