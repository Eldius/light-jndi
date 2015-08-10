package net.eldiosantos.tools.context;

import net.eldiosantos.tools.config.helper.JarExtractor;
import net.eldiosantos.tools.config.helper.ListFiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Class used to help parse root folder configuration.
 */
public class RootConfigPath {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final String root;

    public RootConfigPath(String root) {
        this.root = root;
    }

    public String getRootPath() throws Exception {
        if (root.startsWith("classpath:")) {
            logger.info("Classpath root dir...");
            final String parsedRoot = root.substring("classpath:".length());
            final File jarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
            if(jarFile.isFile()) {
                return new JarExtractor().extract(jarFile, parsedRoot);
             } else {
                return getClass().getClassLoader().getResource(parsedRoot).getFile();
            }
        } else if(root.startsWith("file://")) {
            return root.substring("file://".length());
        } else {
            return root;
        }
    }

    public List<File> getFiles() throws Exception {
        return getFiles(getRootPath());
    }

    private List<File> getFiles(final String dir) throws IOException {
        final List<File> files = new ListFiles().list(dir);
        return files;
    }
}
