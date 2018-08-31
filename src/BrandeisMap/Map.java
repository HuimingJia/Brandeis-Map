package BrandeisMap;

import BrandeisMap.model.Edge;
import BrandeisMap.model.Location;
import BrandeisMap.utils.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by jiahuiming on 11/18/17.
 */
public class Map {
    private Edge[][] graph;
    private int LOCATION_NUM = 0;
    double[][] shortestDis;

    private CustomArrayList<Location> locations = new CustomArrayList<>();
    private CustomArrayList<Edge> edges = new CustomArrayList<>();
    private CustomArrayList<CustomArrayList<Edge>> adjacencyList = new CustomArrayList<>();
    private CustomHashMap<String, Integer> locMap = new CustomHashMap<>();

    /**
     * @param locationsPath the path of file store locations information
     * @param edgesPath the path of file store edge information
     */
    public void buildMap(String locationsPath, String edgesPath){
        try{
            readLocations(locationsPath);
            readEdges(edgesPath);
        } catch (IOException e) {
            System.out.print(e.toString());
        }
    }

    /**
     * function to read all the locations from txt and put all location into list, build <label, id> map for locations
     * @param locationsPath the path of file store locations information
     * @throws IOException
     */
    public void readLocations(String locationsPath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File(locationsPath)));
        if (!reader.ready()){ System.out.print("Can not load the locations");}
        else {
            String tmp;
            while ((tmp = reader.readLine()) != null){
                if (tmp.length() > 0 && tmp.charAt(0) >= '0' && tmp.charAt(0) <= '9') {
                        String[] strs = tmp.split(" "); {
                        String name = tmp.split("\"").length > 1 ? tmp.split("\"")[1]: null;
                        Location loc = new Location(locations.size(), strs[1], name, Integer.parseInt(strs[2]), Integer.parseInt(strs[3]));
                        locations.add(loc);
                        locMap.put(loc.getLabel(), locations.size() - 1);
                    }
                }
            }
            LOCATION_NUM = locations.size();
            reader.close();
        }

    }

    /**
     * function to read all the edges from txt and put all edge into list
     * @param edgesPath the path of file store edge information
     * @throws IOException
     */
    public void readEdges(String edgesPath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File(edgesPath)));
        if (!reader.ready()){ System.out.print("Can not load the edges");}
        else {
            String tmp;
            while ((tmp = reader.readLine()) != null){
                if (tmp.length() > 0 && tmp.charAt(0) >= '0' && tmp.charAt(0) <= '9') {
                    String[] strs = tmp.split(" ");
                    String name = tmp.split("\"").length > 1 ? tmp.split("\"")[1]:null;
                    try {
                        Edge edge = new Edge(Integer.parseInt(strs[0]),locations.get(locMap.get(strs[1])), locations.get(locMap.get(strs[2])), Integer.parseInt(strs[5]), Integer.parseInt(strs[6]), strs[7], strs[8].charAt(1), name);
                        edges.add(edge);
                    } catch (Exception e) {
                        System.out.print(e.toString() + " readEdge Error!\n");
                    }
                }
            }
            reader.close();
        }
    }

    /**
     * build graph and use two dimension matrix and adjacency List to represent graph
     */
    public void buildMap() {
        graph = new Edge[LOCATION_NUM][LOCATION_NUM];
        for (int i = 0; i < LOCATION_NUM; i++) {
            adjacencyList.add(new CustomArrayList<>());
        }

        for (int i = 0; i < edges.size(); i++) {
            int s = locMap.get(edges.get(i).getSource().getLabel());
            int d = locMap.get(edges.get(i).getDestination().getLabel());
            graph[s][d] = edges.get(i);
            adjacencyList.get(s).add(edges.get(i));
        }
    }

    /**
     * search which location user meant to input by incomplete search term, using simple string pattern matching, return first  available location in list
     * @param term user input incomplete term
     * @return return matched result, if no such result, return null location
     */
    private Location searchLocationByName(String term) {
        for (int i = 0; i < locations.size(); i++) {
            if (locations.get(i).getName().toLowerCase().contains(term.toLowerCase())) {
                return locations.get(i);
            }
        }
        return null;
    }

    /**
     * search which location user meant to input by incomplete search label
     * @param term input incompelete label
     * @return found location
     */
    private Location searchLocationByLabel(String term) {
        for (int i = 0; i < locations.size(); i++) {
            if (locations.get(i).getLabel().toLowerCase().equals(term.toLowerCase())) {
                return locations.get(i);
            }
        }
        return null;
    }

    /**
     *schedule a route accourding input parameters, if there is no connect path, print error message
     * @param start start location of the route
     * @param finish end location of the route
     * @param skateboard if user have specify to use or not to use skateboard
     * @param minimize_time if user have specify to schedule a route which has minimum distance or will cost minimum time
     * @return schedule route
     */
    public Route scheduleRoute(String start, String finish, boolean skateboard, boolean minimize_time) {
        Location staLoc = searchLocationByLabel(start);
        Location finLoc = searchLocationByLabel(finish);
        Route route = new Route();
        if (staLoc != null && finLoc != null) {
            int[] paths = Dijkstra(skateboard, minimize_time, staLoc);
            // track path from finish location to start location and add path to route list;
            int des = locMap.get(finLoc.getLabel());
            CustomArrayList<Edge> reversedPaths = new CustomArrayList<>();
            while (paths[des] != -1) {
                reversedPaths.add(graph[paths[des]][des]);
                des = paths[des];
            }

            for (int i = reversedPaths.size() - 1; i >= 0; i--) {
                route.add(reversedPaths.get(i));
            }
        } else {
            System.out.print("No such location or no path!");
        }
        return route;
    }

    /**
     *
     * @param start start point of the Tour schedule by MST which generated by Prim algorithm
     * @param skateboard if user want to use skateboard
     * @param minimize_time if user want a route which cost least time
     * @return shecdule route
     */
    public Route scheduleTour(String start, boolean skateboard, boolean minimize_time) {
        Route tour = new Route();
        Location staLoc = searchLocationByLabel(start);
        SpanningTree spanningTree = new SpanningTree();
        boolean[] visited = new boolean[LOCATION_NUM];
        if (staLoc != null) {
//            spanningTree.generateSpanningTree("Kruskel", skateboard, minimize_time, staLoc, visited, LOCATION_NUM, locMap, graph, edges);
            spanningTree.generateSpanningTree("Prim", skateboard, minimize_time, staLoc, visited, LOCATION_NUM, locMap, graph, edges);
            tour.setRoute(spanningTree.preOrder(staLoc, graph, LOCATION_NUM));
        } else {
            System.out.print("No such location or no path!");
        }
        return tour;
    }

    public Route getSpanningTree(String start, boolean skateboard, boolean minimize_time) {
        Route tour = new Route();
        Location staLoc = searchLocationByLabel(start);
        SpanningTree spanningTree = new SpanningTree();
        boolean[] visited = new boolean[LOCATION_NUM];
        if (staLoc != null) {
//            spanningTree.generateSpanningTree("Kruskel", skateboard, minimize_time, staLoc, visited, LOCATION_NUM, locMap, graph, edges);
            spanningTree.generateSpanningTree("Prim", skateboard, minimize_time, staLoc, visited, LOCATION_NUM, locMap, graph, edges);
            tour.setRoute(spanningTree.getSpanningTree(staLoc, graph, LOCATION_NUM));
        } else {
            System.out.print("No such location or no path!");
        }
        return tour;
    }

    /**
     * shedule a better tour route which is based on MST and Christofides algorithm, first we generate a minimum spanning tree and find out all the location is the graph
     * which have odd degree. Then we use greedy match algorithm match all such locations by pair and generate a shortcut graph.
     * Next, we all the shortcut graph on the original MST to generate a new graph both contain MST edge and Shortcut edge
     * Finally, we find out possible Hamiltonian cycle after we find out the Eulerian Circuit for the graph
     * @param start start location of the tour
     * @param end end location of the tour
     * @param skateboard if user want to use skateboard
     * @param minimize_time  if user want a route which cost least time
     * @return shecdule route
     */
    public Route scheduleShortestTour(String start, String end, boolean skateboard, boolean minimize_time) {
        shortestDis = FloydWarshall(skateboard,minimize_time);
        Route tour = new Route();
        Location staLoc = searchLocationByLabel(start);
        SpanningTree spanningTree = new SpanningTree();
        boolean[] visited = new boolean[LOCATION_NUM];
        if (staLoc != null) {
            spanningTree.generateSpanningTree("Kruskel", skateboard, minimize_time, staLoc, visited, LOCATION_NUM, locMap, graph, edges);
        } else {
            System.out.print("No such location or no path!");
        }

        boolean[][] matrix = spanningTree.getMatrix();
//        tour.setRoute(optimizeTour(tour.getRoute(), skateboard, minimize_time, staLoc));
        boolean[] odLocations = getOddDegreeLocation(matrix);
        boolean[][] pm = MinimumWeightMatching(odLocations, skateboard, minimize_time);
        //combine two matrix
        CustomArrayList<Edge> route = new CustomArrayList<>();
        for (int i = 0; i < LOCATION_NUM; i++) {
            for (int j = 0; j < LOCATION_NUM; j++) {
                if (pm[i][j]) {
                    matrix[i][j] = true;
                }
            }
        }

        CustomArrayList<Location> eulerian = findEulerianCircuit(matrix, staLoc);
        CustomArrayList<Location> Hamiltonian = findHamiltonianCircuit(eulerian, skateboard, minimize_time);

        System.out.println(Hamiltonian.size()  + "\n");

        for (int i = 0; i < Hamiltonian.size(); i++) {
            System.out.println(Hamiltonian.get(i).toString());
        }

        for (int i = 0; i < Hamiltonian.size(); i++) {
            int source = Hamiltonian.get(i).getId();
            int destination = Hamiltonian.get((i + 1) % Hamiltonian.size()).getId();

            System.out.println(source + ":" + destination);
            if (graph[source][destination] != null) {
                route.add(graph[source][destination]);
            } else {

                CustomArrayList<Edge> r = scheduleRoute(locations.get(source).getLabel(), locations.get(destination).getLabel(),skateboard, minimize_time).getRoute();
                for (int k = 0; k < r.size(); k++) {
                    route.add(r.get(k));
                }
            }
        }


        for (int i = 0; i < route.size(); i++) {
            System.out.println(route.get(i).toString());
        }


        tour.setRoute(route);
        return tour;
    }


    /**
     * find out Eulerian Circuit based on the combined graph, using stack and dfs search
     * @param matrix the graph that contains MST edge and shortcut edge
     * @param start the start location of EulerianCircuit
     * @return a found Eulerian Circuit
     */
    private CustomArrayList<Location> findEulerianCircuit(boolean[][] matrix, Location start){
        CustomArrayList<Location> reverse = new CustomArrayList<>();
        CustomArrayList<Location> res = new CustomArrayList<>();
        CustomArrayList<CustomArrayList<Integer>> adList = new CustomArrayList<>();

        CustomStack<Location> locs = new CustomStack<Location>();

        for (int i = 0; i < LOCATION_NUM; i++) {
            adList.add(new CustomArrayList<>());
        }

        for (int i = 0; i < LOCATION_NUM; i++) {
            for (int j = 0; j < LOCATION_NUM; j++) {
                if (matrix[i][j]) {
                    adList.get(i).add(j);
                }
            }
        }

        locs.push(start);
        Location cur = start;
        while (!locs.isEmpty()) {
            if (adList.get(cur.getId()).size() > 0) {
                locs.push(cur);
                int pos = adList.get(cur.getId()).size();
                Location next = locations.get(adList.get(cur.getId()).get(pos - 1));
                adList.get(cur.getId()).remove();
                cur = next;
            } else {
                reverse.add(cur);
                cur = locs.pop();
            }
        }

        for (int i = reverse.size() - 1; i >= 0; i--) {
            res.add(reverse.get(i));
        }
        return res;
    }

    /**
     * find out find possible Hamiltonian Circuit based on eulerian cycle
     * @param eulerian a eulerian circuit which represented by list of location
     * @param skateboard if user want to use skateboard
     * @param minimize_time if user want a route which cost least time
     * @return a cycle of route represented by list of location
     */
    private CustomArrayList<Location> findHamiltonianCircuit(CustomArrayList<Location> eulerian,  boolean skateboard, boolean minimize_time) {
        CustomArrayList<Location> res = new CustomArrayList<>();
        CustomStack<Location> path = new CustomStack<>();
        boolean[] visited = new boolean[LOCATION_NUM];

        Location cur = eulerian.get(0);
        visited[cur.getId()] = true;

        path.push(cur);

        for (int i = 0; i < eulerian.size() - 1; i++) {
            cur = eulerian.get(i + 1);
            if (!visited[cur.getId()]) {
                path.push(cur);
                visited[cur.getId()] = true;
            }
        }

        Location[] locs = new Location[path.size()];
        for (int i = locs.length - 1; i >= 0; i--) {
            locs[i] = path.pop();
        }

        for (int i = 0; i < locs.length; i++) {
            if (locs[i] != null)
                res.add(locs[i]);
        }
        return res;
    }

    /**
     * find out all the Location in a MST whose degree is odd
     * @param matrix MST generated by kruskal algorithm
     * @return a list of locations denoted by array of boolean array, if ith element of the array is true, that's mean the degree of this node is odd
     */
    public boolean[] getOddDegreeLocation(boolean[][] matrix) {
        int[] degree = new int[LOCATION_NUM];
        boolean[] oddLoc = new boolean[LOCATION_NUM];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j]) {
                    degree[i]++;
                    degree[j]++;
                }
            }
        }

        CustomArrayList<Location> odLocations = new CustomArrayList<>();
        for (int i = 0; i < LOCATION_NUM; i++) {
            if ((degree[i] / 2) % 2 != 0) {
                oddLoc[i] = true;
            }
        }
        return oddLoc;
    }

    /**
     * Greedy match algorithm which pairs all the odd degree location
     * @param odloc a list of locations denoted by array of boolean array, if ith element of the array is true, that's mean the degree of this node is odd
     * @param skateboard if user want to use skateboard
     * @param minimize_time if user want a route which cost least time
     * @return a matrix which denote shortcut graph
     */
    public boolean[][] MinimumWeightMatching(boolean[] odloc, boolean skateboard, boolean minimize_time) {
        boolean[][] matchs = new boolean[LOCATION_NUM][LOCATION_NUM];
        int numOfLoc = 0;
        for (int i = 0; i < odloc.length; i++) {
            if (odloc[i]) {
                numOfLoc++;
            }
        }

        CustomMinHeap<ShortestPath> heap = new CustomMinHeap<>(new ShortestPathComparator());
        for (int i = 0; i < LOCATION_NUM; i++) {
            for (int j = 0; j < LOCATION_NUM; j++) {
                if (i != j) {
                    heap.add(new ShortestPath(locations.get(i), locations.get(j), shortestDis[i][j]));
                }
            }
        }

        ShortestPath path;
        while (numOfLoc > 0) {
            do {
                path = heap.poll();
            } while (!odloc[path.getSource().getId()] || !odloc[path.getDestination().getId()]);


            double biggestSwapDifference = 0;
            ShortestPath edgeToSwap = null;

            for (int i = 0; i < LOCATION_NUM; i++) {
                for (int j = 0; j < LOCATION_NUM; j++) {
                    if (matchs[i][j]) {
                        double pairDistance = shortestDis[i][j] + path.getLength();
                        double swapPairDistance1 = shortestDis[i][path.getSource().getId()] + shortestDis[j][path.getDestination().getId()];
                        double swapPairDistance2 = shortestDis[i][path.getDestination().getId()] + shortestDis[j][path.getSource().getId()];

                        if (swapPairDistance1 < swapPairDistance2 && swapPairDistance1 - pairDistance < biggestSwapDifference) {
                            biggestSwapDifference = swapPairDistance1 - pairDistance;
                            edgeToSwap = new ShortestPath(locations.get(i), locations.get(j), shortestDis[i][j]);
                        } else if (swapPairDistance2 - pairDistance < biggestSwapDifference) {
                            biggestSwapDifference = swapPairDistance2 - pairDistance;
                            edgeToSwap = new ShortestPath(locations.get(i), locations.get(j), shortestDis[i][j]);
                            ;
                        }
                    }
                }
            }
            // Swap edge for optimized edges
            if (biggestSwapDifference < 0 && edgeToSwap != null) {
                int id1 = edgeToSwap.getSource().getId();
                int id2 = edgeToSwap.getDestination().getId();

                int id3 = path.getSource().getId();
                int id4 = path.getDestination().getId();

                double swapPairDistance1 = shortestDis[id1][id3] + shortestDis[id2][id4];
                double swapPairDistance2 = shortestDis[id1][id4] + shortestDis[id2][id3];

                if (swapPairDistance1 < swapPairDistance2) {
                    matchs[edgeToSwap.getSource().getId()][edgeToSwap.getDestination().getId()] = false;
                    matchs[id1][id3] = true;
                    matchs[id2][id4] = true;
                } else {
                    matchs[edgeToSwap.getSource().getId()][edgeToSwap.getDestination().getId()] = false;
                    matchs[id1][id4] = true;
                    matchs[id2][id3] = true;
                }
            }

            // Add edge to set, remove both endpoints from vertices set
            if (biggestSwapDifference == 0)
                matchs[path.getSource().getId()][path.getDestination().getId()] = true;

            odloc[path.getSource().getId()] = false;
            odloc[path.getDestination().getId()] = false;
            numOfLoc = numOfLoc - 2;
        }
        return matchs;
    }

    private double[][] FloydWarshall(boolean skateboard, boolean minimize_time) {
        double dist[][] = new double[LOCATION_NUM][LOCATION_NUM];
        for (int i = 0; i < LOCATION_NUM; i++) {
            for (int j = 0; j < LOCATION_NUM; j++) {
                if (graph[i][j] != null) {
                    dist[i][j] = graph[i][j].getWeight(skateboard, minimize_time);
                } else {
                    dist[i][j] = Double.MAX_VALUE;
                }
            }
        }

        for (int k = 0; k < LOCATION_NUM; k++)  {
            for (int i = 0; i < LOCATION_NUM; i++)  {
                for (int j = 0; j < LOCATION_NUM; j++)  {
                    if (dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }
        return dist;
    }

    /**
     * Dijkstra algorithm for finding out shortest path
     * @param skateboard if user want to use skateboard
     * @param minimize_time if user want a route which cost least time
     * @return a path represented by array of integer
     */
    private int[] Dijkstra(boolean skateboard, boolean minimize_time, Location staLoc) {
        double[] dist = new double[LOCATION_NUM];
        boolean[] visited = new boolean[LOCATION_NUM];
        int[] paths = new int[LOCATION_NUM];

        Arrays.fill(dist, Double.MAX_VALUE);
        Arrays.fill(visited, false);
        Arrays.fill(paths, -1);

        dist[locMap.get(staLoc.getLabel())] = 0.0;

        //Dijkstra algorithm
        for (int count = 0; count < LOCATION_NUM - 1; count++) {
            double min = Double.MAX_VALUE;
            int min_index = -1;
            for (int i = 0; i < LOCATION_NUM; i++) {
                if (visited[i] == false && dist[i] <= min) {
                    min = dist[i];
                    min_index = i;
                }
            }

            visited[min_index] = true;

            for (int des = 0; des < LOCATION_NUM; des++) {
                if (!visited[des] && graph[min_index][des] != null && dist[min_index] + graph[min_index][des].getWeight(skateboard, minimize_time) < dist[des]) {
                    paths[des]  = min_index;
                    dist[des] = dist[min_index] + graph[min_index][des].getWeight(skateboard, minimize_time);
                }
            }
        }
        return paths;
    }
}
