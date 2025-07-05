package org.jpmc.awm.tcoe.framework.utils;

import com.microsoft.playwright.*;

public class FrameAndAlertHelper {
    private final Page page;

    public FrameAndAlertHelper(Page page) {
        this.page = page;
    }

    // ========== FRAME HELPERS ==========

    // Switch to frame by name or ID
    public Frame switchToFrameByName(String name) {
        for (Frame frame : page.frames()) {
            if (name.equals(frame.name())) {
                return frame;
            }
        }
        throw new RuntimeException("Frame with name '" + name + "' not found.");
    }

    // Switch to frame by selector (e.g., iframe[name='xyz'] or iframe#index)
    public Frame switchToFrameBySelector(String selector) {
        ElementHandle frameElement = page.waitForSelector(selector);
        return frameElement.contentFrame();
    }

    // Get the main/root frame
    public Frame getMainFrame() {
        return page.mainFrame();
    }

    // Get all frames on the page
    public java.util.List<Frame> getAllFrames() {
        return page.frames();
    }

    // Perform action in a frame (by selector)
    public void fillInputInFrame(String frameSelector, String inputSelector, String text) {
        Frame frame = switchToFrameBySelector(frameSelector);
        frame.fill(inputSelector, text);
    }

    public void clickInFrame(String frameSelector, String elementSelector) {
        Frame frame = switchToFrameBySelector(frameSelector);
        frame.click(elementSelector);
    }

    // ========== ALERT HELPERS ==========

    // Accept alert with text extraction
    public String acceptAlert() {
        final StringBuilder alertText = new StringBuilder();

        page.onceDialog(dialog -> {
            alertText.append(dialog.message());
            dialog.accept();
        });

        return alertText.toString();  // This will be filled only after an action triggers the alert
    }

    // Dismiss alert
    public String dismissAlert() {
        final StringBuilder alertText = new StringBuilder();

        page.onceDialog(dialog -> {
            alertText.append(dialog.message());
            dialog.dismiss();
        });

        return alertText.toString();  // Same: trigger action immediately after calling this
    }

    // Accept alert with prompt input
    public String acceptAlertWithPrompt(String inputText) {
        final StringBuilder alertText = new StringBuilder();

        page.onceDialog(dialog -> {
            alertText.append(dialog.message());
            dialog.accept(inputText);
        });

        return alertText.toString();
    }
}
