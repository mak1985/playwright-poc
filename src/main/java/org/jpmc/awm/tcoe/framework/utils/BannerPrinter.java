    package org.jpmc.awm.tcoe.framework.utils;

    import java.io.BufferedReader;
    import java.io.InputStream;
    import java.io.InputStreamReader;
    import java.nio.charset.StandardCharsets;

    public class BannerPrinter {
        private static boolean isBannerPrinted = false;

        public static void printBanner() {
            if (isBannerPrinted) return;

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                    BannerPrinter.class.getClassLoader().getResourceAsStream("banner.txt"), StandardCharsets.UTF_8))) {

                String ANSI_BOLD_BLUE = "\033[1;34m";
                String ANSI_RESET = "\033[0m";

                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(ANSI_BOLD_BLUE + line + ANSI_RESET);
                }
                isBannerPrinted = true;
            } catch (Exception e) {
                System.out.println("Unable to load banner: " + e.getMessage());
            }
        }
    }
