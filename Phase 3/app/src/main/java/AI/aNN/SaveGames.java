package AI.aNN;

import application.GameSimTrain;

public class SaveGames {
    int[][] input ;
    int[][] output ;
    int counter;
    final int NUMBER_OF_ROUNDS = 20;
    final int NUMBER_OF_CARDS = 40;
    ANNData annData;

    public SaveGames(){
        input = new int[NUMBER_OF_ROUNDS][NUMBER_OF_CARDS];
        output = new int[NUMBER_OF_ROUNDS][1];
        counter = 0;

    }

    public SaveGames(int number_of_games){
        input = new int[number_of_games * NUMBER_OF_ROUNDS][NUMBER_OF_CARDS];
        output = new int[number_of_games * NUMBER_OF_ROUNDS][1];
        counter = 0;

    }
    public ANNData getAnnData(){return annData;}

    public void save(ANNData round){
//        System.out.println();
//
//        System.out.println(counter);
//        System.out.println();
//        for (int i = 0 ; i < 40; i ++){
//            System.out.print("  [" + round.getDeckInt()[i] + "]   , ");
//            System.out.println();
//
//
//        }
        input[counter] = round.getDeckInt();
        output[counter] = round.getOutput();
        counter++;
    }

    public int[][] getInput() {
        return input;
    }

    public int[][] getOutput() {
        return output;
    }




}
