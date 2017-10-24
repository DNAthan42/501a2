import java.lang.reflect.*;

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

    	Method[] methods;
    	Constructor[] constructors;
    	Field[] fields;

    	Class thisClass = obj.getClass();

        declaringClass(obj);
        superClass(obj);
        interfaces(obj);

        System.out.println("Method Summary");
        methods = thisClass.getDeclaredMethods();
        methods(methods);


    }

    public void declaringClass(Object obj){ System.out.println("class " + obj.getClass().getName()); }

    public void superClass(Object obj){ System.out.println("extends " + obj.getClass().getSuperclass()); }

    public void interfaces(Object obj){
    	boolean first = true; //string formatting is a pita
    	Class[] interfaces = obj.getClass().getInterfaces();
    	System.out.print("implements ");
    	for (Class i: interfaces){
    		System.out.printf("%s%s", (first)?"":", ", i);
    		if (first) first = false;
		}
		System.out.println();
	}

	public void methods(Method[] methods){
    	for (Method method: methods){
    		Parameter[] params = method.getParameters();

    		System.out.print(Modifier.toString(method.getModifiers()) + " ");

    		//print the name
			System.out.print(method.getName());

			//print method signature
			System.out.print("(");
			boolean first = true;
			for (Parameter parameter: params){
				System.out.printf("%s%s %s", (first)?"":", ", parameter.getType().getName(), parameter.getName());
				if (first) first = false;
			}
			System.out.print(")");

			//print exceptions
			first = true;
			for (Class exception: method.getExceptionTypes()){
				System.out.printf("%s%s", (first)?"":", ", exception.getName());
				if (first) first = false;
			}

			//end the line
			System.out.println();
		}
	}
}
