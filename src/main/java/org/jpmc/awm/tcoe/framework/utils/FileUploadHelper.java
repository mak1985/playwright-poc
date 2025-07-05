package org.jpmc.awm.tcoe.framework.utils;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.nio.file.Paths;

public class FileUploadHelper {
    private final Page page;

    public FileUploadHelper(Page page) {
        this.page = page;
    }

    // 1. Upload using <input type="file">
    public void uploadViaInput(String fileInputSelector, String absoluteFilePath) {
        page.locator(fileInputSelector).setInputFiles(Paths.get(absoluteFilePath));
    }

    // 2. Upload triggered via drag-and-drop or button click that opens file chooser
    public void uploadViaFileChooser(String clickSelector, String absoluteFilePath) {
        FileChooser fileChooser = page.waitForFileChooser(() -> {
            page.locator(clickSelector).click();
        });
        fileChooser.setFiles(Paths.get(absoluteFilePath));
    }

    // 3. Upload inside Shadow DOM
    public void uploadInShadowDOM(String shadowHostSelector, String shadowInputSelector, String absoluteFilePath) {
        Locator shadowHost = page.locator(shadowHostSelector);

        // Ensure the shadow host is attached and visible
        shadowHost.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED));

        // Use JavaScript to get the shadow DOM input element and upload
        JSHandle inputHandle = shadowHost.evaluateHandle(
                "(host, selector) => host.shadowRoot.querySelector(selector)",
                shadowInputSelector
        );

        ElementHandle inputElement = inputHandle.asElement();
        if (inputElement == null) {
            throw new RuntimeException("Unable to locate shadow DOM input element: " + shadowInputSelector);
        }

        inputElement.setInputFiles(Paths.get(absoluteFilePath));
    }
}
