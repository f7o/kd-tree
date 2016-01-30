package de.tu_darmstadt.gris.edge_bundling.util;

/**
 * Created by flo on 29.01.2016.
 */
@FunctionalInterface
public interface AxisComparator<K> {

    /**
     * @param v1 obj1
     * @param v2 obj2
     * @param dim dimension to compare
     * @return 0 if equal, -1 if (v1 <= v2), 1 if (v2 > v1)
     */
    int compare(K v1, K v2, int dim);

}
