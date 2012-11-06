import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: rlokc
 * Date: 27.10.12
 * Time: 11:53
 * Реализация ненаправленого графа. Граф хранится в матрице смежности, где на пересечениях указан их вес.
 * To change this template use File | Settings | File Templates.
 */
public class UndirectedGraph {

    private ArrayList<ArrayList<Integer>> weightList;
    private Integer nodeAmount;
    private ArrayList<Node> nodes;

    /**
     * Nodes list getter
     *
     * @return List of nodes
     */
    public ArrayList<Node> getNodesList() {
        return nodes;
    }

    /**
     * Node amount getter
     *
     * @return Node amount
     */
    public Integer getNodeAmount() {
        return nodeAmount;
    }

    /**
     * Node amount setter
     *
     * @param nodeAmount Node amount to set
     */
    public void setNodeAmount(Integer nodeAmount) {
        this.nodeAmount = nodeAmount;
    }

    /**
     * Weight list setter
     *
     * @param weightList Weight list to set
     */
    public void setWeightList(ArrayList<ArrayList<Integer>> weightList) {
        this.weightList = weightList;
    }

    /**
     * Node generator
     */
    public void nodeGenerator() {
        nodes = new ArrayList<Node>(nodeAmount);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write addresses of all networks");
        for (int i = 0; i < nodeAmount; i++) {                  //Making nodeAmount of nodes
            Node tmpNode = new Node(scanner.next());
            nodes.add(tmpNode);
        }
        for (int i = 0; i < nodeAmount; i++) {                  //Looking through rows (the nodes)
            Node tmpNode1 = nodes.get(i);
            for (int j = 0; j < nodeAmount; j++) {              //Looking through links with tmpNodes
                Node comparableNode = nodes.get(j);
                if (tmpNode1 != comparableNode) {                 //Checking if not comparing to itself, we don't need this
                    int tmpWeigth = weightList.get(i).get(j);
                    if (tmpWeigth != 0) {
                        Edge tmpEdge = new Edge(tmpNode1, comparableNode, tmpWeigth);    //Adding edges to both nodes
                        tmpNode1.addAdjacency(tmpEdge);
                        //Edge compEdge = new Edge(comparableNode, tmpNode1, tmpWeigth);  //TODO: make them add each-other to themselves
                        //comparableNode.addAdjacency(compEdge);
                    }
                }
            }
        }
    }

    /**
     * Don't know
     *
     * @return ???
     */
    public UndirectedGraph generateGraph() {
        Scanner scanner = new Scanner(System.in);
        int nodeAmount = scanner.nextInt();
        UndirectedGraph graph = new UndirectedGraph();
        ArrayList<ArrayList<Integer>> matrix = new ArrayList<ArrayList<Integer>>(nodeAmount);
        for (int i = 0; i < nodeAmount; i++) {
            ArrayList<Integer> tmp = new ArrayList<Integer>(nodeAmount);
            tmp.clear();
            for (int j = 0; j < nodeAmount; j++) {
                tmp.add(scanner.nextInt());
            }
            matrix.add(tmp);
        }
        graph.setWeightList(matrix);
        graph.setNodeAmount(nodeAmount);
        graph.nodeGenerator();
        scanner.close();
        return graph;

    }

//Fuck Stas's searcher, let's do our own!
//TODO: Currently it doesn't work with "segmented" graphs (some nodes are separated), but who cares really :3
//TODO: Good idea to check if it actually works, but i don't have any graphs here right now

    void dijkstraSearch(Node startNode, Node targetNode) {
        startNode.setMark(0);
        startNode.isPassed = true;
        Node currentNode = startNode;
        Node nextNode = null;                            //Next node we are going to visit
        int nextNodeMark = Integer.MAX_VALUE;
        boolean isFinished = false;

        while (!isFinished) {
            for (Edge tmpEdge : currentNode.getAdjacencies()) {  //Looking through each connection in the node
                Node endNode = tmpEdge.getEndNode();             //Ending node of current edge
                if (!endNode.isPassed) {                         //If this node wasn't passed early
                    int tmpWeigth = tmpEdge.getWeight();           //Weight of current edge
                    int addingMark = (startNode.getMark() < Integer.MAX_VALUE) ? startNode.getMark() + tmpWeigth : tmpWeigth; //This mark we will compare with the current endnode mark
                    int endNodeMark = endNode.getMark();            //The mark of a current end node
                    if (addingMark < endNodeMark) {
                        endNode.setMark(addingMark);               //Changing endnodemark to adding mark if it was less
                    }
                    if (endNode.getMark() < nextNodeMark) {
                        nextNode = endNode;
                    }
                }
            }
            currentNode.isPassed = true;                       //Our node is now "red", we don't need to visit it
            currentNode = nextNode;
            isFinished = true;                                 //Checking if all nodes are out
            for (Node checkNote : this.getNodesList()) {
                if (!checkNote.isPassed) isFinished = false;
            }
        }
    }
}



