package net.eldiosantos.tools.config.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * It's responsability is to list files on the configuration
 * folder.
 * At this time the only supported file type is the properties.
 */
public class ListFiles {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public List<File> list(final String root) throws IOException {

        if(logger.isDebugEnabled()) {
            logger.debug(String.format("file path: %s\t[exists: %s]", Paths.get(root), Paths.get(root).toFile().exists()));
        }

        return Files.list(Paths.get(root))
                .peek(p -> logger.debug(String.format("file before: '%s'\t[ok: %s]", p.getFileName(), p.toFile().getName().endsWith(".properties"))))
                .filter(p->p.toFile().getName().endsWith(".properties"))
                .peek(p -> logger.debug(String.format("file after: '%s'", p.getFileName())))
                .map(p -> p.toFile())
                .collect(Collectors.toList());

    }
}
