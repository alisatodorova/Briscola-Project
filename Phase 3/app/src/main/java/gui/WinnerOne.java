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

public class WinnerOne {
    OnePlayer onePlayer;
    Main main;
    Player player;

    public WinnerOne(OnePlayer onePlayer, Main main){
        this.onePlayer = onePlayer;
        this.main = main;
    }

    public Scene gridPane (Stage primaryStage)
    {
        GridPane grid = new GridPane();
        StackPane spaceOne = new StackPane();
        String bgHome = "art.jpg";
        Scene endGrid = new Scene(spaceOne);

        player = onePlayer.game.winGame();

        ImagePane bgH = new ImagePane(bgHome);

        spaceOne.getChildren().addAll(bgH, grid);

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(40);
        grid.setVgap(40);
        grid.setPadding(new Insets(45));

        String winningText = player.getName() + "is the Winner!";
        if (player.getId() == onePlayer.playerHuman.getId())
        {
            winningText = player.getName() + " are the Winner! Congratulations!";
        }
        else if (player.getId() == onePlayer.playerAI.getId()) {
            winningText = player.getName() + " is the Winner! Next Time.";
        }
        if(onePlayer.DEBUG)System.out.println(winningText);

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

        /*
        Button newGame = new Button("Rematch");
        grid.add(newGame,0,2,1,1);
        newGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            *//**
             * When the user presses the new game button,
             * the player is taken to the initial screen
             * to start a new beiscola game with the same
             * player information
             * @param e the action event
             *//*
            public void handle(ActionEvent e) {
                if(onePlayer.DEBUG)System.out.println(onePlayer.playerHuman.getName() + "name =" + onePlayer.nameHuman.getText());
                if(onePlayer.DEBUG)System.out.println(onePlayer.playerAI.getName() + "name =" + onePlayer.nameAI.getText());
                onePlayer.briscolaStart();
                onePlayer.gameWindow(primaryStage);
                onePlayer.playerPickCard();
                primaryStage.setScene(onePlayer.scene2); // Go to playing Briscola scene
            }
        });*/

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
