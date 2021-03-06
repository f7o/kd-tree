package de.f7o.kd_tree.example;



import de.f7o.kd_tree.KDTree;
import de.f7o.kd_tree.util.AxisComparator;
import de.f7o.kd_tree.util.AxisValueResolver;
import de.f7o.kd_tree.util.Metric;




/**
 * Created by flo on 29.01.2016.
 * Min. Java 1.8
 *
 * this is just an Example test with an 4D KD Tree (possibly every dimension)
 * i implemented this cause i needed an fully generic kd-tree
 *
 * instead of vector4d u could use anything u like, if u get it to implement the metric and compare function
 */
public class Example {
    public static void main(String[] args) {
        Metric<Vector4d> euclidDist4d = (v1, v2) -> {
            Double diff0 = v1.x - v2.x;
            Double diff1 = v1.y - v2.y;
            Double diff2 = v1.z - v2.z;
            Double diff3 = v1.w - v2.w;
            return Math.sqrt(diff0 * diff0 + diff1 * diff1 + diff2 * diff2 + diff3 * diff3);
        };
        AxisComparator<Vector4d> comp4d = (v1, v2, k) -> {
            switch (k) {
                case 0:
                    return Double.compare(v1.x, v2.x);
                case 1:
                    return Double.compare(v1.y, v2.y);
                case 2:
                    return Double.compare(v1.z, v2.z);
                case 3:
                    return Double.compare(v1.w, v2.w);
                default:
                    return 0;
            }
        };
        AxisValueResolver<Vector4d> getAxisVal = (v, axis) -> {
            switch(axis) {
                case 0: return v.x;
                case 1: return v.y;
                case 2: return v.z;
                case 3: return v.w;
                default: return 0.0;
            }
        };

        KDTree<Vector4d> edgeTree = new KDTree<>(euclidDist4d, comp4d, getAxisVal, 4);

        Vector4d tmp = new Vector4d(1.0, 2.0, 3.0, 4.0);

        /**
         * here you could add your nodes..
         */
        edgeTree.addNode(tmp);

        /**
         * returns collection of k nearest
         */
        edgeTree.findKNearestNeighbors(tmp, 3);
    }
}
