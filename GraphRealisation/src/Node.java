import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: rlokc
 * Date: 27.10.12
 * Time: 11:58
 */

public class Node {
    private String name;
    private ArrayList<Edge> adjacencies;
    public boolean isPassed = false;        //For searchers
    private int mark = Integer.MAX_VALUE;

    public int getProxMark() {
        return proxMark;
    }

    public void setProxMark(int proxMark) {
        this.proxMark = proxMark;
    }

    private int proxMark = Integer.MAX_VALUE;
    private ArrayList<Node> pathTo = null;

    public ArrayList<Node> getPathTo() {
        return pathTo;
    }

    public void addToPathTo(Node node){
        pathTo.add(node);
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
}
