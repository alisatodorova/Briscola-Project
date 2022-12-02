package AI.LargeBrain;
import application.Card;
import application.GameLogic;
import org.checkerframework.checker.units.qual.A;
import java.util.Random;

import java.util.*;

public class BigBrain extends application.Player{

    private static final boolean DEBUG = true;
    Tree tree;
    ArrayList<Card> path;
    GameLogic game = new GameLogic();
    ArrayList te = new ArrayList();
    boolean react;
    private final static int determinization = 30;

    public BigBrain(int id){
        super("AI",id);
    }
    public BigBrain(){
        super("AI", 1);
    }

    public int oldMiniMax(BigState initialState, Card onFloor){
        ArrayList <TreeNode> sameUtility = new ArrayList<>();
        Random rand = new Random();
        path = new ArrayList<>();
        TreeNode<BigState> root = new TreeNode<>(initialState, null);
        tree = new Tree<TreeNode>(root);
        react = false;
        if(onFloor != null){
            react = true;
            path.add(onFloor);
        }
        root.setUtility(maxValue(root));
//        print(root);
        for (int i = 0; i < root.getChildren().size(); i++) {
            if (root.getChild(i).getUtility() == root.getUtility()) {
                sameUtility.add(root.getChild(i)); //saves all the same moves
            }
        }
        if (sameUtility.size() == 1) {
            return sameUtility.get(0).getAction();
        } else {
            return sameUtility.get(rand.nextInt(sameUtility.size())).getAction();
        }

    }


    public int miniMax(BigState initialState, Card onFloor, Card[] humanHand) {
        if(initialState.getRound() == 20 ){

            for(int i = 0 ; i < initialState.getAiHand().length; i ++){
                if(initialState.getAiHand()[i] != null){
                    return i;
                }
            }

        }
        int[] count = new int[3];
        for (int i = 0; i < determinization; i++) {
            if(initialState.aiTurn){ //generates the random hand
                initialState.setHumanHand(RandomHand.randomHand(initialState.getCardsLeft(),3, humanHand));
            }else{
                initialState.setHumanHand(RandomHand.randomHand(initialState.getCardsLeft(),2, humanHand));
            }
            count[oldMiniMax(initialState, onFloor)]++;

        }
        int bestMove = 0;
        for (int k=1; k<count.length; k++){
            if(count[bestMove] < count[k]){
                bestMove = k;
            }
        }

        return bestMove;
    }



    public int maxValue(TreeNode<BigState> node){
        if(node.getState().terminalTest()){
            node.setUtility(node.getState().calUtility(path, react));
            return node.getUtility();
        }
        node.setUtility(Integer.MIN_VALUE);
            for (int a = 0; a < node.getState().getAiHand().length; a++) {
                if (node.getState().getAiHand()[a] != null) {
                    node.addChildren(node.getState().result(a));
                    node.getChild().setAction(a);
                    path.add(node.getState().getAiHand()[a]);
                    node.setUtility(Math.max(node.getUtility(), minValue(node.getChild())));
                    path.remove(path.size()-1);

                }

            }
        return node.getUtility();
    }

    public int minValue(TreeNode<BigState> node ) {
        if (node.getState().terminalTest()) {
            node.setUtility(node.getState().calUtility(path,react));
            return node.getUtility();
        }
        node.setUtility(Integer.MAX_VALUE);
            for(int a = 0 ; a < node.getState().getHumanHand().length; a++ ){
                if (node.getState().getHumanHand()[a] != null) {
                    node.addChildren(node.getState().result(a));
                    node.getChild().setAction(a);
                    path.add(node.getState().getHumanHand()[a]);
                    node.setUtility(Math.min(node.getUtility(), maxValue(node.getChild())));
                    path.remove(path.size()-1);
                }
            }

        return node.getUtility();
    }

    /**
     * print a tree in breath-first search
     * @param n root of the tree
     */
    private void print(TreeNode<BigState> n) {
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.add(n);
        while (!queue.isEmpty()) {
            TreeNode temp = queue.poll();
            System.out.print(temp.getUtility() + " ->");
            for(int i =0; i <temp.getChildren().size() ; i++ ){
                if (temp.getChild(i) != null) {
                    queue.add(temp.getChild(i));
                }
            }
        }
    }


}