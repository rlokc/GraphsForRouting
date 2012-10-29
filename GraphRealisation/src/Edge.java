/**
 * Created with IntelliJ IDEA.
 * User: rlokc
 * Date: 27.10.12
 * Time: 21:12
 * To change this template use File | Settings | File Templates.
 */
public class Edge {

    private Node startNode;
    private Node endNode;

    public int getWeigth() {
        return weigth;
    }

    private int weigth;

    public Edge(Node startNode, Node endNode, int weigth) {
        this.startNode = startNode;
        this.endNode = endNode;
        this.weigth = weigth;
    }

}
