package application;


import AI.LargeBrain.*;
import AI.aNN.ANNAgent;
import AI.lowBrain.*;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

import java.io.*;
import java.util.LinkedList;
import java.util.Random;

public class GameSimulator {
    //Number of games that will be simulated
    static int simulations = 100000;

    //Set AI difficulties
    protected static int diffTop = 1;
    protected static int diffBottom = 7;

    static int bottomBackUp = 0;
    static int topBackUp = 0;
    static int gameCounter = 0;
    static int topWonCounter = 0;
    static int bottomWonCounter = 0;
    static int drawCounter = 0;
    static int bottomPoints, topPoints = 0;

    //Boolean switches
    private final static boolean DEBUG = false;
    private final static boolean console = false;
    private final static boolean file = false;
    private final static boolean stopAt61 = false;
    protected static boolean fixedDeck = false;
    protected static boolean recorded = false;


    private static PrintWriter out;
    private Briscola game;

    private Player top = new Player("TOP", 2);
    private CheatingBrain topAICheatingBig;
    private BigBrain topAIBig;
    private BigBrainPruning topAIPruned;
    private ANNAgent topANN;
    private BigState topBigState;
    private State topState;
    private Brain topAI;
    private int topInt;
    private Card topCard;
    private LinkedList<Card> cardsLeftTop;

    private Player bottom = new Player("BOTTOM", 1);
    private BigBrain bottomAIBig;
    private CheatingBrain bottomAICheating;
    private BigBrainPruning bottomAIPruned;
    private Brain bottomAI;
    private ANNAgent bottomANN;
    private BigState bottomBigState;
    private State bottomState;
    private int bottomInt;
    private Card bottomCard;
    private LinkedList<Card> cardsLeftBottom;
    private int roundCount;

    private Player winner;
    private LinkedList<Card> cardsLeft;
    private LinkedList<Card> cardsPlayed;
    private boolean finalRound = false;

    /**
     * Constructor
     */
    public GameSimulator(){
        topInt = -1;
        bottomInt = -1;
        recorded = Boolean.parseBoolean(null);
        winner = null;
        top.resetPoints();
        bottom.resetPoints();
        game = new Briscola(top, bottom);
        game.start();

        // AI SetUp
        aiSetUp();

        cardsPlayed = new LinkedList<Card>();
        cardsLeft = (LinkedList<Card>) (game.deck.sorted.clone());
        Deck.cardsLeftUpdate(cardsLeft, game.getHighSuit());
        cardsLeftBottom = (LinkedList<Card>) cardsLeft.clone();
        Deck.cardsLeftUpdateHand(cardsLeftBottom, bottom.getHand());
        cardsLeftTop = (LinkedList<Card>) cardsLeft.clone();
        Deck.cardsLeftUpdateHand(cardsLeftTop, top.getHand());

        if (DEBUG){System.out.println(game.getHighSuit().toString());}
        oneGame(top, bottom);
    }

    /**
     * Main method called from App
     * @param args
     * @param fixed in case the game will be played with a fixed deck
     * @param closeWriter
     */
    public static void main(String []args, boolean fixed, boolean closeWriter) {

        fixedDeck = fixed;
        if(closeWriter)
            createWinnerFile();
        for (int i = 0; i < simulations; i++) {
            if (DEBUG) System.out.println((i+1) + " simulations");
            GameSimulator simulation = new GameSimulator();
        }
        out.println("TOP: " + diffTop + ", BOTTOM: " + diffBottom + ", Cheating: " + RandomHand.cheatingFixed);
        out.println("Games played: " + gameCounter);
        out.println("TOP won: "+ topWonCounter + "   -> " + ((topWonCounter *100)/(gameCounter * 1.0)));
        out.println("BOTTOM won: " + bottomWonCounter + "   -> " + ((bottomWonCounter *100)/(gameCounter * 1.0)));
        out.println("Draws: " + drawCounter + "   -> " + ((drawCounter *100)/(gameCounter * 1.0)));
        out.println("Points Top: "+ topPoints + " AVG TOP:" + ((topPoints)/(gameCounter * 1.0)));
        out.println("Points Bottom: "+ bottomPoints + " AVG BOTTOM:" + ((bottomPoints)/(gameCounter * 1.0)));
        out.println("");
        System.out.println("TOP: " + diffTop + ", BOTTOM: " + diffBottom + ", Cheating: " + RandomHand.cheatingFixed);
        System.out.println("Games played: " + gameCounter);
        System.out.println("TOP won: " + topWonCounter + "      -> " + ((topWonCounter *100)/(gameCounter * 1.0)));
        System.out.println("BOTTOM won: " + bottomWonCounter + "   -> " + ((bottomWonCounter *100)/(gameCounter * 1.0)));
        System.out.println("Draws: " + drawCounter + "          -> " + ((drawCounter *100)/(gameCounter * 1.0)));
        System.out.println("Points Top: "+ topPoints + " AVG TOP:" + ((topPoints)/(gameCounter * 1.0)));
        System.out.println("Points Bottom: "+ bottomPoints + " AVG BOTTOM:" + ((bottomPoints)/(gameCounter * 1.0)));
        if (DEBUG && topBackUp > 0) System.out.println("Top backUp used: " + topBackUp + " in " + gameCounter + " Games") ;
        if (DEBUG && bottomBackUp > 0) System.out.println("Bottom backUp used: " + bottomBackUp);
        System.out.println("");
        System.out.println("");

        gameCounter = 0;
        bottomWonCounter = 0;
        topWonCounter = 0;
        drawCounter = 0;
        bottomPoints = 0;
        topPoints = 0;
        if (closeWriter){
            out.close();
        } else {
            if(DEBUG) System.out.println("Round end and on to next...");
            out.println("\n\n");
        }

    }

    /**
     * Override of main method
     * @param args
     * @param fixed
     */
    public static void main(String[] args, boolean fixed) {
        GameSimulator.main(args, fixed, true);
    }

    /**
     * Method that sets up the brains to the wished difficulty
     */
    private void aiSetUp(){
        if (DEBUG) {System.out.println(game.getHighSuit() + " aiSetup top " + diffTop + " bottom "+ diffBottom);}
        switch (diffTop)
        {
            case 1 -> topAI = new RandBrain();
            case 2 -> topAI = new BaseLineBrain(game.getHighSuit());
            case 3 -> topAI = new SimpleBrain(game.getHighSuit());
            case 4 -> topAIPruned = new BigBrainPruning();
            case 5 -> topAIBig = new BigBrain(top.getId());
            case 6 -> topAICheatingBig = new CheatingBrain(game.shuffledClone, top.getId());
            case 7 -> {
                topANN = new ANNAgent();
                topAIPruned = new BigBrainPruning();
            }
        }
        switch (diffBottom) {
            case 1 -> bottomAI = new RandBrain();
            case 2 -> bottomAI = new BaseLineBrain(game.getHighSuit());
            case 3 -> bottomAI = new SimpleBrain(game.getHighSuit());
            case 4 -> bottomAIPruned = new BigBrainPruning();
            case 5 -> bottomAIBig = new BigBrain(bottom.getId());
            case 6 -> bottomAICheating = new CheatingBrain(game.shuffledClone, bottom.getId());
            case 7 -> {
                bottomANN = new ANNAgent();
                bottomAIPruned = new BigBrainPruning();
            }
        }
    }

    /**
     * Creates the .txt file were the output will be given to
     * @return
     */
    public static PrintWriter createWinnerFile(){
        String outputFileName = "experiment20k-all.txt";
        out = null;
        try {
            out = new PrintWriter(outputFileName);
            return out;
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * Once one of the players won a game, output is created for terminal and/or txt file
     */
    private void recordWinner() {
        if(finalRound){
            checkRound();
        }
        checkWinner(top, bottom);
        if (console) {
            System.out.println(winner.getName() + " in console");
            System.out.println("bottom: " + bottom.getPoints() + ", Top: " + top.getPoints());
            System.out.println( roundCount + " rounds after");
            System.out.println();
        }

        if (file){
            out.println(winner.getName() + " in file");
            out.println("bottom: " + bottom.getPoints() + ", Top: " + top.getPoints());
            out.println( roundCount + " rounds after");
            out.println();
        }
        bottomPoints += bottom.getPoints();
        topPoints += top.getPoints();
        gameCounter ++;
    }

    /**
     * Method to play one Briscola game
     * @param one, player one of a Briscola game
     * @param two, player two of a Briscola game
     * @return winner player
     */
    private Player oneGame(Player one, Player two) {
        Player next = (winner != null) ? winner : top; // Top Player starts
        roundCount = 1;
        while (!isEmpty(bottom.getHand()) && !isEmpty(top.getHand()))
        {
            if(stopAt61 &&( bottom.getPoints() > 60 || top.getPoints() > 60))
            {
                recorded = true;
                recordWinner();
                break;
            }
            
            aiTurn(next);
            next = checkNext();
            if (topInt != -1 && bottomInt != -1 ) { // && (!isEmpty(bottom.getHand()) && !isEmpty(top.getHand()))
                newRound();
            }
        }
        if (DEBUG && (bottomCard != null || topCard != null)){
            System.out.println(bottom.toString() + " Card on table Bottom: " + bottomCard);
            System.out.println(top.toString() + " Card on table TOP " + topCard);
        }

        if(!stopAt61 && roundCount <= 20)
        {
            aiTurn(next);
            if(DEBUG) System.out.println("The new round in stopAt61");
//            newRound();
            finalRound = true;
            recorded = true;
            recordWinner();
        }
        if (DEBUG)System.out.print(next.getId() + ", ");
        if (DEBUG) System.out.println("bottom: " + bottom.getPoints() + ", Top: " + top.getPoints());
        if (!recorded)
            recordWinner();
        if (DEBUG) {System.out.println("We played a game");}
        return winner;
    }

    /**
     * Checking if a hand is empty
     * @param hand of cards from a Player
     * @return true if all [] are null
     */
    protected boolean isEmpty (Card[] hand){
        for (Card card : hand) {
            if (card != null) {
                return false;
            }
        }
        return true;
    }

    /**
     *Method to 'think' of a card the next player will play, and then return the player who has to play next
     * @param next, the player who has to play a card
     * @return Player who now has to play a card
     */
    protected int aiTurn (Player next){
        if (DEBUG) System.out.println(" ***** AI turn");
        if (next.getId() == bottom.getId()){
            if (diffBottom < 4) {
                bottomState = new State(true, bottom.getHand());
                bottomInt = bottomAI.whatToPlay(bottomState, topCard);
            }
            else if (4 <= diffBottom && diffBottom <= 6) {
                if (DEBUG && diffBottom !=6) System.out.print("S");
//                bottomBigState = new BigState(cardsLeftBottom, bottom.getHand(), RandomHand.randomHand(cardsLeftBottom), game.getHighSuit());
                bottomBigState = new BigState(cardsLeftBottom, bottom.getHand(), game.getHighSuit(), roundCount);
                if (diffBottom == 4) {
                    bottomInt = bottomAIPruned.alphaBeta(bottomBigState, topCard, top.getHand());
                } else if(diffBottom == 5){
                    if (DEBUG) System.out.println("TRY------------------------------------------------");
                    bottomInt = bottomAIBig.miniMax(bottomBigState, topCard, top.getHand());
                } else {
                    bottomInt = bottomAICheating.miniMax(bottomBigState, topCard, top.getHand());
                }
            }
            else if (diffBottom == 7) {
                bottomInt = bottomANN.playCard(cardsLeftBottom, game.highSuit, bottom.currentHand, topCard);
                if (DEBUG) System.out.println("7 Bottom chose: " + bottomInt + " From: " + bottom.toString());
                if(bottomInt <= -1) {
                    bottomBackUp++;
                    if (DEBUG){System.out.println("Back up used");}
                    bottomBigState = new BigState(cardsLeftBottom, bottom.getHand(), game.getHighSuit(), roundCount);
                    bottomInt = bottomAIPruned.alphaBeta(bottomBigState, topCard, top.getHand());
                }
            }
            else{
                bottomInt = 0;
                System.out.println("ERROR ON BOTTOM HAND CALLING");
            }
            bottomCard = bottom.playCard(bottomInt);
            cardsLeftUpdater(bottomCard);
            return bottom.getId();
        }
        else{
            if (diffTop < 4){
                topState = new State(true, top.getHand());
                topInt = topAI.whatToPlay(topState, bottomCard);
            }
            else if (4 <= diffTop && diffTop <= 6){
//                topBigState = new BigState(cardsLeftTop, top.getHand(), RandomHand.randomHand(cardsLeftTop), game.getHighSuit());
                topBigState = new BigState(cardsLeftTop, top.getHand(), game.getHighSuit(), roundCount);
                if (diffTop == 4){
                    topInt = topAIPruned.alphaBeta(topBigState, bottomCard, bottom.getHand());
                } else if (diffTop == 5){
                    topInt = topAIBig.miniMax(topBigState, bottomCard, bottom.getHand());
                } else{
                    topInt = topAICheatingBig.miniMax(topBigState, bottomCard, bottom.getHand());
                }
            }
            else if (diffTop == 7) {
                topInt = topANN.playCard(cardsLeftTop, game.highSuit, top.currentHand, topCard);
                if (DEBUG) System.out.println("7 TOP chose: " + topInt + " From: " + top.toString());
                if (topInt <= -1){
                    topBackUp++;
                    if (DEBUG){System.out.println("Back up used");}
                    topBigState = new BigState(cardsLeftTop, top.getHand(), game.getHighSuit(), roundCount);
                    topInt = topAIPruned.alphaBeta(topBigState, bottomCard, bottom.getHand());
                }
            }else{
                topInt = 0;
                System.out.println("ERROR ON TOP------- HAND CALLING");
            }
            topCard = top.playCard(topInt);
            cardsLeftUpdater(topCard);
            return top.getId();
        }
    }

    private Player checkNext(){
        if (DEBUG) {System.out.println("Checking next ");}
        if (winner != null && topInt == -1 && bottomInt == -1){
            if (winner.getId() == bottom.getId()){
                return bottom;}
            else {
                return top;}
        }
        if ((winner == null && bottomInt == -1 && topInt == -1)){
            return bottom;
        }
        else if ((bottomInt != -1 && topInt == -1)){
            if (DEBUG) {System.out.println("Are we here?");}
            return top;
        }
        else if (bottomInt == -1 && topInt != -1){
            if (DEBUG) {System.out.println("Are we here? 2");}
            return bottom;
        }
        else if(bottomInt != -1 && topInt != -1){
            if (DEBUG) System.out.println("Go to check round");
            return checkRound();
        }
        else {
            if(DEBUG) System.out.println("ERROR IN Check Next Allowing player 1");
            return bottom;
        }
    }

    private Player checkRound(){
        if (DEBUG) {System.out.println("Checking round");}
        if (winner == null || winner.getId() == bottom.getId()) {
            winner = game.gameLogic.checkCards(bottomCard, topCard, game.getHighSuit(), bottom, top);
        } else {
            winner = game.gameLogic.checkCards(topCard, bottomCard, game.getHighSuit(), top, bottom);
        }
        return winner;
    }

    private void newRound(){
        if (DEBUG) {System.out.println("----- new round");}
        if(DEBUG) {System.out.println(roundCount);}
        if (!game.shuffled.isEmpty()){
            game.newCards();
        }
        roundCount++;
        bottomInt = -1;
        bottomCard = null;
        topInt = -1;
        topCard = null;
    }

    private void checkWinner(Player top, Player bottom){
        if (bottom.getPoints() > 60){
            winner = bottom;
            bottomWonCounter ++;
        }
        else if (top.getPoints() > 60) {
            winner = top;
            topWonCounter ++;
        }

        else {
            winner = new Player ("DRAW", 2);
            drawCounter ++;
        }
    }

    private void cardsLeftUpdater(Card played){
        Deck.cardsLeftUpdate(cardsLeft, played);
        Deck.cardsLeftUpdate(cardsLeftBottom, played);
        Deck.cardsLeftUpdate(cardsLeftTop, played);
        cardsPlayed.add(played);
    }
}
