package com.dm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.String.format;
import static java.nio.file.Files.notExists;
import static java.nio.file.Path.of;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public class ConstantsAndDefaults {

    public static final String NL = "\n";
    public static final String BACKUP_TAG = "Backup";
    public static final String SKIP_TAG = "Skip";
    public static final String BACKUP_DIR = ".jbackup";
    public static final String[] BASE_COMMAND = {"/bin/zsh", "-c"};
    public static final String[] TAG_BACKUP_ROOT = {BASE_COMMAND[0], BASE_COMMAND[1], format("tag -A -m %s", BACKUP_TAG)};
    public static final String[] TAG_SKIP_ROOT = {BASE_COMMAND[0], BASE_COMMAND[1], format("tag -A -m %s", SKIP_TAG)};
    public static final String[] TAG_BACKUP_RECURSIVE = {BASE_COMMAND[0], BASE_COMMAND[1], format("tag -A -R -m %s", BACKUP_TAG)};
    public static final String ADD_TAGS = "tag --add %s %s";
    public static final String HOME_DIR;
    public static final String TEMP_DIR;
    public static final String FILE_NAME = "paths.txt";

    private static final Logger LOGGER = LoggerFactory.getLogger(ConstantsAndDefaults.class);
    private static final String ENV_TEMP_DIR = "java.io.tmpdir";
    private static final String ENV_HOME_DIR = "HOME";

    static {
        HOME_DIR = System.getenv(ENV_HOME_DIR);
        TEMP_DIR = System.getProperty(ENV_TEMP_DIR);

        if (isEmpty(HOME_DIR) || notExists(of(HOME_DIR))) {
            LOGGER.error("Failed to obtain USER HOME directory [{}] from the environment, "
                + "it is not set or does not exist. Terminating the application.", HOME_DIR);
            System.exit(1);
        }
        LOGGER.info("USER HOME directory [{}] and JAVA TMPDIR [{}] path variables have been obtained"
            + " from the environment", HOME_DIR, TEMP_DIR);
    }
}
