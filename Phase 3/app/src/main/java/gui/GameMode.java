package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GameMode {
    public Scene scene;
    public Stage primaryStage;
    Main main;

    public GameMode(Main main){ this.main = main;}

    public Scene stage(Stage primaryStage){
        this.primaryStage = primaryStage;
        GridPane grid = new GridPane();

        StackPane stackPane = new StackPane();
        scene = new Scene(stackPane);
        String bgHome = "art.jpg";

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(40);
        grid.setVgap(45);
        grid.setPadding(new Insets(45, 45, 45, 45));

        ImagePane bgH = new ImagePane(bgHome);

        Label title = new Label("Briscola");
        title.setStyle("-fx-font-size: 40; -fx-text-fill: white;-fx-font-weigth:bold");
        grid.add(title, 1,0,1,1);

        Button twoPlayers = new Button("Human vs Human");
        Button onePlayers = new Button("Human vs AI");
        Button aiPlayers = new Button("AI vs AI");

        grid.add(twoPlayers, 1,1);
        grid.add(onePlayers, 1, 2);
        grid.add(aiPlayers, 1, 3);

        stackPane.getChildren().addAll(bgH, grid);


        primaryStage.setScene(scene);


        twoPlayers.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                TwoPlayers twoPlayer = new TwoPlayers(main);
                twoPlayer.stage(primaryStage);
            }
        });
        onePlayers.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                OnePlayer onePlayer = new OnePlayer(main);
                onePlayer.stage(primaryStage);
            }
        });
        aiPlayers.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                AIPlayer aiPlayer = new AIPlayer(main);
                aiPlayer.stage(primaryStage);
            }
        });

        return scene;
    }
}
