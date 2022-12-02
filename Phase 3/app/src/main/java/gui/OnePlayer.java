package gui;

import AI.LargeBrain.BigBrainPruning;
import AI.LargeBrain.BigState;
import AI.LargeBrain.BigBrain;
import AI.LargeBrain.RandomHand;
import AI.aNN.ANNAgent;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.LinkedList;

public class OnePlayer {
    public boolean DEBUG = false;
    public boolean DEBUG_LOOP = false;
    boolean access = false;
    boolean winnerScreenActivated = false;
    public State aiState;
    public BigState aiBigState;
    private int difficulty;
    //Player instances
    public Briscola game;
    public BorderPane pane;
    public Label nameHuman, nameAI;
    public Label pointsHuman, pointsAI;
    public Card humanCardPlayed, aiCardPlayed;
    public int humanIntPlayed, aiIntPlayed;
    public Player winner;
    public Main main;
    public Stage primaryStage;
    public Rectangle[] humanHand,aiHand;
    public Scene scene1, scene2;
    public Player playerHuman = new Player("You", 1);
    public Player playerAI = new Player("Computer", 2);
    public Image backOfCards = new Image("cards/z_card_back_black.png");
    public TranslateTransition tr = new TranslateTransition();
    public Brain brainAI;
    public BigBrainPruning bigBrainPruningAI;
    public BigBrain bigBrainAI;
    public ANNAgent aiANN;
    protected LinkedList<Card> cardsLeft;



    public OnePlayer(Main main){
        this.main = main;
    }

    public void setDifficulty(int level)
    {
        this.difficulty = level;
    }

    public Stage stage (Stage primaryStage){
        this.primaryStage = primaryStage;
        try{
            humanHand = new Rectangle[3];
            aiHand = new Rectangle[3];
            for (int i = 0; i < 3; i++)
            {
                humanHand[i] = new Rectangle(100, 150);
                aiHand[i] = new Rectangle(100, 150);
            }
            if (difficulty == 0)
            {
                AIDifficulty aiDifficulty = new AIDifficulty(this);
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
        game = new Briscola(playerHuman, playerAI);
        game.start();
        cardsLeft = (LinkedList<Card>)(game.deck.sorted.clone());
        if (DEBUG) System.out.println("cards Left size " + cardsLeft.size());
//        Deck.cardsLeftUpdate(cardsLeft, game.getHighSuit());
//        Deck.cardsLeftUpdateHand(cardsLeft, playerAI.currentHand);
        aiSetUp();
        setAiHand();
        aiIntPlayed = -1;
        humanIntPlayed = -1;
    }

    // we will keep the Game Window in the basic the same just remove the Player 2 actions that are possible...
    public void gameWindow(Stage primaryStage)
    {
        pane = new BorderPane();
        StackPane spaceTwo = new StackPane();
        spaceTwo.setBackground(new Background(new BackgroundFill(Color.DARKGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        scene2 = new Scene(spaceTwo, 1000, 800);
        setHumanHand();
        setAiHand();
        bottomScreen();
        upScreen();
        playerPickCard();

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
    public void playerPickCard ()
    {
        EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getSource() == humanHand[0] && access) {
                    access = false;
                    humanCardPlayed = playerHuman.playCard(0);
                    humanIntPlayed = 0;
//                    Deck.cardsLeftUpdate(cardsLeft, playerHuman.currentHand[humanIntPlayed]);
                    tr.setByY(-200);
                    tr.setByX(+120);
                    tr.setDuration(Duration.millis(600));
                    tr.setNode(humanHand[0]);
                    tr.play();
                    tr.setOnFinished(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            checkPlayer();
                        }
                    });
                } else if (event.getSource() == humanHand[1] && access) {
                    access = false;
                    humanCardPlayed = playerHuman.playCard(1);
                    humanIntPlayed = 1;
//                    Deck.cardsLeftUpdate(cardsLeft, playerHuman.currentHand[humanIntPlayed]);
                    tr.setByY(-200);
                    tr.setByX(0);
                    tr.setDuration(Duration.millis(600));
                    tr.setNode(humanHand[1]);
                    tr.play();
                    tr.setOnFinished(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            checkPlayer();
                        }
                    });
                } else if (event.getSource() == humanHand[2] && access) {
                    access = false;
                    humanCardPlayed = playerHuman.playCard(2);
                    humanIntPlayed = 2;
//                    Deck.cardsLeftUpdate(cardsLeft, playerHuman.currentHand[humanIntPlayed]);
                    tr.setByY(-200);
                    tr.setByX(-120);
                    tr.setDuration(Duration.millis(600));
                    tr.setNode(humanHand[2]);
                    tr.play();
                    tr.setOnFinished(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            checkPlayer();
                        }
                    });
                }
                event.consume();
            }
        };
        for (int i = 0; i < 3; i++) {

            if (humanHand[i].getFill() != Color.BLACK) {
                humanHand[i].setOnMouseClicked(mouseHandler);
            }
        }
        boolean isEmpty = true;
        for (int i = 0; i < playerHuman.getHand().length; i++) {
            if (playerHuman.getHand()[i] != null)
                isEmpty=  false;
            if (playerAI.getHand()[i] != null)
                isEmpty = false;
        }
        if (isEmpty)
        {
            //call end Game
            WinnerOne winner = new WinnerOne(this, main );
//            GridPane grid2 = new GridPane();
//            grid2 = winner.gridPane(primaryStage);
            winner.gridPane(primaryStage);
        }

    }

    private void bottomScreen()
    {
        BorderPane bottom = new BorderPane();
        HBox cardsHuman = new HBox(20, humanHand[0], humanHand[1], humanHand[2]);
        bottom.setCenter(cardsHuman);
        cardsHuman.setAlignment(Pos.CENTER);
        cardsHuman.setPadding(new Insets(10, 10, 10, 10));
        bottom.setLeft(humanInfo());
        pane.setBottom(bottom);
    }

    private void upScreen()
    {
        BorderPane up = new BorderPane();
        HBox cardsAI = new HBox(20, aiHand[0], aiHand[1], aiHand[2]);
        up.setCenter(cardsAI);
        cardsAI.setAlignment(Pos.CENTER);
        cardsAI.setPadding(new Insets(10));
        up.setLeft(aiInfo());
        pane.setTop(up);
    }

    private VBox humanInfo()
    {
        nameHuman.setFont(Font.font("Verdana", 20));
        nameHuman.setStyle("-fx-font-size: 20;");
        if(DEBUG) System.out.println("p1 Name" + nameHuman.toString());
        pointsHuman = new Label("Points: " + playerHuman.getPoints());
        pointsHuman.setFont(Font.font("Verdana", 20));
        pointsHuman.setStyle("-fx-font-size: 20;");
        VBox infoHuman = new VBox(10, nameHuman, pointsHuman);
        infoHuman.setPadding(new Insets(10, 10, 10, 10));
        infoHuman.setAlignment(Pos.CENTER);
        return infoHuman;
    }

    private VBox aiInfo(){
        nameAI.setFont(Font.font("Verdana", 20));
        nameAI.setStyle("-fx-font-size: 20;");
        if(DEBUG) System.out.println("Name P2" + nameAI.toString());
        pointsAI = new Label("Points: " + playerAI.getPoints());
        pointsAI.setFont(Font.font("Verdana", 20));
        pointsAI.setStyle("-fx-font-size: 20;");
        VBox infoAI = new VBox(10, nameAI, pointsAI);
        infoAI.setPadding(new Insets(10, 10, 10, 10));
        infoAI.setAlignment(Pos.CENTER);
        return infoAI;
    }

    private void aiSetUp(){
        // Here one can change the Easy AIs to use during the Game State
        if (difficulty == 1)
        {
            brainAI = new RandBrain();
        }
        else if (difficulty == 2)
        {
            brainAI = new BaseLineBrain(game.getHighSuit());
        }
        else if (difficulty == 3)
        {
            brainAI = new SimpleBrain(game.getHighSuit());
        }
        else if (difficulty == 4)
        {
            bigBrainPruningAI = new BigBrainPruning();
//            bigBrainAI = new BigBrain(playerAI.getId());
        }
        else if (difficulty == 5){
            aiANN = new ANNAgent();
            bigBrainPruningAI = new BigBrainPruning();
        }

    }

    private void aiTurn()
    {
        // state
        if(humanIntPlayed != -1)
        {
            Deck.cardsLeftUpdate(cardsLeft, playerHuman.getHand()[humanIntPlayed]);
        }
        Deck.cardsLeftUpdateHand(cardsLeft, playerAI.getHand());
        if (difficulty == 4) {
            aiBigState = new BigState(cardsLeft, playerAI.currentHand, game.getHighSuit());
//            aiIntPlayed = bigBrainAI.miniMax(aiBigState, humanCardPlayed, playerHuman.getHand());
            aiIntPlayed = bigBrainPruningAI.alphaBeta(aiBigState, humanCardPlayed, playerHuman.getHand());
            if (playerAI.getHand()[aiIntPlayed] == null) {
                brainAI = new SimpleBrain(game.getHighSuit());
                aiState = new State(true, playerAI.getHand());
                aiIntPlayed = brainAI.whatToPlay(aiState, humanCardPlayed);
            }
        }else if(difficulty == 5){
            aiIntPlayed = aiANN.playCard(cardsLeft, game.getHighSuit(), playerAI.getHand(), humanCardPlayed);
            if (aiIntPlayed <= -1){
                aiBigState = new BigState(cardsLeft, playerAI.currentHand, game.getHighSuit());
                aiIntPlayed = bigBrainPruningAI.alphaBeta(aiBigState, humanCardPlayed, playerHuman.getHand());
            }
        }else{
            aiState = new State(true, playerAI.currentHand);
            // AI Move in Integer that represents the Card that is bein played
            aiIntPlayed = brainAI.whatToPlay(aiState, humanCardPlayed);
        }
        // Reveiling the card that is being played
        if (DEBUG) System.out.println("AI card played Index " + aiIntPlayed);
        aiHand[aiIntPlayed].setFill(new ImagePattern(new Image("cards/" + playerAI.getHand()[aiIntPlayed] + ".png")));
        // AI Move in the card instance to have the backend working with it.
        aiCardPlayed = playerAI.playCard(aiIntPlayed);
        // Move Card
        tr.setByY(200);
        if (aiIntPlayed == 0){
            tr.setByX(+120);
        } else if (aiIntPlayed == 1) {
            tr.setByX(0);
        } else {
            tr.setByX(-120);
        }
        tr.setDuration(Duration.millis(600));
        tr.setNode(aiHand[aiIntPlayed]);
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
        if (winner == null && aiIntPlayed != -1 && humanIntPlayed != -1 ) {
            // && (humanCardPlayed != null && aiCardPlayed != null)
            winner = game.gameLogic.checkCards(humanCardPlayed, aiCardPlayed, game.getHighSuit(), playerHuman, playerAI);
        }
        else if (aiIntPlayed != -1 && humanIntPlayed != -1)
        {
//            if (winner == null) winner = playerHuman;

            if (winner.getId() == playerHuman.getId()) {
                winner = game.gameLogic.checkCards(humanCardPlayed, aiCardPlayed, game.getHighSuit(), playerHuman, playerAI);
            } else {
                winner = game.gameLogic.checkCards(aiCardPlayed, humanCardPlayed, game.getHighSuit(), playerAI, playerHuman);
            }
        }
        showWinner();
    }

    private void showWinner(){
        if (winner.getId() == playerHuman.getId())
        {
            if(DEBUG) System.out.println("Player One won this Round");
            tr.setByY(-175);
            tr.setByX(0);
            tr.setDuration(Duration.millis(600));
            tr.setNode(humanHand[humanIntPlayed]);
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
            if(DEBUG) System.out.println("Player two won this Round");
            tr.setByY(175);
            tr.setByX(0);
            tr.setDuration(Duration.millis(600));
            tr.setNode(aiHand[aiIntPlayed]);
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
        if(!game.shuffled.isEmpty())
            game.newCards();
        updatePoints();
        clearTable();

        setAiHand();
        setHumanHand();

        bottomScreen();
        upScreen();

        checkPlayer();

    }

    private void updatePoints() {
        if (DEBUG) System.out.println("AI points: " + playerAI.getPoints());
        if (DEBUG) System.out.println("Human points: " + playerHuman.getPoints());
        pointsAI.setText("Points: " + playerAI.getPoints());
        pointsHuman.setText("Points: " + playerHuman.getPoints());
    }

    private void clearTable()
    {
        for (int i = 0; i < humanHand.length; i++) {
            if (i == aiIntPlayed) {
                aiHand[i].setFill(Color.DARKGREEN);
                aiHand[i] = null;
                Rectangle newCard = new Rectangle(100, 150);
                newCard.setFill(new ImagePattern(backOfCards));
                aiHand[i] = newCard;
            }
            if (i == humanIntPlayed) {
                humanHand[i].setFill(Color.DARKGREEN);
                humanHand[i] = null;
                Rectangle newCard = new Rectangle(100, 150);
                newCard.setFill(Color.BLACK);
                humanHand[i] = newCard;
            }
        }

        humanIntPlayed = -1;
        aiIntPlayed = -1;
        aiCardPlayed = null;
        humanCardPlayed = null;

        if (DEBUG) System.out.println("Table Cleared");

    }
    // SwitchingPlayers

    private void setHumanHand ()
    {
        for (int i = 0; i<3; i++){
            if ((humanHand[i] != null)) {
                if ((playerHuman.getHand()[i] == null)) {
                    if (DEBUG) System.out.println("BLACK CARD -----------------------------------");
                    humanHand[i].setFill(Color.BLACK);
                }
                else {
                    humanHand[i].setFill(new ImagePattern(new Image("cards/" + playerHuman.getHand()[i] + ".png")));
                }
            }
            else
            {

                humanHand[i] = new Rectangle(100, 150);
                humanHand[i].setFill(Color.GRAY);
            }
        }
        setAiHand();
    }

    private void setAiHand ()
    {
        if(DEBUG) System.out.println("AI HAND SETUP ---");
        for (int i = 0; i<3; i++){
            if (aiHand[i] != null) {
                if ((playerAI.getHand()[i] == null)) {
                    if (DEBUG) System.out.println("BLACK CARD -----------------------------------");
                    if (DEBUG) System.out.println("WRong Turn in AI Hand stup");
                    aiHand[i].setFill(Color.BLACK);
                } else {
                    if (DEBUG) {
                        System.out.println("Back of Cards");
                        System.out.println("AI Hand "+playerAI.currentHand[i]);
                        System.out.println(cardsLeft.toString());
                        Deck.cardsLeftUpdate(cardsLeft, playerAI.currentHand[i]);
                    }
                    aiHand[i].setFill(new ImagePattern(backOfCards));
                }

            }
            else {
                aiHand[i] = new Rectangle(100, 150);
                aiHand[i].setFill(new ImagePattern(backOfCards));
                if (DEBUG)System.out.print("setAIHand with rectangle being Null");
            }
        }
    }

    private void checkPlayer() {

//         if Winner
        int count = 0;
        for (int i = 0; i < playerHuman.getHand().length; i++) {
            if (playerHuman.getHand()[i] == null) {
                count++;
            }
            if (playerAI.getHand()[i] == null) {
                count++;
            } else {
                break;
            }
        }
        if (count == 6) {
            winnerScreenActivated = true;
            if (DEBUG) System.out.println("Winner Screen Activation");
            WinnerOne winnerOne = new WinnerOne(this, main);
            Scene winner1 = winnerOne.gridPane(primaryStage);

            primaryStage.setScene(winner1);
        }

        if (!winnerScreenActivated) {
            if (DEBUG_LOOP) System.out.println("Human Card Center " + humanCardPlayed);
            if (DEBUG_LOOP) System.out.println("Human Card Center INT" + humanIntPlayed);
            if (DEBUG_LOOP) System.out.println("AI Card Center " + aiCardPlayed);
            if (DEBUG_LOOP) System.out.println("AI Card Center INT" + aiIntPlayed);
            if (winner != null && DEBUG_LOOP) System.out.println("Winner: " + winner.getName());

            // Winner is Known and we then choose based on the winner who goes first
            if (winner != null && aiIntPlayed == -1 && humanIntPlayed == -1) {
                if (winner.getId() == playerHuman.getId()) {
                    access = true;
                    playerPickCard();
                } else {
                    aiTurn();
                }
            }
            // in this case Winner MUST be null
            // start of the game -> AI and Human have not played
            else if (winner == null && humanIntPlayed == -1 && aiIntPlayed == -1) {
                access = true;
                playerPickCard();
            }
            // Human already played and AI hasnt
            else if (humanIntPlayed != -1 && aiIntPlayed == -1) {
                aiTurn();
            }
            // HUman Hasnt played yet but AI has
            else if (humanIntPlayed == -1 && aiIntPlayed != -1) {
                access = true;
                playerPickCard();
            }
            // If Both have played already then Check the cards and determine the winner
            else if (humanIntPlayed != -1 && aiIntPlayed != -1) {
                checkWinner();
            } else {
                System.out.println("ERROOOOOOOORRR--------------------------------------");
                access = true;
                playerPickCard();
            }
        }
    }

}