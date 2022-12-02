package AI;

import AI.LargeBrain.BigBrainPruning;
import AI.LargeBrain.BigState;
import AI.LargeBrain.RandomHand;
import AI.lowBrain.Brain;
import application.*;

import java.util.*;

public class AIStart {

    private static final boolean DEBUG = true;
    static Brain brainAI;
    
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

//        System.out.println("Player1 name :");
        Player one = new Player("scan.next()", 2);
        BigBrainPruning two = new BigBrainPruning();
//        BigBrain two = new BigBrain();

        Briscola game = new Briscola(two, one);



        game.start();

        System.out.println("High Suit:");
        System.out.println(game.getHighSuit().toString());

        // Here one can change the Easy AIs to use during the Game State
//        SimpleBrain brainAI = new SimpleBrain(game.getHighSuit());
//        BaseLineBrain brainAI = new BaseLineBrain(game.getHighSuit());
//        RandBrain brainAI = new RandBrain();
//        BigBrainPruning brainAI = new BigBrainPruning(game.getHighSuit());
        LinkedList<Card> cardsLeft = new LinkedList<>();
        cardsLeft = (LinkedList<Card>) (game.deck.sorted.clone());
        cardsLeft = Deck.cardsLeftUpdateHand(cardsLeft, two.getHand());
//        if (DEBUG) System.out.println(Arrays.toString(cardsLeft.toArray()));
        Card[] human = RandomHand.randomHand(cardsLeft);
        System.out.println(human[0].toString() +"  " + human[1].toString() +"  " + human[2].toString());
        BigState brainState = new BigState(cardsLeft, two.getHand(), human, game.getHighSuit());


        System.out.println("AI Turns:");
        System.out.println(two.toString());
//        State start = new State(false, two.getHand());
//        Card cardTwo = two.playCard(brainAI.whatToPlay(start, cardOne)
//        brainState.setAITurn(true);
//        brainState.setAITurn();
//        Card cardTwo = two.playCard(two.miniMax(brainState, null));
        Card cardTwo = two.playCard(two.alphaBeta(brainState, null));
        System.out.println("AI played    " + cardTwo.toString());


        System.out.println("\n");
        //player 1 always starts
        System.out.println( one.getName()+ "   hand:");
        System.out.println(one.toString());
        System.out.println("\n");

        System.out.println("play:");
        Card cardOne = one.playCard(scan.nextInt());
        System.out.println("Human Played    " + cardOne.toString());

        System.out.println("\n");

        Player next = game.gameLogic.checkCards(cardTwo,cardOne , game.getHighSuit(), two, one);

        System.out.println(next.getName() +"  won this round with  " + next.getPoints()+ " points");
        LinkedList<Card> cardsLeftWhile = new LinkedList<>();
        cardsLeftWhile = (LinkedList<Card>) (cardsLeft.clone());
        // loop that plays the game
        while (!game.shuffled.isEmpty()) {
            game.newCards();
            // checking who is the winner p1/p2
            if (next.getId() == one.getId()) {
                System.out.println(one.getName() + "  hand: ");
                System.out.println(one.getHand()[0] + "    "+one.getHand()[1] + "    "+one.getHand()[2]);

                System.out.println("\n\n");

                System.out.println("play:");
                cardOne = one.playCard(scan.nextInt());
                System.out.println("Human Played    " + cardOne.toString());

                System.out.println("AI Turns:");
                System.out.println(two.toString());
//                start = new State(false, two.getHand());
                LinkedList<Card> cardsLeftWhile2 = new LinkedList<>();
                cardsLeftWhile2 = (LinkedList<Card>) (cardsLeftWhile.clone());
                brainState = new BigState(cardsLeft,two.getHand(),RandomHand.randomHand(cardsLeft), game.highSuit);
//                cardTwo = two.playCard(two.miniMax(brainState, cardOne));
                cardTwo = two.playCard(two.alphaBeta(brainState, cardOne));
                System.out.println("AI played    " + cardTwo.toString());
                next = game.gameLogic.checkCards(cardOne, cardTwo, game.getHighSuit(), one, two);
                System.out.println(next.getName() +"  won this round with  " + next.getPoints() + " points");
            } else if(next.getId() == two.getId()){ //P2 won and can play first

                System.out.println("AI Turns:");
                System.out.println(two.toString());
//                start = new State(true, two.getHand());
                brainState = new BigState(cardsLeft,two.getHand(),RandomHand.randomHand(cardsLeft), game.highSuit);
//                cardOne = two.playCard(two.miniMax(brainState,null));
                cardOne = two.playCard(two.alphaBeta(brainState,null));

//                System.out.println("\n\n\n\n\n\n");

                System.out.println("AI played      " + cardOne.toString());
                System.out.println(one.getName() + "   hand: ");
                System.out.println(one.getHand()[0] + "    "+one.getHand()[1] + "    "+one.getHand()[2]);
                System.out.println("\n");

                System.out.println("play:");
                cardTwo = one.playCard(scan.nextInt());
                System.out.println("Human Played    " + cardTwo.toString());
                System.out.println("\n");

                next = game.gameLogic.checkCards(cardOne, cardTwo, game.getHighSuit(), two, one);
                System.out.println(next.getName() +"  won this round with  " + next.getPoints() + " points");
            }
            else {
                System.out.println("wrong");
            }

//            List<Card> cardsLeftWhile = new LinkedList<>();
//            Collections.copy(cardsLeftWhile, cardsLeftWhile2);
        }
        // the last 2 rounds - deck is now empty and we need to show the user that they can play still but dont get new Cards.
        for(int i =0; i<2 ; i++) {
            if (next.getId() == one.getId()) {
                System.out.println(one.getName() + "  hand: ");
                System.out.println(one.getHand()[0] + "    "+one.getHand()[1] + "    "+one.getHand()[2]);

                System.out.println("\n");

                System.out.println("play:");
                cardOne = one.playCard(scan.nextInt());
                System.out.println("Human Played    " + cardOne.toString());
                System.out.println("Human Played    " + cardOne.toString());

                System.out.println("AI Turns:");
//                System.out.println(two.toString());
//                start = new State(false, two.getHand());
                brainState = new BigState(cardsLeft,two.getHand(),RandomHand.randomHand(cardsLeft), game.highSuit);
//                cardTwo = two.playCard(two.miniMax(brainState, cardOne));
                cardTwo = two.playCard(two.alphaBeta(brainState, cardOne));
                System.out.println("AI played    " + cardTwo.toString());
                next = game.gameLogic.checkCards(cardOne, cardTwo, game.getHighSuit(), one, two);
                System.out.println(next.getName() +"  won this round with  " + next.getPoints() + " points");
            } else if(next.getId() == two.getId()){ // second player won the previous round

                System.out.println("AI Turns:");
//                System.out.println(two.toString());
//                start = new State(true, two.getHand());
                brainState = new BigState(cardsLeft,two.getHand(),RandomHand.randomHand(cardsLeft), game.highSuit);
//                cardOne = two.playCard(two.miniMax(brainState, null));
                cardOne = two.playCard(two.alphaBeta(brainState, null));

//                System.out.println("\n\n\n\n\n\n");

                System.out.println("AI played      " + cardOne.toString());
                System.out.println(one.getName() + "   hand: ");
                System.out.println(one.getHand()[0] + "    "+one.getHand()[1] + "    "+one.getHand()[2]);
                System.out.println("\n\n");

                System.out.println("play:");
                cardTwo = one.playCard(scan.nextInt());
                System.out.println("Human Played" + cardTwo);
                System.out.println("\n\n");

                next = game.gameLogic.checkCards(cardOne, cardTwo, game.getHighSuit(), two, one);
                System.out.println(next.getName() +"  won this round with  " + next.getPoints() + " points");
            }
            else {
                System.out.println("wrong");
            }
        }
        game.endGame();
    }

}
