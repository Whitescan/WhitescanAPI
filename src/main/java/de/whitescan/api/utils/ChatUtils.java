package de.whitescan.api.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.md_5.bungee.api.ChatColor;

/**
 * 
 * @author Whitescan
 *
 */
public class ChatUtils {

	/**
	 * Pattern for HEX codes
	 */
	private static final Pattern HEX_PATTERN = Pattern.compile("#[a-fA-F0-9]{6}");

	/**
	 * Translate HEX codes in chat components.
	 *
	 * @param input
	 * @param formatColors - should format codes also be converted?
	 * @return formatted string
	 */
	public static String translateHexCodes(String input, boolean formatColors) {

		if (formatColors)
			input = ChatColor.translateAlternateColorCodes('&', input);

		Matcher matcher = HEX_PATTERN.matcher(input);

		while (matcher.find()) {
			String color = input.substring(matcher.start(), matcher.end());
			input = input.replace(color, ChatColor.of(color) + "");
			matcher = HEX_PATTERN.matcher(input);
		}

		return input;

	}

}
