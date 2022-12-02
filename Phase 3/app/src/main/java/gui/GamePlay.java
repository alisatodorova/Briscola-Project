/*
package gui;

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

public class GamePlay {
    public static boolean endAt61 = false; // this can be activated to finish the game at 61 points rather than playing
    public int nextPlayerID;                // \->through the entire game.
    public static int playerCount;
    public Player playerBottom, playerTop;
    public LinkedList<Card> cardsLeft;
    protected TranslateTransition tr = new TranslateTransition();
    protected Main main;
    protected static final boolean DEBUG = true;
    protected static final boolean DEBUG_LOOP = true;
    protected boolean winnerScreenActivated = false;
    protected boolean access = false;
    protected Briscola game;
    protected BorderPane pane;
    protected Label nameBottom, nameTop;
    protected Label pointsBottom, pointsTop;
    protected Card bottomCardPlayed, topCardPlayed;
    protected int bottomIntPlayed, topIntPlayed;
    protected Player winner;
    protected Stage primaryStage;
    protected Rectangle[] bottomHand,topHand;
    protected Scene scene1, scene2;
    protected Image backOfCards = new Image("cards/z_card_back_black.png");
    private int roundCount; // if one wants to end the game prematurely, if one Player hit 61 points.

    public GamePlay (Main main){
        this.main = main;
        this.roundCount = 0;
    }

    public Stage stageGame (Stage primaryStage){
        this.primaryStage = primaryStage;
        try{
            bottomHand = new Rectangle[3];
            topHand = new Rectangle[3];
            for (int i = 0; i < 3; i++)
            {
                bottomHand[i] = new Rectangle(100, 150);
                topHand[i] = new Rectangle(100, 150);
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

    public void briscolaStart() {
        game = new Briscola(playerBottom, playerTop);
        game.start();
        cardsLeft = (LinkedList<Card>)(game.deck.sorted.clone());
        topIntPlayed = -1;
        bottomIntPlayed = -1;
    }

    public void gameWindow()
    {
        pane = new BorderPane();
        StackPane spaceTwo = new StackPane();
        spaceTwo.setBackground(new Background(new BackgroundFill(Color.DARKGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        scene2 = new Scene(spaceTwo, 1000, 800);
        setHandDown(bottomHand, playerBottom);
        setHandDown(topHand, playerTop);
        screen(true);
        screen(false);

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

    protected void screen(boolean playerBottomBoolean){
        BorderPane bPane = new BorderPane();
        HBox box;
        if (playerBottomBoolean) {
            box = new HBox(20, bottomHand[0], bottomHand[1], bottomHand[2]);
        } else { //player ID is 2
            box = new HBox(20, topHand[0], topHand[1], topHand[2]);
        }
        bPane.setCenter(box);
        box.setPadding(new Insets(10));
        box.setAlignment(Pos.CENTER);
        if (playerBottomBoolean){
            bPane.setLeft(bottomInfo());
            pane.setBottom(bPane);
        }
        else{
            bPane.setLeft(topInfo());
            pane.setTop(bPane);
        }

    }

    protected VBox bottomInfo(){
        nameBottom.setFont(Font.font("Verdana", 20));
        nameBottom.setStyle("-fx-font-size: 20;");
        if(DEBUG) System.out.println("p1 Name" + nameBottom.toString());
        pointsBottom = new Label("Points: " + playerBottom.getPoints());
        pointsBottom.setFont(Font.font("Verdana", 20));
        pointsBottom.setStyle("-fx-font-size: 20;");
        VBox infoBottom = new VBox(10, nameBottom, pointsBottom);
        infoBottom.setPadding(new Insets(10));
        infoBottom.setAlignment(Pos.CENTER);
        return infoBottom;
    }

    protected VBox topInfo(){
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

    private void setHand(Rectangle[] hand, Player player){
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
    private void setHandDown (Rectangle[] hand, Player player) {
        for (int i = 0; i < hand.length; i++){
            if (hand[i] != null){
                if (player.getHand()[i] == null){
                    hand[i].setFill(Color.BLACK);
                }
                else {hand[i].setFill(new ImagePattern(backOfCards));}
            }
            else {
                hand[i] = new Rectangle(100, 150);
                hand[i].setFill(Color.DARKGRAY);
            }
        }
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

    private void newRound()
    {
        if(!game.shuffled.isEmpty()) {
            game.newCards();
            roundCount++;
        }
        updatePoints();
        if (endAt61)
        {
            endingWith61();
        }
        clearTable();

        setHandDown(bottomHand, playerBottom);
        setHandDown(topHand, playerTop);
        screen(true);
        screen(false);

        nextPlayerID = checkPlayer();
    }

    protected void endingWith61 (){
        if(playerBottom.getPoints() > 60 || playerTop.getPoints() > 60){
            winnerScreenActivated = true;
            if (DEBUG) System.out.println("Winner Screen Activation");
            System.out.println("Game Won after " + roundCount + " rounds.");
            Winner winner = new Winner(this, main);
            GridPane winner1 = winner.gridPane(primaryStage);
            primaryStage.setScene(new Scene(winner1));
        }
    }

    protected void checkFinal(){
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
            Winner winner = new Winner(this, main);
            Scene winner1 = new Scene(winner.gridPane(primaryStage));

            primaryStage.setScene(winner1);
        }
    }

    protected int checkPlayer() {
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
                    return 1;
                } else { return 2; }
            }
            // in this case Winner MUST be null
            // start of the game -> AI and Human have not played
            else if (winner == null && bottomIntPlayed == -1 && topIntPlayed == -1) {
                return 1;
            }
            // Human already played and AI hasnt
            else if (bottomIntPlayed != -1 && topIntPlayed == -1) {
                return 2;
            }
            // HUman Hasnt played yet but AI has
            else if (bottomIntPlayed == -1 && topIntPlayed != -1) {

                return 1;
            }
            // If Both have played already then Check the cards and determine the winner
            else if (bottomIntPlayed != -1 && topIntPlayed != -1) {
                checkWinner();
            } else {
                if(DEBUG_LOOP) System.out.println("ERROOOOOOOORRR--------------------------------------");
                return 1;
            }
        }
        return winner.getId();
    }

    protected void checkWinner()
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

    protected void showWinner(){
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

    public void start() {
    }

    protected void move (Player player){
    }

    protected boolean isEmpty (Card[] hand){
        for (Card card : hand) {
            if (card != null) {
                return false;
            }
        }
        return true;
    }

    protected void moveCard (int index, Player player, boolean newAccess){
        access = newAccess;
        if(player.getId() == 1) {
            bottomCardPlayed = player.playCard(index);
            bottomIntPlayed = index;
            tr.setByY(-200);
            Deck.cardsLeftUpdate(cardsLeft, playerBottom.currentHand[bottomIntPlayed]);
        }
        else {
            topCardPlayed = player.playCard(index);
            topIntPlayed = index;
            tr.setByY(200);
            Deck.cardsLeftUpdate(cardsLeft, playerTop.currentHand[topIntPlayed]);
        }

        if (index == 0) {
            tr.setByX(120);
        } else if (index == 1) {
            tr.setByX(0);
        } else {
            tr.setByX(-120);
        }
        tr.setDuration(Duration.millis(600));
        tr.setNode(bottomHand[2]);
        tr.play();
        tr.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                checkPlayer();
            }
        });
    }
}
*/
