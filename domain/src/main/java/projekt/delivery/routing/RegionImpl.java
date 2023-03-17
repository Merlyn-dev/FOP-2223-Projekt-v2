package projekt.delivery.routing;

import org.jetbrains.annotations.Nullable;
import projekt.base.DistanceCalculator;
import projekt.base.EuclideanDistanceCalculator;
import projekt.base.Location;

import java.util.*;

import static org.tudalgo.algoutils.student.Student.crash;

class RegionImpl implements Region {

    private final Map<Location, NodeImpl> nodes = new HashMap<>();
    private final Map<Location, Map<Location, EdgeImpl>> edges = new HashMap<>();
    private final List<EdgeImpl> allEdges = new ArrayList<>();
    private final DistanceCalculator distanceCalculator;

    /**
     * Creates a new, empty {@link RegionImpl} instance using a {@link EuclideanDistanceCalculator}.
     */
    public RegionImpl() {
        this(new EuclideanDistanceCalculator());
    }

    /**
     * Creates a new, empty {@link RegionImpl} instance using the given {@link DistanceCalculator}.
     */
    public RegionImpl(DistanceCalculator distanceCalculator) {
        this.distanceCalculator = distanceCalculator;
    }

    @Override
    public @Nullable Node getNode(Location location) {
        return nodes.get(location);
    }

    @Override
    public @Nullable Edge getEdge(Location locationA, Location locationB) {
        Edge edge = edges.getOrDefault(locationA, Collections.emptyMap()).get(locationB);
        //
        if (edge == null) {
            edge = edges.getOrDefault(locationB, Collections.emptyMap()).get(locationA);
        }
        return edge;
    }

    @Override
    public Collection<Node> getNodes() {
        return Collections.unmodifiableCollection(nodes.values());
    }

    @Override
    public Collection<Edge> getEdges() {
        return Collections.unmodifiableList(allEdges);
    }

    @Override
    public DistanceCalculator getDistanceCalculator() {
        return distanceCalculator;
    }

    /**
     * Adds the given {@link NodeImpl} to this {@link RegionImpl}.
     * @param node the {@link NodeImpl} to add.
     */
    void putNode(NodeImpl node) {
        if (!this.equals(node.getRegion())) {
            throw new IllegalArgumentException("Node " + node + " has incorrect region"); //not in the map
        }
        nodes.put(node.getLocation(), node); //only if no exception is thrown
    }

    /**
     * Adds the given {@link EdgeImpl} to this {@link RegionImpl}.
     * @param edge the {@link EdgeImpl} to add.
     */
    void putEdge(EdgeImpl edge) {
        if (edge.getRegion() != this) {
            throw new IllegalArgumentException("Edge " + edge + " has incorrect region");
        }
        //
        if (edge.getNodeA() == null || edge.getNodeB() == null) {
            String location = edge.getNodeA() == null ? edge.getNodeA().getLocation().toString() : edge.getNodeB().getLocation().toString();
            throw new IllegalArgumentException("Node"  + (edge.getNodeA() == null ? "A " : "B ") + location + " is not part of the region");
        }
        if (edge.getNodeA().getRegion() != this || edge.getNodeB().getRegion() != this) {
            String location = edge.getNodeA().getRegion() != this ? edge.getNodeA().getLocation().toString() : edge.getNodeB().getLocation().toString();
            throw new IllegalArgumentException("Node"  + (edge.getNodeA().getRegion() != this ? "A " : "B ") + location + " is not part of the region");
        }
        if (!nodes.containsValue(edge.getNodeA()) || !nodes.containsValue(edge.getNodeB())) {
            throw new IllegalArgumentException("Edge " + edge + " has incorrect region");
        }
        allEdges.add(edge);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) { //identical?
            return true;
        }
        if (!(o instanceof RegionImpl)) { //passed object is of type RegionImpl or a subtype?
            return false;
        }
        return Objects.equals(this.nodes, ((RegionImpl) o).nodes) && Objects.equals(this.edges, ((RegionImpl) o).edges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodes, edges);
    }
}
