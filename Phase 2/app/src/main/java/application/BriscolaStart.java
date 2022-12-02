package application;

import java.util.Scanner;
import application.Briscola;

/**
 * if we start the Terminal game then from this file
 */
public class BriscolaStart {
    /**
     * starting the Terminal GUI Game version
     * @param args
     */
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Player1 name :");
        Player one = new Player(scan.next(), 1);
        System.out.println("Player2 name :");
        Player two = new Player(scan.next(), 2);

        Briscola game = new Briscola(one, two);

        game.start();

        System.out.println("High Suit:");
        System.out.println(game.getHighSuit().toString());
        System.out.println("\n\n");
        //player 1 always starts
        System.out.println( one.getName()+ "   hand:");
        System.out.println(one.toString());
        System.out.println("\n\n");

        System.out.println("play:");
        Card cardOne = one.playCard(scan.nextInt());

        System.out.println("\n\n\n\n\n\n");

        System.out.println("oponent played    " + cardOne.toString());
        System.out.println("\n\n");
        System.out.println(two.getName() + "   hand:");
        System.out.println(two.toString());
        System.out.println("\n\n");
        //player 2 goes second
        System.out.println("play:");
        Card cardTwo = two.playCard(scan.nextInt());
        System.out.println("\n\n");

        Player next = game.gameLogic.checkCards(cardOne, cardTwo, game.getHighSuit(), one, two);

        System.out.println(next.getName() +"  won this round with  " + next.getPoints()+ " points");

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

                System.out.println("\n\n\n\n\n\n");

                System.out.println("oponent played     " + cardOne.toString());
                System.out.println(two.getName() + "  hand: ");
                System.out.println(two.getHand()[0] + "    "+two.getHand()[1] + "    "+two.getHand()[2]);
                System.out.println("\n\n");

                System.out.println("play:");
                cardTwo = two.playCard(scan.nextInt());
                System.out.println("\n\n");

                next = game.gameLogic.checkCards(cardOne, cardTwo, game.getHighSuit(), one, two);
                System.out.println(next.getName() +"  won this round with  " + next.getPoints() + " points");
            } else if(next.getId() == two.getId()){ //P2 won and can play first
                System.out.println(two.getName() + "    hand: ");
                System.out.println(two.getHand()[0] + "    "+two.getHand()[1] + "    "+two.getHand()[2]);
                System.out.println("\n\n");

                System.out.println("play:");
                cardOne = two.playCard(scan.nextInt());

                System.out.println("\n\n\n\n\n\n");

                System.out.println("oponent played      " + cardOne.toString());
                System.out.println(one.getName() + "   hand: ");
                System.out.println(one.getHand()[0] + "    "+one.getHand()[1] + "    "+one.getHand()[2]);
                System.out.println("\n\n");

                System.out.println("play:");
                cardTwo = one.playCard(scan.nextInt());
                System.out.println("\n\n");

                next = game.gameLogic.checkCards(cardOne, cardTwo, game.getHighSuit(), two, one);
                System.out.println(next.getName() +"  won this round with  " + next.getPoints() + " points");
            }
            else {
                System.out.println("wrong");
            }
        }
        // the last 2 rounds - deck is now empty and we need to show the user that they can play still but dont get new Cards.
        for(int i =0; i<2 ; i++) {
            if (next.getId() == one.getId()) {
                System.out.println(one.getName() + "  hand: ");
                System.out.println(one.getHand()[0] + "    "+one.getHand()[1] + "    "+one.getHand()[2]);

                System.out.println("\n\n");

                System.out.println("play:");
                cardOne = one.playCard(scan.nextInt());

                System.out.println("\n\n\n\n\n\n");

                System.out.println("oponent played     " + cardOne.toString());
                System.out.println(two.getName() + "  hand: ");
                System.out.println(two.getHand()[0] + "    "+two.getHand()[1] + "    "+two.getHand()[2]);
                System.out.println("\n\n");

                System.out.println("play:");
                cardTwo = two.playCard(scan.nextInt());
                System.out.println("\n\n");

                next = game.gameLogic.checkCards(cardOne, cardTwo, game.getHighSuit(), one, two);
                System.out.println(next.getName() +"  won this round with  " + next.getPoints() + " points");
            } else if(next.getId() == two.getId()){ // second player won the previous round
                System.out.println(two.getName() + "    hand: ");
                System.out.println(two.getHand()[0] + "    "+two.getHand()[1] + "    "+two.getHand()[2]);
                System.out.println("\n\n");

                System.out.println("play:");
                cardOne = two.playCard(scan.nextInt());

                System.out.println("\n\n\n\n\n\n");

                System.out.println("oponent played      " + cardOne.toString());
                System.out.println(one.getName() + "   hand: ");
                System.out.println(one.getHand()[0] + "    "+one.getHand()[1] + "    "+one.getHand()[2]);
                System.out.println("\n\n");

                System.out.println("play:");
                cardTwo = one.playCard(scan.nextInt());
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
