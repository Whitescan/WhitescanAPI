package de.whitescan.api.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import dev.dbassett.skullcreator.SkullCreator;

/**
 * 
 * @author Whitescan
 *
 */
public class BukkitUtils {

	/**
	 * 
	 * @author Whitescan
	 *
	 */
	public enum SkinMethod {
		URL, BASE64, UUID;
	}

	public static ItemStack createItem(String displayName, Material material, int amount, List<String> lores) {
		ItemStack itemStack = new ItemStack(material, amount);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(displayName);
		itemMeta.setLore(lores);
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

	public static ItemStack createItem(String displayName, Material material, List<String> lores) {
		ItemStack itemStack = new ItemStack(material);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(displayName);
		itemMeta.setLore(lores);
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

	public static ItemStack createItem(String displayName, Material material, int amount) {
		ItemStack itemStack = new ItemStack(material, amount);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(displayName);
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

	public static ItemStack createItem(String displayName, Material material) {
		ItemStack itemStack = new ItemStack(material);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(displayName);
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

	public static ItemStack createPotion(PotionType potionType, String displayName, List<String> lores) {
		ItemStack itemStack = new ItemStack(Material.POTION, 1);
		PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();
		potionMeta.clearCustomEffects();
		potionMeta.setBasePotionData(new PotionData(potionType));
		potionMeta.setDisplayName(displayName);
		potionMeta.setLore(lores);
		itemStack.setItemMeta(potionMeta);
		return itemStack;
	}

	public static ItemStack createHead(SkinMethod method, String value) {

		final ItemStack itemStack;

		switch (method) {

		case BASE64:
			itemStack = SkullCreator.itemFromBase64(value);
			break;

		case URL:
			itemStack = SkullCreator.itemFromUrl(value);
			break;

		case UUID:
			itemStack = SkullCreator.itemFromUuid(UUID.fromString(value));
			break;

		default:
			itemStack = new ItemStack(Material.PLAYER_HEAD);
			break;
		}

		SkullMeta itemMeta = (SkullMeta) itemStack.getItemMeta();
		itemStack.setItemMeta(itemMeta);
		return itemStack;

	}

	public static ItemStack createHead(SkinMethod method, String value, String displayName) {
		ItemStack itemStack = createHead(method, value);
		SkullMeta itemMeta = (SkullMeta) itemStack.getItemMeta();
		itemMeta.setDisplayName(displayName);
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

	public static ItemStack createHead(SkinMethod method, String value, String displayName, List<String> lores) {
		ItemStack itemStack = createHead(method, value, displayName);
		SkullMeta itemMeta = (SkullMeta) itemStack.getItemMeta();
		itemMeta.setLore(lores);
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

	/**
	 * Converts the player inventory to a String array of Base64 strings. First string is the content and second string is
	 * the armor.
	 *
	 * @param playerInventory to turn into an array of strings.
	 * @return Array of strings: [ main content, armor content ]
	 * @throws IllegalStateException
	 */
	public static String[] playerInventoryToBase64(PlayerInventory playerInventory) throws IllegalStateException {
		// get the main content part, this doesn't return the armor
		String content = toBase64(playerInventory);
		String armor = itemStackArrayToBase64(playerInventory.getArmorContents());

		return new String[] { content, armor };
	}

	/**
	 *
	 * A method to serialize an {@link ItemStack} array to Base64 String.
	 *
	 * Based off of {@link #toBase64(Inventory)}.
	 *
	 * @param items to turn into a Base64 String.
	 * @return Base64 string of the items.
	 * @throws IllegalStateException
	 */
	public static String itemStackArrayToBase64(ItemStack[] items) throws IllegalStateException {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

			// Write the size of the inventory
			dataOutput.writeInt(items.length);

			// Save every element in the list
			for (ItemStack item : items) {
				dataOutput.writeObject(item);
			}

			// Serialize that array
			dataOutput.close();
			return Base64Coder.encodeLines(outputStream.toByteArray());
		} catch (Exception e) {
			throw new IllegalStateException("Unable to save item stacks.", e);
		}
	}

	/**
	 * A method to serialize an inventory to Base64 string.
	 *
	 * @param inventory to serialize
	 * @return Base64 string of the provided inventory
	 * @throws IllegalStateException
	 */
	public static String toBase64(Inventory inventory) throws IllegalStateException {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

			// Write the size of the inventory
			dataOutput.writeInt(inventory.getSize());

			// Save every element in the list
			for (int i = 0; i < inventory.getSize(); i++) {
				dataOutput.writeObject(inventory.getItem(i));
			}

			// Serialize that array
			dataOutput.close();
			return Base64Coder.encodeLines(outputStream.toByteArray());
		} catch (Exception e) {
			throw new IllegalStateException("Unable to save item stacks.", e);
		}
	}

	/**
	 *
	 * A method to get an {@link Inventory} from an encoded, Base64, string.
	 *
	 * @param data Base64 string of data containing an inventory.
	 * @return Inventory created from the Base64 string.
	 * @throws IOException
	 */
	public static Inventory fromBase64(String data) throws IOException {
		try {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
			BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
			Inventory inventory = Bukkit.getServer().createInventory(null, dataInput.readInt());

			// Read the serialized inventory
			for (int i = 0; i < inventory.getSize(); i++) {
				inventory.setItem(i, (ItemStack) dataInput.readObject());
			}

			dataInput.close();
			return inventory;
		} catch (ClassNotFoundException e) {
			throw new IOException("Unable to decode class type.", e);
		}
	}

	/**
	 * Gets an array of ItemStacks from Base64 string.
	 *
	 * Base off of {@link #fromBase64(String)}.
	 *
	 * @param data Base64 string to convert to ItemStack array.
	 * @return ItemStack array created from the Base64 string.
	 * @throws IOException
	 */
	public static ItemStack[] itemStackArrayFromBase64(String data) throws IOException {
		try {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
			BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
			ItemStack[] items = new ItemStack[dataInput.readInt()];

			// Read the serialized inventory
			for (int i = 0; i < items.length; i++) {
				items[i] = (ItemStack) dataInput.readObject();
			}

			dataInput.close();
			return items;
		} catch (ClassNotFoundException e) {
			throw new IOException("Unable to decode class type.", e);
		}
	}

}
