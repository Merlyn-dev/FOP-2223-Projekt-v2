package projekt.delivery.routing;

import org.jetbrains.annotations.NotNull;
import projekt.base.Location;

import java.util.Objects;

import static org.tudalgo.algoutils.student.Student.crash;

/**
 * Represents a weighted edge in a graph.
 */
@SuppressWarnings("ClassCanBeRecord")
class EdgeImpl implements Region.Edge {

    private final Region region;
    private final String name;
    private final Location locationA;
    private final Location locationB;
    private final long duration;

    /**
     * Creates a new {@link EdgeImpl} instance.
     * @param region The {@link Region} this {@link EdgeImpl} belongs to.
     * @param name The name of this {@link EdgeImpl}.
     * @param locationA The start of this {@link EdgeImpl}.
     * @param locationB The end of this {@link EdgeImpl}.
     * @param duration The length of this {@link EdgeImpl}.
     */
    EdgeImpl(
        Region region,
        String name,
        Location locationA,
        Location locationB,
        long duration
    ) {
        this.region = region;
        this.name = name;
        // locations must be in ascending order
        if (locationA.compareTo(locationB) > 0) {
            throw new IllegalArgumentException(String.format("locationA %s must be <= locationB %s", locationA, locationB));
        }
        this.locationA = locationA;
        this.locationB = locationB;
        this.duration = duration;
    }

    /**
     * Returns the start of this {@link EdgeImpl}.
     * @return The start of this {@link EdgeImpl}.
     */
    public Location getLocationA() {
        return locationA;
    }

    /**
     * Returns the end of this {@link EdgeImpl}.
     * @return The end of this {@link EdgeImpl}.
     */
    public Location getLocationB() {
        return locationB;
    }

    @Override
    public Region getRegion() {
        return region;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public long getDuration() {
        return duration;
    }

    @Override
    public Region.Node getNodeA() {
        return region.getNode(locationA); //locationA / null
    }

    @Override
    public Region.Node getNodeB() {
        return region.getNode(locationB); //locationB / null
    }

    @Override
    public int compareTo(Region.@NotNull Edge o) {
        int compare = Long.compare(duration, o.getDuration());
        if (compare == 0) {
            compare = name.compareTo(o.getName());
        }
        return compare;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { //identical?
            return true;
        }
        if (o == null || getClass() != o.getClass()) { //null or not the correct data type
            return false;
        }
        return Objects.equals(name, ((EdgeImpl) o).name) && Objects.equals(locationA, ((EdgeImpl) o).locationA) && Objects.equals(locationB, ((EdgeImpl) o).locationB) && duration == ((EdgeImpl) o).duration;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, locationA, locationB, duration);
    }

    @Override
    public String toString() {
        return "EdgeImpl(name='" + name + "', locationA='" + locationA + "', locationB='" + locationB + "', duration='" + duration + "')";
    }
}
