/**
 * Created with IntelliJ IDEA.
 * User: rlokc
 * Date: 27.10.12
 * Time: 21:12
 */

public class Edge {
    public Node getStartNode() {
        return startNode;
    }

    private Node startNode;
    private Node endNode;
    private int weight;

    /**
     * Weight getter
     * @return Weight of edge
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Target node getter
     * @return endNode
     */

    public Node getEndNode() {
            return endNode;
        }

    /**
     * Edge constructor
     * @param startNode Start node for connection
     * @param endNode End node for connection
     * @param weight Edge weight
     */
    public Edge(Node startNode, Node endNode, int weight) {
        this.startNode = startNode;
        this.endNode = endNode;
        this.weight = weight;
    }

}
