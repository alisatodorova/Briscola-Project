package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class AIDifficultyDouble {
    private static final boolean DEBUG = true;
    private static final boolean DEBUG_CONT = false;
    AIPlayer aiPlayer;

    public AIDifficultyDouble(AIPlayer aiPlayer) {this.aiPlayer = aiPlayer;}

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


        aiPlayer.scene1 = new Scene(stackPane);

        Label title = new Label("Briscola AI Difficulty");
        title.setStyle("-fx-font-size: 40; -fx-text-fill: white;-fx-font-weigth:bold");
        grid.add(title, 1,0,1,1);

        Button easyT = new Button("Random");
        Button mediumT = new Button("BaseLine");
        Button hardT = new Button("Greedy");
        Button extremeT = new Button("MiniMax");
        Button hybridT = new Button("Hybrid");
        Label top = new Label("Player Top: ");
        top.setStyle("-fx-font-size: 30; -fx-text-fill: WHITE;-fx-font-weigth:bold");

        Button easyB = new Button("Random");
        Button mediumB = new Button("BaseLine");
        Button hardB = new Button("Greedy");
        Button extremeB = new Button("Minimax");
        Button hybridB = new Button("Hybrid");
        Label bottom = new Label("Player Bottom: ");
        bottom.setStyle("-fx-font-size: 30; -fx-text-fill: white;-fx-font-weigth:bold");



        grid.add(top, 1,1, 1, 1);
        grid.add(easyT, 1,2);
        grid.add(mediumT, 2, 2);
        grid.add(hardT, 1, 3);
        grid.add(extremeT, 2, 3);
        grid.add(hybridT, 1, 4);

        grid.add(bottom, 1,5, 1, 1);
        grid.add(easyB, 1,6);
        grid.add(mediumB, 2, 6);
        grid.add(hardB, 1, 7);
        grid.add(extremeB, 2, 7);
        grid.add(hybridB, 1, 8);

        stackPane.getChildren().addAll(bgH, grid);


        easyT.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent event) {
                if (DEBUG) System.out.println("EASY PRESSED..._______________ Random Top");
                // Here goes the action to be perfomed
                aiPlayer.setDifficultyTop(1);
                cont();
            }
        });
        mediumT.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent event) {
                if (DEBUG) System.out.println("MEDIUM PRESSED..._______________ BaseLine Top");
                // Here goes the action to be perfomed
                aiPlayer.setDifficultyTop(2);
                cont();
            }
        });
        hardT.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent event) {
                if (DEBUG) System.out.println("HARD PRESSED..._______________ Single State MiniMax Top");
                // Here goes the action to be perfomed
                aiPlayer.setDifficultyTop(3);
                cont();
            }
        });
        extremeT.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent event) {
                if (DEBUG) System.out.println("EXTREME PRESSED..._______________MC Approximation Top");
                // Here goes the action to be perfomed
                aiPlayer.setDifficultyTop(4);
                cont();
            }
        });
        hybridT.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent event) {
                if (DEBUG) System.out.println("HYBRID PRESSED..._______________ANNAgent Top");
                // Here goes the action to be perfomed
                aiPlayer.setDifficultyTop(5);
                cont();
            }
        });

        easyB.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent event) {
                if (DEBUG) System.out.println("easy PRESSED..._______________ Random Bottom");
                // Here goes the action to be perfomed
                aiPlayer.setDifficultyBottom(1);
                cont();
            }
        });
        mediumB.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent event) {
                if (DEBUG) System.out.println("medium PRESSED..._______________ BaseLine Bottom");
                // Here goes the action to be perfomed
                aiPlayer.setDifficultyBottom(2);
                cont();
            }
        });
        hardB.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent event) {
                if (DEBUG) System.out.println("hard PRESSED..._______________ Single State MiniMax Bottom");
                // Here goes the action to be perfomed
                aiPlayer.setDifficultyBottom(3);
                cont();
            }
        });
        extremeB.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent event) {
                if (DEBUG) System.out.println("extreme PRESSED..._______________ MCApprox. Bottom");
                // Here goes the action to be perfomed
                aiPlayer.setDifficultyBottom(4);
                cont();
            }
        });
        hybridB.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent event) {
                if (DEBUG) System.out.println("hybrid PRESSED..._______________ ANNAgent Bottom");
                // Here goes the action to be perfomed
                aiPlayer.setDifficultyBottom(5);
                cont();
            }
        });

        return grid;
    }

    private void cont()
    {
        if (aiPlayer.getDifficultyTop() != 0 && aiPlayer.getDifficultyBottom() != 0) {
            if (DEBUG_CONT) System.out.println("Top Diff" + aiPlayer.getDifficultyTop());
            if (DEBUG_CONT) System.out.println("Bottom Diff" + aiPlayer.getDifficultyBottom());
            aiPlayer.nameBottom = new Label(aiPlayer.playerBottom.getName());
            aiPlayer.nameTop = new Label(aiPlayer.playerTop.getName());
            aiPlayer.briscolaStart();
            // Starting the Green Game Window
            aiPlayer.gameWindow();
            // Setting the Next Window
            aiPlayer.primaryStage.setScene(aiPlayer.scene2);
            aiPlayer.aiTurn(1);
//            aiPlayer.checkPlayer();
        }
    }
}
