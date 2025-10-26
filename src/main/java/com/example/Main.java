package com.example;

import algorithm.KruskalAlgorithm;
import algorithm.PrimAlgorithm;
import model.Edge;
import model.Graph;
import model.MSTResult;
import util.JSONReader;
import util.JSONWriter;
import analysis.PerformanceAnalyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║   ASSIGNMENT 3: MST OPTIMIZATION - CITY TRANSPORTATION    ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");

        String inputFile = "src/main/resources/ass_3_input.json";
        List<Graph> graphs = JSONReader.readGraphs(inputFile);

        if (graphs.isEmpty()) {
            System.err.println("❌ ERROR: No graphs loaded from " + inputFile);
            System.err.println("Please check if the file exists and is properly formatted.");
            return;
        }

        System.out.println("✅ Successfully loaded " + graphs.size() + " graph(s)\n");

        List<Map<String, Object>> results = new ArrayList<>();

        for (Graph graph : graphs) {
            PrimAlgorithm prim = new PrimAlgorithm();
            MSTResult primResult = prim.findMST(graph);

            KruskalAlgorithm kruskal = new KruskalAlgorithm();
            MSTResult kruskalResult = kruskal.findMST(graph);

            PerformanceAnalyzer.printComparison(graph, primResult, kruskalResult);

            Map<String, Object> graphResult = new HashMap<>();
            graphResult.put("graph_id", graph.getId());

            Map<String, Object> inputStats = new HashMap<>();
            inputStats.put("vertices", graph.getVertexCount());
            inputStats.put("edges", graph.getEdgeCount());
            graphResult.put("input_stats", inputStats);

            graphResult.put("prim", formatResult(primResult));
            graphResult.put("kruskal", formatResult(kruskalResult));

            results.add(graphResult);
        }

        if (graphs.size() > 1) {
            PerformanceAnalyzer.printOverallStatistics(results);
        }

        PerformanceAnalyzer.printRecommendations();

        String outputFile = "src/main/resources/ass_3_output.json";
        JSONWriter.writeResults(outputFile, results);

        System.out.println("✅ Program completed successfully!");
        System.out.println("📄 Results saved to: " + outputFile);
    }

    private static Map<String, Object> formatResult(MSTResult result) {
        Map<String, Object> formatted = new HashMap<>();

        List<Map<String, Object>> edges = new ArrayList<>();
        for (Edge edge : result.getMstEdges()) {
            Map<String, Object> edgeMap = new HashMap<>();
            edgeMap.put("from", edge.getFrom());
            edgeMap.put("to", edge.getTo());
            edgeMap.put("weight", edge.getWeight());
            edges.add(edgeMap);
        }

        formatted.put("mst_edges", edges);
        formatted.put("total_cost", result.getTotalCost());
        formatted.put("operations_count", result.getOperationsCount());
        formatted.put("execution_time_ms",
                Math.round(result.getExecutionTimeMs() * 100.0) / 100.0);

        return formatted;
    }
}
