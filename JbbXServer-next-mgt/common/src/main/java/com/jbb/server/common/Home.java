package com.jbb.server.common;

import java.io.File;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jbb.server.common.util.CodecUtil;
import com.jbb.server.common.util.StringUtil;

/**
 * Home
 * @author VincentTang
 * @date 2017年12月20日
 */
public class Home {
	static final String HOME_DIR_PROPERTY = "JBB_MGT_HOME";
	static final String ENCRYPTION_PASSWORD_PROPERTY = "JBB_MGT_ENCRYPTION_PASSWORD";
	static final String ENCRYPTION_ALGORITHM = "PBEWithSHA1AndDESede";

	private static final String KEK_PROPERTY = "JBB_MGT_KEK";
	private static final String DEFAULT_HOME_DIR = File.separator + "opt" + File.separator + "jbb";
	private static final String MANIFEST_HOME_ENTRY = "JBB";
	private static final String MANIFEST_ATTR_HOME = "home";
	private static final String MANIFEST_ATTR_VERSION = "version";
	private static final String MANIFEST_ATTR_REPOSITORY_PATH = "repositoryPath";

	/** a sub-directory to store configuration files */
	private static final String CONFIGURATION_DIR = File.separator + "config" + File.separator;
	private static final String TEMPLATES_DIR = CONFIGURATION_DIR + "templates" + File.separator;

	private static final String WEB_INF_DIR = "WEB-INF";
	private static final String META_INF_DIR = "META-INF";
	private static final String MANIFEST_NAME = "MANIFEST.MF";
	private static final String LIB_DIR = "lib";
	private static final String JAR_FILTER_END = ".jar";

	private static String homeDirectory = null;
	private static String moduleName = null;
	private static String convenientModuleName = null;
	private static String moduleVersion = null;
	private static String moduleRepositoryPath = null;
	private static String coreVersion = null;
	private static String coreRepositoryPath = null;
	private static String commonVersion = null;
	private static String commonRepositoryPath = null;
	private static String videoprocVersion = null;
	private static String videoprocRepositoryPath = null;
	private static String appEncryptionPassword = null;
	private static byte[] kek = null;

	public static final long START_TIME = System.currentTimeMillis();

	/**
	 * Get home directory. First try to read home directory path and version
	 * from this class JAR file manifest. Second try to get home directory from
	 * the system property. If nothing helps, use default.
	 * 
	 * @return home directory
	 */
	public static String getHomeDir() {
		if (homeDirectory == null) {
			boolean readFromJar = false;
			String home = System.getProperty(HOME_DIR_PROPERTY);

			if (home == null) {
				home = System.getenv(HOME_DIR_PROPERTY);

				if (home == null) {
					home = readFromJarFile();
					readFromJar = true;

					if (home == null) {
						log("Home: The home directory is not set. Use default " + DEFAULT_HOME_DIR);
						home = DEFAULT_HOME_DIR;
					}
				}
			}

			// set home dir
			homeDirectory = home;
			System.setProperty(HOME_DIR_PROPERTY, home);

			if (!readFromJar) {
				readFromJarFile();
			}

			getKek();
		}

		return homeDirectory;
	}

	public static String getConfigDir() {
		return getHomeDir() + CONFIGURATION_DIR;
	}

	public static String getTemplatesDir() {
		return getHomeDir() + TEMPLATES_DIR;
	}

	private static void log(String message) {
		System.out.println(message);
	}

	private static void logError(String message, Exception e) {
		System.err.println(message + e.toString());
		e.printStackTrace();
	}

	/**
	 * Read home directory location from a jar file
	 * 
	 * @return home directory
	 */
	private static String readFromJarFile() {
		String home = null;

		try {
			// get jar file location
			URL classUrl = Home.class.getProtectionDomain().getCodeSource().getLocation();
			String thisClassLocation = URLDecoder.decode(classUrl.getPath(), Constants.UTF8);

			if (!StringUtil.isEmpty(thisClassLocation)) {
				String[] manifestData = readFromJarManifest(thisClassLocation);
				home = manifestData[0];
				commonVersion = manifestData[1];
				moduleVersion = commonVersion;
				commonRepositoryPath = manifestData[2];
			}
		} catch (Exception e) {
			logError("Exception in reading JAR file", e);
		}

		return home;
	}

	/**
	 * Read home directory path and version from JAR manifest
	 * 
	 * @param jarPath
	 *            JAR file path
	 * @return {home directory path or null, jar file version}
	 */
	private static String[] readFromJarManifest(String jarPath) throws Exception {
		String[] res = new String[3];

		File file = new File(jarPath);

		if (file.canRead() && file.isFile()) {
			JarFile jarFile = new JarFile(file);
			Manifest manifest = jarFile.getManifest();
			jarFile.close();
			if (manifest != null) {
				Attributes attrs = manifest.getAttributes(MANIFEST_HOME_ENTRY);
				if (attrs != null) {
					String homePath = attrs.getValue(MANIFEST_ATTR_HOME);
					// check if the directory exists
					if (!StringUtil.isEmpty(homePath) && isDirectory(homePath)) {
						res[0] = homePath;
					}

					res[1] = attrs.getValue(MANIFEST_ATTR_VERSION);
					res[2] = attrs.getValue(MANIFEST_ATTR_REPOSITORY_PATH);
				}
			}
		} // if (file.canRead())

		return res;
	}

	/**
	 * Check if this path is existing directory
	 * 
	 * @param dirPath
	 *            directory path to check
	 * @return true if this directory exists
	 */
	private static boolean isDirectory(String dirPath) {
		File dir = new File(dirPath);
		return dir.exists() && dir.isDirectory();
	}

	static String getEncryptionPassword() {
		if (appEncryptionPassword == null) {
			appEncryptionPassword = System.getProperty(ENCRYPTION_PASSWORD_PROPERTY);
			if (appEncryptionPassword == null) {
				appEncryptionPassword = System.getenv(ENCRYPTION_PASSWORD_PROPERTY);
			}
		}

		return appEncryptionPassword;
	}

	private static String getLocalHost() {
		String res = null;

		try {
			InetAddress ip = InetAddress.getLocalHost();
			res = ip.getHostName() + '/' + ip.getHostAddress();
		} catch (UnknownHostException e) {
			// ignore
		}

		return res;
	}

	public static void settingsTest() {
		Logger logger = LoggerFactory.getLogger(Home.class);
		logger.debug(">settingsTest()");
		String localHost = getLocalHost();

		try {
			lightSettingsTest(logger, localHost);

			String jdbcPassword = PropertyManager.decryptProperty("jdbc.password");
			if (!StringUtil.isEmpty(jdbcPassword))
				logger.info("JDBC password is set on " + localHost);
			else
				logger.error("JDBC password is not set on " + localHost);

			if (getKek() != null)
				logger.info("KEK is set on " + localHost);
			else
				logger.error("KEK is not set on " + localHost);
		} catch (Exception e) {
			logger.error("Exception in testing settings on " + localHost, e);
		}

		logger.debug("<settingsTest()");
	}

	public static void lightSettingsTest() {
		Logger logger = LoggerFactory.getLogger(Home.class);
		String localHost = getLocalHost();

		try {
			lightSettingsTest(logger, localHost);
		} catch (Exception e) {
			logger.error("Exception in testing settings on " + localHost, e);
		}
	}

	private static void lightSettingsTest(Logger logger, String localHost) {
		String homeDirEnv = System.getenv(HOME_DIR_PROPERTY);
		if (!StringUtil.isEmpty(homeDirEnv))
			logger.info("Home dir env is set to " + homeDirEnv + " on " + localHost);
		else
			logger.warn("Home dir env is not set on " + localHost);

		String passwordEnv = System.getenv(ENCRYPTION_PASSWORD_PROPERTY);
		if (!StringUtil.isEmpty(passwordEnv))
			logger.info("Encryption password env is set on " + localHost);
		else
			logger.warn("Encryption password env is not set on " + localHost);

		String homeDir = getHomeDir();
		if (!StringUtil.isEmpty(homeDir))
			logger.info("Home dir is set to " + homeDir + " on " + localHost);
		else
			logger.error("Home dir is not set on " + localHost);

		String password = getEncryptionPassword();
		if (!StringUtil.isEmpty(password))
			logger.info("Encryption password is set on " + localHost);
		else
			logger.error("Encryption password is not set on " + localHost);
	}

	/**
	 * Get module version
	 * 
	 * @return module version
	 */
	public static String getModuleVersion() {
		return moduleVersion;
	}

	/**
	 * Get core version
	 * 
	 * @return core version
	 */
	public static String getCoreVersion() {
		return coreVersion;
	}

	/**
	 * Get common version
	 * 
	 * @return common version
	 */
	public static String getCommonVersion() {
		return commonVersion;
	}

	/**
	 * Get module SVN repository path
	 * 
	 * @return module SVN repository path
	 */
	public static String getModuleRepositoryPath() {
		return moduleRepositoryPath;
	}

	/**
	 * Get core SVN repository path
	 * 
	 * @return core SVN repository path
	 */
	public static String getCoreRepositoryPath() {
		return coreRepositoryPath;
	}

	/**
	 * Get common SVN repository path
	 * 
	 * @return common SVN repository path
	 */
	public static String getCommonRepositoryPath() {
		return commonRepositoryPath;
	}

	public static byte[] getKek() {
		if (kek == null) {
			String kekVar = System.getenv(KEK_PROPERTY);
			if (StringUtil.isEmpty(kekVar)) {
				kekVar = System.getProperty(KEK_PROPERTY);
			}

			if (!StringUtil.isEmpty(kekVar)) {
				kek = CodecUtil.fromBase64(kekVar);
			}
		}

		return kek;
	}

	public static String getVideoprocVersion() {
		return videoprocVersion;
	}

	public static String getVideoprocRepositoryPath() {
		return videoprocRepositoryPath;
	}

	public static String getModuleName() {
		return moduleName;
	}

	public static String convenientModuleName() {
		if (convenientModuleName == null) {
			getHomeDir();
			convenientModuleName = moduleName != null ? moduleName.replace('.', '_')
					: "RND" + StringUtil.randomLowerCaseAlphaNum(4);
		}

		return convenientModuleName;
	}

	/**
	 * Shutdown all pools and close open connections. This method must be called
	 * before stopping the application!
	 */
	public static void shutdown() {
		PropertyManager.shutdown();
	}
}
