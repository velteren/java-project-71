package hexlet.code;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Comparator;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Differ {

    public static HashMap<String, Object> getMapFromJSON(String path) throws Exception {
        Path filePath = Paths.get(path).toAbsolutePath().normalize();
        ObjectMapper mapper = new ObjectMapper();
        var map = new HashMap<String, Object>();
        map = mapper.readValue(new File(filePath.toString()), new TypeReference<HashMap<String, Object>>() {

        });
        return map;
    }

    public static String generate(HashMap<String, Object> map1, HashMap<String, Object> map2) {
        HashMap<String, Object> result = new LinkedHashMap<>();
        SortedSet<String> set = new TreeSet<>(Comparator.naturalOrder());
        set.addAll(map1.keySet());
        set.addAll(map2.keySet());
        for (var item: set) {
            if (map1.containsKey(item) & map2.containsKey(item)) {
                if (map1.get(item).equals(map2.get(item))) {
                    result.put(item, map1.get(item));
                } else {
                    result.put("- " + item, map1.get(item));
                    result.put("+ " + item, map2.get(item));
                }
            } else if (map1.containsKey(item) & !map2.containsKey(item)) {
                result.put("- " + item, map1.get(item));
            } else {
                result.put("+ " + item, map2.get(item));
            }
        }
        String resultStr = "{\n";
        for (var item: result.entrySet()) {
            if (item.getKey().startsWith("-") || item.getKey().startsWith("+")) {
                resultStr += "  " + item.getKey() + ": " + item.getValue() + "\n";
            } else {
                resultStr += "    " + item.getKey() + ": " + item.getValue() + "\n";
            }
        }
        resultStr += "}";
        return resultStr;
    }
}
