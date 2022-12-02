package application;

import AI.LargeBrain.BigBrain;
import AI.LargeBrain.BigBrainPruning;
import AI.LargeBrain.BigState;
import AI.aNN.ANNData;
import AI.aNN.SaveGames;
import AI.lowBrain.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Arrays;

public class GameSimTrain {
    static int simulations = 1;
    static int gameCounter = 0;
    static int topWonCounter = 0;
    static int bottomWonCounter = 0;
    static int drawCounter = 0;
    private final static boolean DEBUG = false;
    private final static boolean console = true;
    private final static boolean file = false;
    private final static boolean stopAt61 = false;
    protected static boolean fixedDeck = false;
    protected static boolean recorded = false;
    protected static int diffBottom = 4;
    protected static int diffTop = 1;
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
    private boolean finalRound = false;

    SaveGames save;

    public SaveGames getSave(){
        return save;
    }

    public GameSimTrain(){
        save = new SaveGames();
        start();
    }

    public GameSimTrain(int numberOfGames){
        save = new SaveGames(numberOfGames);
        for(int i = 0; i <numberOfGames; i++ ){
            start();
        }

    }

    private void start(){
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
        // System.out.println("DONE----");

    }


    private void aiSetUp(){
        if (DEBUG) {System.out.println(game.getHighSuit() + " aiSetup top " + diffTop + " bottom "+ diffBottom);}
        switch (diffTop)
        {
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

//    private static void createWinnerFile(){
//        String outputFileName = "experimenting.txt";
//        out = null;
//        try {
//            out = new PrintWriter(outputFileName);
//        } catch (FileNotFoundException exception) {
//            exception.printStackTrace();
//        }
//    }

    /*private  void recordWinner() {
        if(finalRound){
            checkRound();
        }
        checkWinner(top, bottom);
        if (console) {
            System.out.println(winner.getName() + " in console");
            System.out.println("bottom: " + bottom.getPoints() + ", Top: " + top.getPoints());
            System.out.println( roundCount + " rounds after");
            System.out.println();
//            System.out.println(Arrays.deepToString(save.getInput()));
//            System.out.println(Arrays.deepToString(save.getOutput()));

        }

        if (file){
            out.println(winner.getName() + " in file");
            out.println("bottom: " + bottom.getPoints() + ", Top: " + top.getPoints());
            out.println( roundCount + " rounds after");
            out.println();
        }
        gameCounter ++;
    }*/

    /*
    This is the method I need to finish
    We need to play a game here
    * */
    private Player oneGame(Player one, Player two) {
        Player next = (winner != null) ? winner : top; // Bottom Player starts
        roundCount = 1;
        while (!isEmpty(bottom.getHand()) && !isEmpty(top.getHand()))
        {
            if(stopAt61 &&( bottom.getPoints() > 60 || top.getPoints() > 60))
            {
                recorded = true;
                //recordWinner();
                break;
            }

            aiTurn(next);
            next = checkNext();
            if (topInt != -1 && bottomInt != -1 ) { // && (!isEmpty(bottom.getHand()) && !isEmpty(top.getHand()))
//                System.out.println("bottomInt if ");
                newRound();
            }
        }
        if ((bottomCard != null || topCard != null) && DEBUG){
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
            //recordWinner();
        }
        if (DEBUG)System.out.print(next.getId() + ", ");
        if (DEBUG) System.out.println("bottom: " + bottom.getPoints() + ", Top: " + top.getPoints());
        if (!recorded)
            //recordWinner();
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
            }
            else {
//                bottomBigState = new BigState(cardsLeftBottom, bottom.getHand(), RandomHand.randomHand(cardsLeftBottom), game.getHighSuit());
                bottomBigState = new BigState(cardsLeftBottom, bottom.getHand(), game.getHighSuit(), roundCount);
                if (diffBottom == 4) {
                    bottomInt = bottomAIPruned.alphaBeta(bottomBigState, topCard, top.getHand());
                } else {
                    bottomInt = bottomAIBig.miniMax(bottomBigState, topCard, top.getHand());
                }
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
            else {
//                topBigState = new BigState(cardsLeftTop, top.getHand(), RandomHand.randomHand(cardsLeftTop), game.getHighSuit());
                topBigState = new BigState(cardsLeftTop, top.getHand(), game.getHighSuit());
                if (diffTop == 4){
                    topInt = topAIPruned.alphaBeta(topBigState, bottomCard,bottom.getHand());
                }
                else{
                    topInt = topAIBig.miniMax(topBigState, bottomCard,bottom.getHand());
                }

            }
//            ANNData aNN = new ANNData(cardsLeftBottom, game.getHighSuit(), bottom.getHand(), topCard, bottom.getHand()[bottomInt]);
            ANNData aNN = new ANNData(cardsLeftTop, game.getHighSuit(), top.getHand(), bottomCard, top.getHand()[topInt]);
            save.save(aNN);

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
