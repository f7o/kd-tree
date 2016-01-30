package de.tu_darmstadt.gris.edge_bundling.util;

/**
 * Created by flo on 29.01.2016.
 */
@FunctionalInterface
public interface AxisValueResolver<K> {



    Double getAxisVal(K v, int axis);
}
