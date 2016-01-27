package baseframe.helpers;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class GenericHelper {

	private GenericHelper() {
	}

	public static Class getGenericClassOfSuperclass(Class src_class, int index) {
		Type t = src_class.getGenericSuperclass();
		if (!(t instanceof ParameterizedType)) {
			return null;
		}
		ParameterizedType pt = (ParameterizedType) t;
		Type[] t_arr = pt.getActualTypeArguments();
		if (index >= t_arr.length) {
			return null;
		}
		t = t_arr[index];
		if (!(t instanceof ParameterizedType)) {
			return (Class) t;
		}
		pt = (ParameterizedType) t;
		return (Class) pt.getRawType();
	}

}
