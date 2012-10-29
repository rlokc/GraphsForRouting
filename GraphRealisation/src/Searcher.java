import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: rlokc
 * Date: 29.10.12
 * Time: 10:52
 */

public class Searcher {

    private ArrayList<Integer> ways = new ArrayList<Integer>();
    private int numberOfNodes;
    private ArrayList<ArrayList<Integer>> waysMatrix = new ArrayList<ArrayList<Integer>>();

    //Constructor. Is supposed to fill the matrix
    Searcher(int numberOfNodes) {
        this.numberOfNodes = numberOfNodes;
        ArrayList<Integer> tmp = new ArrayList<Integer>();

        for (int i = 0; i <= this.numberOfNodes - 1; i++) {   //Yep, this's crunch.
            for (int j = 0; j <= this.numberOfNodes - 1; j++) {
                tmp.add(Integer.MAX_VALUE);
            }
            waysMatrix.add(tmp);
        }
    }

    public void doSearch(ArrayList<Node> inputList, int indexOfFirstNode) {
        int counter = 0;
        //This shit fill the matrix of ways with real numbers. (without summing)
        for (int i = 0; i < numberOfNodes; i++) {
            if (i == indexOfFirstNode) {
                for (int k = 0; k < numberOfNodes - 1; k++) {
                    int j = inputList.get(indexOfFirstNode).getEdgeWeigth(k);
                    if (j < waysMatrix.get(i).get(k)) {
                        if (k == numberOfNodes - 2) {
                            System.out.println(" " + j);
                        } else {
                            System.out.print(" " + j);
                        }
                    }
                }
            }
        }
        //Here should be recursion
        doSearch(inputList, matrixCheck(indexOfFirstNode));
    }
    //We're looking for the shortest way here.
    private int matrixCheck(int indexInput) {
        int j = waysMatrix.get(indexInput).get(0);
        for (int k = 0; k < numberOfNodes; k++) {
            if (j > waysMatrix.get(indexInput).get(k)) {
                j = waysMatrix.get(indexInput).get(k);
            }
        }
        return j;
    }
}

