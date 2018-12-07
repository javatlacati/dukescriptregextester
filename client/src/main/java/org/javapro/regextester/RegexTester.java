package org.javapro.regextester;

import com.mifmif.common.regex.Generex;
import net.java.html.json.*;
import org.javapro.regextester.js.PlatformServices;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Model annotation generates class Data with one message property, boolean
 * property and read only words property
 */
@Model(targetId = "", className = "RegexTesting", instance = true, properties = {
        @Property(name = "testCase", type = String.class)
        , @Property(name = "regexText", type = String.class)
        , @Property(name = "replacementText", type = String.class)
        , @Property(name = "partialMatches", type = String.class, array = true)
        , @Property(name = "possibilities", type = String.class, array = true)
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

    @ComputedProperty(write = "setEscapedRegexText")
    static String escapedRegexText(String regexText) {
        return regexText.replaceAll("[\\\\]", "\\\\\\\\");
    }

    static void setEscapedRegexText(RegexTesting model, String value) {
        try {
            String excaped = value.replaceAll("[\\\\]{2}", Matcher.quoteReplacement("\\"));
            model.setRegexText(excaped);
        } catch (PatternSyntaxException pse) {
            model.setRegexText(pse.getMessage());
            return;
        }
    }

    @ComputedProperty
    static String generateExample(String regexText) {

        Generex generex = new Generex(regexText);
        return generex.random();
    }

    @ComputedProperty
    static boolean matches(String regexText, String testCase) {
        //return testCase.matches(regexText);
        Pattern pattern = Pattern.compile(regexText);
        return pattern.matcher(testCase).matches();
    }

    @Function
    static void allMatches(RegexTesting model) {
        List<String> allMatches = new ArrayList<>();
        Matcher m = Pattern.compile(model.getRegexText()).matcher(model.getTestCase());
        while (m.find()) {
            allMatches.add(m.group());
        }
        model.getPartialMatches().clear();
        model.getPartialMatches().addAll(allMatches);
    }

    @Function
    static void nPossibilities(RegexTesting model) {
        Set<String> allPossibilities = new HashSet<>();
        try {
            Generex generex = new Generex(model.getRegexText());
            for (int i = 0; i < 5; i++) {
                allPossibilities.add(generex.random());
            }
        } catch (IllegalArgumentException iae) {
            allPossibilities.add(iae.getMessage());
        }
        model.getPossibilities().clear();
        model.getPossibilities().addAll(allPossibilities);
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
        RegexTesting model = new RegexTesting("", "", "", false, false);
        model.setPreferences(services);
        model.applyBindings();
    }
}
