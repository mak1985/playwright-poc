package org.jpmc.awm.tcoe.framework.utils;

import com.microsoft.playwright.*;

import java.util.List;

public class CustomDropdownHelper {
    private final Page page;

    public CustomDropdownHelper(Page page) {
        this.page = page;
    }

    // --------- Select dropdown option by visible text ---------
    public void selectByVisibleText(String dropdownToggleSelector, String optionsSelector, String visibleText) {
        // Open the dropdown
        page.locator(dropdownToggleSelector).click();

        // Wait and click the matching option
        Locator options = page.locator(optionsSelector);
        options.locator("text=" + visibleText).click();
    }

    // --------- Select dropdown option by index ---------
    public void selectByIndex(String dropdownToggleSelector, String optionsSelector, int index) {
        page.locator(dropdownToggleSelector).click();
        Locator options = page.locator(optionsSelector);
        options.nth(index).click();
    }

    // --------- Get all options as text ---------
    public List<String> getAllOptions(String dropdownToggleSelector, String optionsSelector) {
        page.locator(dropdownToggleSelector).click();
        Locator options = page.locator(optionsSelector);
        return options.allInnerTexts();
    }

    // --------- Select using keyboard navigation ---------
    public void selectUsingKeyboard(String dropdownToggleSelector, int downPressCount) {
        page.locator(dropdownToggleSelector).click();

        for (int i = 0; i < downPressCount; i++) {
            page.keyboard().press("ArrowDown");
        }

        page.keyboard().press("Enter");
    }
}
