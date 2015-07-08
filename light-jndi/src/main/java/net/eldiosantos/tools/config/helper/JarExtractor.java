package net.eldiosantos.tools.config.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by Eldius on 05/07/2015.
 */
public class JarExtractor {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public String extract(final File jarFile, final String root) throws Exception {
        final JarFile jar = new JarFile(jarFile.getAbsoluteFile());
        final File tempDir = new File(File.createTempFile("context", "tmp").getParent() + File.separator + "context");
        tempDir.mkdirs();
        cleanDir(tempDir);

        final Enumeration<JarEntry> entries = jar.entries();
        while (entries.hasMoreElements()) {
            final JarEntry entry = entries.nextElement();
            if(entry.getName().startsWith(root)) {
                logger.info(String.format("Extracting file %s", entry.getName()));
                final File dest = new File(tempDir.getPath() + File.separator + entry.getName());
                if(entry.isDirectory()) {
                    dest.mkdirs();
                } else {
                    final InputStream in = jar.getInputStream(entry);
                    final OutputStream out = new FileOutputStream(dest);

                    while (in.available() > 0) {
                        out.write(in.read());
                    }
                    out.close();
                    in.close();
                }
            }
        }

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
