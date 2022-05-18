package de.whitescan.api.share.config;

import java.io.File;

import lombok.AllArgsConstructor;

/**
 *
 * @author Whitescan
 *
 */
@AllArgsConstructor
public abstract class AbstractConfigHandler {

	protected final File resourceFile;

	protected final File configFile;

	protected abstract void load();

	protected abstract void saveConfig();

	protected abstract void read();

	public abstract void reload();

}
