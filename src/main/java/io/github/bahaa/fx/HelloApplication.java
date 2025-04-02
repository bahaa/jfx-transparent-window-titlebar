package io.github.bahaa.fx;

import io.github.bahaa.fx.platform.NativeHandleAccessor;
import io.github.bahaa.fx.platform.macos.appkit.NSWindow;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.lang.foreign.MemorySegment;

public class HelloApplication extends Application {

    private double xOffset = 0;
    private double yOffset = 0;

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage stage) throws IOException {
        final var root = new HBox();

        final var leftPane = new VBox();
        leftPane.setBackground(Background.fill(Color.web("#E9E9E8")));
        leftPane.setMinWidth(180);

        final var icon = new FontIcon("ci-settings");
        icon.setIconSize(18);

        final var settingsButton = new Button();
        settingsButton.setGraphic(icon);
        settingsButton.setBackground(Background.fill(Color.web("#E9E9E8")));

        final var leftPaneTopBar = new HBox();
        leftPaneTopBar.getChildren().add(settingsButton);
        leftPaneTopBar.setMaxHeight(30);
        leftPaneTopBar.setAlignment(Pos.CENTER_RIGHT);
        leftPaneTopBar.setPadding(new Insets(8));

        leftPane.getChildren().add(leftPaneTopBar);

        final var rightPane = new VBox();
        rightPane.setBackground(Background.fill(Color.RED));
        VBox.setVgrow(rightPane, Priority.ALWAYS);

        root.getChildren().addAll(leftPane, rightPane);

        root.setOnMousePressed(event -> {
            this.xOffset = event.getSceneX();
            this.yOffset = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - this.xOffset);
            stage.setY(event.getScreenY() - this.yOffset);
        });

        stage.setTitle("Hello!");
        stage.setScene(new Scene(root, 400, 300));
        stage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });

        if (System.getProperty("os.name").toLowerCase().contains("mac1")) {
            stage.setOnShown(event -> {
                final var rawHandle = NativeHandleAccessor.getRawHandle(stage);
                final var nsWindow = NSWindow.from(MemorySegment.ofAddress(rawHandle));
                nsWindow.fullSizeContentView();
                nsWindow.setTitleBarAppearsTransparent(true);
                nsWindow.addToolbar();
                nsWindow.titleVisibility(false);
            });
        }

        stage.show();
    }
}