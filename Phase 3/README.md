# Project2-1

In the terminal please run:
```
$ gradle build
$ gradle run
```
The Rules of the Game:
- Italian Deck of Cards, only 40 cards are used
  - The Suits are Clubs, Coins, Cups, and Swords
  - Rank from highest to lowest is: Ace, 3, King (wearing a Cape), Queen (on Horse), Jack, 7, 6, 5, 4, 2 
    - The Points are awarded for Ace: 11 points, 3: 10 Points, King: 4 points, Queen: 3 points and Jack: 2 points
- At the start of the game, each player is handled 3 cards from the shuffled deck.
- the 7th card from the Deck is a trumping Suit, so any other suit can be taken by a card of that suit
- Player one will play the first card of the first round
- Each Round:
  - every round both players play a card, starting by the winner of the previous round
  - After the first card is played, the opponent has to make a choice,
    - playing the same Suit but higher value will result in them winning 
    - playing the trumping Suit if the fist case is not inside the trumping suit
    - playing any other suit (excluding trumping Suit) than the first card, will result in them loosing
  - After both players played a card, and a winner of the round was decided, both players take a new card so that they
have 3 cards at the start of each round
  - There will be a point were the deck only contains one card. The 7th card (trumping suit decider card) is given to 
one of the players
  - If the Deck is empty no new cards follow, but the current cards on the hand can still be played
- Once every card has been played, the winner will be the one with more than 60 points


The Game has a GUI component that is taking JavaFX, which should be downloaded via Gradle build, which you probably ran before.
<br>
If that fails, go into app/src/main/java/App.java and change the _gui_ boolean to false:
```java
//app/src/main/java/App.java line 11
//change the currently used
private static boolean gui = true;
// to 
private static boolean gui = false;
```

The Agents that are availible are:
- Random
- BaseLine
- Single State MiniMax
- Monte Carlo Approximation
  - With or without Pruning
- Hybrid of MCA and Artificial Neural Network which needs files.

Thank you very much, 
Group 13
 <br>
 (Alisa, Parand, Alex, Chandu, Joaquin, Oscar)

