import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

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
public class Graph {

    private ArrayList<ArrayList<Integer>> weightList;  //Weight of edges between nodes
    private ArrayList<ArrayList<Integer>> proximityList;  //Proximity between nodes
    private Integer nodeAmmount;
    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges = new ArrayList<Edge>();
    private Scanner scanner;
//For Floyd-Warshall
    private ArrayList<ArrayList<Integer>> pathMatrix = new ArrayList<ArrayList<Integer>>();
    private ArrayList<ArrayList<Integer>> stepMatrix = new ArrayList<ArrayList<Integer>>();


    /**
     * Interlayer which takes input and generates global matrix <code>weightList</code>.
     * It also calls to <code>nodeGenerator</code>.
     *
     * @throws FileNotFoundException
     */
    public void generateGraph() throws FileNotFoundException {

        scannerRun();          //Running the scanner choosing dialogue

        System.out.println("Write ammount of nodes in graph:");
        nodeAmmount = scanner.nextInt();
        //Debug
        System.out.println(nodeAmmount);

        System.out.println("Write matrix:");
        ArrayList<ArrayList<Integer>> matrix = new ArrayList<ArrayList<Integer>>(nodeAmmount);
        ArrayList<ArrayList<Integer>> proxmatrix = new ArrayList<ArrayList<Integer>>(nodeAmmount);
        for (int i = 0; i < nodeAmmount; i++) {
            ArrayList<Integer> tmp = new ArrayList<Integer>(nodeAmmount);
            ArrayList<Integer> proxtmp = new ArrayList<Integer>(nodeAmmount);
            tmp.clear();
            proxtmp.clear();
            for (int j = 0; j < nodeAmmount; j++) {
                int l = scanner.nextInt();
                //Adding to proximityList
                int proxl = 0;
                if (l != -1) proxl = 1;
                proxtmp.add(proxl);
                tmp.add(l);
                //Debug
                System.out.print(l);
            }
            System.out.println();
            matrix.add(tmp);
            proxmatrix.add(proxtmp);
        }
        this.weightList = matrix;
        this.proximityList = proxmatrix;
        this.nodeGenerator();
    }

    /**
     * This method is used to generate nodes according to user input. Called in <code>GraphGenerator</code> only.
     * Edge creation is also realised here.
     *
     * @throws FileNotFoundException
     */
    public void nodeGenerator() throws FileNotFoundException {
        nodes = new ArrayList<Node>(nodeAmmount);
        System.out.print("Write addresses of all networks");
        for (int i = 0; i < nodeAmmount; i++) {                  //Making nodeAmmount of nodes
            String nodeName = scanner.next();
            nodes.add(new Node(nodeName));
            System.out.println(nodes.get(i).getName());
        }
        for (int i = 0; i < nodeAmmount; i++) {                  //Looking through rows (the nodes)
            Node tmpNode1 = nodes.get(i);
            for (int j = 0; j < nodeAmmount; j++) {              //Looking through links with tmpNodes
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
    private void dijkstraSearch(Node currentNode, Node endNode) {
        currentNode.setMark(0);
        ArrayList<Node> pathTo = new ArrayList<Node>();
        //Setting initial path
        pathRefresh(pathTo, currentNode);
        currentNode.isPassed = true;
        boolean isFinished = false;

        while (!isFinished) {
            for (Edge tmpEdge : currentNode.getAdjacencies()) {
                Node targetNode = tmpEdge.getEndNode();
                if (!targetNode.isPassed) {
                    //Calculating mark
                    int comparableMark = currentNode.getMark() + tmpEdge.getWeight();
                    if (comparableMark < targetNode.getMark()) {
                        //Replacing if new mark is less
                        targetNode.setMark(comparableMark);
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
        pathWrite(endNode);
    }

    /**
     * Base method for Bellman-Ford's methods of search in graph.
     *
     * @param startNode node as a start point of route
     */
    void bellman_FordSearch(Node startNode, Node endNode) {
        startNode.setMark(0);
        ArrayList<Node> pathTo = new ArrayList<Node>();
        //Setting initial path
        pathRefresh(pathTo, startNode);
        for (int i = 0; i < nodeAmmount - 1; i++) {
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
        pathWrite(endNode);
    }

    void floyd_WarshallSearch(int startNodeIndex, int endNodeIndex) {
        //Making a deep copy of weightlist
        for (int i = 0; i < nodeAmmount; i++) {
            ArrayList<Integer> tmp = new ArrayList<Integer>(nodeAmmount);
            ArrayList<Integer> steptmp = new ArrayList<Integer>(nodeAmmount);
            for (int j = 0; j < nodeAmmount; j++) {
                if (i == j)
                    //Changing self-weights to 0
                    tmp.add(0);
                else
                    //Copying weight from weightlist, if there IS an edge between the nodes
                    tmp.add(weightList.get(i).get(j));
                //Filling stepMatrix with zeroes
                steptmp.add(0);
            }
            pathMatrix.add(tmp);
            stepMatrix.add(steptmp);
        }
        for (int k = 0; k < nodeAmmount; k++)
            for (int i = 0; i < nodeAmmount; i++)
                for (int j = 0; j < nodeAmmount; j++) {
                    //Continue if there's no edge between nodes
                    if ((pathMatrix.get(i).get(k) == -1) || (pathMatrix.get(k).get(j) == -1))
                        continue;
                    int curWeight = pathMatrix.get(i).get(j);
                    int compWeight = pathMatrix.get(i).get(k) + pathMatrix.get(k).get(j);
                    if (curWeight == -1) {
                        pathMatrix.get(i).set(j, compWeight);
                        stepMatrix.get(i).set(j, k);
                    } else if (compWeight < curWeight) {
                        pathMatrix.get(i).set(j, compWeight);
                        stepMatrix.get(i).set(j, k);
                    }
                }
        //Debug
        //Printing finished matrix
        for (int i = 0; i < nodeAmmount; i++) {
            for (int j = 0; j < nodeAmmount; j++) {
                System.out.print(pathMatrix.get(i).get(j));
                System.out.print(' ');
            }
            System.out.println();
        }
        //Printing stepmatrix
        System.out.println();
        for (int i = 0; i < nodeAmmount; i++) {
            for (int j = 0; j < nodeAmmount; j++) {
                System.out.print(stepMatrix.get(i).get(j));
                System.out.print(' ');
            }
            System.out.println();
        }
        System.out.print(WarshallPathRecovery(startNodeIndex,endNodeIndex));
    }
//Recovering paths from the matrix after Floyd-Warshall
    String WarshallPathRecovery (int i,int j){
        int k = stepMatrix.get(i).get(j);
        if (pathMatrix.get(i).get(j)==-1)
            return "No path";
        if (i==j){
            return nodes.get(i).getName();
        }
        else
            return WarshallPathRecovery(i,k)+nodes.get(j).getName();
    }

//Refreshing PathTo

    /**
     * Service method for search algorithms.
     * We use it to change an old path to the specified node to a new one. To shorter one, in most cases.
     *
     * @param pathTo path that we are going to write as node parameters
     * @param node   node which path is being changed
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
        for (Node pathNode : targetNode.getPathTo()) {
            System.out.print(pathNode.getName());
            if (pathNode != targetNode) {
                System.out.print(" -> ");
            }
        }
    }

    //TODO: find a way to integrate inside the normal Dijkstra
    void proximityDijkstra(Node startNode) {
        startNode.setProxMark(0);
        boolean isFinished = false;
        startNode.isPassed = true;
        Node currentNode = startNode;

        while (!isFinished) {
            for (Edge tmpEdge : currentNode.getAdjacencies()) {
                Node endNode = tmpEdge.getEndNode();
                if (!endNode.isPassed) {
                    //Calculating mark
                    int comparableProxMark = currentNode.getProxMark() + 1;
                    if (comparableProxMark < endNode.getProxMark()) {
                        //Replacing if new mark is less
                        endNode.setMark(comparableProxMark);
                    }
                }
            }
            //Our node is now "out", we don't need to visit it later
            currentNode.isPassed = true;
            int nextNodeProxMark = Integer.MAX_VALUE;
            //Checking if all nodes are out
            isFinished = true;
            for (Node checkNode : this.nodes) {
                if (!checkNode.isPassed) {
                    isFinished = false;
                    //Finding unpassed node with the least mark
                    if (checkNode.getProxMark() < nextNodeProxMark) {
                        //and making it the next node
                        currentNode = checkNode;
                        nextNodeProxMark = checkNode.getProxMark();
                    }
                }
            }
        }
    }

    /**
     * Master for running desired algorithms.
     */
    //TODO:Make it array-based, would be more extendable this way
    //TODO:Use the bloody nodenames you have! (Though it'll make it all a lil' bit slower)
    void algorithmMaster() {

        System.out.println("Write a number of a desired algorithm:");
        System.out.println("0 for Dijkstra's\n1 for Bellman-Ford\n2 for Floyd-Warshall");
        int choice = scanner.nextInt();
        //Debug
        System.out.println(choice);
        System.out.println("Write indexes of starting and target nodes:");
        int startNodeIndex = scanner.nextInt();
        int endNodeIndex = scanner.nextInt();
        //Verbose debug
        System.out.print(startNodeIndex);
        System.out.println(endNodeIndex);
        Node startNode = this.nodes.get(startNodeIndex);
        Node endNode = this.nodes.get(endNodeIndex);
        switch (choice) {
            case 0: {  //Dijkstra
                dijkstraSearch(startNode, endNode);
                break;
            }
            case 1: { //Bellman-Ford
                bellman_FordSearch(startNode, endNode);
                break;
            }
            case 2: { //Floyd-Warshall
                floyd_WarshallSearch(startNodeIndex,endNodeIndex);
                break;
            }
            default:
                break;

        }
        pathWrite(endNode);
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
            scanner = new Scanner(new File(result));
        }
    }
}



