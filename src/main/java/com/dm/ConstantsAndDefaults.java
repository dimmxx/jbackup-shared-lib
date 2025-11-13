package com.dm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static java.lang.String.format;
import static java.nio.file.Files.notExists;
import static java.nio.file.Path.of;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public class ConstantsAndDefaults {

    //Paths
    public static final String DEFAULT_HOME_DIR;
    public static String WORK_DIR;

    public static final String APP_DIR = ".jbackup";
    public static final String ABSOLUTE_APP_DIR;

    public static final String TEMP_DIR;
    public static final String OUTPUT_FILE_NAME = "paths.txt";
    public static final String ARCHIVE_FILE_NAME = "jbackup-";
    public static final String ARCHIVE_FILE_EXTENSION = "zip";

    private static final String ENV_TEMP_DIR = "java.io.tmpdir";
    private static final String ENV_HOME_DIR = "HOME";
    public static final String NL = "\n";
    public static final String SPTR = File.separator;

    //Tags
    public static final String DEFAULT_BACKUP_TAG = "Backup";
    public static String BACKUP_TAG;
    public static final String SKIP_TAG = "Skip";

    //Commands
    public static final String[] BASE_COMMAND = {"/bin/zsh", "-c"};
    public static final String ADD_TAGS = "tag --add %s %s";

    private static final Logger log = LoggerFactory.getLogger(ConstantsAndDefaults.class);

    static {
        DEFAULT_HOME_DIR = System.getenv(ENV_HOME_DIR);
        TEMP_DIR = System.getProperty(ENV_TEMP_DIR);

        if (isEmpty(DEFAULT_HOME_DIR) || notExists(of(DEFAULT_HOME_DIR))) {
            log.error("Failed to obtain USER HOME directory [{}] from the environment, "
                + "it is not set or does not exist. Terminating the application.", DEFAULT_HOME_DIR);
            System.exit(1);
        }

        ABSOLUTE_APP_DIR = DEFAULT_HOME_DIR.concat(SPTR).concat(APP_DIR);
        WORK_DIR = DEFAULT_HOME_DIR;
        BACKUP_TAG = DEFAULT_BACKUP_TAG;

        log.info("USER HOME directory [{}] and JAVA TMPDIR [{}] path variables have been obtained"
            + " from the environment", DEFAULT_HOME_DIR, TEMP_DIR);
    }

    public static String[] getRootBackupTag(){
        return new String[]{BASE_COMMAND[0], BASE_COMMAND[1], String.format("tag -A -m %s", BACKUP_TAG)};
    }

    public static String[] getRecursiveBackupTag(){
        return new String[]{BASE_COMMAND[0], BASE_COMMAND[1], format("tag -A -R -m %s", BACKUP_TAG)};
    }

    public static String[] getRootSkipTag(){
        return new String[]{BASE_COMMAND[0], BASE_COMMAND[1], format("tag -A -m %s", SKIP_TAG)};
    }
}
