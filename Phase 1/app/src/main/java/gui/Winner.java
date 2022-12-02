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

public class Winner {
    Main main;
    Player player;

    public Winner(Main main){ this.main = main;}


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

        player = main.game.winGame();

        ImagePane bgH = new ImagePane(bgHome);

        spaceOne.getChildren().addAll(bgH, grid);

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(40);
        grid.setVgap(40);
        grid.setPadding(new Insets(45, 45, 45, 45));

        String winningText = player.getName() + " is the winner!";
        if(main.DEBUG)System.out.println(winningText);
        if(main.DEBUG)System.out.println(player.getName());
        Label winnerName = new Label(winningText);
        winnerName.setStyle("-fx-font-size: 40; -fx-text-fill: white;-fx-font-weigth:bold");
        grid.add(winnerName, 0,0,1,1);

        String pointsText = player.getPointsToString() + " points";
        Label winnerPoints = new Label(pointsText);
        winnerPoints.setStyle("-fx-font-size: 40; -fx-text-fill: white;-fx-font-weigth:bold");
        grid.add(winnerPoints, 1, 1, 1,1);

        Button newGame = new Button("Rematch");
        Button changeNames = new Button("New game");
        Button quit = new Button("Quit");

        grid.add(newGame,0,2,1,1);
        grid.add(changeNames,1,2,1,1);
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
                if(main.DEBUG)System.out.println(main.playerFirst.getName() + "name =" + main.nameP1.getText());
                if(main.DEBUG)System.out.println(main.playerSecond.getName() + "name =" + main.nameP2.getText());
                main.briscolaStart();
                main.gameWindow(primaryStage);
                main.acessPlayerOne = true;
                main.acessPlayerTwo = false;
                main.playerPickCard();
                primaryStage.setScene(main.scene2); // Go to playing Briscola scene
            }
        });

        changeNames.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            /**
             * When the user presses the change names button,
             * the user is taken to the initial screen 
             * @param e the action event
             */
            public void handle(ActionEvent e) {
                main.start(primaryStage);
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
