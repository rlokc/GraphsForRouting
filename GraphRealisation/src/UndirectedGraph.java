import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: rlokc
 * Date: 27.10.12
 * Time: 11:53
 */

public class UndirectedGraph {

    private ArrayList<ArrayList<Integer>> weightList;
    private Integer nodeAmount;
    private ArrayList<Node> nodes;
    private Scanner scanner;

    /**
     * Interlayer which takes input and generates <code>weightList</code> and reers to <code>nodeGenerator</code>
     * @throws FileNotFoundException
     */
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// GraphGenerator
//TODO: Make it possible to read all from some file if desired, punching the numbers is quite annoying
//TODO: Make exceptions for tempScanner if file does not exist
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void generateGraph() throws FileNotFoundException {

        scannerRun();          //Running the scanner choose

        System.out.println("Write ammount of nodes in graph:");
        nodeAmount = scanner.nextInt();
        //Debug
        System.out.println(nodeAmount);

        System.out.println("Write matrix:");
        ArrayList<ArrayList<Integer>> matrix = new ArrayList<ArrayList<Integer>>(nodeAmount);
        for (int i = 0; i < nodeAmount; i++) {
            ArrayList<Integer> tmp = new ArrayList<Integer>(nodeAmount);
            tmp.clear();
            for (int j = 0; j < nodeAmount; j++) {
                int l = scanner.nextInt();
                tmp.add(l);
                //Debug
                System.out.print(l);
            }
            System.out.println();
            matrix.add(tmp);
        }
        this.weightList = matrix;
        this.nodeGenerator();

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Method for Generating nodes. Part of a GraphGenerator
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void nodeGenerator() throws FileNotFoundException {
        nodes = new ArrayList<Node>(nodeAmount);
        System.out.print("Write addresses of all networks");
        for (int i = 0; i < nodeAmount; i++) {                  //Making nodeAmount of nodes
            String nodeName = scanner.next();
            nodes.add(new Node(nodeName));
            System.out.println(nodes.get(i).getName());
        }
        for (int i = 0; i < nodeAmount; i++) {                  //Looking through rows (the nodes)
            Node tmpNode1 = nodes.get(i);
            for (int j = 0; j < nodeAmount; j++) {              //Looking through links with tmpNodes
                Node comparableNode = nodes.get(j);
                if (tmpNode1 != comparableNode) {                 //Checking if not comparing to itself, we don't need this
                    int tmpWeigth = weightList.get(i).get(j);
                    if (tmpWeigth != -1) {
                        Edge tmpEdge = new Edge(tmpNode1, comparableNode, tmpWeigth);    //Adding edges to both nodes
                        tmpNode1.addAdjacency(tmpEdge);
                    }
                }
            }
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Dijkstra's Algorithm realisation
//TODO: Currently it doesn't work with "segmented" graphs (some nodes are separated), but who cares really :3
//TODO: Good idea to check if it actually works, but i don't have any graphs here right now
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Base method for Dijkstra methods of graph realisation realisarion
     * @param startNode node as a start point of route
     * @param targetNode node as destination point (suddenly!)
     */
    private void dijkstraMain(Node startNode, Node targetNode) {
        startNode.setMark(0);
        ArrayList<Node> pathTo = new ArrayList<Node>();
        //Setting initial path
        pathTo.add(startNode);
        startNode.setPathTo(pathTo);
        startNode.isPassed = true;
        Node currentNode = startNode;
        boolean isFinished = false;

        while (!isFinished) {
            for (Edge tmpEdge : currentNode.getAdjacencies()) {
                Node endNode = tmpEdge.getEndNode();
                if (!endNode.isPassed) {
                    //Copying pathTo.
                    ArrayList<Node> pathToEndNode = new ArrayList<Node>();
                    for (Node tmpNode : currentNode.getPathTo()){
                        pathToEndNode.add(tmpNode);
                    }
                    pathToEndNode.add(endNode);
                    //Calculating mark
                    int comparableMark = currentNode.getMark() + tmpEdge.getWeight();
                    if (comparableMark < endNode.getMark()) {
                        //Replacing if new mark is less
                        endNode.setMark(comparableMark);
                        //Changing pathTo
                        endNode.setPathTo(pathToEndNode);
                    }
                }
            }
            //Our node is now "out", we don't need to visit it later
            currentNode.isPassed = true;
            int nextNodeMark = Integer.MAX_VALUE;
            //Checking if all nodes are out
            isFinished = true;
            for (Node checkNode : this.nodes) {
                if (!checkNode.isPassed) {
                    isFinished = false;
                    //Finding unpassed node with the least mark
                    if (checkNode.getMark() < nextNodeMark) {
                        //and making it the next node
                        currentNode = checkNode;
                        nextNodeMark = checkNode.getMark();
                    }
                }
            }
        }
//Debug
        System.out.println("YOUR BUNNY WROTE: ");
        System.out.print(targetNode.getMark());
//Writing path
        System.out.println();
        ArrayList<Node> resultPath = targetNode.getPathTo();
        for (Node pathNode : resultPath) {
            System.out.print(pathNode.getName());
            if (pathNode != targetNode) {
                System.out.print(" -> ");
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Master for running desired algorithms
//TODO:Make it array-based, would be more extendable this way
//TODO:Use the bloody nodenames you have! (Though it'll make it all a lil bit slower)
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void algorithmMaster() {
        System.out.println("Write a number of a desired algorithm:");
        System.out.println("0 for Dijkstra's");
        int choice = scanner.nextInt();
        //Debug
        System.out.println(choice);
        switch (choice) {
            case 0: {  //Dijkstra
                System.out.println("Write indexes of starting and target nodes:");
                Node startNode = this.nodes.get(scanner.nextInt());
                Node endNode = this.nodes.get(scanner.nextInt());
                dijkstraMain(startNode, endNode);
                break;
            }
            default:
                break;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//SCANNER CLOSING
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void closeScanner() {
        scanner.close();
    }

    public void scannerRun() throws FileNotFoundException {
        scanner = new Scanner(System.in);
        System.out.println("0 for console read \nor path for file read: ");
        String result = scanner.next();
        if (!result.equals("0")) {
            //TODO:Read the string
            scanner = new Scanner(new File(result));
        }
    }
}



