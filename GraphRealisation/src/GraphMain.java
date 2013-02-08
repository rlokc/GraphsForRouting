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
        //TODO: Get rid of that DIRTY DIRTY hack
        graph.generateGraph();
        graph.algorithmMaster();
        graph.closeScanner();
    }
}
