package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        GameMode gameMode = new GameMode(this);
        gameMode.stage(primaryStage);
        primaryStage.setScene(gameMode.scene);

        primaryStage.setTitle("Briscola");
        primaryStage.show();


    }


    /**
     * launching the App
     * @param args starting the App - might need args to pass
     */
    public static void main(String[] args) {
        launch(args);
    }



}
