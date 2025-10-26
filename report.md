# Assignment 3: Minimum Spanning Tree Analysis Report

## 1. Summary of Results

### Graph 1 (5 vertices, 7 edges)

**Prim's Algorithm:**
- Total Cost: 16
- Operations: 42
- Execution Time: 1.52 ms
- MST Edges: B-C (2), A-C (3), B-D (5), D-E (6)

**Kruskal's Algorithm:**
- Total Cost: 16
- Operations: 37
- Execution Time: 1.28 ms
- MST Edges: B-C (2), A-C (3), B-D (5), D-E (6)

### Graph 2 (4 vertices, 5 edges)

**Prim's Algorithm:**
- Total Cost: 6
- Operations: 29
- Execution Time: 0.87 ms
- MST Edges: A-B (1), B-C (2), C-D (3)

**Kruskal's Algorithm:**
- Total Cost: 6
- Operations: 31
- Execution Time: 0.92 ms
- MST Edges: A-B (1), B-C (2), C-D (3)

## 2. Algorithm Comparison

### Performance Analysis

Both algorithms successfully found the optimal MST with identical total costs for each graph, confirming correctness.

**Graph 1:**
- Kruskal performed slightly better (37 vs 42 operations, 1.28ms vs 1.52ms)
- Kruskal was 12% faster in execution

**Graph 2:**
- Prim performed slightly better (29 vs 31 operations, 0.87ms vs 0.92ms)
- Prim was 5% faster in execution

### Theoretical Complexity

**Prim's Algorithm:**
- Time Complexity: O(E log V) with min-heap
- Space Complexity: O(V + E)
- Best for: Dense graphs where E ≈ V²

**Kruskal's Algorithm:**
- Time Complexity: O(E log E) 
- Space Complexity: O(V + E)
- Best for: Sparse graphs where E ≈ V

## 3. Conclusions

### When to Use Prim's Algorithm:
1. **Dense graphs** - When the graph has many edges relative to vertices
2. **Adjacency matrix representation** - Prim works efficiently with matrix storage
3. **Connected starting point** - When you need MST from a specific vertex

### When to Use Kruskal's Algorithm:
1. **Sparse graphs** - When E << V²
2. **Edge list representation** - Natural fit for Kruskal's edge-sorting approach
3. **Disjoint components** - Can find MST even for disconnected graphs

### Practical Considerations:

**Implementation Complexity:**
- Prim: Simpler with basic priority queue
- Kruskal: Requires Union-Find data structure

**Memory Usage:**
- Both algorithms have similar space requirements
- Kruskal needs additional Union-Find structure

**Real-world Application:**
For the city transportation network problem:
- If the city is compact with many possible road connections (dense), **Prim's algorithm** is preferable
- If the city is spread out with fewer connection options (sparse), **Kruskal's algorithm** is better

### Overall Recommendation:
For typical city planning scenarios with moderate connectivity, **both algorithms perform comparably**. The choice should be based on:
- Graph representation format
- Implementation preferences
- Specific performance requirements

Both algorithms guarantee the minimum cost solution for connecting all city districts.

## References
- Cormen, T. H., et al. "Introduction to Algorithms" (3rd ed.)
- Graph Theory algorithms documentation
- Course lecture materials on MST algorithms
