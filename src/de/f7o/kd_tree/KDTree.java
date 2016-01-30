package de.tu_darmstadt.gris.edge_bundling.util;

import java.util.*;

/**
 * Created by flo on 29.01.2016.
 */
public class KDTree<V> {
    private KDTreeNode<V> root;
    private Metric<V> metric;
    private AxisComparator<V> axisComparator;
    //max. k=4
    private Integer k;


    public KDTree(Metric<V> metric, AxisComparator<V> axisComparator, Integer k) {
        this.metric = metric;
        this.axisComparator = axisComparator;
        this.k = k;
    }

    public boolean addNode(V val) {
        if(val == null) return false;
        if(this.root == null) {
            this.root = new KDTreeNode<V>(val, this.k);
            return true;
        }

        KDTreeNode<V> node = this.root;

        while(true) {
            int axis = node.getDepth() % k;
            //System.out.println(this.axisComparator.compare(node.getKey(), val, axis) + "<>" + node.getKey()+ "<>" + val + "<>" + axis);
            if(this.axisComparator.compare(node.getKey(), val, axis) >= 0) {

                //lesser
                if(node.getLesser() == null) {
                    KDTreeNode<V> newNode = new KDTreeNode<>(val, node.getDepth() +1, k);
                    newNode.setParent(node);
                    node.setLesser(newNode);
                    System.out.println(newNode);
                    break;
                }
                node = node.getLesser();
            } else {
                //greater
                if(node.getGreater() == null) {
                    KDTreeNode<V> newNode = new KDTreeNode<>(val, node.getDepth() +1, k);
                    newNode.setParent(node);
                    node.setGreater(newNode);
                    System.out.println(newNode);
                    break;
                }
                node = node.getGreater();
            }
        }




        return true;
    }

    public V getValue(Comparable key) {
        return null;
    }

    public Collection<KDTreeNode<V>> findKNearestNeighbors(V val, int k) {
        if (val == null)
            return null;

        // Map used for results
        TreeSet<KDTreeNode<V>> results = new TreeSet<>((o1, o2) -> {
            Comparable dist1 = metric.distance(o1.getKey(), val);
            Comparable dist2 = metric.distance(o2.getKey(), val);
            return dist1.compareTo(dist2);
        });

        // Find the closest leaf node
        KDTreeNode<V> prev = null;
        KDTreeNode<V> node = root;

        Double dist = Double.MAX_VALUE;

        while (node != null) {
            int axis = node.getDepth() % k;
            if (this.axisComparator.compare(node.getKey(), val, axis) >= 0) {
                // Lesser
                prev = node;
                node = node.getLesser();
            } else {
                // Greater
                prev = node;
                node = node.getGreater();
            }
        }
        KDTreeNode<V> leaf = prev;
        System.out.println(leaf);
        if (leaf != null) {
            // Used to not re-examine nodes
            Set<KDTreeNode<V>> examined = new HashSet<>();

            // Go up the tree, looking for better solutions
            node = leaf;

            while (node != null) {
                // Search node
                searchNode(val, node, k, results, examined);
                node = node.getParent();
            }
        }
        // Load up the collection of the results
        Collection<KDTreeNode<V>> collection = new ArrayList<>(k);



        for (KDTreeNode<V> kdNode : results)
            collection.add(kdNode);
        return Collections.unmodifiableCollection(collection);
    }

    private void searchNode(V val, KDTreeNode<V> node, int k, TreeSet<KDTreeNode<V>> results, Set<KDTreeNode<V>> examined) {

    }

    @Override
    public String toString() {
        return "KDTree{}";
    }
}
