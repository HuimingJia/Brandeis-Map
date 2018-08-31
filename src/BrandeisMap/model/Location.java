package BrandeisMap.model;

public class Location implements Comparable<Location> {
    private int id;
    private String label;
    private String name;
    private int xOrdinate;
    private int yOrdinate;
    private double cost = 0;

    @Override
    public int compareTo(Location o) {
        return Double.compare(this.getCost(), o.getCost());
    }

    public Location(int id, String label, String name, int xOrdinate, int yOrdinate) {
        this.id = id;
        this.label = label;
        this.name = name;
        this.xOrdinate = xOrdinate;
        this.yOrdinate = yOrdinate;
    }


    public String toString() {
        return  "id: " + id + " " +
                "label: " + label + " " +
                "name: " + name + " " +
                "xOrdinate: " + xOrdinate + " " +
                "yOrdinate: " + yOrdinate;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Location other = (Location) obj;
        return this.id == other.getId();
    }

    /**
     * Gets id.
     *
     * @return Value of id.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets name.
     *
     * @return Value of name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets xOrdinate.
     *
     * @return Value of xOrdinate.
     */
    public int getXOrdinate() {
        return xOrdinate;
    }

    /**
     * Sets new id.
     *
     * @param id New value of id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets new yOrdinate.
     *
     * @param yOrdinate New value of yOrdinate.
     */
    public void setYOrdinate(int yOrdinate) {
        this.yOrdinate = yOrdinate;
    }

    /**
     * Sets new label.
     *
     * @param label New value of label.
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Sets new cost.
     *
     * @param cost New value of cost.
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * Gets cost.
     *
     * @return Value of cost.
     */
    public double getCost() {
        return cost;
    }

    /**
     * Sets new name.
     *
     * @param name New value of name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets new xOrdinate.
     *
     * @param xOrdinate New value of xOrdinate.
     */
    public void setXOrdinate(int xOrdinate) {
        this.xOrdinate = xOrdinate;
    }

    /**
     * Gets yOrdinate.
     *
     * @return Value of yOrdinate.
     */
    public int getYOrdinate() {
        return yOrdinate;
    }

    /**
     * Gets label.
     *
     * @return Value of label.
     */
    public String getLabel() {
        return label;
    }
}
