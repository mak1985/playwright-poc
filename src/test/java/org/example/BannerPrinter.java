package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BannerPrinter {

    public static void printBannerFromFile() {
        try {
            // Path to the banner.txt file
            Path filePath = Paths.get("banner.txt");

            // Read all lines from the file
            String content = Files.readString(filePath);

            // Print the content to the console
            System.out.println(content);
        } catch (IOException e) {
            // Handle file reading exceptions
            System.out.println("Error reading the banner file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        printBannerFromFile();
    }
}
