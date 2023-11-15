package org.tierup.grading;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {
    public static boolean compare(String file1Path, String file2Path) throws IOException {

        String content1 = new String(Files.readAllBytes(Paths.get(file1Path))).trim();
        String content2 = new String(Files.readAllBytes(Paths.get(file2Path))).trim();

        return content1.equals(content2);
    }

    public static void StringToFile(String content, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        }
    }
}

