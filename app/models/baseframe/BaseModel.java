package models.baseframe;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Transient;

import play.db.jpa.Model;
import play.libs.Codec;

public abstract class BaseModel extends Model {

	private static final Map<String, String> sync_lock_key_kvs = new HashMap<String, String>(0);

	public static final String getSyncLockKey(long id, Class<? extends BaseModel> model_class) {
		String key = model_class.getName() + "." + id;
		String val = sync_lock_key_kvs.get(key);
		if (val == null) {
			val = Codec.UUID();
			sync_lock_key_kvs.put(key, val);
		}
		return val;
	}

	public final String getSyncLockKey() {
		return this.getSyncLockKey(super.id, this.getClass());
	}

	@Override
	public String toString() {
		Class clazz = this.getClass();
		StringBuffer sb = new StringBuffer();
		sb.append(clazz.getName()).append(" [id:").append(super.id);
		Field[] fs = clazz.getDeclaredFields();
		for (Field f : fs) {
			if (!Modifier.isPublic(f.getModifiers()) || Modifier.isStatic(f.getModifiers()) || f.getAnnotation(Transient.class) != null) {
				continue;
			}
			try {
				sb.append(", ").append(f.getName()).append(":").append(f.get(this));
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		sb.append("]");
		return sb.toString();
	}

	protected static final <T> T[] arr(T... vals) {
		return vals;
	}

}
