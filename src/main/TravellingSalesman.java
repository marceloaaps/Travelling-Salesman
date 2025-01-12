package main;

import java.util.ArrayList;
import java.util.List;

public class TravellingSalesman {
    private int shorterDistance = Integer.MAX_VALUE;
    private List<Locality> bestRoute;

    public void calculateRoute(Route route, Locality origin) {
        List<Locality> location = new ArrayList<>(route.getGraph().keySet());
        List<Locality> currentRoute = new ArrayList<>();
        currentRoute.add(origin);

        routeFinder(location, currentRoute, route, origin, 0);
    }

    private void routeFinder(List<Locality> localidades, List<Locality> currentRoute, Route route,
                             Locality atual, int currentDistance) {
        if (currentRoute.size() == localidades.size()) {
            currentDistance += route.getGraph().get(atual).get(currentRoute.get(0));
            if (currentDistance < shorterDistance) {
                shorterDistance = currentDistance;
                bestRoute = new ArrayList<>(currentRoute);
            }
            return;
        }

        for (Locality next : localidades) {
            if (!currentRoute.contains(next)) {
                currentRoute.add(next);
                routeFinder(localidades, currentRoute, route, next,
                        currentDistance + route.getGraph().get(atual).get(next));
                currentRoute.remove(next);
            }
        }
    }

    public int getShorterDistance() {
        return shorterDistance;
    }

    public List<Locality> getBestRoute() {
        return bestRoute;
    }

    public void setShorterDistance(int shorterDistance) {
        this.shorterDistance = shorterDistance;
    }
}
