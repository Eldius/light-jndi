package net.eldiosantos.tools.config.factory.loader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

/**
 * Created by Eldius on 05/07/2015.
 */
public class PropertiesFileLoader {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public Map<String, String> loadFile(final File source) throws IOException {

        // Used a BufferedReader to fix an issue to read the files whith Scanner class in Linux
        final Scanner sc = new Scanner(new BufferedReader(new FileReader(source)));
        final Map<String, String>result = new HashMap<>();
        while (sc.hasNextLine()) {
            final String line = sc.nextLine();
            if(!line.trim().startsWith("#") && line.trim().length() > 3) {
                final String[] keyValue = line.split("=");
                result.put(keyValue[0].trim(), keyValue[1].trim());
            }
        }
        sc.close();
        return result;
    }
}
