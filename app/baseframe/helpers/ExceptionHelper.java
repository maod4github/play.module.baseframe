package baseframe.helpers;

/**
 * 异常助手 <br>
 * <b>作者 : </b>maodun <br>
 * <b>创建时间 : </b>2015年8月11日,上午11:07:05
 */
public final class ExceptionHelper {

	private ExceptionHelper() {
	}

	/**
	 * 打印栈追溯 <br>
	 * <b>作者 : </b>maodun <br>
	 * <b>创建时间 : </b>2015年8月5日,下午4:24:58
	 * @param err_info 错误信息
	 */
	public static final void printErrStackTrace(String err_info) {
		new RuntimeException(err_info).printStackTrace();
	}

}
