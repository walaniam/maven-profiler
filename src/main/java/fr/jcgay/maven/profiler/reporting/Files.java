package fr.jcgay.maven.profiler.reporting;

import static org.slf4j.LoggerFactory.getLogger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import org.slf4j.Logger;

public final class Files {

    private static final Logger LOGGER = getLogger(Files.class);

    private Files() {
    }

    public static void write(File target, String content) {
        try (FileWriter writer = new FileWriter(target)) {
            writer.write(content);
        } catch (IOException e) {
            LOGGER.error("Cannot write profiler report.", e);
        }
    }

    public static long sizeInKb(File file) {
        return Optional.ofNullable(file)
            .map(File::length)
            .map(it -> it / 1024)
            .orElse(-1L);
    }
}
