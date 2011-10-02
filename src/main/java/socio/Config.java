package socio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Config {

	/**
	 * Implementation of a thread safe, synchronized singleton pattern
	 * {@link http://de.wikibooks.org/wiki/Java_Standard:_Muster_Singleton}.
	 */
	private static Config instance;

	public static synchronized Config getInstance() {
		if (Config.instance == null) {
			Config.instance = new Config(false);
		}
		return Config.instance;
	}

	/**
	 * Enables JUnit test mode
	 * 
	 * @return
	 */
	public static synchronized Config testmode() {
		Config.instance = new Config(true);
		return Config.instance;
	}

	private static final Logger LOGGER = Logger.getLogger(Config.class);
	private static final String FILE_NAME = "socio.properties";

	private Properties properties;
	private Properties defaultProperties;

	private Config(Boolean testmode) {
		try {
			defaultProperties = new Properties();
			defaultProperties.load(Launcher.class.getClassLoader().getResourceAsStream("default.properties"));
		} catch (Exception e) {
			LOGGER.error("Could not load default properties file");
		}

		try {
			properties = new Properties(defaultProperties);
			InputStream propertiesFile = testmode ? Launcher.class.getClassLoader().getResourceAsStream("debug.properties") : new FileInputStream(FILE_NAME);
			properties.load(propertiesFile);

			Logger.getRootLogger().setLevel(Level.toLevel(properties.getProperty("rootlogger.level")));
			LOGGER.debug("Loaded properties file " + propertiesFile);
			LOGGER.debug("Set log level to " + properties.getProperty("rootlogger.level"));
		} catch (Exception e) {
			LOGGER.error("Could not load properties file", e);
		}

		while (!testmode && !isValidXmppId(properties.getProperty("xmpp.user"))) {
			setupWizard();
		}
	}

	private void setupWizard() {
		try {
			System.out.println("SocIO setup");
			this.properties = new Properties();
			String input = null;

			while (!isValidXmppId(input)) {
				System.out.print("Please input a valid XMPP id (in the form xmpp://user@example.com): ");
				byte[] buffer = new byte[100];
				System.in.read(buffer, 0, 99);

				input = (new String(buffer)).trim();
				LOGGER.debug("User entered username \"" + input + "\"");
			}

			properties.setProperty("xmpp.user", input);
			input = "";

			while ("".equals(input)) {
				System.out.print("Please input an password: ");
				byte[] buffer = new byte[100];
				System.in.read(buffer, 0, 99);

				input = (new String(buffer)).trim();
				LOGGER.debug("User entered a password");
			}

			properties.setProperty("xmpp.pass", input);

		} catch (Exception e) {
			LOGGER.error("Could not retrieve user inputs:", e);
		}

		try {
			properties.store(new FileOutputStream(FILE_NAME), "SocIO Configuration File");
		} catch (Exception e) {
			LOGGER.error("Could not store properties file " + FILE_NAME, e);
		}
	}

	public static void parseCommandline(String[] args) {
		for (String parameter : args) {
			LOGGER.info("Manual overwrite: " + parameter + " = true");
			Config.getInstance().properties.setProperty(parameter, "true");
		}
	}

	/**
	 * Basic user name validations
	 * 
	 * @return A valid user name / null
	 */
	public static Boolean isValidXmppId(String userName) {
		if (userName == null)
			return false;

		if (!userName.startsWith("xmpp://"))
			return false;

		if (!(userName.split("@").length == 2))
			return false;

		return true;
	}

	// Generic getter methods

	private static String getProperty(String key) {
		return Config.getInstance().properties.getProperty(key);
	}

	private static String getStringProperty(String key) {
		return (getProperty(key) != null ? getProperty(key) : "");
	}

	private static Boolean getBooleanProperty(String key) {
		return getProperty(key) != null && getProperty(key).equals("true");
	}

	// Getter methods for specific settings

	public static String getRootLogLevel() {
		return getStringProperty("rootlogger.level");
	}

	public static String getProxyAddress() {
		return getStringProperty("proxy.address");
	}

	public static int getProxyPort() {
		// TODO Hardcoded: Proxy Port config setting is hardcoded to 80.
		return 80;
	}

	public static String getServerAddress() {
		return getXmppUserId().split("@")[1];
	}

	public static String getUserName() {
		return getXmppUserId().split("@")[0].replaceAll("xmpp://", "");
	}

	public static int getServerPort() {
		// TODO: Hardcoded: Port config setting is hardcoded to 5222.
		return 5222;
	}

	public static String getXmppUserId() {
		return getStringProperty("xmpp.user");
	}

	public static String getPassword() {
		return getStringProperty("xmpp.pass");
	}

	public static Boolean useProxy() {
		return getBooleanProperty("useproxy");
	}

	public static Boolean isHeadless() {
		return getBooleanProperty("headless");
	}

	public static Boolean isDebug() {
		return getBooleanProperty("debug");
	}

	public static boolean isReadonly() {
		return getBooleanProperty("readonly");
	}

	public static boolean isOffline() {
		return getBooleanProperty("offline");
	}

	public static boolean disableTray() {
		return getBooleanProperty("disabletray");
	}

}
