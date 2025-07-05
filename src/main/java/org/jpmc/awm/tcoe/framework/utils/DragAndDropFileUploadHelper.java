package org.jpmc.awm.tcoe.framework.utils;

import com.microsoft.playwright.Page;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

public class DragAndDropFileUploadHelper {
    public static void simulateDragAndDropToShadowDom(Page page, String[] shadowDomPath, String filePath) {
        Path path = Paths.get(filePath);

        try {
            byte[] content = Files.readAllBytes(path);
            String mimeType = Files.probeContentType(path);
            String fileName = path.getFileName().toString();
            String base64 = Base64.getEncoder().encodeToString(content);

            page.evaluate(
                    "async ([selectors, name, type, base64]) => {\n" +
                            "  const response = await fetch(`data:${type};base64,${base64}`);\n" +
                            "  const blob = await response.blob();\n" +
                            "  const file = new File([blob], name, { type });\n" +
                            "  const dataTransfer = new DataTransfer();\n" +
                            "  dataTransfer.items.add(file);\n" +
                            "\n" +
                            "  let current = document;\n" +
                            "  for (const sel of selectors) {\n" +
                            "    current = current.querySelector(sel);\n" +
                            "    if (current && current.shadowRoot) {\n" +
                            "      current = current.shadowRoot;\n" +
                            "    }\n" +
                            "  }\n" +
                            "\n" +
                            "  const dropTarget = current;\n" +
                            "  if (!dropTarget) {\n" +
                            "    throw new Error('Drop target not found via shadow DOM path');\n" +
                            "  }\n" +
                            "\n" +
                            "  ['dragenter', 'dragover', 'drop'].forEach(eventType => {\n" +
                            "    const event = new DragEvent(eventType, {\n" +
                            "      bubbles: true,\n" +
                            "      cancelable: true,\n" +
                            "      dataTransfer\n" +
                            "    });\n" +
                            "    dropTarget.dispatchEvent(event);\n" +
                            "  });\n" +
                            "}",
                    List.of(shadowDomPath, fileName, mimeType, base64)
            );

        } catch (IOException e) {
            throw new RuntimeException("Drag-and-drop upload to shadow DOM failed: " + e.getMessage(), e);
        }
    }
}
