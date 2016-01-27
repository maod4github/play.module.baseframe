package baseframe.blos;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import models.baseframe.BaseModel;
import baseframe.daos.BaseDAO;
import baseframe.factorys.InstanceFactory;
import baseframe.helpers.DatetimeHelper;
import baseframe.helpers.GenericHelper;

/**
 * 基础业务逻辑类<br>
 * <b>作者 : </b>maodun<br>
 * <b>创建时间 : </b>2015年12月23日,下午3:17:03
 * @param <T>
 * @param <U>
 */
public abstract class BaseBLO<T extends BaseModel, U extends BaseDAO<T>> {

	protected final U dao = (U) InstanceFactory.getDAO(GenericHelper.getGenericClassOfSuperclass(this.getClass(), 1));

	protected BaseBLO() {
	}

	public final T save(T model) {
		Date cur = DatetimeHelper.cur();
		Class clazz = model.getClass();
		String name = clazz.getName();
		try {
			Field insert_time_field = clazz.getField("insert_time");
			if (insert_time_field.get(model) == null) {
				insert_time_field.set(model, cur);
			}
		}
		catch (Exception e) {
			System.out.println("INFO : " + name + "没有insert_time字段");
		}
		try {
			clazz.getField("update_time").set(model, cur);
		}
		catch (Exception e) {
			System.out.println("INFO : " + name + "没有update_time字段");
		}
		return this.dao.save(model);
	}

	public final int del(long id) {
		if (id < 1) {
			return 0;
		}
		return this.dao.del(id);
	}

	public final int delAll() {
		return this.dao.delAll();
	}

	public final T query(long id) {
		if (id < 1) {
			return null;
		}
		return this.dao.query(id);
	}

	public final List<T> queryAll() {
		return this.dao.queryAll();
	}

	public final long count() {
		return this.dao.count();
	}

	protected static final <T> T[] arr(T... vals) {
		return vals;
	}

}
