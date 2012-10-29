import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: rlokc
 * Date: 27.10.12
 * Time: 11:58
 * To change this template use File | Settings | File Templates.
 */
public class Node {

    enum Color {
        nigger,
        white
    }

    private Color color = Color.white;

    public String getNAME() {
        return NAME;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    private String NAME;
    private ArrayList<Edge> adjacencies;

    public Node(String argName) {
        NAME = argName;
        adjacencies = new ArrayList<Edge>();
        NAME = null;
    }

    public void addAdjacency(Edge tmpEdge) {
        this.adjacencies.add(tmpEdge);
    }
    //******************
    public int getEdgeWeigth(int indexOfEdge){
        return adjacencies.get(indexOfEdge).getWeigth();
    }
    //******************
}
