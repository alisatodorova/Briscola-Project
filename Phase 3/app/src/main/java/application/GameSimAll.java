package application;

import AI.LargeBrain.RandomHand;

import java.io.PrintWriter;

public class GameSimAll {
    public static void main(String[] args, boolean fixed) {
        GameSimulator.simulations = 10000;
        PrintWriter out = GameSimulator.createWinnerFile();
        for (int dT = 7; dT > 0; dT--) {
            for (int dB = 7; dB > 0; dB--) {
                if(dB == 4 || dT == 4 || dB == 5 || dT == 5){
                    for (int i = 0; i < 4; i++){
                        System.out.println("diffBottom:" + dB + ", diffTop: " + dT + ", Cheat: " + i);
                        RandomHand.cheatingFixed = i;
                        GameSimulator.diffBottom = dB;
                        GameSimulator.diffTop = dT;
                        // System.out.println("Accepted: bot: " + GameSimulator.diffBottom + " top: " + GameSimulator.diffTop + " RH: " + RandomHand.cheatingFixed);
                        GameSimulator.main(args, fixed, false);
                    }
                }else {
                    System.out.println("diffBottom:" + dB + ", diffTop: " + dT);
                    GameSimulator.diffBottom = dB;
                    GameSimulator.diffTop = dT;
                    GameSimulator.main(args, fixed, false);
                }
            }
        }
//        for (int dT = 1; dT < 8; dT++) {
//            if (dT == 5) continue;
//
//            System.out.println("diffBottom:" + 7 + ", diffTop: " + dT);
//            GameSimulator.diffBottom = 7;
//            GameSimulator.diffTop = dT;
//            GameSimulator.main(args, fixed, false);
//        }
//        for (int dB = 1; dB < 8; dB++){
//            System.out.println("diffBottom:" + dB + ", diffTop: " + 7);
//            GameSimulator.diffBottom = dB;
//            GameSimulator.diffTop = 7;
//            GameSimulator.main(args, fixed, false);
//        }
//        GameSimulator.simulations = 1;
//        GameSimulator.diffBottom = 1;
//        GameSimulator.diffTop = 1;
//        GameSimulator.main(args, fixed, true);
        assert out != null;
        out.close();
        System.out.println("Closed");
    }
}