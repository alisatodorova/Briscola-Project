package AI.ReinforcementLearning;

import application.Card;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileStoreQL {
    static boolean DEBUG = false;
    static String statesFile = "qStatesInfo.txt";
    static String qTableFile = "qTableInfo.txt";

    protected static QTable readInfo(){
        try{
            QTable storage = new QTable();
            File frTable = new File(qTableFile);
            Scanner scTable = new Scanner(frTable);
            File frState = new File(statesFile);
            Scanner brState = new Scanner(frState);
            int statesCounter = 0;
            int tableCounter = 0;
            while (brState.hasNextLine()){
                boolean aiTurn;
                Card[] aiHand = new Card[3];
                Card opponentCard = null;
                Card highSuit = null;
                String data = brState.nextLine();
                String[] line = data.split(" ");
                aiTurn = Boolean.parseBoolean(line[0]);
                for (int i = 1; i < line.length; i++) {
                    Card.Suit suitCard = null;
                    Card.Rank rankCard = null;
                    String suit = line[i].split("")[0];
                    String rank = line[i].substring(1);
                    switch (suit) {
                        case "A" -> suitCard = Card.Suit.A;
                        case "B" -> suitCard = Card.Suit.B;
                        case "C" -> suitCard = Card.Suit.C;
                        case "D" -> suitCard = Card.Suit.D;
                    }

                    switch (rank) {
                        case "ACE" -> rankCard = Card.Rank.ACE;
                        case "THREE" -> rankCard = Card.Rank.THREE;
                        case "KING" -> rankCard = Card.Rank.KING;
                        case "QUEEN" -> rankCard = Card.Rank.QUEEN; // PLEASE ADJUST AFTER MERGING with Cheating where the Cards Queen and Jack are changed
                        case "JACK" -> rankCard = Card.Rank.JACK; // this as well...
                        case "SEVEN" -> rankCard = Card.Rank.SEVEN;
                        case "SIX" -> rankCard = Card.Rank.SIX;
                        case "FIVE" -> rankCard = Card.Rank.FIVE;
                        case "FOUR" -> rankCard = Card.Rank.FOUR;
                        case "TWO" -> rankCard = Card.Rank.TWO;
                    }

                    if (i <= 3) {
                        aiHand[i - 1] = new Card(suitCard, rankCard);
                    }
                    else if (i == 4){
                        opponentCard = new Card(suitCard, rankCard);
                    }
                    else if (i == 5){
                        highSuit = new Card(suitCard, rankCard);
                    }
                }
                QState thisRound = new QState(aiTurn, highSuit, aiHand, opponentCard);
                if(DEBUG) System.out.println("Data: " + data);
                if (DEBUG) System.out.println("State " + aiTurn + " " + aiHand[0] + " " + aiHand[1] + " " + aiHand[2] + " " + opponentCard + " " + highSuit);
                storage.addState(statesCounter++, thisRound);
            }
            // While for the Table
            while(scTable.hasNextLine()){

                String data = scTable.nextLine();
                String[] line = data.split(" ");
                double[] roundInt = new double[line.length];
                for (int i = 0; i < line.length; i++){
                    roundInt[i] = Double.parseDouble(line[i]);
                }
                storage.addRow(tableCounter++, roundInt);

            }

            return storage;
        } catch (Exception e) {
            System.out.println("Could not read in both files...");
        }
        return null;
    }

    public static void writeData (QTable qTable1){
        PrintWriter outStates;
        PrintWriter outTable;
        int kStop = 0;
        try {
            outStates = new PrintWriter(statesFile);
            outTable = new PrintWriter(qTableFile);
            System.out.println("StateID last: " + QTable.states1.get(QTable.states1.size() - 1).stateID);
            for (int i = 0; i < qTable1.qTable.length; i++){
                for (int j = 0; j < qTable1.qTable[i].length; j++){
                    outTable.print(qTable1.qTable[i][j] + " ");
                }
                outTable.println();
            }

            for (int k = 0; k < QTable.states1.size(); k++){
                kStop = k;
                outStates.println(QTable.states1.get(k).toString());
            }
            outStates.close();
            outTable.close();
        } catch (Exception e) {
            System.out.println(kStop);
            e.printStackTrace();
        }
    }
}
