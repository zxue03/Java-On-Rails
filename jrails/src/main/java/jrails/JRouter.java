package jrails;

import java.util.Map;
import java.util.HashMap;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class JRouter {

	private static Map<String, String> route_map = new HashMap<>();

	public void addRoute(String verb, String path, Class clazz, String method) {
		route_map.put(verb + path, clazz.getName() + "#" + method);
	}

	// Returns "clazz#method" corresponding to verb+URN
	// Null if no such route
	public String getRoute(String verb, String path) {
		return route_map.get(verb + path);
	}

	// Call the appropriate controller method and
	// return the result
	public Html route(String verb, String path, Map<String, String> params) {
		String clazz_method = route_map.get(verb + path);
		if(clazz_method == null) {
			throw new UnsupportedOperationException();
		}
		String[] names = clazz_method.split("#");
		String clazz_name = names[0];
		String method_name = names[1];
		Class<?> clazz = null;
		try {
			clazz = Class.forName(clazz_name);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Method method = null;
		try {
			method = clazz.getDeclaredMethod(method_name, Map.class);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Html res = null;
		try {
			res = (Html) method.invoke(null, params);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;

	}
}
