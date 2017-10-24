/**
 * Inspector Class
 * Introspectively inspects classes, their hierarchies and fields
 * @author Nathan Douglas
 */
public class Inspector {

    /**
     * Main inspection method
     * @param obj
     * @param recursive
     */
    public void inspect(Object obj, boolean recursive){

        declaringClass();
        superClass();
        interfaces();


    }
}
