import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: rlokc
 * Date: 29.10.12
 * Time: 10:52
 * Updated 30.09.12 at 15:25 by alexkorkod  
 */

public class Searcher {
    /** Well... We don't need it
    *private ArrayList<Integer> ways = new ArrayList<Integer>();
    */
    /** Total value of nodes in graph
    */
    private int numberOfNodes;
    private ArrayList<ArrayList<Integer>> waysMatrix = new ArrayList<ArrayList<Integer>>();

    //Constructor. Is supposed to fill the matrix
    Searcher(int numberOfNodes) {
        this.numberOfNodes = numberOfNodes;
        /** This array contents temporal values of weights 
        *   Here the system breaks 'couse it's the only object in waysMatrix            
        */
        ArrayList<Integer> tmp = new ArrayList<Integer>();

        for (int i = 0; i < this.numberOfNodes; i++) {   //Yep, this's crunch.
            for (int j = 0; j < this.numberOfNodes; j++) { 
                tmp.add(Integer.MAX_VALUE);
            }
            waysMatrix.add(tmp);
        }

    }

    public void doSearch(ArrayList<Node> inputList, int indexOfFirstNode) {
        /**This shit is supposed to fill the matrix of ways with real numbers. (without summing)
        * Ok, it does a lot of excessive stuff.
        * And doesnt work 'couse of the only one object(array) for the every field in tge whole matrix.
        * Maybe.
        */
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

        /**Here should be recursion
        * We must overload this method, i guess;
        * But it'll double our code
        */
        doSearch(inputList, matrixCheck(indexOfFirstNode));

    }
    //We're looking for the shortest way here.
    private int matrixCheck(int indexInput) {
        int j = waysMatrix.get(indexInput).get(0);

        //This stuff should be clear for you
        for (int k = 0; k < numberOfNodes; k++) {
            if (j > waysMatrix.get(indexInput).get(k)) {
                j = waysMatrix.get(indexInput).get(k);
            }
        }

        //I really don't know what this method returns.
        return j;
    }
}



