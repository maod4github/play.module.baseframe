package baseframe.exceptions;

/**
 * 自定义基础异常类
 * <br><b>作者 : </b>maodun
 * <br><b>创建时间 : </b>2015年8月4日,下午6:07:10
 */
public class BaseException extends Exception {

    /**
     * 构造方法
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年8月5日,下午3:47:24
     * @param errInfo 错误信息
     */
    public BaseException(String errInfo) {
        super(errInfo);
    }
    
}
