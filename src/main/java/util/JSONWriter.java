package util;

import com.google.gson.*;
import java.io.*;
import java.util.*;

public class JSONWriter {
    public static void writeResults(String filename, List<Map<String, Object>> results) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Map<String, Object> output = new HashMap<>();
        output.put("results", results);

        try (Writer writer = new FileWriter(filename)) {
            gson.toJson(output, writer);
            System.out.println("✅ Results saved to: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
