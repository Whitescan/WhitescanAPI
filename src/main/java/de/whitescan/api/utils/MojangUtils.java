package de.whitescan.api.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.TreeMap;
import java.util.UUID;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 *
 * @author Whitescan
 *
 */
public class MojangUtils {

	private static final String MOJANG_API_ROOT = "https://api.mojang.com/";
	private static final String MOJANG_USERS_URL = MOJANG_API_ROOT + "users/profiles/minecraft/";
	private static final String MOJANG_USER_URL = MOJANG_API_ROOT + "user/profiles/";

	/**
	 * Use the Mojang API to grab the UUID of a username.
	 *
	 * Make sure to cache results because to many requests to the Mojang API result in a block.
	 *
	 * @param playerName
	 * @return the uuid of the given player, may be null when the request does not return a valid response.
	 */
	public static UUID getUniqueId(String playerName) {

		try {

			URL url = new URL(MOJANG_USERS_URL + playerName);
			URLConnection request = url.openConnection();
			request.connect();

			JsonElement root = JsonParser.parseReader(new InputStreamReader((InputStream) request.getContent()));
			return getUniqueIdFromString(root.getAsJsonObject().get("id").getAsString());

		} catch (NullPointerException e) {

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * Use the Mojang API to grab the latest username of an unique id.
	 *
	 * Make sure to cache results because to many requests to the Mojang API result in a block.
	 *
	 * @param uniqueId
	 * @return the latest username, may be null when the request does not return a valid response.
	 */
	public static String getLatestName(UUID uniqueId) {

		try {

			// Connect to the URL using java's native library
			URL url = new URL(MOJANG_USER_URL + uniqueId.toString().replace("-", "") + "/names");
			URLConnection request = url.openConnection();
			request.connect();

			JsonElement root = JsonParser.parseReader(new InputStreamReader((InputStream) request.getContent()));
			JsonArray rootarray = root.getAsJsonArray().getAsJsonArray();
			JsonElement element = rootarray.get(rootarray.size() - 1);
			return element.getAsJsonObject().get("name").getAsString();

		} catch (NullPointerException e) {

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * Used the Mojang API to retrieve a complete Name History from the given UUID.
	 *
	 * The result is sorted with an <TreeMap> by the time value the player changed his name. The value will be 0 if it's the
	 * original name.
	 *
	 * Make sure to cache results because to many requests to the Mojang API result in a block.
	 *
	 * @param uniqueId
	 * @return the name history of a player, may be null when the request does not return a valid response.
	 */
	public static TreeMap<Long, String> getNameHistory(UUID uniqueId) {

		try {

			// Connect to the URL using java's native library
			URL url = new URL(MOJANG_USER_URL + uniqueId.toString().replace("-", "") + "/names");
			URLConnection request = url.openConnection();
			request.connect();

			JsonElement root = JsonParser.parseReader(new InputStreamReader((InputStream) request.getContent()));
			JsonArray rootarray = root.getAsJsonArray();

			TreeMap<Long, String> history = new TreeMap<>();

			for (JsonElement element : rootarray) {
				JsonObject obj = element.getAsJsonObject();
				JsonElement changed = obj.get("changedToAt");
				long change = changed != null ? changed.getAsLong() : 0;
				history.put(change, obj.get("name").getAsString());
			}

			return history;

		} catch (NullPointerException e) {

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Used to convert Strings to UUIDs since they may be stored without special characters.
	 *
	 * @param uniqueId
	 * @return a correctly formed UUID from the given string
	 */
	public static UUID getUniqueIdFromString(String uniqueId) {
		return UUID.fromString(uniqueId.replaceFirst("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5"));
	}

}
