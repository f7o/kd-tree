package de.f7o.kd_tree.util;

/**
 * Created by flo on 29.01.2016.
 */
@FunctionalInterface
public interface AxisValueResolver<K> {



    Double getAxisVal(K v, int axis);
}
