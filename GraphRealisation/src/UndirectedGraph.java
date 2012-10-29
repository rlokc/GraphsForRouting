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

    private ArrayList<ArrayList<Integer>> weigthList;
    private Integer nodeAmount;
    private ArrayList<Node> nodes;
    //**********************************
    public ArrayList<Node> getNodesList() {
        return nodes;
    }

    public Integer getNodeAmount() {
        return nodeAmount;
    }
    //********************************8
    public void setNodeAmount(Integer nodeAmount) {
        this.nodeAmount = nodeAmount;
    }


    public void setWeigthList(ArrayList<ArrayList<Integer>> weigthList) {
        this.weigthList = weigthList;
    }

    //NODES GENERATOR
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
                    int tmpWeigth = weigthList.get(i).get(j);
                    if (tmpWeigth != 0) {
                        Edge tmpEdge = new Edge(tmpNode1, comparableNode, tmpWeigth);    //Adding edges to both nodes
                        tmpNode1.addAdjacency(tmpEdge);
                        //Edge compEdge = new Edge(comparableNode, tmpNode1, tmpWeigth);
                        //comparableNode.addAdjacency(compEdge);
                    }
                }
            }
        }
    }

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

        graph.setWeigthList(matrix);
        graph.setNodeAmount(nodeAmount);
        graph.nodeGenerator();
        scanner.close();
        return graph;

    }
}


