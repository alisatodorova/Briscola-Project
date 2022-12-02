package gui;

import application.Player;
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

public class WinnerAI {
    AIPlayer aiPlayer;
    Main main;
    Player player;

    public WinnerAI(AIPlayer aiPlayer, Main main){
        this.aiPlayer = aiPlayer;
        this.main = main;
    }

    public Scene gridPane (Stage primaryStage)
    {
        GridPane grid = new GridPane();
        StackPane spaceOne = new StackPane();
        String bgHome = "art.jpg";
        Scene endGrid = new Scene(spaceOne);

        player = aiPlayer.game.winGame();

        ImagePane bgH = new ImagePane(bgHome);

        spaceOne.getChildren().addAll(bgH, grid);

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(40);
        grid.setVgap(40);
        grid.setPadding(new Insets(45));

        String winningText = player.getName() + " is the Winner!";
        System.out.println("Top AI Points: " + aiPlayer.playerTop.getPoints());
        System.out.println("Bottom AI Points: " + aiPlayer.playerBottom.getPoints());
        if(aiPlayer.DEBUG)System.out.println(winningText);

        Label winnerName = new Label (winningText);
        winnerName.setStyle("-fx-font-size: 40; -fx-text-fill: white;-fx-font-weigth:bold");
        grid.add(winnerName, 0,0,1,1);

        String pointsText = player.getPointsToString() + " points";
        Label winnerPoints = new Label(pointsText);
        winnerPoints.setStyle("-fx-font-size: 40; -fx-text-fill: white;-fx-font-weigth:bold");
        grid.add(winnerPoints, 1, 1, 1,1);


        Button mainMenu = new Button("Main menu");
        Button quit = new Button("Quit");


        grid.add(mainMenu,1,2,1,1);
        grid.add(quit,2,2,1,1);

        mainMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            /**
             * When the user presses the change names button,
             * the user is taken to the initial screen
             * @param e the action event
             */
            public void handle(ActionEvent e) {
                try {
                    main.start(primaryStage);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        quit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            /**
             * When the user presses the quit button,
             * the application is closed
             * @param e the action event
             */
            public void handle(ActionEvent event) {
                primaryStage.close();
            }
        });

        return endGrid;
    }
}
