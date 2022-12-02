//package AI;
//
//import AI.LargeBrain.BigBrainPruning;
//import AI.LargeBrain.BigState;
//import AI.LargeBrain.RandomHand;
//import AI.lowBrain.Brain;
//import AI.lowBrain.State;
//import application.Briscola;
//import application.Card;
//import application.Deck;
//import application.Player;
//
//import java.util.Arrays;
//import java.util.LinkedList;
//import java.util.Scanner;
//
//public class AIStartHuman {
//
//
//    private static final boolean DEBUG = true;
//    static Brain brainAI;
//
//    public static void main(String[] args) {
//        Scanner scan = new Scanner(System.in);
//
//        System.out.println("Player2 name :");
//        Player two = new Player(scan.next(), 2);
//        BigBrainPruning one = new BigBrainPruning();
//
//
//        Briscola game = new Briscola(one, two);
//
//
//
//        game.start();
//
//        System.out.println("High Suit:");
//        System.out.println(game.getHighSuit().toString());
//
//        // Here one can change the Easy AIs to use during the Game State
////        SimpleBrain brainAI = new SimpleBrain(game.getHighSuit());
////        BaseLineBrain brainAI = new BaseLineBrain(game.getHighSuit());
////        RandBrain brainAI = new RandBrain();
////        BigBrainPruning brainAI = new BigBrainPruning(game.getHighSuit());
//        LinkedList<Card> cardsLeft = new LinkedList<>();
//        cardsLeft = (LinkedList<Card>) (game.deck.sorted.clone());
//        cardsLeft = Deck.cardsLeftUpdateHand(cardsLeft, two.getHand());
//        if (DEBUG) System.out.println(Arrays.toString(cardsLeft.toArray()));
//        BigState brainState = new BigState(cardsLeft, two.getHand(), RandomHand.randomHand(cardsLeft), game.getHighSuit());
//
//
//        System.out.println("AI Turns:");
//        System.out.println(two.toString());
//        State start = new State(false, two.getHand());
////        Card cardTwo = two.playCard(brainAI.whatToPlay(start, cardOne)
//        brainState.setAITurn(true);
//        Card cardOne = one.playCard(one.alphaBeta(brainState));
//
//
//        System.out.println("\n");
//        //player 1 always starts
//        System.out.println( two.getName()+ "   hand:");
//        System.out.println(two.toString());
//        System.out.println("\n");
//
//        System.out.println("play:");
//        Card cardTwo = two.playCard(scan.nextInt());
//        System.out.println("Human Played    " + cardOne.toString());
//
//        System.out.println("\n");
//
//        Player next = game.gameLogic.checkCards(cardOne, cardTwo, game.getHighSuit(), one, two);
//        System.out.println("AI played    " + cardTwo.toString());
//        System.out.println(next.getName() +"  won this round with  " + next.getPoints()+ " points");
//        LinkedList<Card> cardsLeftWhile = new LinkedList<>();
//        cardsLeftWhile = (LinkedList<Card>) (cardsLeft.clone());
//        // loop that plays the game
//        while (!game.shuffled.isEmpty()) {
//            game.newCards();
//            // checking who is the winner p1/p2
//            if (next.getId() == one.getId()) {
//                System.out.println(one.getName() + "  hand: ");
//                System.out.println(one.getHand()[0] + "    "+one.getHand()[1] + "    "+one.getHand()[2]);
//
//                System.out.println("\n\n");
//
//                System.out.println("play:");
//                cardOne = one.playCard(scan.nextInt());
//                System.out.println("Human Played    " + cardOne.toString());
//
//                System.out.println("AI Turns:");
//                System.out.println(two.toString());
//                start = new State(false, two.getHand());
//                LinkedList<Card> cardsLeftWhile2 = new LinkedList<>();
//                cardsLeftWhile2 = (LinkedList<Card>) (cardsLeftWhile.clone());
//                brainState = new BigState(cardsLeft,two.getHand(),RandomHand.randomHand(cardsLeft), game.highSuit);
//                cardTwo = one.playCard(one.alphaBeta(brainState));
//                System.out.println("AI played    " + cardTwo.toString());
//                next = game.gameLogic.checkCards(cardOne, cardTwo, game.getHighSuit(), one, two);
//                System.out.println(next.getName() +"  won this round with  " + next.getPoints() + " points");
//            } else if(next.getId() == two.getId()){ //P2 won and can play first
//
//                System.out.println("AI Turns:");
//                System.out.println(two.toString());
////                start = new State(true, two.getHand());
//                brainState = new BigState(cardsLeft,two.getHand(),RandomHand.randomHand(cardsLeft), game.highSuit);
//                cardOne = one.playCard(one.alphaBeta(brainState));
//
////                System.out.println("\n\n\n\n\n\n");
//
//                System.out.println("AI played      " + cardOne.toString());
//                System.out.println(one.getName() + "   hand: ");
//                System.out.println(one.getHand()[0] + "    "+one.getHand()[1] + "    "+one.getHand()[2]);
//                System.out.println("\n");
//
//                System.out.println("play:");
//                cardTwo = one.playCard(scan.nextInt());
//                System.out.println("Human Played    " + cardTwo.toString());
//                System.out.println("\n");
//
//                next = game.gameLogic.checkCards(cardOne, cardTwo, game.getHighSuit(), two, one);
//                System.out.println(next.getName() +"  won this round with  " + next.getPoints() + " points");
//            }
//            else {
//                System.out.println("wrong");
//            }
//
////            List<Card> cardsLeftWhile = new LinkedList<>();
////            Collections.copy(cardsLeftWhile, cardsLeftWhile2);
//        }
//        // the last 2 rounds - deck is now empty and we need to show the user that they can play still but dont get new Cards.
//        for(int i =0; i<2 ; i++) {
//            if (next.getId() == one.getId()) {
//                System.out.println(one.getName() + "  hand: ");
//                System.out.println(one.getHand()[0] + "    "+one.getHand()[1] + "    "+one.getHand()[2]);
//
//                System.out.println("\n");
//
//                System.out.println("play:");
//                cardOne = one.playCard(scan.nextInt());
//                System.out.println("Human Played    " + cardOne.toString());
//
//                System.out.println("AI Turns:");
////                System.out.println(two.toString());
//                start = new State(false, two.getHand());
//                brainState = new BigState(cardsLeft,two.getHand(),RandomHand.randomHand(cardsLeft), game.highSuit);
//                cardTwo = one.playCard(one.alphaBeta(brainState));
//                System.out.println("AI played    " + cardTwo.toString());
//                next = game.gameLogic.checkCards(cardOne, cardTwo, game.getHighSuit(), one, two);
//                System.out.println(next.getName() +"  won this round with  " + next.getPoints() + " points");
//            } else if(next.getId() == two.getId()){ // second player won the previous round
//
//                System.out.println("AI Turns:");
////                System.out.println(two.toString());
//                start = new State(true, two.getHand());
//                brainState = new BigState(cardsLeft,two.getHand(),RandomHand.randomHand(cardsLeft), game.highSuit);
//                cardOne = two.playCard(two.alphaBeta(brainState));
//
////                System.out.println("\n\n\n\n\n\n");
//
//                System.out.println("AI played      " + cardOne.toString());
//                System.out.println(one.getName() + "   hand: ");
//                System.out.println(one.getHand()[0] + "    "+one.getHand()[1] + "    "+one.getHand()[2]);
//                System.out.println("\n\n");
//
//                System.out.println("play:");
//                cardTwo = one.playCard(scan.nextInt());
//                System.out.println("Human Played" + cardTwo);
//                System.out.println("\n\n");
//
//                next = game.gameLogic.checkCards(cardOne, cardTwo, game.getHighSuit(), two, one);
//                System.out.println(next.getName() +"  won this round with  " + next.getPoints() + " points");
//            }
//            else {
//                System.out.println("wrong");
//            }
//        }
//        game.endGame();
//    }
//
//}
//
