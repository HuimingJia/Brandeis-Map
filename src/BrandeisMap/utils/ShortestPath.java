package BrandeisMap.utils;

import BrandeisMap.model.Location;

public class ShortestPath {
    private Location source;
    private Location destination;
    private double length;

    public ShortestPath(Location source, Location destination, double length) {
        this.source = source;
        this.destination = destination;
        this.length = length;
    }

    public Location getSource() {
        return source;
    }

    public void setSource(Location source) {
        this.source = source;
    }

    public Location getDestination() {
        return destination;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }
}
