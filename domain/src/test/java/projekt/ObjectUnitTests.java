package projekt;

import java.util.function.Function;

import static org.tudalgo.algoutils.student.Student.crash;
import static org.junit.jupiter.api.Assertions.*;

public class ObjectUnitTests<T> {

    private final Function<Integer, T> testObjectFactory;
    private final Function<T, String> toString;

    private T[] testObjects;
    private T[] testObjectsReferenceEquality;
    private T[] testObjectsContentEquality;

    public ObjectUnitTests(Function<Integer, T> testObjectFactory, Function<T, String> toString) {
        this.testObjectFactory = testObjectFactory;
        this.toString = toString;
    }

    @SuppressWarnings("unchecked")
    public void initialize(int testObjectCount) {
        testObjects = (T[]) new Object[testObjectCount];
        testObjectsReferenceEquality = (T[]) new Object[testObjectCount];
        testObjectsContentEquality = (T[]) new Object[testObjectCount];
        //testObjectsContent = (T[]) new Object[];
        //sfesdeftest
        for (int i = 0; i < testObjectCount; i++) {
            testObjects[i] = testObjectFactory.apply(i);
            testObjectsReferenceEquality[i] = testObjects[i];
            //testObjectsEquality[i] = testObjects[i];
            testObjectsContentEquality[i] = testObjectFactory.apply(i);
        }
    }

    public void testEquals() {
        crash(); // TODO: H12.1 - remove if implemented
    }

    public void testHashCode() {
        for (int i = 0; i < testObjects.length; i++) {
            // Test reference equality
            assertEquals(testObjects[i].hashCode(), testObjectsReferenceEquality[i].hashCode());
            // Test content equality
            assertEquals(testObjects[i].hashCode(), testObjectsContentEquality[i].hashCode());
        }
    }

    public void testToString() {
        for (T testObject : testObjects) {
            assertEquals(testObject.toString(), toString.apply(testObject));
        }
    }

}
