package baseframe.factorys;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import baseframe.blos.BaseBLO;
import baseframe.daos.BaseDAO;

/**
 * 实例工厂
 * <br><b>作者 : </b>maodun
 * <br><b>创建时间 : </b>2015年8月5日,下午7:03:41
 */
public class InstanceFactory {
    
    private InstanceFactory() {}
    
    private static Map<String, BaseDAO> daos = new HashMap<String, BaseDAO>();
    
    private static Map<String, BaseBLO> blos = new HashMap<String, BaseBLO>();
    
    /**
     * 获取DAO实例(若内存中有则直接返回,没有则new)
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年8月5日,下午9:18:41
     * @param clazz DAO Class
     * @return
     */
    public static <T extends BaseDAO> T getDAO(Class<T> clazz) {
        String name = clazz.getCanonicalName();
        BaseDAO dao = InstanceFactory.daos.get(name);
        if (dao == null) {
            dao = (T) InstanceFactory.newInstance(clazz);
            InstanceFactory.daos.put(name, dao);
        }
        return (T) dao;
    }
    
    /**
     * 获取BLO实例(若内存中有则直接返回,没有则new)
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年8月5日,下午9:19:39
     * @param clazz BLO Class
     * @return
     */
    public static <T extends BaseBLO> T getBLO(Class<T> clazz) {
        String name = clazz.getCanonicalName();
        BaseBLO blo = InstanceFactory.blos.get(name);
        if (blo == null) {
            blo = (T) InstanceFactory.newInstance(clazz);
            InstanceFactory.blos.put(name, blo);
        }
        return (T) blo;
    }
    
	/**
	 * 复位<br>
	 * <ul>
	 * <li>该方法将释放内存中缓存实例，等待垃圾回收机制回收</li>
	 * </ul>
	 * <b>作者 : </b>Morton<br>
	 * <b>创建时间 : </b>2015年12月3日,上午12:01:20
	 */
	public static void reset() {
		daos.clear();
		blos.clear();
System.out.println("实例工厂复位成功.");
	}
    
    private static Object newInstance(Class clazz) {
        Object obj = null;
        try {
            Constructor constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            obj = constructor.newInstance();
            constructor.setAccessible(false);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return obj;
    }
    
}


