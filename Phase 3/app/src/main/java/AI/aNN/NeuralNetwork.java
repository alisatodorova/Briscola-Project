package AI.aNN;

import application.GameSimTrain;

import javax.crypto.spec.PSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
    This is the ANN class it used for initializing ,  training and predicting using the ANN a met
 */

public class NeuralNetwork {
    static boolean DEBUG = false;
    static boolean console = false;
    double l_rate=0.1;
    final int number_of_INPUT = 40;
    final int number_of_OUTPUT = 40;
         static Matrix[] weights = new Matrix[2];
     static Matrix[] bias = new Matrix[2];
    final boolean sigmoid = true;
    List<Double> prevError;
    SaveGames save ;
    int counter;



    /**
        this constuctor is for playing, it read in the weights and bias text file
     */
    public NeuralNetwork(){
        Matrix[][] inputFile = FileSaveANN.readRel();
        if(inputFile != null) {
            weights = inputFile[0];
            bias = inputFile[1];
            if (DEBUG) System.out.println( weights[0].rows + " " + weights[0].cols + " Old weights 0: " + Arrays.deepToString(weights[0].data));
            if (DEBUG) System.out.println(bias[0].rows + " " + bias[0].cols +" old Bias 0: " + Arrays.deepToString(bias[0].data));
        }else{
            System.out.println("Could not load files, could you check please... sorry.");
            System.exit(1);
        }

    }
    /**
        this constuctor is using for training the ANN
        @param h the number of nodes in hidden layer
     */
    public NeuralNetwork(int h) {
        int[] hidden = { number_of_INPUT,  h, number_of_OUTPUT};
        for(int i = 0; i < weights.length; i++){
            weights[i] = new Matrix(hidden[i+1] , hidden[i]);
            bias[i] = new Matrix(hidden[i+1],1);
        }
    }
//
//    public NeuralNetwork(int h1, int h2, int h3, int h4) {
//        int[] hidden = { number_of_INPUT,  h1,  h2,  h3,  h4, number_of_OUTPUT};
//        for(int i = 0; i < weights.length; i++){
//            weights[i] = new Matrix(hidden[i+1] , hidden[i]);
//            bias[i] = new Matrix(hidden[i+1],1);
//        }
//    }
    /**
        take input and predict the output
        @param X input
        @return output
     */
    public int predict(int[] X) {
        int nnOutput = -100;
        double biggest = 0;
        Matrix output = predictTrain(X)[2];
        for (int j = 0 ; j < X.length; j++){
            if(output.toArray().get(j) > biggest)
            {
                biggest = output.toArray().get(j);
                nnOutput = j;
            }
        }
        return nnOutput;

    }

    public Matrix[] predictTrain(int[] X){
        Matrix[] layer = new Matrix[3];
        layer[0] = Matrix.fromArray(X);

        for(int i = 0 ; i < layer.length-1; i++){

            layer[i+1] = Matrix.multiply(weights[i],layer[i]);
            layer[i+1].add(bias[i]);

            if(sigmoid)
                layer[i+1].sigmoid();
            else
                layer[i+1].reLU();

        }
        return layer;

    }

    public void train(int [] X, int [] Y) {
        Matrix[] layer = predictTrain(X);

        Matrix target = Matrix.fromArray(Y);
        Matrix error = Matrix.subtract(target, layer[2]);
        prevError = error.toArray();

        Matrix[] gradient = new Matrix[2];
        for(int j = gradient.length-1  ; j >=0; j--){
            if (sigmoid)
                gradient[j] = layer[j+1].dsigmoid();
            else
                gradient[j] = layer[j+1].dRelu();
            gradient[j].multiply(error);
            gradient[j].multiply(l_rate);

            Matrix h_T = Matrix.transpose(layer[j]);
            Matrix w_Delta = Matrix.multiply(gradient[j], h_T);
            weights[j].add(w_Delta);
            bias[j].add(gradient[j]);

            Matrix w_T = Matrix.transpose(weights[j]);
            error = Matrix.multiply(w_T, error);
        }
    }

    public boolean fit(int[][]X,int[][]Y,int epochs) {
        for(int i=1;i<epochs+1;i++)
        {
            counter = i;
            int sampleN =  (int)(Math.random() * X.length );
            this.train(X[sampleN], Y[sampleN]);

            if(!sigmoid) {
                if (prevError.get(0) > 1.0E3) {
                    System.out.println("here");
                    return false;
                }
                if(prevError.get(0).isNaN()){
                    return false;
                }
            }
            if(i% 100000 == 0 ){
//                int outPutttt = -100;
//                int nnOutput = -100;
//                double biggest = 0;
                Matrix predict = predictTrain(X[sampleN])[2];
                System.out.println("Itaration:  " + i );
                System.out.println(" Previous error:   " + prevError.get(0));
//                System.out.println("input is :" );
//                for (int j = 0 ; j < X[sampleN].length; j++){
//                    System.out.print("[" + X[sampleN][j] + "]  , " );


//                    System.out.print("[" + Y[sampleN][j] + "]  , " );
//                    if(Y[sampleN][j] ==1 ){
//                        outPutttt = j;
//                    }
//                    if(x.toArray().get(j) > biggest)
//                    {
//                        biggest = x.toArray().get(j);
//                        nnOutput = j;
//                    }
//                }
//                System.out.println("------------------------------------------ ");
//                System.out.println("this is the predict: ");
//                System.out.println("[" + predict.toArray() + "]");

//                System.out.println();
//                System.out.println("output is" + outPutttt);
//                System.out.println("output is" + Y[sampleN]);

//                System.out.println("Neural Network Output" +nnOutput);
//                System.out.println();
            }

        }
        return true;
    }

    protected void trainNeuralNetwork(NeuralNetwork nn, SaveGames save)  {
        this.save = save;
        boolean continiue = false;
        while(!continiue){
            nn = new NeuralNetwork(20);
//            System.out.println(Arrays.deepToString(save.getOutput()));
            continiue = nn.fit(save.getInput(), save.getOutput(), 50000000);
        }
    }




}
