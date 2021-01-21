package org.wahlzeit.model;

public class Environment {
    static private int IDcounter = 0;
    private int id;
    private EnvironmentType type = null;


    public Environment(EnvironmentType type){
        id = IDcounter++;
        this.type = type;
    }

    public EnvironmentType getType(){
        return type;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return type.getName();
    }

    public boolean haveSameType(Environment other){
        return this.type.equals(other.type);
    }

}