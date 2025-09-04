package edu.ccrm.util;

import java.nio.file.*;
import java.io.IOException;

public class BackupUtility {

    public static void backupDirectory(Path sourceDir, Path destDir) throws IOException {
        if (!Files.exists(sourceDir) || !Files.isDirectory(sourceDir))
            throw new IOException("Source not found or not a directory: " + sourceDir);

        // Ensure target exists
        if (!Files.exists(destDir)) {
            Files.createDirectories(destDir);
        }

        // Use Files.walk for recursive traversal
        Files.walk(sourceDir)
            .forEach(sourcePath -> {
                try {
                    Path relPath = sourceDir.relativize(sourcePath);
                    Path destPath = destDir.resolve(relPath);
                    if (Files.isDirectory(sourcePath)) {
                        if (!Files.exists(destPath))
                            Files.createDirectories(destPath);
                    } else {
                        Files.copy(sourcePath, destPath, StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (IOException e) {
                    System.err.println("Failed to copy: " + sourcePath + " - " + e.getMessage());
                }
            });
    }
}
