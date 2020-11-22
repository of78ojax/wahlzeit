package org.wahlzeit.model;

public enum Environment {

    DEFAULT("None"),
    URBAN("Urban"),
    MOUNTAINS("Mountains"),
    FOREST("Forest"),
    BEACH("Beach"),
    OCEAN("Ocean");

    private final String name;       

    private Environment(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        // (otherName == null) check is not needed because name.equals(null) returns false 
        return name.equals(otherName);
    }

    public String toString() {
       return this.name;
    }

    static Environment getEnvironmentFromString(String eString){
        switch (eString) {
            case "None":
                 return DEFAULT;
            case "Urban":
                 return URBAN;
            case "Mountains":
                 return MOUNTAINS;
            case "Forest":
                 return FOREST;
            case "Beach":
                 return BEACH;
            case "Ocean":
                return OCEAN;
            default:
                return DEFAULT;
        }
    }

}