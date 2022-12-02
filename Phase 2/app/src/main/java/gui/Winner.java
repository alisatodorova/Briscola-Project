//package gui;
//
//import application.Player;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.geometry.Insets;
//import javafx.geometry.Pos;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.StackPane;
//import javafx.stage.Stage;
//
//abstract class Winner {
//    GamePlayer gamePlayer;
//    Main main;
//    Player player;
//
//    public Winner(GamePlayer gamePlayer, Main main){ this.gamePlayer = gamePlayer; this.main = main;}
//
//    public GridPane gridPane (Stage primaryStage)
//    {
//        GridPane grid = new GridPane();
//        StackPane spaceOne = new StackPane();
//        String bgHome = "art.jpg";
//        Scene endging = new Scene(spaceOne);
//
//        player = gamePlayer.game.winGame();
//
//        ImagePane bgH = new ImagePane(bgHome);
//
//        spaceOne.getChildren().addAll(bgH, grid);
//
//        grid.setAlignment(Pos.CENTER);
//        grid.setHgap(40);
//        grid.setVgap(40);
//        grid.setPadding(new Insets(45));
//        String winningText = player.getName() + "is the Winner!";
//        if(gamePlayer.DEBUG)System.out.println(winningText);
//
//        Label winnerName = new Label (winningText);
//        winnerName.setStyle("-fx-font-size: 40; -fx-text-fill: white;-fx-font-weigth:bold");
//        grid.add(winnerName, 0,0,1,1);
//
//        String pointsText = player.getPointsToString() + " points";
//        Label winnerPoints = new Label(pointsText);
//        winnerPoints.setStyle("-fx-font-size: 40; -fx-text-fill: white;-fx-font-weigth:bold");
//        grid.add(winnerPoints, 1, 1, 1,1);
//
//        Button newGame = new Button("Rematch");
//        Button mainMenu = new Button("Main menu");
//        Button quit = new Button("Quit");
//
//        grid.add(newGame,0,2,1,1);
//        grid.add(mainMenu,1,2,1,1);
//        grid.add(quit,2,2,1,1);
//
//        newGame.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            /**
//             * When the user presses the new game button,
//             * the player is taken to the initial screen
//             * to start a new beiscola game with the same
//             * player information
//             * @param e the action event
//             */
//            public void handle(ActionEvent e) {
//                if(gamePlayer.DEBUG)System.out.println(gamePlayer.bottom.getName() + "name =" + twoPlayers.nameP1.getText());
//                if(gamePlayer.DEBUG)System.out.println(gamePlayer.up.getName() + "name =" + twoPlayers.nameP2.getText());
//                gamePlayer.briscolaStart();
//                gamePlayer.gameWindow(primaryStage);
////                twoPlayers.acessPlayerOne = true;
////                twoPlayers.acessPlayerTwo = false;
//                gamePlayer.playerPickCard();
//                primaryStage.setScene(gamePlayer.scene2); // Go to playing Briscola scene
//            }
//        });
//
//        return grid;
//    }
//
//}
