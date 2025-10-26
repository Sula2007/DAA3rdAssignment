import json
import time

# ---------- Helper Classes ----------
class DisjointSet:
    def __init__(self, vertices):
        self.parent = {v: v for v in vertices}
        self.rank = {v: 0 for v in vertices}

    def find(self, item):
        if self.parent[item] != item:
            self.parent[item] = self.find(self.parent[item])
        return self.parent[item]

    def union(self, set1, set2):
        root1 = self.find(set1)
        root2 = self.find(set2)
        if root1 != root2:
            if self.rank[root1] < self.rank[root2]:
                self.parent[root1] = root2
            elif self.rank[root1] > self.rank[root2]:
                self.parent[root2] = root1
            else:
                self.parent[root2] = root1
                self.rank[root1] += 1

# ---------- Kruskal Algorithm ----------
def kruskal(graph):
    edges = sorted(graph["edges"], key=lambda e: e["weight"])
    ds = DisjointSet(graph["nodes"])
    mst = []
    total_cost = 0
    operations = 0

    start_time = time.time()

    for edge in edges:
        operations += 1
        u, v = edge["from"], edge["to"]
        if ds.find(u) != ds.find(v):
            ds.union(u, v)
            mst.append(edge)
            total_cost += edge["weight"]

    execution_time = (time.time() - start_time) * 1000
    return mst, total_cost, operations, round(execution_time, 2)

# ---------- Prim Algorithm ----------
def prim(graph):
    import heapq
    nodes = graph["nodes"]
    edges = graph["edges"]
    adj = {v: [] for v in nodes}
    for e in edges:
        adj[e["from"]].append((e["weight"], e["to"]))
        adj[e["to"]].append((e["weight"], e["from"]))

    start_time = time.time()
    visited = set()
    mst = []
    total_cost = 0
    operations = 0
    heap = [(0, nodes[0], None)]

    while heap:
        weight, node, prev = heapq.heappop(heap)
        if node not in visited:
            visited.add(node)
            if prev:
                mst.append({"from": prev, "to": node, "weight": weight})
                total_cost += weight
            for w, to in adj[node]:
                operations += 1
                if to not in visited:
                    heapq.heappush(heap, (w, to, node))

    execution_time = (time.time() - start_time) * 1000
    return mst, total_cost, operations, round(execution_time, 2)

# ---------- Main ----------
with open("input.json", "r") as f:
    data = json.load(f)

results = []

for g in data["graphs"]:
    prim_mst, prim_cost, prim_ops, prim_time = prim(g)
    kruskal_mst, kruskal_cost, kruskal_ops, kruskal_time = kruskal(g)

    results.append({
        "graph_id": g["id"],
        "input_stats": {
            "vertices": len(g["nodes"]),
            "edges": len(g["edges"])
        },
        "prim": {
            "mst_edges": prim_mst,
            "total_cost": prim_cost,
            "operations_count": prim_ops,
            "execution_time_ms": prim_time
        },
        "kruskal": {
            "mst_edges": kruskal_mst,
            "total_cost": kruskal_cost,
            "operations_count": kruskal_ops,
            "execution_time_ms": kruskal_time
        }
    })

with open("results.json", "w") as f:
    json.dump({"results": results}, f, indent=2)

print("✅ Results saved to results.json")

if __name__ == "__main__":
    main()
