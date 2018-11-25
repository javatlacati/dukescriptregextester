package org.javapro.regextester;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.content.ContentManager;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.web.WebView;
import net.java.html.boot.fx.FXBrowsers;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Javatlacati on 24/11/2018.
 */
public class MyToolWindowFactory implements ToolWindowFactory {
    ToolWindow myToolWindow;

    @Override
    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        if(toolWindow!=null) {
            myToolWindow = toolWindow;
            ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
            final JFXPanel fxPanel = new JFXPanel();
            Content content = contentFactory.createContent(fxPanel, "", false);
            Platform.setImplicitExit(false);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    initFX(fxPanel);
                }
            });
            final ContentManager contentManager = myToolWindow.getContentManager();
            if(contentManager!=null) {
                contentManager.addContent(content);
            }else{
                System.err.println("missing content Manager");
            }
        }else{
            System.err.println("missing tool window");
        }
    }


    private void initFX(JFXPanel fxPanel) {
        Scene scene = createScene();
        fxPanel.setScene(scene);
        fxPanel.setAutoscrolls(true);
    }

    private Scene createScene() {
        Group root = new Group();
        Scene scene = new Scene(root, 800.0, 1600.0, Color.ALICEBLUE);
        WebView webView = new WebView();
        webView.setFontSmoothingType(FontSmoothingType.LCD);
        root.getChildren().add(webView);
        FXBrowsers.load(webView, this.getClass().getResource("/pages/index.html"),
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            RegexTester.onPageLoad(null);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
        return (scene);
    }

}
