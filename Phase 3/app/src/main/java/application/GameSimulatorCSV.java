package application;

import AI.LargeBrain.BigBrain;
import AI.LargeBrain.BigBrainPruning;
import AI.LargeBrain.BigState;
import AI.LargeBrain.RandomHand;
import AI.lowBrain.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class GameSimulatorCSV {
    static int simulations = 1000;
    private final static boolean DEBUG = false;
    private final static boolean DEBUGNull = true;
    private final static boolean end61 = true;
    private final static boolean console = true;
    private final static boolean file = true;
    protected static boolean fixedDeck = false;
    protected static boolean recorded = false;
    protected static int diffBottom = 4;
    protected static int diffTop = 3;
    private static PrintWriter out;
    private Briscola game;

    private Player top = new Player("TOP", 2);
    private BigBrain topAIBig = new BigBrain();
    private BigBrainPruning topAIPruned = new BigBrainPruning();
    private BigState topBigState;
    private State topState;
    private Brain topAI;
    private int topInt;
    private Card topCard;
    private LinkedList<Card> cardsLeftTop;

    private Player bottom = new Player("BOTTOM", 1);
    private BigBrain bottomAIBig = new BigBrain();
    private BigBrainPruning bottomAIPruned = new BigBrainPruning();
    private Brain bottomAI;
    private BigState bottomBigState;
    private State bottomState;
    private int bottomInt;
    private Card bottomCard;
    private LinkedList<Card> cardsLeftBottom;
    private int roundCount;

    private Player winner;
    private LinkedList<Card> cardsLeft;
    private LinkedList<Card> cardsPlayed;

    private static LinkedList<String[]> winnerRecords;

    public GameSimulatorCSV(boolean fixed){
        fixedDeck = fixed;
        createWinnerFile();
        for (int i = 0; i < simulations; i++) {
            start();
        }
        recordAllWins();
//        out.close();

    }
    private void start() {
        topInt = -1;
        bottomInt = -1;
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


    public static void main(String[] args, boolean fixed) {

        GameSimulatorCSV gameSimCSV = new GameSimulatorCSV(fixed);
    }

    private void aiSetUp(){
        System.out.println(game.getHighSuit() + " aiSetup top " + diffTop + " bottom "+ diffBottom);
        switch (diffTop) {
            case 1 -> topAI = new RandBrain();
            case 2 -> topAI = new BaseLineBrain(game.getHighSuit());
            case 3 -> topAI = new SimpleBrain(game.getHighSuit());
            case 4 -> topAIPruned = new BigBrainPruning();
            case 5 -> topAIBig = new BigBrain(top.getId());
        }
        switch (diffBottom) {
            case 1 -> bottomAI = new RandBrain();
            case 2 -> bottomAI = new BaseLineBrain(game.getHighSuit());
            case 3 -> bottomAI = new SimpleBrain(game.getHighSuit());
            case 4 -> bottomAIPruned = new BigBrainPruning();
            case 5 -> bottomAIBig = new BigBrain(bottom.getId());
        }
    }

    private static void createWinnerFile(){
        /*winnerRecords = new LinkedList<>();
        String outputFileName = "experimenting.csv";
        out = null;
        try {
            out = new PrintWriter(outputFileName);
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }*/
    }

    private  void recordWinner() {
        roundCount++;
        checkWinner(top, bottom);
        if (console) {
            System.out.println(winner.getName() + " in console");
            System.out.println( roundCount + " rounds after");
        }

        if (file){
            String[] thisWin = new String[]{bottom.getPointsToString(), top.getPointsToString(), Integer.toString(roundCount)};
            winnerRecords.add(thisWin);
//            out.println(winner.getName() + " in file");
//            out.println("bottom: " + bottom.getPoints() + ", Top: " + top.getPoints());
//            out.println( roundCount + " rounds after");
//            out.println();
        }
    }

    protected String convertToCSV(String[] data) {
        return Stream.of(data)
                .map(this::escapeSpecialCharacters)
                .collect(Collectors.joining(","));
    }
    protected String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }

    protected void recordAllWins() {
        File csvOutputFile = new File("experiments.csv");
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            winnerRecords.stream()
                    .map(this::convertToCSV)
                    .forEach(pw::println);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }



    /*
    This is the method I need to finish
    We need to play a game here
    * */
    private Player oneGame(Player one, Player two) {
        Player next = (winner != null) ? winner : top; // Bottom Player starts
        roundCount = 0;
        while (!isEmpty(bottom.getHand()) && !isEmpty(top.getHand()))
        {

            if((bottom.getPoints() > 60 || top.getPoints() > 60) && end61)
            {
                recorded = true;
                recordWinner();
                break;
            }

            aiTurn(next);
            next = checkNext();
            if (topInt != -1 && bottomInt != -1)
                newRound();
        }
        if (DEBUG)System.out.print(next.getId() + ", ");
        if (DEBUG) System.out.println("bottom: " + bottom.getPoints() + ", Top: " + top.getPoints());
        if (!recorded)
            recordWinner();
        if (DEBUG) {System.out.println("We played a game");}
        return winner;
    }

    protected boolean isEmpty (Card[] hand){
        for (Card card : hand) {
            if (card != null) {
                return false;
            }
        }
        return true;
    }

    protected int aiTurn (Player next){
        if (DEBUG) System.out.println(" ***** AI turn");
        if (next.getId() == bottom.getId()){
            if (diffBottom < 4) {
                bottomState = new State(true, bottom.getHand());
                bottomInt = bottomAI.whatToPlay(bottomState, topCard);
                if (DEBUG) System.out.println("Bottom plays: " + bottomInt);
            }
            else {
//                bottomBigState = new BigState(cardsLeftBottom, bottom.getHand(), RandomHand.randomHand(cardsLeftBottom), game.getHighSuit());
                bottomBigState = new BigState(cardsLeftBottom, bottom.getHand(), game.getHighSuit());
                if (diffBottom == 4) {
                    bottomInt = bottomAIPruned.alphaBeta(bottomBigState, topCard, top.getHand());
                    if (DEBUG) System.out.println("Bottom plays: " + bottomInt);
                } else {
                    bottomInt = bottomAIBig.miniMax(bottomBigState, topCard, top.getHand());
                    if(DEBUG) System.out.println("Bottom plays: " + bottomInt);
                }
            }
            if (DEBUGNull)System.out.println("BottomC "+ bottomInt + " Hand " + bottom.toString());
            bottomCard = bottom.playCard(bottomInt);
            cardsLeftUpdater(bottomCard);
            return bottom.getId();
        }
        else{
            if (diffTop < 4){
                topState = new State(true, top.getHand());
                topInt = topAI.whatToPlay(topState, bottomCard);
            }
            else {
//                topBigState = new BigState(cardsLeftTop, top.getHand(), RandomHand.randomHand(cardsLeftTop), game.getHighSuit());
                topBigState = new BigState(cardsLeftTop, top.getHand(), game.getHighSuit());
                if (diffTop == 4){
                    topInt = topAIPruned.alphaBeta(topBigState, bottomCard, bottom.getHand());
                    if(DEBUG) System.out.println("Top plays: " + topInt);
                }
                else{
                    topInt = topAIBig.miniMax(topBigState, bottomCard,bottom.getHand());
                    if(DEBUG) System.out.println("Top plays: " + topInt);
                }
            }
            if(DEBUGNull) System.out.println("TopCard " + topInt +" Hand " + top.toString());
            topCard = top.playCard(topInt);
            cardsLeftUpdater(topCard);
            return top.getId();
        }
    }

    private Player checkNext(){
        if (DEBUG) {System.out.println("Checking next ");}
        if (winner != null && topInt == -1 && bottomInt == -1){
            if (winner.getId() == bottom.getId()){return bottom;}
            else {return top;}
        }
        if ((winner == null && bottomInt == -1 && topInt == -1)){
            return bottom;
        }
        else if ((bottomInt != -1 && topInt == -1)){
            return top;
        }
        else if (bottomInt == -1 && topInt != -1){
            return bottom;
        }
        else if(bottomInt != -1 && topInt != -1){
            if (DEBUG) System.out.println("Go to check round");
            return checkRound();
        }
        else {
            System.out.println("ERROR IN Check Next Allowing player 1");
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
        }
        else {
            winner = top;
        }
    }

    private void cardsLeftUpdater(Card played){
        Deck.cardsLeftUpdate(cardsLeft, played);
        Deck.cardsLeftUpdate(cardsLeftBottom, played);
        Deck.cardsLeftUpdate(cardsLeftTop, played);
        cardsPlayed.add(played);
    }
}

