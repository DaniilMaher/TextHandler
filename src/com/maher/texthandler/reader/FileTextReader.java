package com.maher.texthandler.reader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileTextReader implements TextReader {

    private static Logger logger = LogManager.getLogger();

    private final Path PATH;

    public FileTextReader(String path) {

        PATH = Paths.get(path);
    }

    public String read() {

        String text;
        try {
            byte[] textBytes = Files.readAllBytes(PATH);
            text = new String(textBytes);
        }
        catch (IOException e) {
            logger.fatal("Can't get text from file " + PATH, e);
            throw new RuntimeException("Can't get text from file " + PATH, e);
        }
        return text;
    }
}
