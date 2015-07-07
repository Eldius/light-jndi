package net.eldiosantos.tools.context;

import net.eldiosantos.tools.config.helper.JarExtractor;
import net.eldiosantos.tools.config.helper.ListFiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by Eldius on 04/07/2015.
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
                return getClass().getClassLoader().getResource(root).getFile();
            }
        } else if(root.startsWith("file://")) {
            return root.substring("file://".length());
        } else {
            return root;
        }
    }

    public File[] getFiles() throws Exception {
        return getFiles(getRootPath());
    }

    private File[] getFiles(final String dir) {
        final File[] files = new ListFiles().list(dir);
        return files!=null?files:new File[0];
    }
}
