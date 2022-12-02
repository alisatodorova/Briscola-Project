package AI.ReinforcementLearning;

import application.Briscola;
import application.Card;
import application.Deck;
import application.Player;
import org.checkerframework.checker.units.qual.C;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.sql.SQLOutput;
import java.util.*;

public class TrainQ {

    //
    // 1.Initialize the Q-values table, Q(s, a).
    // 2.Observe the current state, s.
    // 3.Choose an action, a, for that state based on one of the action selection policies explained here on the previous page (-soft, -greedy or softmax).
    // 4.Take the action, and observe the reward, r, as well as the new state, s'.
    // 5.Update the Q-value for the state using the observed reward and the maximum reward possible for the next state. The updating is done according to the forumla and parameters described above.
    // 6.Set the state to the new state, and repeat the process until a terminal state is reached.
    //



    // 1.Initialize the Q-values table, Q(s, a).
    protected QTable qTable = new QTable();
    protected int trainRuns = 60000;
    protected boolean aiTurn;
    protected boolean nextAITurn;
    protected Card highSuit;
    protected Card[] aiHand;
    protected Card[] newAiHand;
    protected Card opponentCard;
    protected Card newOpponentCard;
    protected LinkedList<Card> cardsLeft;
    protected Stack<Card> cardDeck;
    protected Briscola game;
    protected double epsilon = 1;
    protected QState currentState;
    protected Player human, agent;
    protected final double DECREMENT_VALUE = 0.004;
    protected int immediateReward;
    protected Player winner;
    int count = 0;
    int rownumber = 1;
    int whilecount = 40;
    private int gameCounter = 0;
    int duplicateCounter = 0;
    //public static QState [] states = new QState[1001];
    static String qTableFile = "qTableInfo.txt";
    static String statesFile = "qStatesInfo.txt";


    /*
    public TrainQ()
    {

    }

     */

    public TrainQ() {

        trainingQOuterLoop();

    }


    protected void trainingQOuterLoop(){
        QTable file = FileStoreQL.readInfo();
        if (file != null){
            qTable = file;
        }
        while(gameCounter <= trainRuns){

            human = new Player("Alex", 1);
            agent = new Player("Elon", 2);
            game = new Briscola(human, agent);

           //System.out.println(game.shuffled);

            cardsLeft = (LinkedList<Card>) (game.deck.sorted.clone());
            cardDeck = (Stack<Card>) game.deck.shuffled.clone();


            //cardDeck = game.deck.shuffled;


            game.start();
            highSuit = game.getHighSuit();

            if (gameCounter % 1000 == 0)
                System.out.println("GAME " + gameCounter);
           // System.out.println(highSuit + "is highsuit");

            aiHand = agent.getHand();
            // Game is not restarted

            trainingQ();
            gameCounter++;

        }

        //System.out.println(qTable.states);
        //System.out.println(qTable.states1);

/*
        rownumber = 1;

        for (int i = 0; i < qTable.getQTable().length; i++)//Cycles through rows
        {
            for (int j = 0; j < qTable.getQTable()[0].length; j++)//Cycles through columns
            {
                System.out.printf(qTable.getQTable()[i][j] + " "); //change the %5d to however much space you want
            }

            System.out.println(); //Makes a new row
            System.out.println(rownumber + " : ");
            rownumber++;

        }

 */

        FileStoreQL.writeData(qTable);
        System.out.println("Duplicate : " + duplicateCounter);
    }


    protected void trainingQ() {

        // Generate a random card from a generated deck
        //while(!game.shuffled.isEmpty()){
        opponentCard = cardDeck.pop();
        // System.out.println("Opponent card is " + opponentCard);

        Deck.cardsLeftUpdateHand(cardsLeft, aiHand);
        Deck.cardsLeftUpdate(cardsLeft, highSuit);
//        currentState = new QState(false, highSuit, aiHand, opponentCard);

        /*Everything in here goes in a new method...*/

        // 2.Observe the current state, s.
        currentState = new QState(aiTurn, highSuit, aiHand, opponentCard);
        qTable.states[currentState.getStateID()] = currentState;
       //qTable.states1.add(currentState);

        while (!cardDeck.isEmpty()) {
            //  opponentCard = cardDeck.pop();


            // 3.Choose an action, for that state based on one of the action selection policies explained here on the previous page (-soft, -greedy or softmax).

            int nextAction = epsilonGreedy(currentState);


            // 4.Take the action, and observe the reward, r, as well as the new state,
            //System.out.println(opponentCard + " by Opponent");
            //System.out.println(agent.getHand()[nextAction] + " By Agent");
            count++;
            if (winner == null || winner == human)
                winner = game.gameLogic.checkCards(opponentCard, agent.getHand()[nextAction], highSuit, human, agent);
            else
                winner = game.gameLogic.checkCards(agent.getHand()[nextAction], opponentCard, highSuit, agent, human);

            if (winner == agent) {
                nextAITurn = true;
                immediateReward = calculatePoints(opponentCard, agent.getHand()[nextAction]);
            } else if (winner == human) {
                nextAITurn = false;
                immediateReward = (-1) * calculatePoints(opponentCard, agent.getHand()[nextAction]);
                //  System.out.println("Immediate reward is negative: " + immediateReward);
            } else {
                System.out.println("------ERROR with players - OR first Round and we dont have a winner yet... --------- ");
            }


            // 5.Update the Q-value for the state using the observed reward and the maximum reward possible for the next state. The updating is done according to the forumla and parameters described above.

            newAiHand = agent.getHand().clone();
            if (cardDeck.size() >= 2) {
                //System.out.println("CARD DECK SIZE >= 2");
                newOpponentCard = cardDeck.pop();
                newAiHand[nextAction] = cardDeck.pop();
            } else if (cardDeck.size() == 1) {
                // System.out.println("Card deck size is equal to 1");
                newOpponentCard = cardDeck.pop();
                cardDeck.push(highSuit);
                newAiHand[nextAction] = cardDeck.pop();
            }


            QState nextState = new QState(nextAITurn, highSuit, newAiHand, newOpponentCard);


            // Check for duplicates
           // int foundAt = foundAtRowFinal(qTable.states,currentState); // row of the duplicate 77

            int foundAt = foundAtRowFinal1(qTable.states1,currentState); // row of the duplicate 77

            //System.out.println("Found at " + foundAt);

            /*
            int foundAt2 = foundAtRowFinal(qTable.states,nextState);

            if(foundAt2 != -1) {
                nextState.stateID = foundAt2;
                //System.out.println("Next state id : " + nextState.stateID);
            }

             */

            // 55


            if(currentState.stateID == 0)
            {
                foundAt = -1;
            }

            if (foundAt == -1) {
                double q_value = qTable.newQ(currentState, nextAction, immediateReward, nextState);
                qTable.states[currentState.getStateID()] = currentState;
                qTable.states1.add(currentState);

                //System.out.println("Table size : " + qTable.states1.size());

            }
            else {
                // integer at which to update, currentState
              //  System.out.println("THERE IS A REPEATED STATE");
                qTable.update(foundAt, currentState, nextAction, immediateReward, nextState);

            }

            agent.playCard(nextAction);


            // 6.Set the state to the new state, and repeat the process until a terminal state is reached.
            // agent.setHand(newAiHand);
            agent.currentHand = newAiHand;
            opponentCard = newOpponentCard;
            aiTurn = nextAITurn;
            currentState = nextState;

            /*END WHILE*/
        }


       // System.out.println("I'm out");


    }


    /**
     * Note that at the beginning of the algorithm, every step the agent takes will be random which
     * is useful to help the agent learn about the environment it’s in.
     * As the agent takes more and more steps, the value of epsilon decreases and the agent starts to
     * try existing known good actions more and more
     * @param s QState that is being passed to be sure it is the correct QState
     * @return TBC (Reward or QValue)
     */
    public int epsilonGreedy(QState s){

        int actionInt;
        if(epsilon > Math.random())
        {
            //pick random action
            epsilon-= DECREMENT_VALUE; // The value needs to be determined on how efficiently the algorithm trains

            int rand = (int) (Math.random()*(3-0)+0);
            return rand;
            //return actionInt;


            // (Epsilon Greedy method , Q-Learning) As the agent is exploring, the value of epsilon decreases thus the agent starts exploiting more.
            // What factors influence the decreasing rate of epsilon?
        }
        else {
            double max = qTable.maxQ(s);
            int row = s.getStateID();

            int pickAction = pickAction(max, row);
            return pickAction;
        }
    }


    /**
     * Picks an action out of the 3 possible cards
     * @param max
     * @param row
     * @return the action with the highest Q-Value
     */
    private int pickAction (double max, int row)
    {
        for (int i = 0; i < 3; i++) {

            if(qTable.getQTable()[row][i] == max)
                return i;
        }
        return -1;
    }


     /*CardGenerator.generateCard();
     if(Math.Random(0,1)<epsilon) {
     agent.choserandomaction();
     }
     else choseactionwithhighesqvalue();
     immediate reward = calculatepoints(opponent card, agent card);
     generateCard();
     S = QState(highSuit, isAIturn, AIhand, opponent card);
     newQ(S,A,immediateReward,nextState)
      */


    private int calculatePoints(Card card1, Card card2){
        int totalPoints = 0;
        if (card1.getSuit() == highSuit.getSuit()){
            totalPoints += 10;
        }
        if (card2.getSuit() == highSuit.getSuit()){
            totalPoints += 10;
        }
        return card1.getRank().getCardValue() + card2.getRank().getCardValue() + totalPoints;
    }

    public boolean equalCards(Card card1, Card card2) {

        if(card1 == null || card2 == null)
        {
            return false;
        }
        if(card1.getSuit() == card2.getSuit() && card1.getRank() == card2.getRank()) {

           // System.out.println("YESSSSSSSS");
            return true;
        }
        else
            return false;
    }


    public int foundAtRow(Card hs1, Card hs2, Card opp1, Card opp2, Card aiA,Card aiB, Card aiC, Card ai1, Card ai2, Card ai3, int index )
    {

        int foundAt = -1;
            if(equalCards(hs1,hs2))
            {
                if(equalCards(opp1,opp2))
                {

                    if(equalCards(aiA,ai1) || equalCards(aiA,ai2) || equalCards(aiA,ai3))
                    {
                        //System.out.println("ONE");
                        if(equalCards(aiB,ai1) || equalCards(aiB,ai2) || equalCards(aiB,ai3))
                        {
                            //System.out.println("TWO");
                            if(equalCards(aiC,ai1) || equalCards(aiC,ai2) || equalCards(aiC,ai3))
                            {
                                foundAt = index;
                                System.out.println("A duplicate was found");
                                duplicateCounter ++;

                            }
                        }
                    }

            }
        }

        return foundAt;

    }

    public int foundAtRowFinal(QState[] states, QState currentState )
    {
        int foundAt = -1;
        for (int i = 0; i < states.length; i++) {
            if (states[i] == null || states[i].getAIHand() == null)
            {
                foundAt = -1;
                break;
            }
            Card hs1 = states[i].highsuit;
            Card hs2 = currentState.highsuit;
            Card opp1 = states[i].opponentCard;
            Card opp2 = currentState.opponentCard;
            Card aiA = states[i].getAIHand()[0];
            Card aiB = states[i].getAIHand()[1];
            Card aiC = states[i].getAIHand()[2];
            Card ai1 = currentState.getAIHand()[0];
            Card ai2 = currentState.getAIHand()[1];
            Card ai3 = currentState.getAIHand()[2];

            int index = i;

            foundAt = foundAtRow(hs1,hs2,opp1,opp2,aiA,aiB,aiC,ai1,ai2,ai3, index);

            if(foundAt != -1)
            {
                break;
            }
        }

        return foundAt;
    }

    public int foundAtRowFinal1(ArrayList<QState> states , QState currentState )
    {
        int foundAt = -1;
        for (int i = 0; i < states.size(); i++) {
            if (states.get(i) == null || states.get(i).getAIHand() == null)
            {
                foundAt = -1;
                break;
            }
            Card hs1 = states.get(i).highsuit;
            Card hs2 = currentState.highsuit;
            Card opp1 = states.get(i).opponentCard;
            Card opp2 = currentState.opponentCard;
            Card aiA = states.get(i).getAIHand()[0];
            Card aiB = states.get(i).getAIHand()[1];
            Card aiC = states.get(i).getAIHand()[2];
            Card ai1 = currentState.getAIHand()[0];
            Card ai2 = currentState.getAIHand()[1];
            Card ai3 = currentState.getAIHand()[2];

            int index = i;

            foundAt = foundAtRow(hs1,hs2,opp1,opp2,aiA,aiB,aiC,ai1,ai2,ai3, index);

            if(foundAt != -1)
            {
                break;
            }
        }

        return foundAt;
    }







}