package algorithm;

import model.*;
import java.util.*;

public class PrimAlgorithm {
    private long operationsCount = 0;

    public MSTResult findMST(Graph graph) {
        long startTime = System.nanoTime();
        operationsCount = 0;

        List<Edge> mstEdges = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>();

        // Start from first vertex
        String startNode = graph.getNodes().get(0);
        visited.add(startNode);
        operationsCount++; // add to visited

        // Add all edges from first vertex to queue
        for (Edge edge : graph.getAdjacencyList().get(startNode)) {
            pq.offer(edge);
            operationsCount++; // offer operation
        }

        int totalCost = 0;

        while (!pq.isEmpty() && visited.size() < graph.getVertexCount()) {
            Edge edge = pq.poll();
            operationsCount++; // poll operation

            operationsCount++; // contains check
            if (visited.contains(edge.getTo())) {
                continue;
            }

            mstEdges.add(edge);
            totalCost += edge.getWeight();
            visited.add(edge.getTo());
            operationsCount += 2; // add operations

            for (Edge nextEdge : graph.getAdjacencyList().get(edge.getTo())) {
                operationsCount++; // contains check
                if (!visited.contains(nextEdge.getTo())) {
                    pq.offer(nextEdge);
                    operationsCount++; // offer operation
                }
            }
        }

        long endTime = System.nanoTime();
        double executionTime = (endTime - startTime) / 1_000_000.0;

        return new MSTResult(mstEdges, totalCost, operationsCount, executionTime);
    }
}
