package baseframe.daos;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import javax.persistence.Query;

import models.baseframe.BaseModel;
import play.Play;
import play.db.jpa.GenericModel.JPAQuery;
import play.db.jpa.JPA;
import baseframe.consts.CommonConst;
import baseframe.enums.DatetimePatternEnum;
import baseframe.groovy.CustomTags.DTOs.PagingBean;
import baseframe.helpers.DatetimeHelper;
import baseframe.helpers.GenericHelper;
import baseframe.helpers.SQLHelper;
import baseframe.helpers.StringHelper;

/**
 * 基础数据访问类 <br>
 * <b>作者 : </b>maodun <br>
 * <b>创建时间 : </b>2015年8月4日,下午4:06:59
 * @param <T>
 */
public abstract class BaseDAO<T extends BaseModel> {

	private final Class<T> model_class = GenericHelper.getGenericClassOfSuperclass(this.getClass(), 0);

	private final String table_name = this.model_class.getSimpleName().toLowerCase();

	protected BaseDAO() {
	}

	public final T save(T model) {
		printSQL(" SAVE " + model.toString());
		return model.save();
	}

	public final int del(long id) {
		return this.del(" DELETE t FROM " + this.table_name + " t WHERE t.id = ? ", id);
	}

	protected static final int del(String full_sql, Object... param_vals) {
		return newNativeQuery(full_sql, param_vals).executeUpdate();
	}

	public final int delAll() {
		return this.del(" DELETE t FROM " + this.table_name + " t ");
	}

	protected static final int update(String full_sql, Object... param_vals) {
		return newNativeQuery(full_sql, param_vals).executeUpdate();
	}

	public final T query(long id) {
		return this.query(" WHERE t.id = ? ", id).first();
	}

	protected final JPAQuery query(String tail_sql, Object... param_vals) {
		String full_sql = " SELECT t.* FROM " + this.table_name + " t " + tail_sql;
		return queryForClass(this.model_class, full_sql, param_vals);
	}

	protected static final JPAQuery queryForClass(Class<? extends BaseModel> model_class, String full_sql, Object... param_vals) {
		return newJPAQueryForClass(model_class, full_sql, param_vals);
	}

	public final List<T> queryAll() {
		return this.query("").fetch();
	}

	public final long count() {
		return count(" SELECT COUNT(*) FROM " + this.table_name);
	}

	protected static final long count(String full_sql, Object... param_vals) {
		Query q = newNativeQuery(full_sql, param_vals);
		return ((BigInteger) q.getSingleResult()).longValue();
	}

	protected static final BigDecimal sum(String full_sql, Object... param_vals) {
		Query q = newNativeQuery(full_sql, param_vals);
		return (BigDecimal) q.getSingleResult();
	}

	protected static final BigDecimal max(String full_sql, Object... param_vals) {
		Query q = newNativeQuery(full_sql, param_vals);
		return (BigDecimal) q.getSingleResult();
	}

	protected static final BigDecimal min(String full_sql, Object... param_vals) {
		Query q = newNativeQuery(full_sql, param_vals);
		return (BigDecimal) q.getSingleResult();
	}

	protected final PagingBean newPagingBean(int cur_pageno, String tail_sql, Object... param_vals) {
		return this.newPagingBean(cur_pageno, CommonConst.DEF_PAGING_DISP_COUNT, tail_sql, param_vals);
	}

	protected static final PagingBean newPagingBeanForClass(int cur_pageno, Class<? extends BaseModel> model_class, String full_sql, Object... param_vals) {
		return newPagingBeanForClass(cur_pageno, CommonConst.DEF_PAGING_DISP_COUNT, model_class, full_sql, param_vals);
	}

	protected final PagingBean newPagingBean(int cur_pageno, int disp_count, String tail_sql, Object... param_vals) {
		String full_sql = " SELECT t.* FROM " + this.table_name + " t " + (StringHelper.isValid(tail_sql) ? tail_sql : "");
		return newPagingBeanForClass(cur_pageno, disp_count, this.model_class, full_sql, param_vals);
	}

	protected static final PagingBean newPagingBeanForClass(int cur_pageno, int disp_count, Class<? extends BaseModel> model_class, String full_sql, Object... param_vals) {
		JPAQuery q = newJPAQueryForClass(model_class, full_sql, param_vals);
		PagingBean pb = new PagingBean();
		pb.disp_count = disp_count;
		pb.total_count = count(" SELECT COUNT(*) FROM ( " + full_sql + " ) t ", param_vals);
		pb.cur = cur_pageno;
		pb.data = q.from((pb.cur - 1) * pb.disp_count).fetch(pb.disp_count);
		pb.data_full_sql = SQLHelper.set(full_sql, param_vals);
		return pb;
	}

	protected static final Query newNativeQuery(String full_sql, Object... param_vals) {
//		if (StringHelper.count(full_sql, "?") != param_vals.length) {
//			ExceptionHelper.printErrStackTrace("参数个数不匹配\nSQL : " + full_sql + "\nparam_vals.length : " + param_vals.length);
//			return null;
//		}
		Query q = JPA.em().createNativeQuery(full_sql);
		SQLHelper.set(q, param_vals);
		printSQL(full_sql, param_vals);
		return q;
	}

	protected static final Query newNativeQueryForClass(Class<? extends BaseModel> model_class, String full_sql, Object... param_vals) {
//    	if (StringHelper.count(full_sql, "?") != param_vals.length) {
//            ExceptionHelper.printErrStackTrace("参数个数不匹配\nSQL : " + full_sql + "\nparam_vals.length : " + param_vals.length);
//            return null;
//    	}
		Query q = JPA.em().createNativeQuery(full_sql, model_class);
		SQLHelper.set(q, param_vals);
		printSQL(full_sql, param_vals);
		return q;
	}

	protected static final JPAQuery newJPAQuery(String full_sql, Object... param_vals) {
		Query q = newNativeQuery(full_sql, param_vals);
		return new JPAQuery(q);
	}

	protected static final JPAQuery newJPAQueryForClass(Class<? extends BaseModel> model_class, String full_sql, Object... param_vals) {
		Query q = newNativeQueryForClass(model_class, full_sql, param_vals);
		return new JPAQuery(q);
	}

	private static final void printSQL(String full_sql, Object... param_vals) {
		if (Play.mode.isDev()) {
			String datetime = DatetimeHelper.format(DatetimeHelper.cur(), DatetimePatternEnum.datetime_1002);
			String sql = SQLHelper.set(full_sql, param_vals);
			System.out.println("<" + datetime + "> SQL : " + sql);
		}
	}

	protected static final <T> T[] arr(T... vals) {
		return vals;
	}

}
