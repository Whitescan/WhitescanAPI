package de.whitescan.api.bukkit;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.logging.Logger;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import de.whitescan.api.internal.AbstractConfigHandler;

/**
 *
 * @author Whitescan
 *
 */
public abstract class BukkitConfigHandler extends AbstractConfigHandler {

	protected Plugin plugin;

	protected YamlConfiguration config;

	protected Logger logger;

	public BukkitConfigHandler(Plugin plugin, File configFile) {
		super(configFile);
		this.plugin = plugin;
		this.logger = plugin.getLogger();
		load();
		read();
	}

	@Override
	public void load() {

		if (!configFile.getParentFile().exists()) {
			logger.warning("Config folder " + configFile.getParentFile().getAbsolutePath()
					+ " does not exist, creating it... This can be ignored if you are setting this up.");
			configFile.getParentFile().mkdirs();
		}

		if (!configFile.exists()) {
			logger.warning(
					"Config file " + configFile.getAbsolutePath() + " does not exist, creating default... This can be ignored if you are setting this up.");
			try {
				InputStream in = plugin.getResource(configFile.getName());
				Files.copy(in, configFile.toPath());
			} catch (IOException e) {
				logger.warning("Failed to copy default file " + configFile.getAbsolutePath()
						+ " to datafolder. Make sure the file is not in use (system lock) before reporting this as an error!");
				e.printStackTrace();
			}
		}

		this.config = YamlConfiguration.loadConfiguration(configFile);

	}

	@Override
	public void saveConfig() {
		try {
			config.save(configFile);
		} catch (IOException e) {
			logger.severe("Failed to save config file: " + configFile.getAbsolutePath());
			e.printStackTrace();
		}
	}

	@Override
	public void reload() {

		try {

			config.load(configFile);
			read();

		} catch (IOException e) {
			logger.severe("Failed to reload config file: " + configFile.getAbsolutePath());
			e.printStackTrace();

		} catch (InvalidConfigurationException e) {
			logger.severe("Failed to reload config file: " + configFile.getAbsolutePath()
					+ " it appears the file in your file system is broken. If you can't fix it yourself back it up and delete it. A new default will be created.");
			e.printStackTrace();
		}

	}

}
