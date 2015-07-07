package net.eldiosantos.tools.config.helper;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Created by Eldius on 05/07/2015.
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
