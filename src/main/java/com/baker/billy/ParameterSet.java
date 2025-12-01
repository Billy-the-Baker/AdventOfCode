package com.baker.billy;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ParameterSet {
    private Map<String, String> params;
    private static Set<String> FLAGS = Set.of("--all");

    public ParameterSet(String[] args) {
        params = new HashMap<>();

        if(args == null || args.length == 0) {
            return;
        }

        // More than one argument was provided
        if(args.length > 1) {
            for(int i = 0; i < args.length; i++) {
                String arg = args[i];

                if(!arg.startsWith("--")) {
                    // Bad syntax
                    continue;
                }

                if(FLAGS.contains(arg)) {
                    params.put(arg, "true");
                } else {
                    // Ignore the look ahead if it is not properly formatted
                    String lookAhead = args[i + 1];
                    if(!lookAhead.startsWith("--")) {
                        // No overwriting if the parameter is already in the map
                        params.putIfAbsent(arg, lookAhead);
                    }
                }
            }
        } else {
            params.put(args[0], "true");
        }
    }

    public boolean contains(String key) {
        return params.containsKey(key);
    }

    public String getParameter(String key) {
        return params.get(key);
    }
}
