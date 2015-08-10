package net.eldiosantos.tools.config.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Extracts the files from a jar file in case you use the root path
 * starting with 'classpath:'.
 */
public class JarExtractor {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public String extract(final File jarFile, final String root) throws Exception {

        logger.debug(String.format("config path: %s", root));

        final JarFile jar = new JarFile(jarFile.getAbsoluteFile());
        final File tempDir = new File(File.createTempFile("context", "tmp").getParent() + File.separator + "context");
        tempDir.mkdirs();
        cleanDir(tempDir);

        Collections.list(jar.entries()).stream()
                .peek(j -> logger.debug(String.format("jar file [after]: %s", j.getName())))
                .filter(e -> e.getName().startsWith(root))
                .peek(j -> logger.debug(String.format("jar file [before]: %s", j.getName())))
                .forEach(e->{
                    final File dest = new File(tempDir.getPath() + File.separator + e.getName());
                    if(e.isDirectory()) {
                        dest.mkdirs();
                    } else {
                        try {
                            final InputStream in = jar.getInputStream(e);
                            final OutputStream out = new FileOutputStream(dest);

                            while (in.available() > 0) {
                                out.write(in.read());
                            }
                            out.close();
                            in.close();
                        } catch (Exception ex) {
                            logger.error("Error extracting config files.", ex);
                        }
                    }
                });

        return tempDir.getAbsolutePath() + File.separator + root;

    }

    private void cleanDir(final File dir) {
        for(File f:dir.listFiles()) {
            if(f.isDirectory()) {
                cleanDir(f);
            } else {
                f.delete();
            }
        }
    }
}
