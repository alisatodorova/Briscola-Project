package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.Window;


public class AIDifficulty {
    private static final boolean DEBUG = true;
    private static final boolean DEBUG_CONT = false;
    OnePlayer onePlayer;

    public AIDifficulty(OnePlayer onePlayer) {this.onePlayer = onePlayer;}

    // this is just coppied over from SignUp
    public GridPane gridPane (Stage primaryStage) {

        if (DEBUG_CONT) System.out.println("We create a grid");
        GridPane grid = new GridPane();
        StackPane stackPane = new StackPane();
        String bgHome = "art.jpg";
        ImagePane bgH = new ImagePane(bgHome);

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(40);
        grid.setVgap(45);
        grid.setPadding(new Insets(45, 45, 45, 45));


        onePlayer.scene1 = new Scene (stackPane);

        Label title = new Label("Briscola AI Difficulty");
        title.setStyle("-fx-font-size: 40; -fx-text-fill: white;-fx-font-weigth:bold");
        grid.add(title, 1,0,1,1);

        Button easy = new Button("Random");
        Button medium = new Button("BaseLine");
        Button hard = new Button("Greedy");
        Button extreme = new Button("MiniMax");
        Button hybrid = new Button("Hybrid");

        grid.add(easy, 1,1);
        grid.add(medium, 1, 2);
        grid.add(hard, 1, 3);
        grid.add(extreme, 1, 4);
        grid.add(hybrid, 1, 5);
        stackPane.getChildren().addAll(bgH, grid);


        easy.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                if (DEBUG) System.out.println("EASY PRESSED..._______________ Random");
                // Here goes the action to be perfomed
                onePlayer.setDifficulty(1);
                continueToGame();
            }
        });
        medium.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                if (DEBUG) System.out.println("MEDIUM PRESSED..._______________BaseLine");
                // Here goes the action to be perfomed
                onePlayer.setDifficulty(2);
                continueToGame();
            }
        });
        hard.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                if (DEBUG) System.out.println("HARD PRESSED..._______________SingeState MiniMax");
                // Here goes the action to be perfomed
                onePlayer.setDifficulty(3);
                continueToGame();
            }
        });
        extreme.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                if (DEBUG) System.out.println("EXTREME PRESSED..._______________MCApprox.");
                // Here goes the action to be perfomed
                onePlayer.setDifficulty(4);
                continueToGame();
            }
        });
        hybrid.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                if (DEBUG) System.out.println("HYBRID PRESSED..._______________ANNAgent.");
                // Here goes the action to be perfomed
                onePlayer.setDifficulty(5);
                continueToGame();
            }
        });

        return grid;
    }

    private void continueToGame(){
        onePlayer.nameHuman = new Label(onePlayer.playerHuman.getName());
        onePlayer.nameAI = new Label(onePlayer.playerAI.getName());
        onePlayer.briscolaStart();
        if (DEBUG_CONT) System.out.println(onePlayer.game.deck.sorted.toString());
        // Starting the Green Game Window
        onePlayer.gameWindow(onePlayer.primaryStage);

        // Human starts the Game;
        onePlayer.access = true;
        onePlayer.playerPickCard();
        onePlayer.primaryStage.setScene(onePlayer.scene2);
    }
}
