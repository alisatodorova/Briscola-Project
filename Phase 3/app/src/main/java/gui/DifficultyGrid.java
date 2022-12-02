/*
package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class DifficultyGrid {
   protected static final boolean DEBUG_CONT = false;
   protected static final boolean DEBUG = true;
   protected OnePlayer player;
   protected int playerCount;
   

   public DifficultyGrid (OnePlayer player, int playerCount){
       this.player = player;
       this.playerCount = playerCount;
   }

    protected GridPane grid(Stage primaryStage, DifficultySelector[] actions){
        if (DEBUG_CONT) System.out.println("We create a grid");
        GridPane grid = new GridPane();
        StackPane stackPane = new StackPane();
        String bgHome = "art.jpg";
        ImagePane bgH = new ImagePane(bgHome);

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(40);
        grid.setVgap(45);
        grid.setPadding(new Insets(45, 45, 45, 45));
        Button[] buttons = new Button[actions.length];
        for (int i = 0; i< actions.length; i++){
            buttons[i] = new Button(actions[i].title);
            grid.add (buttons[i], 1, (i+1));
            int finalI = i;
            buttons[i].setOnAction(new EventHandler<>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if (DEBUG) System.out.println("EASY PRESSED..._______________ Random");
                    // Here goes the action to be perfomed
                    player.setDifficulty(actions[finalI].difficulty);
                    continueToGame();
                }
            });
        }
        stackPane.getChildren().addAll(bgH, grid);
        return grid;
    }
    protected void continueToGame(){
        player.nameBottom = new Label(player.playerBottom.getName());
        player.nameTop = new Label(player.playerTop.getName());
        player.briscolaStart();
        if (DEBUG_CONT) System.out.println(player.game.deck.sorted.toString());
        // Starting the Green Game Window
        player.gameWindow();

        // Human starts the Game;
        if (playerCount == 1)
        {
            player.access = true;
            OnePlayer onePlayer = (OnePlayer)(player);
            onePlayer.playerPickCard();
        }
        player.primaryStage.setScene(player.scene2);
        player.start();
    }
}
*/
