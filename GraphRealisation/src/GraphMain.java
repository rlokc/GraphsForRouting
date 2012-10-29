/**
 * Created with IntelliJ IDEA.
 * User: rlokc
 * Date: 27.10.12
 * Time: 16:26
 * To change this template use File | Settings | File Templates.
 */
public class GraphMain {

    public static void main(String[] args) {

        //GraphGenerator generator = new GraphGenerator();
        UndirectedGraph graph = new UndirectedGraph();
        graph = graph.generateGraph();
        Searcher testing = new Searcher(graph.getNodeAmount());
        testing.doSearch(graph.getNodesList(), 2);
    }
}
