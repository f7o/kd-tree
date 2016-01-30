package de.f7o.kd_tree;

import de.f7o.kd_tree.util.AxisComparator;
import de.f7o.kd_tree.util.AxisValueResolver;
import de.f7o.kd_tree.util.Metric;

import java.util.*;

/**
 * Created by flo on 29.01.2016.
 */
public class KDTree<V> {
    private KDTreeNode<V> root;
    private Metric<V> metric;
    private AxisComparator<V> axisComparator;
    private AxisValueResolver<V> axisValueResolver;
    //max. k=4
    private Integer k;


    public KDTree(Metric<V> metric, AxisComparator<V> axisComparator, AxisValueResolver<V> axisValueResolver, Integer k) {
        this.metric = metric;
        this.axisComparator = axisComparator;
        this.axisValueResolver = axisValueResolver;
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
                    //System.out.println(newNode);
                    break;
                }
                node = node.getLesser();
            } else {
                //greater
                if(node.getGreater() == null) {
                    KDTreeNode<V> newNode = new KDTreeNode<>(val, node.getDepth() +1, k);
                    newNode.setParent(node);
                    node.setGreater(newNode);
                    //System.out.println(newNode);
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
        examined.add(node);

        // Search node
        KDTreeNode<V> lastNode = null;
        Double lastDistance = Double.MAX_VALUE;
        if (results.size() > 0) {
            lastNode = results.last();
            lastDistance = metric.distance(lastNode.getKey(), val);
        }
        Double nodeDistance = metric.distance(node.getKey(), val);
        if (nodeDistance.compareTo(lastDistance) < 0) {
            if (results.size() == k && lastNode != null)
                results.remove(lastNode);
            results.add(node);
        } else if (nodeDistance.equals(lastDistance)) {
            results.add(node);
        } else if (results.size() < k) {
            results.add(node);
        }
        lastNode = results.last();
        lastDistance = metric.distance(lastNode.getKey(), val);

        int axis = node.getDepth() % this.k;
        KDTreeNode<V> lesser = node.getLesser();
        KDTreeNode<V> greater = node.getGreater();


        // Search children branches, if axis aligned distance is less than
        // current distance
        if (lesser != null && !examined.contains(lesser)) {
            examined.add(lesser);

            double nodePoint = Double.MIN_VALUE;
            double valueMinusDistance = Double.MIN_VALUE;

            nodePoint = this.axisValueResolver.getAxisVal(node.getKey(), axis);
            valueMinusDistance = this.axisValueResolver.getAxisVal(val, axis) - lastDistance;


            boolean lineIntersectsCube = ((valueMinusDistance <= nodePoint) ? true : false);

            // Continue down lesser branch
            if (lineIntersectsCube)
                searchNode(val, lesser, k, results, examined);
        }
        if (greater != null && !examined.contains(greater)) {
            examined.add(greater);

            double nodePoint = Double.MIN_VALUE;
            double valuePlusDistance = Double.MIN_VALUE;

            nodePoint = this.axisValueResolver.getAxisVal(node.getKey(), axis);
            valuePlusDistance = this.axisValueResolver.getAxisVal(val, axis) - lastDistance;

            boolean lineIntersectsCube = ((valuePlusDistance >= nodePoint) ? true : false);

            // Continue down greater branch
            if (lineIntersectsCube)
                searchNode(val, greater, k, results, examined);
        }
    }

    @Override
    public String toString() {
        return "KDTree{}";
    }
}
