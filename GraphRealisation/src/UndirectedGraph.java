import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Math;

/**
 * Created with IntelliJ IDEA.
 * User: rlokc
 * Date: 27.10.12
 * Time: 11:53
 * OHMYGOD MY CODE IS HOWWWWWWWWWWWWWWIBLE
 */

/**
 * Graph as a whole thing.
 */
public class UndirectedGraph {

    private ArrayList<ArrayList<Integer>> weightList;
    private Integer nodeAmount;
    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges = new ArrayList<Edge>();
    private Scanner scanner;

    /**
     * Interlayer which takes input and generates global matrix <code>weightList</code>.
     * It also calls to <code>nodeGenerator</code>.
     *
     * @throws FileNotFoundException
     */
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

    /**
     * This method is used to generate nodes according to user input. Called in <code>GraphGenerator</code> only.
     * Edge creation is also realised here.
     *
     * @throws FileNotFoundException
     */
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
                        edges.add(tmpEdge);
                    }
                }
            }
        }
    }

    /**
     * Base method for Dijkstra's methods of graph routing.
     *
     * @param startNode node as a start point of route
     */
    private void dijkstraMain(Node startNode) {
        startNode.setMark(0);
        ArrayList<Node> pathTo = new ArrayList<Node>();
        //Setting initial path
        pathRefresh(pathTo, startNode);
        startNode.isPassed = true;
        Node currentNode = startNode;
        boolean isFinished = false;

        while (!isFinished) {
            for (Edge tmpEdge : currentNode.getAdjacencies()) {
                Node endNode = tmpEdge.getEndNode();
                if (!endNode.isPassed) {
                    //Calculating mark
                    int comparableMark = currentNode.getMark() + tmpEdge.getWeight();
                    if (comparableMark < endNode.getMark()) {
                        //Replacing if new mark is less
                        endNode.setMark(comparableMark);
                        //Changing pathTo
                        pathRefresh(currentNode.getPathTo(), endNode);
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
    }

    /**
     * Base method for Bellman-Ford's methods of search in graph.
     *
     * @param startNode node as a start point of route
     */
    void bellmanFordSearch(Node startNode) {
        startNode.setMark(0);
        ArrayList<Node> pathTo = new ArrayList<Node>();
        //Setting initial path
        pathRefresh(pathTo, startNode);
        for (int i = 0; i < nodeAmount - 1; i++) {
            for (Edge edge : edges) {
                Node aNode = edge.getStartNode();
                Node bNode = edge.getEndNode();
                if (aNode.getMark() < Integer.MAX_VALUE) {
                    int bMark = aNode.getMark() + edge.getWeight();
                    if (bMark < bNode.getMark()) {
                        //Replacing if new mark is less
                        bNode.setMark(bMark);
                        //Changing pathTo
                        pathRefresh(aNode.getPathTo(), bNode);
                    }
                }
            }
        }
    }
//Refreshing PathTo

    /**
     * Service method for search algorithms.
     * We use it to change an old path to the specified node to some new. To shorter one, in most cases.
     *
     * @param pathTo path that we are going to write as node parameters
     * @param node   node we're changing path to
     */
    void pathRefresh(ArrayList<Node> pathTo, Node node) {
        //Copying previous node's path
        ArrayList<Node> resultPath = (ArrayList<Node>) pathTo.clone();
        //Adding our node to it
        if (!pathTo.contains(node)) resultPath.add(node);
        node.setPathTo(resultPath);
    }

    void pathWrite(Node targetNode) {
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

    /**
     * Master for running desired algorithms.
     */
    //TODO:Make it array-based, would be more extendable this way
    //TODO:Use the bloody nodenames you have! (Though it'll make it all a lil bit slower)
    void algorithmMaster() {

        System.out.println("Write a number of a desired algorithm:");
        System.out.println("0 for Dijkstra's\n1 for Bellman-Ford");
        int choice = scanner.nextInt();
        //Debug
        System.out.println(choice);
        switch (choice) {
            case 0: {  //Dijkstra
                System.out.println("Write indexes of starting and target nodes:");
                Node startNode = this.nodes.get(scanner.nextInt());
                Node endNode = this.nodes.get(scanner.nextInt());
                dijkstraMain(startNode);
                pathWrite(endNode);
                break;
            }
            case 1:  //Bellman-Ford
                System.out.println("Write indexes of starting and target nodes:");
                Node startNode = this.nodes.get(scanner.nextInt());
                Node endNode = this.nodes.get(scanner.nextInt());
                bellmanFordSearch(startNode);
                pathWrite(endNode);
                break;
            default:
                break;

        }
    }

    /**
     * Guess what! It's just closing one little thread.
     */
    public void closeScanner() {
        scanner.close();
    }

    /**
     * Asks user to input some path to input file and gives that to <code>scanner</code>.
     *
     * @throws FileNotFoundException
     */
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



