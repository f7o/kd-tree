package de.f7o.kd_tree.util;

/**
 * Created by flo on 29.01.2016.
 */
@FunctionalInterface
public interface Metric<V> {
    Double distance(V val1, V val2);
}
