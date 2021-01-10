package org.wahlzeit.model;
import java.util.Objects;
import java.util.HashMap;

final class CoordinateData {
    final double x;
    final double y;
    final double z;
    private static HashMap<Integer, CoordinateData> map = new HashMap<Integer, CoordinateData>();

    static CoordinateData getCoordinateData(double x, double y, double z) {
        double rx = (double)(Math.round(x *10000000d) / 10000000d);
        double ry = (double)(Math.round(y *10000000d) / 10000000d);
        double rz = (double)(Math.round(z *10000000d) / 10000000d);

        int hash = hash(rx, ry, rz);
        CoordinateData res = map.get(hash);
        if (res == null) {
            res = new CoordinateData(rx, ry, rz);
            map.put(hash, res);
        }
        return res;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }

    @Override
    public int hashCode() {
        return hash(x, y, z);
    }
    static private int hash(double x, double y, double z){
        return Objects.hash(x, y, z);
    }

    protected CoordinateData(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;

    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

}
