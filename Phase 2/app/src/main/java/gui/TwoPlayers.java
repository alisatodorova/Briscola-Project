package gui;

import application.Card;
import javafx.animation.TranslateTransition;

import java.lang.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.CornerRadii;
import application.Player;
import application.Briscola;
import javafx.util.Duration;

public class TwoPlayers{
	Main main;
	public boolean DEBUG = false;
	public Scene scene1, scene2;
	public Label nameP1, nameP2;
	public BorderPane pane;
	public TextField p1NameTextField, p2NameTextField;
	public Player playerFirst, playerSecond;
	public Boolean firstPlayed;
	public Briscola game;
	public boolean acessPlayerOne, acessPlayerTwo;
	public Label pointsP2, pointsP1;
	public Rectangle[] playerOneHand, playerTwoHand;
	public Card playerOnePlayed, playerTwoPlayed;
	public Player winner;
	public Stage primaryStage;
	public int playedP1, playedP2 = -1;
	public boolean firstRound = true;
	public Image backOfCards = new Image("cards/z_card_back_black.png");
	public TranslateTransition tr = new TranslateTransition();

	public TwoPlayers(Main main){
		this.main = main;
	}

	/**
	 * starting the Java FX GUI
	 * @param primaryStage - the window frame
	 */

	public Stage stage(Stage primaryStage){
		this.primaryStage = primaryStage;
		// creating the arrays of rectangles of the players hand
		try {
			playerOneHand = new Rectangle[3];
			playerTwoHand = new Rectangle[3];
			for (int i = 0; i < 3; i++)
			{
				// defining the Rectangles and adding color for debugging
				playerOneHand[i] = new Rectangle(100, 150);
				if(DEBUG) playerOneHand[i].setFill(Color.ORANGE);
				playerTwoHand[i] = new Rectangle(100, 150);
				if(DEBUG) playerTwoHand[i].setFill(Color.ORANGE);
			}

			// -------------------------- SCENE 1: WELCOME PAGE
			// --------------------------------//
			nameP2 = new Label();
			nameP1 = new Label();
			SignUp signup = new SignUp(this);
			GridPane grid = signup.gridPane(primaryStage);

			briscolaStart();

			// -------------------------- SCENE 2: Playing Briscola
			// --------------------------------//
			gameWindow(primaryStage);
			acessPlayerOne = true;
			acessPlayerTwo = false;
			playerPickCard();
			
			primaryStage.setScene(scene1);
			primaryStage.setTitle("Briscola");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return primaryStage;
	}


	/**
	 * starting the Game -
	 * getting the names passed from the input
	 * starting Briscola - which does all the backend of the game
	 * setting firstPlayed to true - since player 1 is starting
	 */
	public void briscolaStart() {
		if(DEBUG) System.out.println("Briscola Start");
		playerFirst = new Player(nameP1.getText(), 1);
		playerSecond = new Player(nameP2.getText(), 2);
		if(DEBUG)System.out.println(playerFirst.getName() + "name =" + nameP1.getText());
		if(DEBUG)System.out.println(playerSecond.getName() + "name =" + nameP2.getText());
		game = new Briscola(playerFirst, playerSecond);
		firstPlayed = true;
		game.start();
	}

	/**
	 * creating the Game view (backgroud DARKGREEN)
	 * @param primaryStage taking the window frame - so that there is not one more window but rather a new input inside the main frame
	 * @return the interior items of teh Window frame (BorderPane pane)
	 */
	public void gameWindow(Stage primaryStage) {
		if(DEBUG) System.out.println("Game Window Beginning");
		pane = new BorderPane();
		StackPane spaceTwo = new StackPane();
		spaceTwo.setBackground(new Background(new BackgroundFill(Color.DARKGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
		scene2 = new Scene(spaceTwo, 1000, 800);
		// adding the Player one hand
		handP1Up();
		bottomScreen();
		if(DEBUG) System.out.println("hand P1 set");
		// addign the Player 2 hand to the screen - hand p2 up so we have all the Cards loaded for quick switching
		handP2Up();
		// facing the Player 2 hand down sicne polayer one starts
		handDown(playerTwoHand, 2);
		upScreen();
		if(DEBUG) System.out.println("hand P2 set");

		// adding tjhe Deck and High Suit on the right hand side of the screen
		Rectangle deckOfCards = new Rectangle(100, 150);
		deckOfCards.setFill(new ImagePattern(backOfCards));

		Rectangle highSuit = new Rectangle(100, 150);
		highSuit.setFill(new ImagePattern(new Image("cards/" + game.getHighSuit().toString() + ".png")));

		VBox rightPanel = new VBox(50, deckOfCards, highSuit);
		rightPanel.setPadding(new Insets(10, 10, 10, 10));
		// reassuring that when starting the First player starts
		firstPlayed = true;

		rightPanel.setAlignment(Pos.CENTER);
		pane.setRight(rightPanel);

		spaceTwo.getChildren().add(pane);
		// return pane;
	}

	/**
	 * Update the bottom of screen of GUI
	 */
	private void bottomScreen(){
		if(DEBUG) System.out.println("BottomScreen");
		BorderPane botom = new BorderPane();
		HBox CardsPlayerOne = new HBox(20, playerOneHand[0], playerOneHand[1], playerOneHand[2]);
		botom.setCenter(CardsPlayerOne);
		CardsPlayerOne.setAlignment(Pos.CENTER);
		CardsPlayerOne.setPadding(new Insets(10, 10, 10, 10));
		botom.setLeft(p1Info());
		pane.setBottom(botom);
	}

	/**
	 * Update the top of screen
	 */
	private void upScreen(){
		if(DEBUG) System.out.println("UpScreen");
		BorderPane up = new BorderPane();
		HBox CardsPlayerTwo = new HBox(20, playerTwoHand[0], playerTwoHand[1], playerTwoHand[2]);
		up.setCenter(CardsPlayerTwo);
		CardsPlayerTwo.setAlignment(Pos.CENTER);
		CardsPlayerTwo.setPadding(new Insets(10, 10, 10, 10));
		up.setLeft(p2Info());
		pane.setTop(up);
	}

	/**
	 * When player click on a card this method will be called
	 * Play the card and delete if from player hand
	 * Do the animation on GUI
	 */
	public void playerPickCard() {
		EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(acessPlayerOne) {
					if (event.getSource() == playerOneHand[0] && acessPlayerOne) {
						playerOnePlayed = playerFirst.playCard(0);
						playedP1 = 0;
						tr.setByY(-200);
						tr.setByX(+120);
						tr.setDuration(Duration.millis(600));
						tr.setNode(playerOneHand[0]);
						tr.play();
						tr.setOnFinished(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								switching();
							}
						});
					}
					else if (event.getSource() == playerOneHand[1] && acessPlayerOne) {
						playerOnePlayed = playerFirst.playCard(1);
						playedP1 = 1;
						tr.setByY(-200);
						tr.setByX(0);
						tr.setDuration(Duration.millis(600));
						tr.setNode(playerOneHand[1]);
						tr.play();
						tr.setOnFinished(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								switching();
							}
						});
					}
					else if (event.getSource() == playerOneHand[2] && acessPlayerOne) {
						playerOnePlayed = playerFirst.playCard(2);
						playedP1 = 2;
						tr.setByY(-200);
						tr.setByX(-120);
						tr.setDuration(Duration.millis(600));
						tr.setNode(playerOneHand[2]);
						tr.play();
						tr.setOnFinished(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								switching();
							}
						});
					}
				}
				else if (acessPlayerTwo) {
					if (event.getSource() == playerTwoHand[0] && acessPlayerTwo) {
						playerTwoPlayed = playerSecond.playCard(0);
						playedP2 = 0;
						tr.setByY(200);
						tr.setByX(+120);
						tr.setDuration(Duration.millis(600));
						tr.setNode(playerTwoHand[0]);
						tr.play();
						tr.setOnFinished(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								switching();
							}
						});
					}
					else if (event.getSource() == playerTwoHand[1] && acessPlayerTwo) {
						playerTwoPlayed = playerSecond.playCard(1);
						playedP2 = 1;
						tr.setByY(200);
						tr.setByX(0);
						tr.setDuration(Duration.millis(600));
						tr.setNode(playerTwoHand[1]);
						tr.play();
						tr.setOnFinished(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								switching();
							}
						});
					}
					else if (event.getSource() == playerTwoHand[2] && acessPlayerTwo) {
						playerTwoPlayed = playerSecond.playCard(2);
						playedP2 = 2;
						tr.setByY(200);
						tr.setByX(-120);
						tr.setDuration(Duration.millis(600));
						tr.setNode(playerTwoHand[2]);
						tr.play();
						tr.setOnFinished(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								switching();
							}
						});

					}
				}
				event.consume();
			}
		};
		int count = 0;
		for (int i = 0; i < 3; i++) {

			if (playerOneHand[i].getFill() != Color.BLACK) {
				playerOneHand[i].setOnMouseClicked(mouseHandler);
			}
			if (playerTwoHand[i].getFill() != Color.BLACK) {
				playerTwoHand[i].setOnMouseClicked(mouseHandler);
			}
		}
		boolean isEmpty = true;
		for (int i = 0; i < playerFirst.getHand().length; i++) {
			if (playerFirst.getHand()[i] != null)
			isEmpty=  false;
			if (playerSecond.getHand()[i] != null)
			isEmpty = false;
		}
		if (isEmpty)
		{
			//call end Game
			WinnerTwo winner = new WinnerTwo(this, main );
			GridPane grid2 = new GridPane();
			grid2 = winner.gridPane(primaryStage);
		}
	}

	/**
	 * After one player played,
	 * put the cards of player to back of cards
	 * and show other player hands
	 */
	private void switchPlayer(){
		handDown(playerOneHand, 1);
		handDown(playerTwoHand, 2);

		if (firstPlayed){
			handP2Up();

			firstPlayed = false;
			acessPlayerTwo = true;
			acessPlayerOne = false;
		} else {
			handP1Up();

			firstPlayed = true;
			acessPlayerOne = true;
			acessPlayerTwo = false;
		}

	}

	/**
	 * Calculate who won the last round
	 * Give new cards
	 * Start the new round
	 * Specify who should start the new hand
	 */
	public void switching() {

		if (firstRound && (playerTwoPlayed != null && playerOnePlayed != null)) {
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			winner = game.gameLogic.checkCards(playerOnePlayed, playerTwoPlayed, game.getHighSuit(), playerFirst, playerSecond);
			firstRound = false;
			showWinner();
		} else if (playedP1 != -1 && playedP2 != -1 && playerOnePlayed != null && playerTwoPlayed != null) {
			// game Logic
			if (winner.getId() == playerFirst.getId()) {
				winner = game.gameLogic.checkCards(playerOnePlayed, playerTwoPlayed, game.getHighSuit(), playerFirst, playerSecond);
			} else {
				winner = game.gameLogic.checkCards(playerTwoPlayed, playerOnePlayed, game.getHighSuit(), playerSecond, playerFirst);
			}

			showWinner();
		}
		else
		{
			switchPlayer();
		}
	}

	/**
	 * Transition that shows us the Winner of the Round - by covering the Other pLayers card.*/
	private void showWinner()
	{
		if (winner.getId() == playerFirst.getId())
		{
			if(DEBUG) System.out.println("Player One won this Round");
			tr.setByY(-175);
			tr.setByX(0);
			tr.setDuration(Duration.millis(600));
			tr.setNode(playerOneHand[playedP1]);
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
			tr.setNode(playerTwoHand[playedP2]);
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

	/**
	 * start the new round
	 */
	private void newRound()
	{
		if(!game.shuffled.isEmpty())
			game.newCards();
		updatePoints();
		clearTable();

		handP1Up();
		handP2Up();
		if (winner.getId() == playerFirst.getId())
		{
			firstPlayed = true;
			acessPlayerOne = true;
			acessPlayerTwo = false;
			handDown(playerTwoHand, 2);
		}
		else {
			firstPlayed = false;
			acessPlayerTwo = true;
			acessPlayerOne = false;
			handDown(playerOneHand, 1);
		}
		bottomScreen();
		upScreen();
		playerPickCard();
	}

	/**
	 * After each round, clean everything for the new round
	 */
	private void clearTable () {
		for (int i = 0; i < playerOneHand.length; i++) {
			if (i == playedP2) {
				playerTwoHand[i].setFill(Color.DARKGREEN);
				playerTwoHand[i] = null;
				Rectangle newCard = new Rectangle(100, 150);
				newCard.setFill(Color.BLACK);
				playerTwoHand[i] = newCard;
			}
			if (i == playedP1) {
				playerOneHand[i].setFill(Color.DARKGREEN);
				playerOneHand[i] = null;
				Rectangle newCard = new Rectangle(100, 150);
				newCard.setFill(Color.BLACK);
				playerOneHand[i] = newCard;
			}
		}
		playedP1 = -1;
		playedP2 = -1;
		playerOnePlayed = null;
		playerTwoPlayed = null;
		acessPlayerOne = true;
		acessPlayerTwo = true;

		if (DEBUG) System.out.println("Table Cleared");
	}
	/**
	 * If the back of the cards for player 1 are displayed,
	 * this methods switches the cards to display their front.
	 */
	private void handP1Up(){
		if (DEBUG) System.out.println("p1 hand:" +playerFirst.getHand()[0] + "    "+playerFirst.getHand()[1] + "    "+playerFirst.getHand()[2]);
		for (int i = 0; i<3; i++){
			if ((playerOneHand[i] != null)) {
				if ((playerFirst.getHand()[i] == null)) {
					if (DEBUG) System.out.println("BLACK CARD -----------------------------------");
					playerOneHand[i].setFill(Color.BLACK);
				}
				else {
					playerOneHand[i].setFill(new ImagePattern(new Image("cards/" + playerFirst.getHand()[i] + ".png")));
				}
			}
			else
			{

				playerOneHand[i] = new Rectangle(100, 150);
				playerOneHand[i].setFill(Color.GRAY);
			}
		}
	}

	/**
	 * If the back of the cards for player 2 are displayed,
	 * this methods switches the cards to display their front.
	 */
	private void handP2Up(){
		if (DEBUG) System.out.println("P2 hand :" +playerSecond.getHand()[0] + "    "+playerSecond.getHand()[1] + "    "+playerSecond.getHand()[2]);
		for (int i = 0; i<3; i++){
			if ((playerTwoHand[i] != null)) {
				if ((playerSecond.getHand()[i] == null)) {
					if (DEBUG) System.out.println("BLACK CARD -----------------------------------");
					playerTwoHand[i].setFill(Color.BLACK);
				}
				else {
					playerTwoHand[i].setFill(new ImagePattern(new Image("cards/" + playerSecond.getHand()[i] + ".png")));
				}
			}
			else
			{
				playerTwoHand[i] = new Rectangle(100, 150);
				playerTwoHand[i].setFill(Color.GRAY);
			}
		}
	}

	/**
	 * Put the cards display to back of the card so other player cant see this player cards
	 * @param cards Cards need to be changed
	 * @param player Id of the player with these cards
	 */
	private void handDown(Rectangle[] cards, int player){
		for (int i = 0; i<3; i++){
			if ((playedP1 != i && player == 1) || (playedP2 != i && player == 2) ) {
				if (player == 2 && (cards[i] != null)) {
					if ((playerSecond.getHand()[i] == null)) {
						if (DEBUG) System.out.println("BLACK CARD -----------------------------------");
						cards[i].setFill(Color.BLACK);
					} else {
						cards[i].setFill(new ImagePattern(backOfCards));
					}
				} else if (player == 1 && (cards[i] != null)) {
					if ((playerFirst.getHand()[i] == null)) {
						if (DEBUG) System.out.println("BLACK CARD -----------------------------------");
						cards[i].setFill(Color.BLACK);
					} else {
						cards[i].setFill(new ImagePattern(backOfCards));
					}
				} else {
					cards[i] = new Rectangle(100, 150);
					cards[i].setFill(Color.GRAY);
				}
			}
		}
	}

	/**
	 * Make the label for name and points of first player and put them in a vbox
	 * @return Vbox of player one information
	 */
	private VBox p1Info() {
		nameP1.setFont(Font.font("Verdana", 20));
		nameP1.setStyle("-fx-font-size: 20;");
		if(DEBUG) System.out.println("p1 Name" + nameP1.toString());
		pointsP1 = new Label("Points: " + playerFirst.getPoints());
		pointsP1.setFont(Font.font("Verdana", 20));
		pointsP1.setStyle("-fx-font-size: 20;");
		VBox playerOne = new VBox(10, nameP1, pointsP1);
		playerOne.setPadding(new Insets(10, 10, 10, 10));
		playerOne.setAlignment(Pos.CENTER);
		return playerOne;
	}
	/**
	 * Make the label for name and points of second player and put them in a vbox
	 * @return Vbox of player one information
	 */
	private VBox p2Info() {
		nameP2.setFont(Font.font("Verdana", 20));
		nameP2.setStyle("-fx-font-size: 20;");
		if(DEBUG) System.out.println("Name P2" + nameP2.toString());
		pointsP2 = new Label("Points: " + playerSecond.getPoints());
		pointsP2.setFont(Font.font("Verdana", 20));
		pointsP2.setStyle("-fx-font-size: 20;");
		VBox playerTwo = new VBox(10, nameP2, pointsP2);
		playerTwo.setPadding(new Insets(10, 10, 10, 10));
		playerTwo.setAlignment(Pos.CENTER);
		return playerTwo;
	}

	/**
	 * Update the points displaying on screen
	 */
	public void updatePoints(){
		if(DEBUG) System.out.println("player2 score "  + playerSecond.getPoints());
		if(DEBUG) System.out.println("player1 score "  + playerFirst.getPoints());
		pointsP2.setText("Points: " + playerSecond.getPoints());
		pointsP1.setText("Points: " + playerFirst.getPoints());
	}
}
