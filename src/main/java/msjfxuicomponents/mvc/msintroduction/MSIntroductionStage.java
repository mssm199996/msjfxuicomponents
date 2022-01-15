package msjfxuicomponents.mvc.msintroduction;

import com.sun.glass.ui.Screen;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.MSLightweightJFXUIComponentsHolder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

public class MSIntroductionStage extends Stage {

    public MSIntroductionStage(String title) throws MalformedURLException, IOException, URISyntaxException {
        super();

        this.initScreen();
        this.initComponentHolder();

        StackPane introductionPane = (StackPane) FXMLLoader.load(
                getClass().getResource("/msjfxuicomponents/mvc/msintroduction/MSIntroduction.fxml").toURI().toURL());

        introductionPane.setBackground(new Background(
                new BackgroundImage(new Image(Paths.get("background.png").toAbsolutePath().toUri().toURL().toString()),
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                        new BackgroundSize(MSLightweightJFXUIComponentsHolder.SCREEN_WIDTH,
                                MSLightweightJFXUIComponentsHolder.SCREEN_HEIGHT, false, false, false, false))));

        this.setScene(new Scene(introductionPane));
        this.setTitle(title);
        this.setMaximized(true);
        this.getIcons().addAll(MSLightweightJFXUIComponentsHolder.WINDOW_ICON);
        this.setOnCloseRequest(event -> {
        });
        this.show();

        MSJFXUIComponentsHolder.MS_INTRODUCTION_STAGE = this;
    }

    private void initScreen() {
        MSLightweightJFXUIComponentsHolder.SCREEN_WIDTH = Screen.getMainScreen().getWidth();
        MSLightweightJFXUIComponentsHolder.SCREEN_HEIGHT = Screen.getMainScreen().getHeight();
    }

    private void initComponentHolder() {
        MSLightweightJFXUIComponentsHolder.WINDOW_ICON = new Image(
                getClass().getResource("/MVC/Icons/logo.png").toString());

        MSLightweightJFXUIComponentsHolder.LITTLE_MINUS_ICON = new Image(
                getClass().getResource("/msjfxuicomponents/mvc/icons/littleMinus.png").toString());
        MSLightweightJFXUIComponentsHolder.LITTLE_PLUS_ICON = new Image(
                getClass().getResource("/msjfxuicomponents/mvc/icons/littlePlus.png").toString());

        MSLightweightJFXUIComponentsHolder.MODIFY_ICON = new Image(
                getClass().getResource("/msjfxuicomponents/mvc/icons/modifier.png").toString());
        MSLightweightJFXUIComponentsHolder.SEARCH_ICON = new Image(
                getClass().getResource("/msjfxuicomponents/mvc/icons/recherche.png").toString());

        MSLightweightJFXUIComponentsHolder.RIGHT_GREEN_ARROW_ICON = new Image(
                getClass().getResource("/msjfxuicomponents/mvc/icons/green_right_arrow.png").toString());
        MSLightweightJFXUIComponentsHolder.LEFT_GREEN_ARROW_ICON = new Image(
                getClass().getResource("/msjfxuicomponents/mvc/icons/green_left_arrow.png").toString());

        MSLightweightJFXUIComponentsHolder.LEFT_RED_ARROW_ICON = new Image(
                getClass().getResource("/msjfxuicomponents/mvc/icons/red_left_arrow.png").toString());
        MSLightweightJFXUIComponentsHolder.RIGHT_RED_ARROW_ICON = new Image(
                getClass().getResource("/msjfxuicomponents/mvc/icons/red_right_arrow.png").toString());

        MSLightweightJFXUIComponentsHolder.PRINT_ICON = new Image(
                getClass().getResource("/msjfxuicomponents/mvc/icons/printBarcode.png").toString());
    }
}
