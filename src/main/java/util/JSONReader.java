package util;

import com.google.gson.*;
import model.*;
import java.io.*;
import java.util.*;

public class JSONReader {
    public static List<Graph> readGraphs(String filename) {
        try (Reader reader = new FileReader(filename)) {
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            JsonArray graphsArray = json.getAsJsonArray("graphs");

            List<Graph> graphs = new ArrayList<>();

            for (JsonElement graphElement : graphsArray) {
                JsonObject graphObj = graphElement.getAsJsonObject();
                int id = graphObj.get("id").getAsInt();

                // Read nodes
                List<String> nodes = new ArrayList<>();
                for (JsonElement node : graphObj.getAsJsonArray("nodes")) {
                    nodes.add(node.getAsString());
                }

                // Read edges
                List<Edge> edges = new ArrayList<>();
                for (JsonElement edgeElement : graphObj.getAsJsonArray("edges")) {
                    JsonObject edgeObj = edgeElement.getAsJsonObject();
                    String from = edgeObj.get("from").getAsString();
                    String to = edgeObj.get("to").getAsString();
                    int weight = edgeObj.get("weight").getAsInt();
                    edges.add(new Edge(from, to, weight));
                }

                graphs.add(new Graph(id, nodes, edges));
            }

            return graphs;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
