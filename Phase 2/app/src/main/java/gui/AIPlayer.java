package gui;

import AI.LargeBrain.BigBrain;
import AI.LargeBrain.BigBrainPruning;
import AI.LargeBrain.BigState;
import AI.LargeBrain.RandomHand;
import AI.lowBrain.*;
import application.Briscola;
import application.Card;
import application.Deck;
import application.Player;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.LinkedList;

public class AIPlayer {
    public boolean DEBUG = false;
    public boolean DEBUG_LOOP = false;
    public LinkedList<Card> cardsLeft;
    boolean winnerScreenActivated = false;
    public State aiState;
    public BigState aiBigState;
    private int difficultyT;
    private int difficultyB;
    //Player instances
    public Briscola game;
    public BorderPane pane;
    public Label nameBottom, nameTop;
    public Label pointsBottom, pointsTop;
    public Card bottomCardPlayed, topCardPlayed;
    public int bottomIntPlayed, topIntPlayed;
    public Player winner;
    public Main main;
    public Stage primaryStage;
    public Rectangle[] bottomHand,topHand;
    public Scene scene1, scene2;
    public Player playerBottom = new Player("Bottom AI", 1);
    public Player playerTop = new Player("Top AI", 2);
    public Image backOfCards = new Image("cards/z_card_back_black.png");
    public TranslateTransition tr = new TranslateTransition();
    public Brain brainAITop, brainAIBottom;
    public BigBrainPruning bigBrainPruningAITop, bigBrainPruningAIBottom;
    public BigBrain bigBrainTop, bigBrainBottom;
    private int roundCount;



    public AIPlayer(Main main){
        this.main = main;
        roundCount = 0;
    }

    public void setDifficultyBottom(int level) {
        this.difficultyB = level;
    }

    public void setDifficultyTop(int level) {
        this.difficultyT = level;
    }

    public int getDifficultyBottom() {
        return this.difficultyB;
    }

    public int getDifficultyTop() {
        return this.difficultyT;
    }

    public Stage stage (Stage primaryStage){
        this.primaryStage = primaryStage;
        try{
            bottomHand = new Rectangle[3];
            topHand = new Rectangle[3];
            for (int i = 0; i < 3; i++)
            {
                bottomHand[i] = new Rectangle(100, 150);
                topHand[i] = new Rectangle(100, 150);
            }
            if (difficultyT == 0 || difficultyB == 0)
            {
                AIDifficultyDouble aiDifficulty = new AIDifficultyDouble(this);
                GridPane grid = aiDifficulty.gridPane(primaryStage);
            }

            primaryStage.setScene(scene1);
            primaryStage.setTitle("Briscola");
            primaryStage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return primaryStage;
    }

    //We need to adapt briscolaStart from TwoPlayers
    public void briscolaStart() {
        game = new Briscola(playerBottom, playerTop);
        game.start();
        cardsLeft = (LinkedList<Card>)(game.deck.sorted.clone());
        aiSetUp();
        topIntPlayed = -1;
        bottomIntPlayed = -1;
    }

    // we will keep the Game Window in the basic the same just remove the Player 2 actions that are possible...
    public void gameWindow()
    {
        pane = new BorderPane();
        StackPane spaceTwo = new StackPane();
        spaceTwo.setBackground(new Background(new BackgroundFill(Color.DARKGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        scene2 = new Scene(spaceTwo, 1000, 800);
        setHands(bottomHand, playerBottom);
        setHands(topHand, playerTop);
        bottomScreen();
        upScreen();

        Rectangle deckOfCards = new Rectangle(100, 150);
        deckOfCards.setFill(new ImagePattern(backOfCards));

        Rectangle highSuit = new Rectangle(100, 150);
        highSuit.setFill(new ImagePattern(new Image("cards/" + game.getHighSuit().toString() + ".png")));

        VBox rightPanel = new VBox(50, deckOfCards, highSuit);
        rightPanel.setPadding(new Insets(10, 10, 10, 10));
        rightPanel.setAlignment(Pos.CENTER);
        pane.setRight(rightPanel);

        spaceTwo.getChildren().add(pane);
        //return pane;
    }

    //    Human Player will pick the card

    private void bottomScreen()
    {
        BorderPane bottom = new BorderPane();
        HBox cardsHuman = new HBox(20, bottomHand[0], bottomHand[1], bottomHand[2]);
        bottom.setCenter(cardsHuman);
        cardsHuman.setAlignment(Pos.CENTER);
        cardsHuman.setPadding(new Insets(10, 10, 10, 10));
        bottom.setLeft(bottomInfo());
        pane.setBottom(bottom);
    }

    private void upScreen()
    {
        BorderPane up = new BorderPane();
        HBox cardsAI = new HBox(20, topHand[0], topHand[1], topHand[2]);
        up.setCenter(cardsAI);
        cardsAI.setAlignment(Pos.CENTER);
        cardsAI.setPadding(new Insets(10));
        up.setLeft(topInfo());
        pane.setTop(up);
    }

    private VBox bottomInfo()
    {
        nameBottom.setFont(Font.font("Verdana", 20));
        nameBottom.setStyle("-fx-font-size: 20;");
        if(DEBUG) System.out.println("p1 Name" + nameBottom.toString());
        pointsBottom = new Label("Points: " + playerBottom.getPoints());
        pointsBottom.setFont(Font.font("Verdana", 20));
        pointsBottom.setStyle("-fx-font-size: 20;");
        VBox infoHuman = new VBox(10, nameBottom, pointsBottom);
        infoHuman.setPadding(new Insets(10, 10, 10, 10));
        infoHuman.setAlignment(Pos.CENTER);
        return infoHuman;
    }

    private VBox topInfo(){
        nameTop.setFont(Font.font("Verdana", 20));
        nameTop.setStyle("-fx-font-size: 20;");
        if(DEBUG) System.out.println("Name P2" + nameTop.toString());
        pointsTop = new Label("Points: " + playerTop.getPoints());
        pointsTop.setFont(Font.font("Verdana", 20));
        pointsTop.setStyle("-fx-font-size: 20;");
        VBox infoAI = new VBox(10, nameTop, pointsTop);
        infoAI.setPadding(new Insets(10, 10, 10, 10));
        infoAI.setAlignment(Pos.CENTER);
        return infoAI;
    }

    private void aiSetUp(){
        switch (difficultyT)
        {
            case 1 : brainAITop = new RandBrain();
            case 2 : brainAITop = new BaseLineBrain(game.getHighSuit());
            case 3 : brainAITop = new SimpleBrain(game.getHighSuit());
            case 4 : {
                bigBrainPruningAITop = new BigBrainPruning();
                bigBrainTop = new BigBrain(playerTop.getId());
            }
        }
        switch (difficultyB)
        {
            case 1 : brainAIBottom = new RandBrain();
            case 2 : brainAIBottom = new BaseLineBrain(game.getHighSuit());
            case 3 : brainAIBottom = new SimpleBrain(game.getHighSuit());
            case 4 : {
                bigBrainPruningAIBottom = new BigBrainPruning(); bigBrainBottom = new BigBrain(playerBottom.getId());}
        }
    }

    public void aiTurn(int playerID)
    {
        if (playerID == 1){
            // state
            LinkedList<Card> cardsLeftBottom = (LinkedList<Card>)cardsLeft.clone();
            Deck.cardsLeftUpdateHand(cardsLeftBottom, playerBottom.getHand());
            if (difficultyB == 4)
            {
                aiBigState = new BigState(cardsLeftBottom, playerBottom.getHand(), RandomHand.randomHand(cardsLeftBottom), game.getHighSuit());
//                bottomIntPlayed = bigBrainBottom.miniMax(aiBigState, topCardPlayed);
                bottomIntPlayed = bigBrainPruningAIBottom.alphaBeta(aiBigState, topCardPlayed);
                if (playerBottom.getHand()[bottomIntPlayed] == null){
                    brainAIBottom = new SimpleBrain(game.getHighSuit());
                    aiState = new State(true, playerBottom.getHand());
                    bottomIntPlayed = brainAIBottom.whatToPlay(aiState, topCardPlayed);
                }
            }
            else{
                aiState = new State(true, playerBottom.getHand());
                // AI Move in Integer that represents the Card that is being played
                bottomIntPlayed = brainAIBottom.whatToPlay(aiState, topCardPlayed);
            }
            if (DEBUG) System.out.println("Bottom AI Index " + bottomIntPlayed);
            // AI Move in the card instance to have the backend working with it.
            bottomCardPlayed = playerBottom.playCard(bottomIntPlayed);
            Deck.cardsLeftUpdate(cardsLeft, playerBottom.getHand()[bottomIntPlayed]);
            // Move Card
            aiTurnMove(bottomHand, bottomIntPlayed, -1);

        }else if (playerID == 2) { // for the TOP AI
            LinkedList<Card> cardsLeftTop = (LinkedList<Card>) (cardsLeft.clone());
            Deck.cardsLeftUpdateHand(cardsLeftTop, playerTop.getHand());
            if (difficultyT == 4) {
                aiBigState = new BigState(cardsLeftTop, playerTop.getHand(), RandomHand.randomHand(cardsLeftTop), game.getHighSuit());
//                topIntPlayed = bigBrainTop.miniMax(aiBigState, bottomCardPlayed);
                topIntPlayed = bigBrainPruningAITop.alphaBeta(aiBigState, bottomCardPlayed);
                if (playerTop.getHand()[topIntPlayed] == null){
                    brainAITop = new SimpleBrain(game.getHighSuit());
                    aiState = new State(true, playerTop.getHand());
                    topIntPlayed = brainAITop.whatToPlay(aiState, bottomCardPlayed);
                }
            } else {
                aiState = new State(true, playerTop.currentHand);
                // AI Move in Integer that represents the Card that is being played
                topIntPlayed = brainAITop.whatToPlay(aiState, bottomCardPlayed);
            }
            // AI Move in the card instance to have the backend working with it.
            topCardPlayed = playerTop.playCard(topIntPlayed);
            Deck.cardsLeftUpdate(cardsLeft, playerTop.getHand()[topIntPlayed]);
            // Move Card
            aiTurnMove(topHand, topIntPlayed, 1);
        }

//        Add for BigBrainPruning...

    }

    private void aiTurnMove(Rectangle[] hand, int intPlayed, int plus){
        tr.setByY(200 * plus);
        if (intPlayed == 0){
            tr.setByX(+120);
        } else if (intPlayed == 1) {
            tr.setByX(0);
        } else {
            tr.setByX(-120);
        }
        tr.setDuration(Duration.millis(600));
        tr.setNode(hand[intPlayed]);
        tr.play();
        tr.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                checkPlayer();
            }
        });
    }

    private void checkWinner()
    {
        if (winner == null && topIntPlayed != -1 && bottomIntPlayed != -1 ) {
            // && (humanCardPlayed != null && topCardPlayed != null)
            winner = game.gameLogic.checkCards(bottomCardPlayed, topCardPlayed, game.getHighSuit(), playerBottom, playerTop);
        }
        else if (topIntPlayed != -1 && bottomIntPlayed != -1)
        {
            if (winner.getId() == playerBottom.getId()) {
                winner = game.gameLogic.checkCards(bottomCardPlayed, topCardPlayed, game.getHighSuit(), playerBottom, playerTop);
            } else {
                winner = game.gameLogic.checkCards(topCardPlayed, bottomCardPlayed, game.getHighSuit(), playerTop, playerBottom);
            }
        }
        showWinner();
    }

    private void showWinner(){
        if (winner.getId() == playerBottom.getId())
        {
            if(DEBUG) System.out.println("AI Bottom won this Round");
            tr.setByY(-175);
            tr.setByX(0);
            tr.setDuration(Duration.millis(600));
            tr.setNode(bottomHand[bottomIntPlayed]);
            tr.play();
            tr.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if(DEBUG) System.out.println("Clearing");
                    newRound();
                }
            });
        }
        else {
            if(DEBUG) System.out.println("AI Top won this Round");
            tr.setByY(175);
            tr.setByX(0);
            tr.setDuration(Duration.millis(600));
            tr.setNode(topHand[topIntPlayed]);
            tr.play();
            tr.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if(DEBUG) System.out.println("Clearing");
                    newRound();
                }
            });
        }
    }

    private void newRound()
    {
        if(!game.shuffled.isEmpty()) {
            game.newCards();
            roundCount++;
        }
        updatePoints();
//        if(playerBottom.getPoints() > 60 || playerTop.getPoints() > 60){
//            winnerScreenActivated = true;
//            if (DEBUG) System.out.println("Winner Screen Activation");
//            System.out.println("Game Won after " + roundCount + " rounds.");
//            WinnerAI winnerAI = new WinnerAI(this, main);
//            Scene winner1 = winnerAI.gridPane(primaryStage);
//            primaryStage.setScene(winner1);
//        }
        clearTable();

        setHands(bottomHand, playerBottom);
        setHands(topHand, playerTop);
        bottomScreen();
        upScreen();

        checkPlayer();
    }

    private void updatePoints() {
        if (DEBUG) System.out.println("Top points: " + playerTop.getPoints());
        if (DEBUG) System.out.println("Bottom points: " + playerBottom.getPoints());
        pointsTop.setText("Points: " + playerTop.getPoints());
        pointsBottom.setText("Points: " + playerBottom.getPoints());
    }

    private void clearTable()
    {
        for (int i = 0; i < bottomHand.length; i++) {
            if (i == topIntPlayed) {
                topHand[i].setFill(Color.DARKGREEN);
                topHand[i] = null;
                Rectangle newCard = new Rectangle(100, 150);
                newCard.setFill(new ImagePattern(backOfCards));
                topHand[i] = newCard;
            }
            if (i == bottomIntPlayed) {
                bottomHand[i].setFill(Color.DARKGREEN);
                bottomHand[i] = null;
                Rectangle newCard = new Rectangle(100, 150);
                newCard.setFill(Color.BLACK);
                bottomHand[i] = newCard;
            }
        }

        bottomIntPlayed = -1;
        topIntPlayed = -1;
        topCardPlayed = null;
        bottomCardPlayed = null;

        if (DEBUG) System.out.println("Table Cleared");
    }


    private void setHands(Rectangle[] hand, Player player){
        for (int i = 0; i<3; i++){
            if ((hand[i] != null)) {
                if ((player.getHand()[i] == null)) {
                    if (DEBUG) System.out.println("BLACK CARD -----------------------------------");
                    hand[i].setFill(Color.BLACK);
                }
                else {
                    hand[i].setFill(new ImagePattern(new Image("cards/" + player.getHand()[i] + ".png")));
                }
            }
            else
            {
                hand[i] = new Rectangle(100, 150);
                hand[i].setFill(Color.GRAY);
            }
        }
    }

    private void checkPlayer() {
        checkFinal();

        if (!winnerScreenActivated) {
            if (DEBUG_LOOP) System.out.println("Bottom Card Center " + bottomCardPlayed);
            if (DEBUG_LOOP) System.out.println("Bottom Card Center INT " + bottomIntPlayed);
            if (DEBUG_LOOP) System.out.println("Top Card Center " + topCardPlayed);
            if (DEBUG_LOOP) System.out.println("Top Card Center INT " + topIntPlayed);
            if (winner != null && DEBUG_LOOP) System.out.println("Winner: " + winner.getName());
            // Winner is Known and we then choose based on the winner who goes first
            if (winner != null && topIntPlayed == -1 && bottomIntPlayed == -1) {
                if (winner.getId() == playerBottom.getId()) {
                    aiTurn(1);
                } else { aiTurn(2); }
            }
            // in this case Winner MUST be null
            // start of the game -> AI and Human have not played
            else if (winner == null && bottomIntPlayed == -1 && topIntPlayed == -1) {
                aiTurn(1);
            }
            // Human already played and AI hasnt
            else if (bottomIntPlayed != -1 && topIntPlayed == -1) {
                aiTurn(2);
            }
            // HUman Hasnt played yet but AI has
            else if (bottomIntPlayed == -1 && topIntPlayed != -1) {

                aiTurn(1);
            }
            // If Both have played already then Check the cards and determine the winner
            else if (bottomIntPlayed != -1 && topIntPlayed != -1) {
                checkWinner();
            } else {
                if(DEBUG_LOOP) System.out.println("ERROOOOOOOORRR--------------------------------------");
                aiTurn(1);
            }
        }
    }

    private void checkFinal(){
        //         if Winner
        int count = 0;
        for (int i = 0; i < playerBottom.getHand().length; i++) {
            if (playerBottom.getHand()[i] == null) {
                count++;
            } else { break; }
            if (playerTop.getHand()[i] == null) {
                count++;
            } else {
                break;
            }
        }
        if (count == 6) {
            winnerScreenActivated = true;
            if (DEBUG) System.out.println("Winner Screen Activation");
            WinnerAI winnerAI = new WinnerAI(this, main);
            Scene winner1 = winnerAI.gridPane(primaryStage);

            primaryStage.setScene(winner1);
        }
    }

}
