package softwaredesign;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.dsl.FXGL;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicReference;


public class Main extends GameApplication {

    private static final int BUTTON_COUNT = 4;
    private AnimationTimer stopwatchTimer;
    private long stopwatchStartTime;

    @Override
    protected void initSettings(GameSettings settings){

        // Config window size
        settings.setTitle("Office Pets");
        settings.setHeight(37 * 16);
        settings.setWidth(32 * 16);
    }

    private Entity pet;

    @Override
    protected void initGame() {

        // Spawn pet
        pet = FXGL.entityBuilder()
                .at((32 * 8) - 16 * 8, (37 * 8) - 16 * 4)
                .with(new AnimationComponent())
                .buildAndAttach();
    }

    @Override
    protected  void initInput() {}

    @Override
    protected void initUI() {

        //Set up the top ui
        VBox topUi = new VBox();
        topUi.setPrefSize(FXGL.getAppWidth(),14 * 16);
        topUi.setStyle("-fx-background-color: #1a1a1a;");
        topUi.setAlignment(Pos.CENTER);

        HBox clockBar = new HBox();
        clockBar.setPrefSize(FXGL.getAppWidth(), 8 * 16);
        clockBar.setStyle("-fx-background-color: #00ff00;");
        clockBar.setAlignment(Pos.CENTER);
        topUi.getChildren().add(clockBar);

        Label timerLabel = new Label("00:00:00");

        timerLabel.setText("00:00:00");
        final LocalTime[] time = {LocalTime.MIDNIGHT};

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            time[0] = time[0].plusSeconds(1);
            timerLabel.setText(String.format("%02d:%02d:%02d",
                    time[0].getHour(),
                    time[0].getMinute(),
                    time[0].getSecond()
            ));
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        clockBar.getChildren().add(timerLabel);

        // Set up the top bar
        HBox topBar = new HBox();
        topBar.setPrefSize(FXGL.getAppWidth(), 6 * 16);
        topBar.setStyle("-fx-background-color: #1a1a1a;");
        topBar.setAlignment(Pos.CENTER);
        topUi.getChildren().add(topBar);

        // Add buttons to the top bar
        for (int i = 0; i < BUTTON_COUNT; i++) {
            Button button = new Button("Button " + (i + 1));
            button.setPrefSize(100, 50);
            topBar.getChildren().add(button);
        }

        // Set up the bottom bar
        HBox bottomBar = new HBox();
        bottomBar.setPrefSize(FXGL.getAppWidth(), 6 * 16);
        bottomBar.setStyle("-fx-background-color: #1a1a1a;");
        bottomBar.setAlignment(Pos.CENTER);

        // Add buttons to the bottom bar
        for (int i = 0; i < BUTTON_COUNT; i++) {
            Button button = new Button("Button " + (i + 1));
            button.setPrefSize(100, 50);
            bottomBar.getChildren().add(button);
        }

        // Add the bars to the UI
        VBox ui = new VBox();
        ui.getChildren().addAll(topUi, bottomBar);
        ui.setAlignment(Pos.CENTER);
        ui.setSpacing(FXGL.getAppHeight() - topUi.getPrefHeight() - bottomBar.getPrefHeight());

        // Add the UI to the game scene
        FXGL.getGameScene().addUINode(ui);
    }

    public static void main(String[] args) {
        launch(args);
    }
}