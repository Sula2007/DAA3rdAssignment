package analysis;

import model.*;
import java.util.*;

public class PerformanceAnalyzer {
    
    public static void printComparison(Graph graph, MSTResult primResult, MSTResult kruskalResult) {
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println("                    GRAPH " + graph.getId() + " ANALYSIS");
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println("📊 Input Statistics:");
        System.out.println("   • Vertices: " + graph.getVertexCount());
        System.out.println("   • Edges: " + graph.getEdgeCount());
        System.out.println("   • Graph Density: " + calculateDensity(graph) + "%");
        System.out.println();
        
        System.out.println("┌─────────────────────────────────────────────────────────┐");
        System.out.println("│                  PRIM'S ALGORITHM                       │");
        System.out.println("└─────────────────────────────────────────────────────────┘");
        printMSTResult(primResult);
        
        System.out.println("┌─────────────────────────────────────────────────────────┐");
        System.out.println("│                KRUSKAL'S ALGORITHM                      │");
        System.out.println("└─────────────────────────────────────────────────────────┘");
        printMSTResult(kruskalResult);
        
        System.out.println("┌─────────────────────────────────────────────────────────┐");
        System.out.println("│              PERFORMANCE COMPARISON                     │");
        System.out.println("└─────────────────────────────────────────────────────────┘");
        
        System.out.println("✓ Total MST Cost:");
        System.out.println("   Prim:    " + primResult.getTotalCost());
        System.out.println("   Kruskal: " + kruskalResult.getTotalCost());
        if (primResult.getTotalCost() == kruskalResult.getTotalCost()) {
            System.out.println("   ✅ Both algorithms found optimal solution!");
        }
        System.out.println();
        
        long primOps = primResult.getOperationsCount();
        long kruskalOps = kruskalResult.getOperationsCount();
        System.out.println("📈 Operations Count:");
        System.out.println("   Prim:    " + primOps + " operations");
        System.out.println("   Kruskal: " + kruskalOps + " operations");
        
        if (primOps < kruskalOps) {
            double improvement = ((kruskalOps - primOps) * 100.0) / kruskalOps;
            System.out.println("   🏆 Prim is " + String.format("%.1f", improvement) + "% more efficient");
        } else if (kruskalOps < primOps) {
            double improvement = ((primOps - kruskalOps) * 100.0) / primOps;
            System.out.println("   🏆 Kruskal is " + String.format("%.1f", improvement) + "% more efficient");
        }
        System.out.println();
        
        double primTime = primResult.getExecutionTimeMs();
        double kruskalTime = kruskalResult.getExecutionTimeMs();
        System.out.println("⏱️  Execution Time:");
        System.out.println("   Prim:    " + String.format("%.2f", primTime) + " ms");
        System.out.println("   Kruskal: " + String.format("%.2f", kruskalTime) + " ms");
        
        if (primTime < kruskalTime) {
            double speedup = ((kruskalTime - primTime) * 100.0) / kruskalTime;
            System.out.println("   🚀 Prim is " + String.format("%.1f", speedup) + "% faster");
        } else if (kruskalTime < primTime) {
            double speedup = ((primTime - kruskalTime) * 100.0) / primTime;
            System.out.println("   🚀 Kruskal is " + String.format("%.1f", speedup) + "% faster");
        }
        System.out.println("\n");
    }
    
    private static void printMSTResult(MSTResult result) {
        System.out.println("   Total Cost: " + result.getTotalCost());
        System.out.println("   Operations: " + result.getOperationsCount());
        System.out.println("   Time: " + String.format("%.2f", result.getExecutionTimeMs()) + " ms");
        System.out.println("   MST Edges:");
        for (Edge edge : result.getMstEdges()) {
            System.out.println("      • " + edge.getFrom() + " ─[" + edge.getWeight() + "]─ " + edge.getTo());
        }
        System.out.println();
    }
    
    private static double calculateDensity(Graph graph) {
        int v = graph.getVertexCount();
        int e = graph.getEdgeCount();
        int maxEdges = (v * (v - 1)) / 2;
        return Math.round((e * 100.0) / maxEdges * 10.0) / 10.0;
    }
    
    public static void printOverallStatistics(List<Map<String, Object>> results) {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║              OVERALL PERFORMANCE SUMMARY                   ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
        
        int primWinsOps = 0;
        int kruskalWinsOps = 0;
        int primWinsTime = 0;
        int kruskalWinsTime = 0;
        
        for (Map<String, Object> result : results) {
            @SuppressWarnings("unchecked")
            Map<String, Object> prim = (Map<String, Object>) result.get("prim");
            @SuppressWarnings("unchecked")
            Map<String, Object> kruskal = (Map<String, Object>) result.get("kruskal");
            
            long primOps = ((Number) prim.get("operations_count")).longValue();
            long kruskalOps = ((Number) kruskal.get("operations_count")).longValue();
            
            if (primOps < kruskalOps) primWinsOps++;
            else if (kruskalOps < primOps) kruskalWinsOps++;
            
            double primTime = ((Number) prim.get("execution_time_ms")).doubleValue();
            double kruskalTime = ((Number) kruskal.get("execution_time_ms")).doubleValue();
            
            if (primTime < kruskalTime) primWinsTime++;
            else if (kruskalTime < primTime) kruskalWinsTime++;
        }
        
        System.out.println("📊 Algorithm Performance Across All Graphs:");
        System.out.println("   Total Graphs Tested: " + results.size());
        System.out.println();
        System.out.println("   Operations Efficiency:");
        System.out.println("      Prim won:    " + primWinsOps + " time(s)");
        System.out.println("      Kruskal won: " + kruskalWinsOps + " time(s)");
        System.out.println();
        System.out.println("   Execution Speed:");
        System.out.println("      Prim won:    " + primWinsTime + " time(s)");
        System.out.println("      Kruskal won: " + kruskalWinsTime + " time(s)");
        System.out.println();
    }
    
    public static void printRecommendations() {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                  RECOMMENDATIONS                           ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
        
        System.out.println("📌 When to use PRIM'S Algorithm:");
        System.out.println("   ✓ Dense graphs (many edges relative to vertices)");
        System.out.println("   ✓ When using adjacency matrix representation");
        System.out.println("   ✓ Need MST from specific starting vertex");
        System.out.println("   ✓ Graph density > 50%");
        System.out.println();
        
        System.out.println("📌 When to use KRUSKAL'S Algorithm:");
        System.out.println("   ✓ Sparse graphs (few edges relative to vertices)");
        System.out.println("   ✓ When using edge list representation");
        System.out.println("   ✓ Can work with disconnected graphs");
        System.out.println("   ✓ Graph density < 50%");
        System.out.println();
        
        System.out.println("💡 General Observations:");
        System.out.println("   • Both algorithms guarantee optimal MST");
        System.out.println("   • Performance difference is minimal for small graphs");
        System.out.println("   • Choice depends on graph structure and representation");
        System.out.println("   • For city planning: consider graph density and size");
        System.out.println();
    }
}
