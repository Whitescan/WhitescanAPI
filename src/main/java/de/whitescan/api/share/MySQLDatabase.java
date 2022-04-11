package de.whitescan.api.share;

import java.sql.DriverManager;
import java.util.Properties;
import java.util.logging.Logger;

import de.whitescan.api.internal.AbstractDatabase;

/**
 *
 * @author Whitescan
 *
 */
public class MySQLDatabase extends AbstractDatabase {

	private String host;
	private int port;

	private String username;
	private String password;

	public MySQLDatabase(Logger logger, String host, int port, String database, String username, String password, String prefix) {
		super(logger, database, prefix);

		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;

		connect();
	}

	/**
	 * Connect to the database
	 */
	@Override
	public void connect() {

		try {

			final Properties prop = new Properties();
			prop.setProperty("user", username);
			prop.setProperty("password", password);
			prop.setProperty("useSSL", "false");
			prop.setProperty("autoReconnect", "true");

			this.connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, prop);

			logger.info("Connection to hostaddress: " + host + ":" + port + " was successful!");

		} catch (Exception e) {
			logger.severe("Connection to hostaddress: " + host + ":" + port + " failed! Please check credentials before reporting this as an issue...");
			e.printStackTrace();
		}

	}

}
