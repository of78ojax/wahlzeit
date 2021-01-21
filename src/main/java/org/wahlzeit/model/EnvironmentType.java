package org.wahlzeit.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class EnvironmentType {
    static protected Map<String, EnvironmentType> types = new HashMap<String, EnvironmentType>();

    static EnvironmentType getEnvironmentType(String name) {
        EnvironmentType res = types.get(name);
        if (res == null) {
            EnvironmentType t = new EnvironmentType(name);
            res = t;
            types.put(name, t);
        }
        return res;
    }

    String typename;
    protected EnvironmentType superType = null;
    protected Set<EnvironmentType> subTypes = new HashSet<EnvironmentType>();

    private EnvironmentType(String name) {
        typename = name;
    }

    @Override
    public int hashCode() {
        return typename.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof EnvironmentType))
            return false;

        return this.typename == (((EnvironmentType) obj).typename);
    }

    public boolean isSubtype(EnvironmentType eType) {
        EnvironmentType currentType = this;
        while ((currentType = currentType.superType) != null) {
            if (currentType.equals(eType))
                return true;
        }
        return false;
    }

    public String getName(){
        return typename;
    }

    public Environment createEnvironmentInstance(){
        return new Environment(this);
    }

    public EnvironmentType getSuperType() {
        return superType;
    }

    public void setSuperType(EnvironmentType eType) {
        superType = eType;
    }

    public Iterator<EnvironmentType> getSubTypeIterator() {
        return subTypes.iterator();
    }

    public void addSubType(EnvironmentType eType) {
        assert (eType != null);
        eType.setSuperType(this);
        subTypes.add(eType);
    }
}
