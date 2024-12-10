import java.util.HashMap;
import java.util.Map;

public class Route {
    private Map<Locality, Map<Locality, Integer>> graph;

    public Route() {
        graph = new HashMap<>();
    }

    public void addLocality(Locality place) {
        graph.putIfAbsent(place, new HashMap<>());
    }

    public void addEdge(Locality origin, Locality destination, int distance) {
        graph.get(origin).put(destination, distance);
        graph.get(destination).put(origin, distance);
    }

    public Map<Locality, Map<Locality, Integer>> getGraph() {
        return graph;
    }
}
