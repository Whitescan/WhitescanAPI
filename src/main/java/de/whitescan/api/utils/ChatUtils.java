package de.whitescan.api.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

	public static String translateCodes(String input) {
		return ChatColor.translateAlternateColorCodes('&', input);
	}

	public static String escape(String message) {
		message = message.replace("%", "%%");
		return message;
	}

	public static boolean isCaps(String message) {

		if (message.length() <= 4)
			return false;

		int upperCase = 0;

		for (int k = 0; k < message.length(); k++)
			if (Character.isUpperCase(message.charAt(k)))
				upperCase++;

		return (double) upperCase / message.length() >= 0.50;

	}

	public static List<String> findBadwords(String input, Map<String, String[]> badwords) {

		if (input == null)
			return new ArrayList<>();

		// Common Aliases
		input = input.replaceAll("1", "i");
		input = input.replaceAll("!", "i");
		input = input.replaceAll("3", "e");
		input = input.replaceAll("4", "a");
		input = input.replaceAll("@", "a");
		input = input.replaceAll("5", "s");
		input = input.replaceAll("7", "t");
		input = input.replaceAll("0", "o");
		input = input.replaceAll("9", "g");
		input = input.toLowerCase().replaceAll("[^a-zA-Z]", "");

		List<String> found = new ArrayList<>();

		long largestWordLength = 0;

		for (String word : badwords.keySet()) {
			if (word.length() > largestWordLength) {
				largestWordLength = word.length();
			}
		}

		for (int start = 0; start < input.length(); start++) {

			for (int offset = 1; offset < (input.length() + 1 - start) && offset < largestWordLength; offset++) {

				String currentWord = input.substring(start, start + offset);

				if (badwords.containsKey(currentWord)) {

					String[] ignoreCheck = badwords.get(currentWord);

					boolean ignore = false;

					for (int pos = 0; pos < ignoreCheck.length; pos++) {
						if (input.contains(ignoreCheck[pos])) {
							ignore = true;
							break;
						}
					}

					if (!ignore)
						found.add(currentWord);

				}
			}
		}

		return found;

	}

}
