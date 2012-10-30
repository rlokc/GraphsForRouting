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

    /**
     * Enumerator for node color (used in search)
     */
    enum Color {
        nigger,
        white
    }

    private Color color = Color.white;

    /**
     * Node name getter
     *
     * @return Node name
     */
    public String getName() {
        return name;
    }

    /**
     * Node color getter
     *
     * @return Node color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Node color setter
     *
     * @param color Enum color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Node constructor
     *
     * @param name Node name
     */
    public Node(String name) {
        this.name = name;
        adjacencies = new ArrayList<Edge>();
        //name = null; //WTF?!
    }

    /**
     * Setting edge connected with node
     *
     * @param tmpEdge Edge to connect with
     */
    public void addAdjacency(Edge tmpEdge) {
        this.adjacencies.add(tmpEdge);
    }

    /**
     * Getting weight of edge with some index
     * @param indexOfEdge Index of needed edge
     * @return Edge weigth
     */
    public int getEdgeWeight(int indexOfEdge) {
        return adjacencies.get(indexOfEdge).getWeight();
    }
}
