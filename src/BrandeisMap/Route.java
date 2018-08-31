package BrandeisMap;

import BrandeisMap.model.Edge;
import BrandeisMap.utils.CoordinateConverter;
import BrandeisMap.utils.CustomArrayList;
import BrandeisMap.utils.TimeUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Route {
    public void setRoute(CustomArrayList<Edge> route) {
        this.route = route;
    }

    CustomArrayList<Edge> route;

    public Route(){
        route = new CustomArrayList<>();
    }

    public void add(Edge edge) {
        route.add(edge);
    }

    public CustomArrayList<Edge> getRoute() {
        return route;
    }

    public void printRoute(String path, boolean skateboard) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(path)));
        int feet = 0;
        int time = 0;
        for (int i =  0; i < route.size(); i++) {
             System.out.print(route.get(i).toRouteString(skateboard));
             writer.write(route.get(i).toRouteString(skateboard));
            feet += route.get(i).getLength();
            time += route.get(i).getTime(skateboard);
        }
        System.out.println("legs = " + route.size() + ", distance = " + feet +" feet, time = "+ TimeUtil.translateTime(time));
        writer.write("legs = " + route.size() + ", distance = " + feet +" feet, time = "+ TimeUtil.translateTime(time));
        writer.close();
    }

    public void printPixels(String path, boolean skateboard, boolean cropped) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(path)));
        int feet = 0;
        int time = 0;
        for (int i =  0; i < route.size(); i++) {
            int sourX, sourY, destX, destY;
            if (cropped) {
                sourX = CoordinateConverter.xToCroppedPixel(route.get(i).getSource().getXOrdinate());
                sourY = CoordinateConverter.yToCroppedPixel(route.get(i).getSource().getYOrdinate());
                destX = CoordinateConverter.xToCroppedPixel(route.get(i).getDestination().getXOrdinate());
                destY = CoordinateConverter.yToCroppedPixel(route.get(i).getDestination().getYOrdinate());
            } else {
                sourX = CoordinateConverter.xToPixel(route.get(i).getSource().getXOrdinate());
                sourY = CoordinateConverter.yToPixel(route.get(i).getSource().getYOrdinate());
                destX = CoordinateConverter.xToPixel(route.get(i).getDestination().getXOrdinate());
                destY = CoordinateConverter.yToPixel(route.get(i).getDestination().getYOrdinate());
            }
            writer.write(sourX + " " + sourY + " " + " " +destX + " " + destY + "\n");
            feet += route.get(i).getLength();
            time += route.get(i).getTime(skateboard);
        }
        writer.close();
    }
}
