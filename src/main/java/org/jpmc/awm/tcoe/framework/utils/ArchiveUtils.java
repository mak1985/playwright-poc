package org.jpmc.awm.tcoe.framework.utils;

import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ArchiveUtils {
    public static void archiveTestRunArtifacts() {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String outputZip = "target/archive/TestRun_" + timestamp + ".zip";

        try {
            new File("target/archive").mkdirs();
            try (FileOutputStream fos = new FileOutputStream(outputZip);
                 ZipOutputStream zos = new ZipOutputStream(fos)) {

                zipFolder(Paths.get("target/screenshots"), "screenshots", zos);
                zipFolder(Paths.get("logs"), "logs", zos);
            }

            System.out.println("✅ Test run artifacts archived to: " + outputZip);
        } catch (IOException e) {
            System.err.println("❌ Failed to archive artifacts: " + e.getMessage());
        }
    }

    private static void zipFolder(Path folderPath, String basePath, ZipOutputStream zos) throws IOException {
        if (!Files.exists(folderPath)) return;

        Files.walk(folderPath).filter(Files::isRegularFile).forEach(path -> {
            try {
                String zipEntryName = basePath + "/" + folderPath.relativize(path).toString();
                zos.putNextEntry(new ZipEntry(zipEntryName));
                Files.copy(path, zos);
                zos.closeEntry();
            } catch (IOException e) {
                System.err.println("Error zipping file: " + path + " -> " + e.getMessage());
            }
        });
    }
}
