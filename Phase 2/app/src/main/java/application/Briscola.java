package application;

import java.util.Scanner;
import java.util.Stack;

/**
 * Game Back end of the Game, all the administrative stuff
 * creating the players hands, giving out cards
 * checking if one player won the game
 */
public class Briscola {

	public Player playerOne;
	public Player playerTwo;
	public Card highSuit;
	public Deck deck = new Deck();
	public GameLogic gameLogic;
	public Stack<Card> shuffled;

	/**
	 * Constructor of the game, getting the Players and starting the game Logic while shuffling the deck
	 * @param playerOne - the Players
	 * @param playerTwo
	 */
	public Briscola(Player playerOne, Player playerTwo) {
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
		this.gameLogic = new GameLogic();
		this.shuffled = deck.shuffle(deck.sorted);

	}

	/**
	 * Starting the Game - giving out the Cards to the players
	 * setting the 7th card to high suit
	 */
	public void start() {
		for (int i = 0; i < 3; i++) {
			playerOne.setCard(shuffled.pop());
			playerTwo.setCard(shuffled.pop());
		}
		highSuit = shuffled.pop();
	}

	/**
	 * getting the high suit to display in the
	 * @return the High Suit card
	 */
	public Card getHighSuit() {
		return highSuit;
	}

	/**
	 * giving out a new card to the players
	 * if the Deck only has one card left, the high suit gets added to be handed out.
	 */
	public void newCards() {

		if (shuffled.size() >= 2) {
			playerOne.setNullCard(shuffled.pop());
			playerTwo.setNullCard(shuffled.pop());
		} else if (shuffled.size() == 1) {
			playerOne.setNullCard(shuffled.pop());
			shuffled.push(highSuit);
			playerTwo.setNullCard(shuffled.pop());
		}
		// Once stack is empty we don't have cards to give out anymore.

	}

	/**
	 * in the Terminal GUI ending the Game and displaying the Winner
	 */
	public void endGame() {
		System.out.println("we are in end game ---------------------------------------");
		
		Player winner = winGame();
		System.out.println(winner.getName() + " Wins!");
	}

	/**
	 * return the winning player of the Game
	 * @return the winning player
	 */
	public Player winGame() {
		if (playerOne.getPoints() >= 61) {
			return playerOne;
		} else if (playerTwo.getPoints() >= 61) {
			return playerTwo;
		}
		else{
			return playerOne;
		}
	}
}
