package model;

import java.util.List;

public class MSTResult {
    private List<Edge> mstEdges;
    private int totalCost;
    private long operationsCount;
    private double executionTimeMs;

    public MSTResult(List<Edge> mstEdges, int totalCost,
                     long operationsCount, double executionTimeMs) {
        this.mstEdges = mstEdges;
        this.totalCost = totalCost;
        this.operationsCount = operationsCount;
        this.executionTimeMs = executionTimeMs;
    }

    public List<Edge> getMstEdges() { return mstEdges; }
    public int getTotalCost() { return totalCost; }
    public long getOperationsCount() { return operationsCount; }
    public double getExecutionTimeMs() { return executionTimeMs; }
}
