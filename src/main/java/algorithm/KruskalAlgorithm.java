package algorithm;

import model.*;
import java.util.*;

public class KruskalAlgorithm {
    private long operationsCount = 0;
    private Map<String, String> parent;
    private Map<String, Integer> rank;

    public MSTResult findMST(Graph graph) {
        long startTime = System.nanoTime();
        operationsCount = 0;

        List<Edge> mstEdges = new ArrayList<>();
        List<Edge> sortedEdges = new ArrayList<>(graph.getEdges());
        Collections.sort(sortedEdges);
        operationsCount += sortedEdges.size() * Math.log(sortedEdges.size());

        parent = new HashMap<>();
        rank = new HashMap<>();
        for (String node : graph.getNodes()) {
            parent.put(node, node);
            rank.put(node, 0);
            operationsCount += 2;
        }

        int totalCost = 0;

        for (Edge edge : sortedEdges) {
            String rootFrom = find(edge.getFrom());
            String rootTo = find(edge.getTo());

            operationsCount++; // comparison

            if (!rootFrom.equals(rootTo)) {
                mstEdges.add(edge);
                totalCost += edge.getWeight();
                union(rootFrom, rootTo);
                operationsCount++; // add operation
            }
        }

        long endTime = System.nanoTime();
        double executionTime = (endTime - startTime) / 1_000_000.0;

        return new MSTResult(mstEdges, totalCost, operationsCount, executionTime);
    }

    private String find(String node) {
        operationsCount++; // find operation
        if (!parent.get(node).equals(node)) {
            parent.put(node, find(parent.get(node))); // Path compression
        }
        return parent.get(node);
    }

    private void union(String root1, String root2) {
        operationsCount++; // union operation
        if (rank.get(root1) > rank.get(root2)) {
            parent.put(root2, root1);
        } else if (rank.get(root1) < rank.get(root2)) {
            parent.put(root1, root2);
        } else {
            parent.put(root2, root1);
            rank.put(root1, rank.get(root1) + 1);
        }
    }
}
