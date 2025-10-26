import json
import time
from typing import List, Dict, Tuple

class UnionFind:
    """Union-Find data structure for Kruskal's algorithm"""
    def __init__(self, vertices):
        self.parent = {v: v for v in vertices}
        self.rank = {v: 0 for v in vertices}
    
    def find(self, x):
        if self.parent[x] != x:
            self.parent[x] = self.find(self.parent[x])
        return self.parent[x]
    
    def union(self, x, y):
        px, py = self.find(x), self.find(y)
        if px == py:
            return False
        if self.rank[px] < self.rank[py]:
            self.parent[px] = py
        elif self.rank[px] > self.rank[py]:
            self.parent[py] = px
        else:
            self.parent[py] = px
            self.rank[px] += 1
        return True

def prim_mst(nodes: List[str], edges: List[Dict]) -> Tuple[List[Dict], int, int]:
    """
    Prim's algorithm for finding MST
    Returns: (mst_edges, total_cost, operations_count)
    """
    operations = 0
    
    # Build adjacency list
    graph = {node: [] for node in nodes}
    for edge in edges:
        graph[edge['from']].append((edge['to'], edge['weight']))
        graph[edge['to']].append((edge['from'], edge['weight']))
        operations += 2
    
    # Initialize
    visited = set()
    mst_edges = []
    total_cost = 0
    
    # Start from first node
    start = nodes[0]
    visited.add(start)
    operations += 1
    
    # Available edges (weight, from, to)
    available_edges = []
    for neighbor, weight in graph[start]:
        available_edges.append((weight, start, neighbor))
        operations += 1
    
    while len(visited) < len(nodes) and available_edges:
        # Find minimum edge
        available_edges.sort()
        operations += len(available_edges)
        
        weight, from_node, to_node = available_edges.pop(0)
        operations += 1
        
        if to_node in visited:
            operations += 1
            continue
        
        # Add edge to MST
        mst_edges.append({"from": from_node, "to": to_node, "weight": weight})
        total_cost += weight
        visited.add(to_node)
        operations += 3
        
        # Add new edges from the newly added node
        for neighbor, edge_weight in graph[to_node]:
            if neighbor not in visited:
                available_edges.append((edge_weight, to_node, neighbor))
                operations += 1
    
    return mst_edges, total_cost, operations

def kruskal_mst(nodes: List[str], edges: List[Dict]) -> Tuple[List[Dict], int, int]:
    """
    Kruskal's algorithm for finding MST
    Returns: (mst_edges, total_cost, operations_count)
    """
    operations = 0
    
    # Sort edges by weight
    sorted_edges = sorted(edges, key=lambda x: x['weight'])
    operations += len(edges)
    
    uf = UnionFind(nodes)
    mst_edges = []
    total_cost = 0
    
    for edge in sorted_edges:
        operations += 1
        from_node = edge['from']
        to_node = edge['to']
        weight = edge['weight']
        
        # Check if adding this edge creates a cycle
        if uf.find(from_node) != uf.find(to_node):
            operations += 2
            uf.union(from_node, to_node)
            mst_edges.append({"from": from_node, "to": to_node, "weight": weight})
            total_cost += weight
            operations += 3
            
            if len(mst_edges) == len(nodes) - 1:
                break
    
    return mst_edges, total_cost, operations

def process_graph(graph_data: Dict) -> Dict:
    """Process a single graph with both algorithms"""
    graph_id = graph_data['id']
    nodes = graph_data['nodes']
    edges = graph_data['edges']
    
    result = {
        "graph_id": graph_id,
        "input_stats": {
            "vertices": len(nodes),
            "edges": len(edges)
        }
    }
    
    # Run Prim's algorithm
    start_time = time.perf_counter()
    prim_edges, prim_cost, prim_ops = prim_mst(nodes, edges)
    prim_time = (time.perf_counter() - start_time) * 1000
    
    result["prim"] = {
        "mst_edges": prim_edges,
        "total_cost": prim_cost,
        "operations_count": prim_ops,
        "execution_time_ms": round(prim_time, 2)
    }
    
    # Run Kruskal's algorithm
    start_time = time.perf_counter()
    kruskal_edges, kruskal_cost, kruskal_ops = kruskal_mst(nodes, edges)
    kruskal_time = (time.perf_counter() - start_time) * 1000
    
    result["kruskal"] = {
        "mst_edges": kruskal_edges,
        "total_cost": kruskal_cost,
        "operations_count": kruskal_ops,
        "execution_time_ms": round(kruskal_time, 2)
    }
    
    return result

def main():
    # Read input file
    with open('ass_3_input.json', 'r') as f:
        input_data = json.load(f)
    
    # Process all graphs
    results = []
    for graph in input_data['graphs']:
        result = process_graph(graph)
        results.append(result)
        
        # Print results
        print(f"\n=== Graph {result['graph_id']} ===")
        print(f"Vertices: {result['input_stats']['vertices']}, Edges: {result['input_stats']['edges']}")
        print(f"\nPrim's Algorithm:")
        print(f"  Total Cost: {result['prim']['total_cost']}")
        print(f"  Operations: {result['prim']['operations_count']}")
        print(f"  Time: {result['prim']['execution_time_ms']} ms")
        print(f"  MST Edges: {result['prim']['mst_edges']}")
        print(f"\nKruskal's Algorithm:")
        print(f"  Total Cost: {result['kruskal']['total_cost']}")
        print(f"  Operations: {result['kruskal']['operations_count']}")
        print(f"  Time: {result['kruskal']['execution_time_ms']} ms")
        print(f"  MST Edges: {result['kruskal']['mst_edges']}")
    
    # Save output
    output = {"results": results}
    with open('ass_3_output.json', 'w') as f:
        json.dump(output, f, indent=2)
    
    print("\n✓ Results saved to ass_3_output.json")

if __name__ == "__main__":
    main()
