import java.io.FileNotFoundException;

/**
 * Created with IntelliJ IDEA.
 * User: rlokc
 * Date: 27.10.12
 * Time: 16:26
 */

public class GraphMain {

    public static void main(String[] args) throws FileNotFoundException {
        Graph graph = new Graph();
        graph.generateGraph();    //TODO: Embed in the constructor
        graph.algorithmMaster();
        graph.closeScanner();
    }
}
