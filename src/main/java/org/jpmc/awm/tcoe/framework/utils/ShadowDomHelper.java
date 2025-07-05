package org.jpmc.awm.tcoe.framework.utils;

import com.microsoft.playwright.*;

public class ShadowDomHelper {
    private final Page page;

    public ShadowDomHelper(Page page) {
        this.page = page;
    }

    public Locator getShadowElement(String hostSelector, String shadowSelector) {
        Locator host = page.locator(hostSelector);
        return (Locator) host.evaluateHandle("host => host.shadowRoot.querySelector('" + shadowSelector + "')")
                .asElement();
    }

    public void clickShadowElement(String hostSelector, String shadowSelector) {
        getShadowElement(hostSelector, shadowSelector).click();
    }

    public String getShadowElementText(String hostSelector, String shadowSelector) {
        return getShadowElement(hostSelector, shadowSelector).innerText();
    }
}
