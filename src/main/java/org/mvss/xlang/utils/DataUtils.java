package org.mvss.xlang.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Utility class to work with generic maps, lists and other vectors
 *
 * @author Manian
 */
@SuppressWarnings("unused")
public class DataUtils {
    /**
     * Create a shallow clone of a HashMap
     */
    public static <K, V> HashMap<K, V> cloneMap(Map<K, V> source) {
        HashMap<K, V> clone = new HashMap<>();

        if (source != null) {
            clone.putAll(source);
        }
        return clone;
    }

    /**
     * Create a shallow clone of a ConcurrentHashMap
     */
    public static <K, V> ConcurrentHashMap<K, V> cloneConcurrentMap(Map<K, V> source) {
        ConcurrentHashMap<K, V> clone = new ConcurrentHashMap<>();

        if (source != null) {
            clone.putAll(source);
        }
        return clone;
    }
}
