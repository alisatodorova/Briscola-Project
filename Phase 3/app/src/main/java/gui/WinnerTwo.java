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

public class WinnerTwo {
    TwoPlayers twoPlayers;
    Main main;
    Player player;

    public WinnerTwo(TwoPlayers twoPlayers, Main main){ this.twoPlayers = twoPlayers; this.main = main;}



    /**
     * Creates final screen where the players see the winner
     * and can also choose to start a new game with new names,
     * a rematch or to quit
     * @param primaryStage initial stage
     * @return the grid containing all JavaFX fields
     */
    public GridPane gridPane(Stage primaryStage){
        GridPane grid = new GridPane();
        StackPane spaceOne = new StackPane();
        String bgHome = "art.jpg";
        Scene ending = new Scene(spaceOne);

        player = twoPlayers.game.winGame();

        ImagePane bgH = new ImagePane(bgHome);

        spaceOne.getChildren().addAll(bgH, grid);

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(40);
        grid.setVgap(40);
        grid.setPadding(new Insets(45, 45, 45, 45));

        String winningText = player.getName() + " is the winner!";
        if(twoPlayers.DEBUG)System.out.println(winningText);
        if(twoPlayers.DEBUG)System.out.println(player.getName());
        Label winnerName = new Label(winningText);
        winnerName.setStyle("-fx-font-size: 40; -fx-text-fill: white;-fx-font-weigth:bold");
        grid.add(winnerName, 0,0,1,1);

        String pointsText = player.getPointsToString() + " points";
        Label winnerPoints = new Label(pointsText);
        winnerPoints.setStyle("-fx-font-size: 40; -fx-text-fill: white;-fx-font-weigth:bold");
        grid.add(winnerPoints, 1, 1, 1,1);

        Button newGame = new Button("Rematch");
        Button mainMenu = new Button("Main menu");
        Button quit = new Button("Quit");

        grid.add(newGame,0,2,1,1);
        grid.add(mainMenu,1,2,1,1);
        grid.add(quit,2,2,1,1);

        newGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            /**
             * When the user presses the new game button,
             * the player is taken to the initial screen
             * to start a new beiscola game with the same
             * player information 
             * @param e the action event
             */
            public void handle(ActionEvent e) {
                if(twoPlayers.DEBUG)System.out.println(twoPlayers.playerFirst.getName() + "name =" + twoPlayers.nameP1.getText());
                if(twoPlayers.DEBUG)System.out.println(twoPlayers.playerSecond.getName() + "name =" + twoPlayers.nameP2.getText());
                twoPlayers.briscolaStart();
                twoPlayers.gameWindow(primaryStage);
                twoPlayers.acessPlayerOne = true;
                twoPlayers.acessPlayerTwo = false;
                twoPlayers.playerPickCard();
                primaryStage.setScene(twoPlayers.scene2); // Go to playing Briscola scene
            }
        });

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
        primaryStage.setScene(ending);
        return grid;
    }
}
