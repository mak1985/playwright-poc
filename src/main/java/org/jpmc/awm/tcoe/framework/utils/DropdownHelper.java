package org.jpmc.awm.tcoe.framework.utils;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.SelectOption;

import java.util.List;

public class DropdownHelper {
    private final Page page;

    public DropdownHelper(Page page) {
        this.page = page;
    }

    // --------- Select by value (for <select> dropdowns) ---------
    public void selectByValue(String selector, String value) {
        page.selectOption(selector, new SelectOption().setValue(value));
    }

    // --------- Select by label (visible text) ---------
    public void selectByLabel(String selector, String label) {
        page.selectOption(selector, new SelectOption().setLabel(label));
    }

    // --------- Select by index ---------
    public void selectByIndex(String selector, int index) {
        page.selectOption(selector, new SelectOption().setIndex(index));
    }

    // --------- Select multiple values (multi-select dropdown) ---------
    public void selectMultipleValues(String selector, String... values) {
        SelectOption[] options = new SelectOption[values.length];
        for (int i = 0; i < values.length; i++) {
            options[i] = new SelectOption().setValue(values[i]);
        }
        page.selectOption(selector, options);
    }

    // --------- Get all available options in dropdown ---------
    public List<String> getAllDropdownOptions(String selector) {
        return page.locator(selector + " > option").allInnerTexts();
    }

    // --------- Get selected value(s) ---------
    public List<String> getSelectedOptions(String selector) {
        return page.locator(selector + " > option[selected]").allInnerTexts();
    }

    // --------- Get selected value (if single select) ---------
    public String getSelectedValue(String selector) {
        return page.locator(selector + " > option[selected]").first().innerText();
    }

    // --------- Deselect all (only applies if multiple allowed) ---------
    public void deselectAll(String selector) {
        page.selectOption(selector, new String[]{}); // Empty array to deselect
    }
}
