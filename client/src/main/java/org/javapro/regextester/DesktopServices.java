package org.javapro.regextester;

import org.javapro.regextester.js.PlatformServices;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.prefs.Preferences;

public final class DesktopServices extends PlatformServices {
    @Override
    public String getPreferences(String key) {
        return Preferences.userNodeForPackage(Main.class).get(key, null);
    }

    @Override
    public void setPreferences(String key, String value) {
        Preferences.userNodeForPackage(Main.class).put(key, value);
    }

    @Override
    public void openWebBrowser(String url) {
        try {
            Desktop.getDesktop().browse(new URL(url).toURI());
//                new ProcessBuilder("x-www-browser", url).start();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

}