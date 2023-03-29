package softwaredesign;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Window;
import javafx.util.Duration;
import java.time.LocalTime;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.scene.Scene;

import static com.almasb.fxgl.dsl.FXGL.*;

public class Main extends GameApplication {

    private static final int BUTTON_COUNT = 4;

    @Override
    protected void initSettings(GameSettings settings){

        // Config window size
        settings.setTitle("Office Pets");
        settings.setHeight(37 * 16);
        settings.setWidth(32 * 16);
    }

    Pet pet = new Pet();
    Entity petEntity;

    @Override
    protected void initGame() {

        // Spawn pet
        petEntity = FXGL.entityBuilder()
                .at((32 * 8) - 16 * 8, (37 * 8) - 16 * 4)
                .with(new AnimationComponent(pet))
                .buildAndAttach();
    }

    @Override
    protected  void initInput() {}

    @Override
    protected void initUI() {

        // Set up the top ui
        VBox topUi = new VBox();
        topUi.setPrefSize(FXGL.getAppWidth(),14 * 16);
        topUi.setStyle("-fx-background-color: #1a1a1a;");
        topUi.setAlignment(Pos.CENTER);

        // Creating clock
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
            Button button = new Button();
            Image iconImage = getAssetLoader().loadImage("fork.png"); // load your icon image
            ImageView iconView = new ImageView(iconImage);
            iconView.setFitHeight(80);
            iconView.setFitWidth(80);
            button.setGraphic(iconView);
            button.setPrefSize(80, 80);
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    pet.birthday();
                    petEntity.getComponent(AnimationComponent.class).setAnim(pet);
                    System.out.println(pet.getAge());
                }
            });
            topBar.getChildren().add(button);
        }

        // Set up the bottom bar
        HBox bottomBar = new HBox();
        bottomBar.setPrefSize(FXGL.getAppWidth(), 6 * 16);
        bottomBar.setStyle("-fx-background-color: #1a1a1a;");
        bottomBar.setAlignment(Pos.CENTER);

        // Add buttons to the bottom bar
        for (int i = 0; i < BUTTON_COUNT; i++) {
            Button button = new Button();
            Image iconImage = getAssetLoader().loadImage("fork.png"); // load your icon image
            ImageView iconView = new ImageView(iconImage);
            iconView.setFitHeight(80);
            iconView.setFitWidth(80);
            button.setGraphic(iconView);
            button.setPrefSize(80, 80);

            //popup
            Popup popup = new Popup();
            Label popupLabel = new Label();
            popupLabel.setText("Pet Stats:");
            popupLabel.setMinWidth(300);
            popupLabel.setMinHeight(380);
//            FXGL.getPrimaryStage().setX(0);   //does weird shit
//            FXGL.getPrimaryStage().setY(30);
            popupLabel.setStyle("-fx-background-color:#FAF9F6; -fx-font-size:25");
            popupLabel.setPadding(new Insets(20));
            popup.getContent().add(popupLabel);

            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if(!popup.isShowing()){
                        popup.show(FXGL.getPrimaryStage());
                    }
                    else{
                        popup.hide();
                    }
                }
            });
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