package org.jpmc.awm.tcoe.framework.utils;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DatePickerHelper {
    private final Page page;

    public DatePickerHelper(Page page) {
        this.page = page;
    }

    // 1. Direct input (type or fill)
    public void enterDate(String inputSelector, String date, boolean clearFirst) {
        Locator input = page.locator(inputSelector);
        input.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        if (clearFirst) {
            input.fill(""); // clear the field
        }
        input.fill(date); // or use input.type(date) to simulate typing
    }

    // 2. Select date from calendar widget (basic click-based)
    public void selectDateFromCalendar(String calendarInputSelector, String dayToSelect) {
        // Open calendar widget
        page.locator(calendarInputSelector).click();

        // Wait for calendar popup (adjust selector as per your app)
        page.locator("text='" + dayToSelect + "'").waitFor();

        // Click on the day
        page.locator("text='" + dayToSelect + "'").click();
    }

    // 3. Date picker inside Shadow DOM
    public void selectDateInShadowDOM(String shadowHostSelector, String shadowInputSelector, String dateValue) {
        Locator shadowHost = page.locator(shadowHostSelector);
        shadowHost.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED));

        JSHandle inputHandle = shadowHost.evaluateHandle(
                "(host, selector) => host.shadowRoot.querySelector(selector)",
                shadowInputSelector
        );

        ElementHandle input = inputHandle.asElement();
        if (input == null) throw new RuntimeException("Shadow DOM input not found.");

        input.fill(dateValue);
    }

    // 4. Date picker inside an iFrame
    public void selectDateInIFrame(String iframeSelector, String dateInputSelector, String dateValue) {
        // Wait for the iframe to be available
        FrameLocator frameLocator = page.frameLocator(iframeSelector);
        Locator input = frameLocator.locator(dateInputSelector);

        // Wait until input is ready and then fill
        input.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        input.fill(dateValue);
    }

    // 5. Utility method to generate formatted date strings
    public String getFormattedDate(int daysFromToday, String pattern) {
        LocalDate targetDate = LocalDate.now().plusDays(daysFromToday);
        return targetDate.format(DateTimeFormatter.ofPattern(pattern));
    }
}
