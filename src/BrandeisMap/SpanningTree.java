package BrandeisMap;

import BrandeisMap.model.Edge;
import BrandeisMap.model.Location;
import BrandeisMap.utils.CustomArrayList;
import BrandeisMap.utils.CustomHashMap;
import BrandeisMap.utils.CustomMinHeap;
import BrandeisMap.utils.EdgeComparator;

public class SpanningTree {
    CustomMinHeap<Edge> minHeap;
    boolean[][] matrix;



    public void generateSpanningTree(String method, boolean skateboard, boolean minimize_time, Location staLoc, boolean[] visited, int LOCATION_NUM, CustomHashMap<String, Integer> locMap, Edge[][] graph, CustomArrayList<Edge> edges) {
        if (method.equals("Kruskel")) {
            Kruskel(skateboard, minimize_time, visited, LOCATION_NUM, locMap, edges, LOCATION_NUM, graph);
        } else {
            Prim(skateboard, minimize_time, staLoc, visited, LOCATION_NUM, locMap, graph);
        }

    }

    private void Prim(boolean skateboard, boolean minimize_time, Location staLoc, boolean[] visited, int LOCATION_NUM, CustomHashMap<String, Integer> locMap, Edge[][] graph) {
        matrix = new boolean[LOCATION_NUM][LOCATION_NUM];
        minHeap = new CustomMinHeap<Edge>(new EdgeComparator(skateboard, minimize_time));
        for (int i = 0; i < LOCATION_NUM; i++) {
            if (graph[locMap.get(staLoc.getLabel())][i] != null && visited[i] == false) {
                minHeap.add(graph[locMap.get(staLoc.getLabel())][i]);
            }
        }
        visited[locMap.get(staLoc.getLabel())] = true;

        while(!minHeap.isEmpty()) {
            Edge edge = minHeap.poll();
            if (visited[locMap.get(edge.getSource().getLabel())] && visited[locMap.get(edge.getDestination().getLabel())])
                continue;

            visited[locMap.get(edge.getSource().getLabel())] = true;
            for (int i = 0; i < LOCATION_NUM; i++) {
                if (graph[locMap.get(edge.getDestination().getLabel())][i] != null && visited[i] == false) {
                    minHeap.add(graph[locMap.get(edge.getDestination().getLabel())][i]);
                }
            }

            visited[locMap.get(edge.getDestination().getLabel())] = true;
            matrix[locMap.get(edge.getSource().getLabel())][locMap.get(edge.getDestination().getLabel())] = true;
            matrix[locMap.get(edge.getDestination().getLabel())][locMap.get(edge.getSource().getLabel())] = true;
        }
    }

    public CustomArrayList<Edge> preOrder(Location staLoc, Edge[][] graph, int LOCATION_NUM) {
        CustomArrayList<Edge> paths = new CustomArrayList<>();
        boolean[] visited = new boolean[LOCATION_NUM];
        BackTrackHelper(paths, staLoc.getId(), graph, visited);
        return paths;
    }

    private void BackTrackHelper(CustomArrayList<Edge> paths, int from, Edge[][] graph, boolean[] visited){
        visited[from] = true;
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[from][i] && visited[i] == false) {
                paths.add(graph[from][i]);
                BackTrackHelper(paths, i, graph, visited);
                paths.add(graph[i][from]);
            }
        }
        visited[from] = false;
    }

    private void Kruskel(boolean skateboard, boolean minimize_time, boolean[] visited, int LOCATION_NUM, CustomHashMap<String, Integer> locMap, CustomArrayList<Edge> edges, int left, Edge[][] graph) {
        matrix = new boolean[LOCATION_NUM][LOCATION_NUM];
        minHeap = new CustomMinHeap<>(new EdgeComparator(skateboard, minimize_time));

        for (int i = 0; i < edges.size(); i++) {
            minHeap.add(edges.get(i));
        }

        int[] Sets = new int[LOCATION_NUM];
        for (int i = 0; i < LOCATION_NUM; i++) {
            Sets[i] = i;
        }
        int numOfLoc = 0;

        while (numOfLoc < left - 1 && !minHeap.isEmpty()) {
            Edge nextEdge = minHeap.poll();
            if (visited[nextEdge.getSource().getId()]) continue;
            if (visited[nextEdge.getDestination().getId()]) continue;

            int source = find(Sets, locMap.get(nextEdge.getSource().getLabel()));
            int destination = find(Sets, locMap.get(nextEdge.getDestination().getLabel()));

            if (source != destination) {

//                System.out.print(graph[nextEdge.getSource().getId()][nextEdge.getDestination().getId()].toString() + "\n");
                matrix[nextEdge.getSource().getId()][nextEdge.getDestination().getId()] = true;
                matrix[nextEdge.getDestination().getId()][nextEdge.getSource().getId()] = true;
                union(Sets, source, destination);
                numOfLoc++;
            }
        }
    }

    private int find(int[] Sets, int i) {
        while (Sets[i] != i) {
            i = Sets[i];
        }
        return i;
    }

    private void union(int[] Sets, int x, int y) {
        int xRoot = find(Sets, x);
        int yRoot = find(Sets, y);

        if (xRoot != yRoot) {
            Sets[xRoot] = yRoot;
        }
    }

    /**
     * Sets new matrix.
     *
     * @param matrix New value of matrix.
     */
    public void setMatrix(boolean[][] matrix) {
        this.matrix = matrix;
    }

    /**
     * Gets minHeap.
     *
     * @return Value of minHeap.
     */
    public CustomMinHeap<Edge> getMinHeap() {
        return minHeap;
    }

    /**
     * Sets new minHeap.
     *
     * @param minHeap New value of minHeap.
     */
    public void setMinHeap(CustomMinHeap<Edge> minHeap) {
        this.minHeap = minHeap;
    }

    /**
     * Gets matrix.
     *
     * @return Value of matrix.
     */
    public boolean[][] getMatrix() {
        return matrix;
    }

    public CustomArrayList<Edge> getSpanningTree(Location staLoc, Edge[][] graph, int LOCATION_NUM) {
        CustomArrayList<Edge> paths = new CustomArrayList<>();
        boolean[] visited = new boolean[LOCATION_NUM];
        DFSHelper(paths, staLoc.getId(), graph, visited);
        return paths;
    }

    private void DFSHelper(CustomArrayList<Edge> paths, int from, Edge[][] graph, boolean[] visited){
        visited[from] = true;
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[from][i] && visited[i] == false) {
                paths.add(graph[from][i]);
                DFSHelper(paths, i, graph, visited);
            }
        }
        visited[from] = false;
    }

}
