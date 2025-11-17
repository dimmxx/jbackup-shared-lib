package com.dm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static java.nio.file.Files.notExists;
import static java.nio.file.Path.of;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public class BasePathResolver {

    public static final String DEFAULT_HOME_DIR;
    public static final String ABSOLUTE_APP_DIR;
    public static final String TEMP_DIR;
    public static final String OUTPUT_FILE_NAME = "on-tag-paths.txt";
    public static final String ARCHIVE_FILE_NAME_PREFIX = "jbackup-";
    public static final String NL = "\n";
    public static final String SPTR = File.separator;
    private static final String ENV_TEMP_VAR_NAME = "java.io.tmpdir";
    private static final String ENV_HOME_VAR_NAME = "HOME";
    private static final String APP_DIR = ".jbackup";
    private static final Logger log = LoggerFactory.getLogger(BasePathResolver.class);

    static {
        DEFAULT_HOME_DIR = System.getenv(ENV_HOME_VAR_NAME);
        TEMP_DIR = System.getProperty(ENV_TEMP_VAR_NAME);

        if (isEmpty(DEFAULT_HOME_DIR) || notExists(of(DEFAULT_HOME_DIR))) {
            log.error("Failed to obtain USER HOME directory [{}] from the environment, "
                + "it is not set or does not exist. Terminating the application.", DEFAULT_HOME_DIR);
            System.exit(1);
        }

        ABSOLUTE_APP_DIR = DEFAULT_HOME_DIR.concat(SPTR).concat(APP_DIR);

        log.info("USER HOME directory [{}] and JAVA TMPDIR [{}] path variables have been obtained"
            + " from the environment", DEFAULT_HOME_DIR, TEMP_DIR);
    }
}
