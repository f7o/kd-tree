package de.tu_darmstadt.gris.edge_bundling.util;

/**
 * Created by flo on 29.01.2016.
 */
public class KDTreeNode<K> {
    private K key;
    private int depth = 0;
    private static int k = 0;
    private KDTreeNode<K> parent = null;
    private KDTreeNode<K> lesser = null;
    private KDTreeNode<K> greater = null;


    public KDTreeNode(K key, int k) {
        this.key = key;
        this.k = k;
    }

    public KDTreeNode(K key, int depth, int k) {
        this(key, k);
        this.depth = depth;
    }

    public KDTreeNode<K> getParent() {
        return parent;
    }

    public void setParent(KDTreeNode<K> parent) {
        this.parent = parent;
    }

    public KDTreeNode<K> getLesser() {
        return lesser;
    }

    public void setLesser(KDTreeNode<K> lesser) {
        this.lesser = lesser;
    }

    public KDTreeNode<K> getGreater() {
        return greater;
    }

    public void setGreater(KDTreeNode<K> greater) {
        this.greater = greater;
    }

    public int getDepth() {
        return depth;
    }

    public K getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "KDTreeNode{" +
                "depth=" + depth +
                ", key=" + key +
                '}';
    }
}
