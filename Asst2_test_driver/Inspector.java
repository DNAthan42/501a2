import java.lang.reflect.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Inspector Class
 * Introspectively inspects classes, their hierarchies and fields
 * @author Nathan Douglas
 */
public class Inspector {

	private static String[] ARRAY_TYPE_CODES = {"B", "C", "D", "F", "I", "J", "S", "Z"};
	private static String[] ARRAY_TYPE_NAMES = {"byte", "char", "double", "float", "int", "long", "short", "boolean"};

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

    	//todo: catch if this class is an array type and handle differently

        declaringClass(obj);
        superClass(obj);
        interfaces(obj);

        System.out.println("Method Summary");
        methods = thisClass.getDeclaredMethods();
        methods(methods);

        System.out.println();

        System.out.println("Constructor Summary");
        constructors = thisClass.getDeclaredConstructors();
        constructors(constructors);

        System.out.println();

        System.out.println("Field Summary");
        fields = thisClass.getDeclaredFields();
        fields(fields);


    }

    private void declaringClass(Object obj){
    	System.out.print("class ");
    	if (obj.getClass().isArray()) System.out.println(arrayCodeToFormattedString(obj.getClass().getName()));
    	else System.out.println(obj.getClass().getName());
    }

    private void superClass(Object obj){ System.out.println("extends " + obj.getClass().getSuperclass()); }

    private void interfaces(Object obj){
    	boolean first = true; //string formatting is a pita
    	Class[] interfaces = obj.getClass().getInterfaces();
    	System.out.print("implements ");
    	for (Class i: interfaces){
    		System.out.printf("%s%s", (first)?"":", ", i);
    		if (first) first = false;
		}
		System.out.println();
	}

	private String getSignature(Executable exec){
    	Parameter[] parameters = exec.getParameters();
    	String ret = "";

    	//get modifiers
    	ret += Modifier.toString(exec.getModifiers()) + " ";

    	//get name
		ret += exec.getName();

		//get params
		ret += "(";
		boolean first = true;
		String type;
		for (Parameter parameter: parameters){
			//check for arrays
			type = parameter.getType().getName();
			if (parameter.getType().isArray()){
				type = arrayCodeToFormattedString(type);
			}
			//don't precede the first param with a comma
			if (first) {
				ret += "";
				first = false;
			}
			else ret += ", ";
			//throw in the type and name
			ret += type + " " + parameter.getName();
		}
		ret += ")"; //close the method sig

		return ret;
	}

	private String arrayCodeToFormattedString(String code){
    	String ret = "";
    	String type = "";
		//checks 'code' against the regex. regex looks for array types of n>0 dimensions
		//array types occasionally have a semi colon at the end and IDK why
		//hence the ending being things that aren't colons followed by maybe some colons
		Matcher matcher = Pattern.compile("([\\[]+)([BCDFIJLSZ])([^;]*);*").matcher(code);

		if (!matcher.matches()) return ""; //not a match then return the empty string. probably shouldn't happen ever

		type = matcher.group(2); //isolate just the type character
		boolean typeFound = false;
		for (int i = 0; i < ARRAY_TYPE_CODES.length; i++){
			if (type.equals(ARRAY_TYPE_CODES[i])){
				ret = ret.concat(ARRAY_TYPE_NAMES[i]);
				typeFound = true;
				break; //no need to search the rest of the list
			}
		}
		if (!typeFound){ //type.equals("L") (for reference types)
			ret = ret.concat(matcher.group(3)); //put the class name in here
		}
		for (int i = 0; i < matcher.group(1).length(); i++){ //add in a pair of square brackets for each open square bracket found
			ret = ret.concat("[]");
		}
		return ret;
	}

	private void methods(Method[] methods){
    	for (Method method: methods){
    		Parameter[] params = method.getParameters();

//    		//print the modifiers
//    		System.out.print(Modifier.toString(method.getModifiers()) + " ");
//
//    		//print the name
//			System.out.print(method.getName());
//
//			//print method signature
//			System.out.print("(");
//			boolean first = true;
//			String type;
//			for (Parameter parameter: params){
//				type = parameter.getType().getName();
//				if (parameter.getType().isArray()){
//					type = arrayCodeToFormattedString(type);
//				}
//				System.out.printf("%s%s %s", (first)?"":", ", type, parameter.getName());
//				if (first) first = false;
//			}
//			System.out.print(")");

			System.out.print(getSignature(method));

			//print exceptions
			boolean first = true;
			if (method.getExceptionTypes().length != 0) System.out.print(" throws "); //if the method doesn't throw anything, don't write throws
			for (Class exception: method.getExceptionTypes()){ //print a list of all the exceptions
				System.out.printf("%s%s", (first)?"":", ", exception.getName());
				if (first) first = false;
			}

			//end the line
			System.out.println();

			//print the returns on a new line
			System.out.print("	returns ");
			if (method.getReturnType().isArray()) System.out.println(arrayCodeToFormattedString(method.getReturnType().getName()));
			else System.out.println(method.getReturnType().getName());
		}
	}

	private void constructors(Constructor[] constructors){
		for (Constructor constructor: constructors){
			System.out.println(getSignature(constructor));
		}
	}

	private void fields(Field[] fields){
		for (Field field: fields){
			if (field.getDeclaringClass().isArray()) System.out.print(arrayCodeToFormattedString(field.getDeclaringClass().getName()));
			else System.out.print(field.getDeclaringClass().getName());

			System.out.print(" ");
			System.out.println(field.getName());
			//todo get and print the value
		}
	}
}
