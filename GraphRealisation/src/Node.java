import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: rlokc
 * Date: 27.10.12
 * Time: 11:58
 * To change this template use File | Settings | File Templates.
 */
public class Node {
    private String name;
    private ArrayList<Edge> adjacencies;
    public boolean isPassed = false;        //For searchers
    private int mark = Integer.MAX_VALUE; //For dijkstra search
    private ArrayList<Node> pathTo = null;

    public ArrayList<Node> getPathTo() {
        return pathTo;
    }

    public void setPathTo(ArrayList<Node> pathTo) {
        this.pathTo = pathTo;
    }

    /**
     * Node name getter
     *
     * @return Node name
     */
    public String getName() {
        return name;
    }

    /**
     * Node constructor
     *
     * @param argName Name of a node, used later to show it
     */
    public Node(String argName) {
        name = argName;
        adjacencies = new ArrayList<Edge>();
    }

    public ArrayList<Edge> getAdjacencies() {
        return adjacencies;
    }

    /**
     * Setting edge connected with node
     *
     * @param tmpEdge Edge, connecting with this node
     */
    public void addAdjacency(Edge tmpEdge) {
        this.adjacencies.add(tmpEdge);
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {

        this.mark = mark;
    }

    /**
     * Getting weight of edge with some index
     *
     * @param indexOfEdge Index of needed edge
     * @return Edge weigth
     */
    public int getEdgeWeight(int indexOfEdge) {
        return adjacencies.get(indexOfEdge).getWeight();
    }
}
