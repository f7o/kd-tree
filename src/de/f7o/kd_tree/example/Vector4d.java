package de.f7o.kd_tree.example;

/**
 * Created by flo on 26.01.2016.
 */
public class Vector4d {

    protected Double x, y, z, w;

    public Vector4d(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;

    }

    public Double distance(Vector4d v) {
        Double diff0 = x - v.x;
        Double diff1 = y - v.y;
        Double diff2 = z - v.z;
        Double diff3 = w - v.w;

        return Math.sqrt(diff0 * diff0 + diff1 * diff1 + diff2 * diff2 + diff3 * diff3);
    }

}
