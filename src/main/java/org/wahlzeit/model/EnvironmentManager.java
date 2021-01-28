package org.wahlzeit.model;

import java.util.HashMap;

public class EnvironmentManager {
    static private HashMap<Integer,Environment> environments = new HashMap<Integer,Environment>();
    

    // Entry point for creating an Environment 
    static public Environment createEnvironment(String envName){
        EnvironmentType eType = EnvironmentType.getEnvironmentType(envName);
        Environment env = eType.createEnvironmentInstance();
        environments.put(env.getId(), env);
        return env;
    }


    static boolean hasID(int id){
        return environments.containsKey(id);
    }

    static Environment getEnvironment(int id){
        return environments.get(id);
    }
}
