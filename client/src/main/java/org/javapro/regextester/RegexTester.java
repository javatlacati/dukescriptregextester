package org.javapro.regextester;

import net.java.html.json.*;
import org.javapro.regextester.js.PlatformServices;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Model annotation generates class Data with one message property, boolean
 * property and read only words property
 */
@Model(targetId = "", className = "RegexTesting", instance = true, properties = {
        @Property(name = "testCase", type = String.class)
        , @Property(name = "regexText", type = String.class)
        , @Property(name = "escapedRegexText", type = String.class)
        , @Property(name = "replacementText", type = String.class)
        , @Property(name = "partialMatches", type = String.class, array = true, mutable = true)
        , @Property(name = "displayReplacement", type = boolean.class)
        , @Property(name = "displayMatches", type = boolean.class)
})
final class RegexTester {
    private PlatformServices services;

    @ComputedProperty
    static String replaced(String regexText, String testCase, String replacementText) {
        Pattern pattern = Pattern.compile(regexText);
        return pattern.matcher(testCase).replaceAll(replacementText);
    }

    @ComputedProperty
    static String escapeRegexText(String regexText) {
        return regexText.replaceAll("[\\\\]", "\\\\\\\\");
    }

    @ComputedProperty
    static String unEscapeRegexText(String regexText) {
        return regexText.replaceAll("[\\\\]", "\\");
    }

    @ComputedProperty
    static boolean matches(String regexText, String testCase) {
        //return testCase.matches(regexText);
        Pattern pattern = Pattern.compile(regexText);
        return pattern.matcher(testCase).matches();
    }

    @Function
    static void allMatches(RegexTesting model) {
        List<String> allMatches = new ArrayList<String>();
        Matcher m = Pattern.compile(model.getRegexText()).matcher(model.getTestCase());
        while (m.find()) {
            allMatches.add(m.group());
        }
        model.getPartialMatches().clear();
        model.getPartialMatches().addAll(allMatches);
    }

    @Function
    void openWebBrowser(RegexTesting model, String data) { //, PlatformServices services
//        model.getRegexText();
        services.openWebBrowser(data);
    }

    @ModelOperation
    public void setPreferences(RegexTesting model, PlatformServices services) {
        this.services = services;
    }

    /**
     * Called when the page is ready.
     */
    static void onPageLoad(PlatformServices services) {
        RegexTesting model = new RegexTesting("", "", "", "", false, false);
        model.setPreferences(services);
        model.applyBindings();
    }
}
