package BrandeisMap.model;

import BrandeisMap.Globals;
import BrandeisMap.utils.TimeUtil;

public class Edge{
    private int id;
    private Location source;
    private Location destination;
    private int length;
    private int angle;
    private String direction;
    private char code;
    private String name;

    public Edge(int id, Location source, Location destination, int length, int angle, String direction, char code, String name) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.length = length;
        this.angle = angle;
        this.direction = direction;
        this.code = code;
        this.name = name;
    }



    public String toString() {
        return "id: " + id + " " +
                "source: " + source + " " +
                "destination: " + destination + " " +
                "length: " + length + " " +
                "angle: " + angle + " " +
                "destination: " + destination + " " +
                "code: " + code + " " +
                "name: " + name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Edge other = (Edge) obj;
        return this.id == other.getId();
    }

    /**
     * according code in data file, figura out if such a edge we can use skate board on it
     * @return if skate boar avaliable
     */
    public boolean skateAvailable() {
        if (code <= 'z' && code >= 'a' && code != 'x') return false;
        else return true;
    }

    /**
     * if use don't want to minimize time spend on such edge, then the weight of this edge should be its length, otherwise the weight of such edge should be
     * how many time it will take to cross the edge, we don't directly return spent time. Insteadly, we will return the length we scale accord edge code
     * @param skateboard if use want to use skate board on this edge
     * @param minimize_time if use want to minimize time for this edge
     * @return the weight base on input
     */
    public double getWeight(boolean skateboard, boolean minimize_time) {
        if (minimize_time == false)
            return (double)length;
        switch (code) {
            case 'f' : {
                return (double)length / Globals.WALK_FACTOR_F;
            } case 'd' : {
                return (double)length / Globals.WALK_FACTOR_D;
            } case 'u' : {
                return (double)length / Globals.WALK_FACTOR_U;
            } case 's':{
                return (double)length / Globals.STEP_FACTOR_U;
            } case 't' : {
                return (double)length / Globals.STEP_FACTOR_D;
            } case 'b' : {
                return (double)length / Globals.BRIDGE_FACTOR;
            } case 'F' : {
                if (skateboard)
                    return (double)length / Globals.SKATE_FACTOR_F;
                else
                    return (double)length / Globals.WALK_FACTOR_F;
            } case 'U' : {
                if (skateboard)
                    return (double)length / Globals.SKATE_FACTOR_U;
                else
                    return (double)length / Globals.WALK_FACTOR_U;
            }  case 'D' : {
                if (skateboard)
                    return (double)length / Globals.SKATE_FACTOR_D;
                else
                    return (double)length / Globals.WALK_FACTOR_D;
            } case 'x' : {
                if (skateboard)
                    return (double)length / Globals.SKATE_FACTOR_F;
                else
                    return (double)length / Globals.WALK_FACTOR_F;
            } default:
                return -1;
        }
    }

    /**
     * compute tje time spend on this edge, if using skate board and edge's code decide the time
     * @param skateboard if use want to use skate board on this edge
     * @return how many time we need to spend on crossing this edge
     */
    public double getTime(boolean skateboard) {
        switch (code) {
            case 'f' : {
                return ((double)length / (Globals.WALK_FACTOR_F * Globals.WALK_SPEED) * 60);
            } case 'd' : {
                return ((double)length / (Globals.WALK_FACTOR_D * Globals.WALK_SPEED) * 60);
            } case 'u' : {
                return ((double)length / (Globals.WALK_FACTOR_U * Globals.WALK_SPEED) * 60);
            } case 's':{
                return ((double)length / (Globals.STEP_FACTOR_U * Globals.WALK_SPEED) * 60);
            } case 't' : {
                return ((double)length / (Globals.STEP_FACTOR_D * Globals.WALK_SPEED) * 60);
            } case 'b' : {
                return ((double)length / (Globals.BRIDGE_FACTOR * Globals.WALK_SPEED) * 60);
            } case 'F' : {
                if (skateboard)
                    return ((double)length / (Globals.SKATE_FACTOR_F * Globals.WALK_SPEED) * 60);
                else
                    return ((double)length / (Globals.WALK_FACTOR_F * Globals.WALK_SPEED) * 60);
            } case 'U' : {
                if (skateboard)
                    return ((double)length / (Globals.SKATE_FACTOR_U * Globals.WALK_SPEED) * 60);
                else
                    return ((double)length / (Globals.WALK_FACTOR_U * Globals.WALK_SPEED) * 60);
            }  case 'D' : {
                if (skateboard)
                    return ((double)length / (Globals.SKATE_FACTOR_D * Globals.WALK_SPEED) * 60);
                else
                    return ((double)length / (Globals.WALK_FACTOR_D * Globals.WALK_SPEED) * 60);
            } case 'x' : {
                if (skateboard)
                    return ((double)length / (Globals.SKATE_FACTOR_F * Globals.WALK_SPEED) * 60);
                else
                    return ((double)length / (Globals.WALK_FACTOR_F * Globals.WALK_SPEED) * 60);
            } default:
                return -1;
        }
    }

    public String toRouteString(boolean skateboard) {
        double speed;
        String action;
        double time;
        String note;
        double weight;

        note = (skateboard ==  true && code >= 'a' && code <= 'z' && code != 'x')? "no skateboards allowed, " : "";
        if (code == 'f' || code == 'F' || code == 'u' || code == 'U' || code == 'x') {
            if (skateboard && (code == 'F' || code == 'U' || code == 'x')) {
                action = "Glide";
            } else {
                action = "Walk";
            }
        } else if (code == 'd' || code == 'D') {
            if (skateboard && code == 'D') {
                action = "Coast down";
            } else {
                action = "Walk down";
            }
        } else if (code == 's') {
            action = "Go";
        } else if (code == 't') {
            action = "Go down";
        } else {
            action = "Walk";
        }

        time = this.getTime(skateboard);

        String str = "FROM: " + "(" + this.getSource().getLabel() + ") " + this.getSource().getName() + "\n";
        if (this.getName() != null) {
            str = str + "ON: " + this.getName() + "\n";
        }
        str = str + action + " " + this.getLength() + " feet in direction " + this.getAngle() + " degrees " + this.getDirection() + ".\n" +
                "TO: " + "(" +this.getDestination().getLabel() + ") " + this.getDestination().getName() + "\n" +
                "(" + note + TimeUtil.translateTime(time)+ ")\n\n";
        return str;
    }

    /**
     * Gets destination.
     *
     * @return Value of destination.
     */
    public Location getDestination() {
        return destination;
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
     * Gets code.
     *
     * @return Value of code.
     */
    public char getCode() {
        return code;
    }

    /**
     * Sets new source.
     *
     * @param source New value of source.
     */
    public void setSource(Location source) {
        this.source = source;
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
     * Sets new destination.
     *
     * @param destination New value of destination.
     */
    public void setDestination(Location destination) {
        this.destination = destination;
    }

    /**
     * Gets direction.
     *
     * @return Value of direction.
     */
    public String getDirection() {
        return direction;
    }

    /**
     * Gets length.
     *
     * @return Value of length.
     */
    public int getLength() {
        return length;
    }

    /**
     * Gets source.
     *
     * @return Value of source.
     */
    public Location getSource() {
        return source;
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
     * Sets new direction.
     *
     * @param direction New value of direction.
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * Sets new code.
     *
     * @param code New value of code.
     */
    public void setCode(char code) {
        this.code = code;
    }

    /**
     * Gets angle.
     *
     * @return Value of angle.
     */
    public int getAngle() {
        return angle;
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
     * Sets new angle.
     *
     * @param angle New value of angle.
     */
    public void setAngle(int angle) {
        this.angle = angle;
    }

    /**
     * Sets new length.
     *
     * @param length New value of length.
     */
    public void setLength(int length) {
        this.length = length;
    }
}
