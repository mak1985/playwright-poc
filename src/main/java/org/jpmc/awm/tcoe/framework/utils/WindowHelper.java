package org.jpmc.awm.tcoe.framework.utils;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;

public class WindowHelper {
    private final BrowserContext context;

    public WindowHelper(BrowserContext context) {
        this.context = context;
    }

    public Page switchToNewTab(Page currentPage, Runnable actionOpeningNewTab) {
        Page[] newPage = new Page[1];
        context.onPage(page -> newPage[0] = page);

        actionOpeningNewTab.run(); // Click or action that opens new tab

        while (newPage[0] == null) {
            try { Thread.sleep(100); } catch (InterruptedException ignored) {}
        }

        return newPage[0];
    }
}
