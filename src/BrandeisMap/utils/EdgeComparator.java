package BrandeisMap.utils;

import BrandeisMap.model.Edge;

import java.util.Comparator;

public class EdgeComparator implements Comparator<Edge> {
    boolean skateboard;
    boolean minimize_time;

    public EdgeComparator(boolean skateboard, boolean minimize_time) {
        this.skateboard = skateboard;
        this.minimize_time = minimize_time;
    }
    @Override
    public int compare(Edge o1, Edge o2) {
        double d1 = o1.getWeight(this.skateboard, this.minimize_time);
        double d2 = o2.getWeight(this.skateboard, this.minimize_time);
        return Double.compare(d1, d2);
    }
}
