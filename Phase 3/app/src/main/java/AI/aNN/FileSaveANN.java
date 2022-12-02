package AI.aNN;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class FileSaveANN {
    static String weightsFile = "weightsANNSigmoid3.txt";
    static String biasFile = "biasANNSigmoid3.txt";
    // as BackUp
//    static String weightsFile = "backUP-ANN-Data/weightsANNSigmoid3.txt";
//    static String biasFile = "backUP-ANN-Data/biasANNSigmoid3.txt";
    static boolean DEBUG = false;

    protected static void saveFinal(Matrix[] weightMatrices, Matrix[] biasMatrices){

        PrintWriter outWeights;
        PrintWriter outBias;
        try {
            outWeights = new PrintWriter(weightsFile);
            outBias = new PrintWriter(biasFile);
            //outWeights.println("HEIGHT = " + output.length);
            //out.println("WIDTH = " + output[0].length);
            for (Matrix tmpMatrix : weightMatrices) {
                for (int j = 0; j < tmpMatrix.data.length; j++) {
                    for (int k = 0; k < tmpMatrix.data[j].length; k++) {
                        outWeights.print(tmpMatrix.data[j][k] + " ");
                    }
                    outWeights.println();
                }
                outWeights.println();
            }
            for (Matrix tmpMatrix : biasMatrices) {
                for (int j = 0; j < tmpMatrix.data.length; j++) {
                    for (int k = 0; k < tmpMatrix.data[j].length; k++) {
                        outBias.print(tmpMatrix.data[j][k] + " ");
                    }
                    outBias.println();
                }
                outBias.println();
            }


            outWeights.close();
            outBias.close();
        } catch (Exception e){
            System.out.println();
            e.printStackTrace();
        }
    }


    protected static void saveFinalD(double[][] output, String outputFile){
        PrintWriter out;
        try {
            out = new PrintWriter(outputFile);
            out.println("HEIGHT = " + output.length);
            out.println("WIDTH = " + output[0].length);
            for(int i = 0; i < output.length; i++){
                for (int j = 0; j < output[i].length; j++){
                    out.print(output[i][j] + " ");
                }
                out.println();
            }
            out.close();
        } catch (Exception e){
            System.out.println("Safe Error--- saveFinalD of ANN");
            e.printStackTrace();
        }
    }

    protected static Matrix[][] readRel(){
        try{
            File fileWeights = new File(weightsFile);
            Scanner weightsReader = new Scanner(fileWeights);
            File fileBias = new File (biasFile);
            Scanner biasReader = new Scanner(fileBias);
            if(DEBUG) System.out.println("Bias = " + biasReader.hasNextLine());
            if(DEBUG) System.out.println("Weights = " + weightsReader.hasNextLine());

            ArrayList<Matrix> weightsAll = new ArrayList<>();
            ArrayList<Matrix> biasAll = new ArrayList<>();
//            int[][] input;
            ArrayList<double[]> matrixPreBuild = new ArrayList<double[]>();
            while (weightsReader.hasNextLine()){
                String data = weightsReader.nextLine();
                if(data.equals("")) {
                    Matrix tmpMatrix = new Matrix(0,0);
                    tmpMatrix.data = matrixPreBuild.toArray(new double[0][0]);
                    tmpMatrix.updateRowCol();
                    weightsAll.add(tmpMatrix);
                    matrixPreBuild = new ArrayList<double[]>();
                }else {
                    String[] line = data.split(" ");
                    double[] matrixRow = new double[line.length];
                    for (int i = 0; i < line.length; i++) {
                        matrixRow[i] = Double.parseDouble(line[i]);
                    }
                    matrixPreBuild.add(matrixRow);
                }
            }

            ArrayList<double[]> biasPreBuild = new ArrayList<double[]>();
            while (biasReader.hasNextLine()){
                String data = biasReader.nextLine();
                if(data.equals("")) {
                    Matrix tmpMatrix = new Matrix(0, 0);
                    tmpMatrix.data = biasPreBuild.toArray(new double[0][0]);
                    tmpMatrix.updateRowCol();
                    biasAll.add(tmpMatrix);
                    biasPreBuild = new ArrayList<double[]>();
                }else {
                    String[] line = data.split(" ");
                    double[] matrixRow = new double[line.length];
                    for (int i = 0; i < line.length; i++) {
                        matrixRow[i] = Double.parseDouble(line[i]);
                    }
                    biasPreBuild.add(matrixRow);
                }
            }

            //            input = new double[height][width];
////            input = new int[height][width];
//            for (int i = 0; i < height; i++){
//                if (myReader.hasNextLine()) {
//                    String[] row = myReader.nextLine().split(" ");
//                    for (int j = 0; j < width; j++) {
//                        input[i][j] = Double.parseDouble(row[j]);
//                    }
//                }
//            }

            Matrix[] biasArr = biasAll.toArray(new Matrix[0]);
            Matrix[] weightsArr = weightsAll.toArray(new Matrix[0]);
            Matrix[][] output = new Matrix[][] {weightsArr, biasArr};
            return output;
        } catch (Exception e){
            System.out.println("ERROR - File not foound to read in the ANN Data");
        }
        return null;
    }
}
