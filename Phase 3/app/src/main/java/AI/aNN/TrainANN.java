package AI.aNN;

import application.GameSimTrain;

/**
this file is used for training the ANN
 */
public class TrainANN {

    public static void main(String[] args) {


        NeuralNetwork nn = new NeuralNetwork(20);
//        NeuralNetwork nn = new NeuralNetwork();
       TrainANN train = new TrainANN();

       SaveGames save = train.simGame(5);
//        int predict = nn.predict(save.getInput()[0]);
        nn.trainNeuralNetwork(nn, save);
//        nn.check(nn);
//        System.out.println("this is the input");
//        for (int j = 0 ; j < save.getOutput()[0].length; j++){
//            System.out.print("[" + save.getInput()[0][j] + "], ");
//
//        }
//        System.out.println();
//        int outputTrue = -100;
//        for (int j = 0 ; j < save.getOutput()[0].length; j++){
//            if(save.getOutput()[0][j] ==1 ){
//                outputTrue = j;
//            }
//        }
//        System.out.println("this is the right outoput" + outputTrue);
//        System.out.println("this is nn output" + predict);

        FileSaveANN.saveFinal(nn.weights, nn.bias);

//        if(console) System.out.println("weights 0: "+Arrays.deepToString(weights[0].data));
//        if(console) System.out.println("bias 0: "+Arrays.deepToString(bias[0].data));
        FileSaveANN.saveFinal(nn.weights, nn.bias);

    }
    private SaveGames simGame(int number_of_games){
        GameSimTrain gamesim = new GameSimTrain(number_of_games);
        return gamesim.getSave();

    }
}
