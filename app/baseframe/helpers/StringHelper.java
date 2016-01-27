package baseframe.helpers;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 字符串助手
 * <br><b>作者 : </b>maodun
 * <br><b>创建时间 : </b>2015年8月4日,下午5:34:10
 */
public class StringHelper {
    
    private StringHelper() {}
    
    /**
     * 是否有效
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年8月4日,下午5:33:58
     * @param str
     * @return true:有效, false:无效
     */
    public static boolean isValid(String str) {
        return str != null && !"".equals(str) && !"NULL".equalsIgnoreCase(str);
    }
    
    /**
     * 是否有效 - 去掉前后空格
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年8月4日,下午5:33:43
     * @param str
     * @return true:有效, false:无效
     */
    public static boolean isValidForTrim(String str) {
        return str != null && StringHelper.isValid(str.trim());
    }
    
    /**
     * 计算@a字符串内@b字符串出现的次数
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年8月4日,下午5:23:33
     * @param a
     * @param b
     * @return
     */
    public static int count(String a, String b) {
        if (!StringHelper.isValid(a) || !StringHelper.isValid(b)) {
            return 0;
        }
        int times = 0;
        int index = -1;
        while ((index = a.indexOf(b)) >= 0) {
            times ++;
            a = a.substring(index + b.length());
        }
        return times;
    }
    
    /**
     * 移除@html中所有的HTML标签
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年9月7日,下午12:33:25
     * @param html
     * @return
     */
	public static String removeAllHTMLTag(String html) {
		html = html == null ? "" : html;
		return html.replaceAll("<[^>]+>", "");
	}
	
	/**
     * 提取@url中的参数值
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年9月7日,下午12:25:38
     * @param url
     * @param key 参数key
     * @return
     */
    public static String extrURLParamVal(String url, String key) {
		if (!StringHelper.isValidForTrim(url)) {
			return "";
		}
		url = url.trim();
		if (!StringHelper.isValidForTrim(key)) {
			return "";
		}
		key = key.trim();
		if (!url.contains(key + "=")) {
			return "";
		}
		int a = url.indexOf(key);
		String b = url.substring(a + key.length() + 1);
		int c = b.indexOf("&");
		if (c >= 0) {
			return b.substring(0, c);
		}
		return b;
    }
    
    /**
     * 是否是手机号码
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年10月9日,下午12:17:57
     * @param str
     * @return true:是, false:否
     */
    public static Boolean isMobileNo(String str) {
    	return str != null && str.matches("1[3-8]\\d{9}");
    }
    
    /**
     * 是否是电子邮件地址
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年11月5日,上午11:28:38
     * @param str
     * @return true:是, false:否
     */
    public static Boolean isEmail(String str) {
        return str != null && str.matches("^([a-zA-Z0-9])+([a-zA-Z0-9_.-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$");
    }
    
    /**
     * 是否是身份证号码
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年11月5日,上午11:48:50
     * @param str
     * @return true:是, false:否
     */
    public static Boolean isIdNo(String str) {
    	return IDCardHelper.chekIdCard(0, str);
    }
    
    /**
     * 是否是数字<ul><li>包括正负整数、正负小数</li><li>支持 '.12' 与 '12.' 格式的匹配</li></ul>
     * <br><b>作者 : </b>maodun
     * <br><b>创建时间 : </b>2015年10月14日,下午7:48:46
     * @param str
     * @return
     */
    public static Boolean isNum(String str) {
    	return str != null && str.matches("(([-|+]{0,1}\\d*\\.{0,1}\\d+)|([-|+]{0,1}\\d+\\.{0,1}\\d*))");
    }
    
	/**
	 * 替换所有以指定字符串开头并且以指定字符串结尾的字符串
	 * <br><b>作者 : </b>maodun
	 * <br><b>创建时间 : </b>2015年10月30日,下午4:47:54
	 * @param operatedStr 操作的字符串
	 * @param startStr 开始字符串
	 * @param endStr 结尾字符串
	 * @param replaceStr 替换字符串
	 * @return 将operatedStr中以startStr开头并且以endStr结尾的字符串替换成replaceStr后的字符串
	 */
	public static String replaceAll(String operatedStr, String startStr, String endStr, String replaceStr) {
		if (!StringHelper.isValid(operatedStr)) {
			return "";
		}
		if (!StringHelper.isValid(startStr) || !StringHelper.isValid(endStr)) {
			return operatedStr;
		}
		while (operatedStr.indexOf(startStr) != -1 && operatedStr.indexOf(endStr) != -1) {
			String findedStr = operatedStr.substring(operatedStr.indexOf(startStr), operatedStr.indexOf(endStr)	+ endStr.length());
			operatedStr = operatedStr.replace(findedStr, replaceStr);
		}
		return operatedStr;
	}

	/**
	 * 替换所有以指定字符串开头并且以指定字符串结尾的字符串为两者之间的字符串
	 * <br><b>作者 : </b>maodun
	 * <br><b>创建时间 : </b>2015年10月30日,下午4:48:38
	 * @param operatedStr 操作的字符串
	 * @param startStr 开始字符串
	 * @param endStr 结尾字符串
	 * @return 将operatedStr中以startStr开头并且以endStr结尾的字符串替换成两者之间的字符串后的字符串
	 */
	public static String replaceAllWithInnerStr(String operatedStr, String startStr, String endStr) {
		if (!StringHelper.isValid(operatedStr)) {
			return "";
		}
		if (!StringHelper.isValid(startStr) || !StringHelper.isValid(endStr)) {
			return operatedStr;
		}
		while (operatedStr.indexOf(startStr) != -1 && operatedStr.indexOf(endStr) != -1) {
			String findedStr = operatedStr.substring(operatedStr.indexOf(startStr), operatedStr.indexOf(endStr)	+ endStr.length());
			String innerStr = findedStr.substring(startStr.length(), findedStr.length() - endStr.length());
			operatedStr = operatedStr.replace(findedStr, innerStr);
		}
		return operatedStr;
	}
	
	/**
	 * 身份证号码验证
	 * 1、号码的结构
	 * 公民身份号码是特征组合码，由十七位数字本体码和一位校验码组成。排列顺序从左至右依次为：六位数字地址码，
	 * 八位数字出生日期码，三位数字顺序码和一位数字校验码。
	 * 2、地址码(前六位数）
	 * 表示编码对象常住户口所在县(市、旗、区)的行政区划代码，按GB/T2260的规定执行。
	 * 3、出生日期码（第七位至十四位）
	 * 表示编码对象出生的年、月、日，按GB/T7408的规定执行，年、月、日代码之间不用分隔符。
	 * 4、顺序码（第十五位至十七位）
	 * 表示在同一地址码所标识的区域范围内，对同年、同月、同日出生的人编定的顺序号，
	 * 顺序码的奇数分配给男性，偶数分配给女性。
	 * 5、校验码（第十八位数）
	 * （1）十七位数字本体码加权求和公式 S = Sum(Ai * Wi), i = 0, , 16 ，先对前17位数字的权求和
	 * Ai:表示第i位置上的身份证号码数字值 Wi:表示第i位置上的加权因子 Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4
	 * 2 （2）计算模 Y = mod(S, 11) （3）通过模得到对应的校验码 Y: 0 1 2 3 4 5 6 7 8 9 10 校验码: 1 0 X 9 8 7 6 5 4 3 2
	 * 功能：身份证的有效验证
	 * /**
	 * 
	 * @param IDStr          IDStr
	 *            身份证号
	 * @return 有效：返回"" 无效：返回String信息
	 */
	private static class IDCardHelper {

	    /** 标识18位身份证号码 */
		private static final int EIGHTEEN_IDCARD = 18;

	    /** 标识15位身份证号码 */
		private static final int FIFTEEN_IDCARD = 15;

	    /** 大陆地区地域编码最大值 */
		private static final int MAX_MAINLAND_AREACODE = 659004;

	    /** 大陆地区地域编码最小值 */
		private static final int MIN_MAINLAND_AREACODE = 110000;

	    /** 香港地域编码值 */
		private static final int HONGKONG_AREACODE = 810000;

	    /** 台湾地域编码值 */
		private static final int TAIWAN_AREACODE = 710000;

	    /** 澳门地域编码值 */
		private static final int MACAO_AREACODE = 820000;

	    /** 储存18位身份证校验码 */
	    private static final String[] arrCh = new String[] { "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2" };
	    private static final Integer[] arrInt = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };

	    /**
	     * 验证身份证号码是否正确
	     * <br><b>作者 : </b>maodun
	     * <br><b>创建时间 : </b>2015年11月5日,上午11:46:26
	     * @param sex 0=忽略性别  1=男  2=女
	     * @param idCardInput
	     * @return
	     */
	    private static boolean chekIdCard(int sex, String idCardInput) {
	        if (!StringHelper.isValid(idCardInput)) {
	            return false;
	        }
	        int len = idCardInput.length();
	        if(len != FIFTEEN_IDCARD && len != EIGHTEEN_IDCARD ) {
	            return false;
	        }
	        if (idCardInput.length() == FIFTEEN_IDCARD) {
	            return checkIdCard15(sex, idCardInput);
	        }
	        return checkIdCard18(sex, idCardInput);
	    }

	    /**
	     * 验证15位身份证号码
	     */
	    private static boolean checkIdCard15(int sex, String idCardInput) {
	        boolean flag = checkNumber(FIFTEEN_IDCARD, idCardInput);
	        if (!flag) {
	            return false;
	        }
	        flag = checkArea(idCardInput);
	        if (!flag) {
	            return false;
	        }
	        flag = checkBirthDate(FIFTEEN_IDCARD, idCardInput);
	        if(!flag) {
	            return false;
	        }
	        if (sex != 0) {
	            if (!checkSortCode(FIFTEEN_IDCARD, sex, idCardInput)) {
	                return false;
	            }
	        }
	        return checkCheckCode(FIFTEEN_IDCARD, idCardInput);
	    }

	    /**
	     * 验证18位身份证号码
	     */
	    private static boolean checkIdCard18(int sex, String idCardInput) {
	        boolean flag = checkNumber(EIGHTEEN_IDCARD, idCardInput);
	        if (!flag) {
	            return false;
	        }
	        flag = checkArea(idCardInput);
	        if (!flag) {
	            return false;
	        }
	        flag = checkBirthDate(EIGHTEEN_IDCARD, idCardInput);
	        if (!flag) {
	            return false;
	        }
	        if(sex != 0) {
	            if (!checkSortCode(EIGHTEEN_IDCARD, sex, idCardInput)) {
	                return false;
	            }
	        }
	        return checkCheckCode(EIGHTEEN_IDCARD, idCardInput);
	    }

	    /**
	     * 验证身份证的地域编码是符合规则
	     */
	    private static boolean checkArea(String idCardInput) {
	        String subStr = idCardInput.substring(0, 6);
	        int areaCode = Integer.parseInt(subStr);
	        if (areaCode != HONGKONG_AREACODE && areaCode != TAIWAN_AREACODE && areaCode != MACAO_AREACODE
	                && (areaCode > MAX_MAINLAND_AREACODE || areaCode < MIN_MAINLAND_AREACODE)) {
	            return false;
	        }
	        return true;
	    }

	    /**
	     * 验证身份证号码数字字母组成是否符合规则
	     */
	    private static boolean checkNumber(int idCardType, String idCard) {
	        char[] chars = idCard.toCharArray();
	        if (idCardType == FIFTEEN_IDCARD) {
	            for (int i = 0; i < chars.length; i++) {
	                // 15位身份证号码中不能出现字母
	                if (chars[i] > '9') {
	                    return false;
	                }
	            }
	        } else {
	            for (int i = 0; i < chars.length; i++) {
	                if (i < chars.length - 1) {
	                    // 18位身份证号码中前17位不能出现字母
	                    if (chars[i] > '9') {
	                        return false;
	                    }
	                } else {
	                    // 18位身份证号码中最后一位只能是数字0~9或字母X
	                    if (chars[i] > '9' && chars[i] != 'X') {
	                        return false;
	                    }
	                }
	            }
	        }
	        return true;
	    }

	    /**
	     * 验证身份证号码出生日期是否符合规则
	     */
	    private static boolean checkBirthDate(int idCardType, String idCardInput) {
	        boolean flag = checkBirthYear(idCardType, idCardInput);
	        if (!flag) {
	            return false;
	        }
	        flag = checkBirthMonth(idCardType, idCardInput);
	        if(!flag) {
	            return false;
	        }
	        return checkBirthDay(idCardType, idCardInput);
	    }

	    /**
	     * 验证身份证号码出生日期年份是否符合规则
	     */
	    private static boolean checkBirthYear(int idCardType, String idCardInput) {
	        if (idCardType == FIFTEEN_IDCARD) {
	            int year = Integer.parseInt(idCardInput.substring(6, 8));
	            //15位的身份证号码年份须在00~99内
	            if (year < 0 || year > 99) {
	                return false;
	            }
	        } else {
	            int year = Integer.parseInt(idCardInput.substring(6, 10));
	            int yearNow = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
	            
	            // 18位的身份证号码年份须在1900至今的范围内;
	            if (year < 1900 || year > yearNow) {
	                return false;
	            }
	        }
	        return true;
	    }

	    /**
	     * 验证身份证号码出生日期月份是否符合规则
	     */
	    private static boolean checkBirthMonth(int idCardType, String idCardInput) {
	        int month = 0;
	        if (idCardType == FIFTEEN_IDCARD) {
	            month = Integer.parseInt(idCardInput.substring(8, 10));
	        } else {
	            month = Integer.parseInt(idCardInput.substring(10, 12));
	        }
	        // 身份证号码月份须在01~12内
	        if (month < 1 || month > 12) {
	            return false;
	        }
	        return true;
	    }

	    /**
	     * 验证身份证号码出生日期天数是否符合规则
	     */
	    private static boolean checkBirthDay(int idCardType, String idCardInput) {
	        boolean bissextile = false;
	        int year, month, day;
	        if (idCardType == FIFTEEN_IDCARD) {
	            year = Integer.parseInt("19" + idCardInput.substring(6, 8));
	            month = Integer.parseInt(idCardInput.substring(8, 10));
	            day = Integer.parseInt(idCardInput.substring(10, 12));
	        } else {
	            year = Integer.parseInt(idCardInput.substring(6, 10));
	            month = Integer.parseInt(idCardInput.substring(10, 12));
	            day = Integer.parseInt(idCardInput.substring(12, 14));
	        }
	        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
	            bissextile = true;
	        }
	        if(month == 2) {
	            if (bissextile) {
	                // 闰年2月日期须在1~29之间
	                if (day < 1 || day > 29) {
	                    return false;
	                }
	            } else {
	                // 非闰年2月日期年份须在1~28之间
	                if (day < 1 || day > 28) {
	                    return false;
	                }
	            }
	        } else if(month == 4 || month == 6 || month == 9 || month == 11 ){
	            // 小月份日期只能在1~30之间
	            if(day < 1 || day > 30) {
	                return false;
	            }
	        } else{
	            // 大月份日期只能在1~31之间
	            if(day < 1 || day > 31) {
	                return false;
	            }
	        }
	        return true;
	    }

	    /**
	     * 验证身份证号码顺序码是否符合规则,男性为奇数数,女性为偶数
	     */
	    private static boolean checkSortCode(int idCardType, int sex, String idCardInput) {
	        int sortCode = 0;
	        if (idCardType == FIFTEEN_IDCARD) {
	            sortCode = Integer.parseInt(idCardInput.substring(12, 15));
	        } else {
	            sortCode = Integer.parseInt(idCardInput.substring(14, 17));
	        }
	        sortCode %= 2;
	        sex %= 2;
	        return sortCode == sex;
	    }

	    /**
	     * 验证18位身份证号码校验码是否符合规则
	     */
	    private static boolean checkCheckCode(int idCardType, String idCard) {
	        if (idCardType == EIGHTEEN_IDCARD) {
	            int sigma = 0;
	            for (int i = 0; i < 17; i++) {
	                int ai = Integer.parseInt(idCard.substring(i, i + 1));
	                int wi = arrInt[i];
	                sigma += ai * wi;
	            }
	            int number = sigma % 11;
	            String check_number = arrCh[number];
	            if (!check_number.equals(idCard.substring(17))) {
	                return false;
	            }
	        }
	        return true;
	    }

	}
    
}
