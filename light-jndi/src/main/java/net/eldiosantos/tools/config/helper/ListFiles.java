package net.eldiosantos.tools.config.helper;

import java.io.File;
import java.io.FilenameFilter;

/**
 * It's responsability is to list files on the configuration
 * folder.
 * At this time the only supported file type is the properties.
 */
public class ListFiles {
    public File[] list(final String root) {
        return new File(root).listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return (
                        name.endsWith(".ini")
                                || name.endsWith(".xml")
                                || name.endsWith(".properties")
                );
            }
        });

    }
}
