package BrandeisMap.utils;

import java.util.Comparator;

public class ShortestPathComparator implements Comparator<ShortestPath>{
    @Override
    public int compare(ShortestPath o1, ShortestPath o2) {
        double d1 = o1.getLength();
        double d2 = o2.getLength();
        return Double.compare(d1, d2);
    }
}
