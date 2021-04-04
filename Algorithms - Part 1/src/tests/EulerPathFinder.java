package tests;

import java.util.*;

public class EulerPathFinder {
    private Map<Integer, Set<Integer>> graphNodes;
    private List<Integer> oddNodes = new ArrayList<>();

    private void removeEdge(Integer i, Integer j) {
        removeNodeConnection(i, j);
        removeNodeConnection(j, i);
    }

    private void removeNodeConnection(Integer i, Integer j) {
        var connections = graphNodes.get(i);
        if (connections.size() > 0) {
            connections.remove(j);
            if (connections.isEmpty()) {
                graphNodes.remove(i);
            } else {
                graphNodes.put(i, connections);
            }
        } else {
            graphNodes.remove(i);
        }
    }

    public void readGraph(int[][] graph) {
        graphNodes = new HashMap<>();

        populateNodesAndEdges(graph);
        findOddNodes();
    }

    private void findOddNodes() {
        graphNodes.forEach((key, value) -> {
            if (value.size() % 2 == 1) {
                oddNodes.add(key);
            }
        });
    }

    private void populateNodesAndEdges(int[][] graph) {
        Arrays.stream(graph).forEach(tuple -> {
            if (graphNodes.containsKey(tuple[0])) {
                var connections = graphNodes.get(tuple[0]).add(tuple[1]);
            }

            if (graphNodes.containsKey(tuple[1])) {
                var connections = graphNodes.get(tuple[1]).add(tuple[0]);
            }

            if (!graphNodes.containsKey(tuple[1]) && !graphNodes.containsKey(tuple[0])) {
                var connections1 = new HashSet<Integer>();
                connections1.add(tuple[1]);
                graphNodes.put(tuple[0], connections1);
                var connections2 = new HashSet<Integer>();
                connections2.add(tuple[0]);
                graphNodes.put(tuple[1], connections2);
            }
        });
    }

    public void findEulerPath() {
        if (hasOddNodes()) {
            Integer node = oddNodes.get(0);
            Integer lastNode = oddNodes.get(1);

            while (!graphNodes.isEmpty()) {
                Integer secondNode = findSecondNode(node);
                System.out.println(node + " - " + secondNode);
                removeEdge(node, secondNode);
                node = secondNode;
            }
        }
    }

    private Integer findSecondNode(Integer node) {
        int maxAdj = 0;
        Integer secondNode = null;

        for (Integer connection : graphNodes.get(node)) {
            if (secondNode == null) {
                secondNode = connection;
            }
            int count = graphNodes.get(connection).size();
            if (maxAdj < count) {
                maxAdj = count;
                secondNode = connection;
            }
        }
        return secondNode;
    }

    public boolean hasOddNodes() {
        return oddNodes.size() > 0;
    }

    public static void main(String[] args) {
        var pathFinder = new EulerPathFinder();
        int[][] graph = {{1, 2}, {2, 3}, {3, 1}};
        pathFinder.readGraph(graph);
        pathFinder.findEulerPath();
    }
}

