package com.camel.framework.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 运行正则表达式的一些公共方式
 * @author sunkey
 * @date Mar 15, 2013 6:34:32 PM
 * @version 1.0.0 
 * @copyright fpx.com 
 */
public class RegexUtil {
	
	public final static String DATE_REGEXQ = "^((((19|20)\\d{2})-(0?(1|[3-9])|1[012])-(0?[1-9]|[12]\\d|30))|(((19|20)\\d{2})-(0?[13578]|1[02])-31)|(((19|20)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|((((19|20)([13579][26]|[2468][048]|0[48]))|(2000))-0?2-29))"
	 + "([ ])(([0-1]{1}[0-9]{1})|([2]{1}[0-3]{1}))([:])([0-5]{1}[0-9]{1})([:])([0-5]{1}[0-9]{1})$";
	
	public final static Pattern NUMPAT = Pattern.compile("^(\\d)*");

	public final static Pattern PAGEPAT = Pattern.compile("tpage=(\\d)*");

	public final static Pattern EMAIL = Pattern
			.compile("^([a-zA-Z0-9_]+[\\.a-zA-Z0-9_-]*){1,}@([a-zA-Z0-9-]+\\.){1,}(com|org|net|edu|ac|ad|ae|af|ag|ai|al|am|an|ao|aq|ar|as|at|au|aw|az|ax|ba|bb|bd|be|bf|bg|bh|bi|bj|bm|bn|bo|br|bs|bt|bv|bw|by|bz|ca|cc|cd|cf|cg|ch|ci|ck|cl|cm|cn|co|cr|cs|cu|cv|cx|cy|cz|de|dj|dk|dm|do|dz|ec|ee|eg|eh|er|es|et|eu|fi|fj|fk|fm|fo|fr|ga|gb|gd|ge|gf|gg|gh|gi|gl|gm|gn|gp|gq|gr|gsslands|gt|gu|gw|gy|hk|hm|hn|hr|ht|hu|id|ie|il|im|in|io|iq|ir|is|it|je|jm|jo|jp|ke|kg|kh|ki|km|kn|kp|kr|kw|ky|kz|la|lb|lc|li|lk|lr|ls|lt|lu|lv|ly|ma|mc|md|mg|mh|mkc of|ml|mm|mn|mo|mp|mq|mr|ms|mt|mu|mv|mw|mx|my|mz|na|nc|ne|nf|ng|ni|nl|no|np|nr|nu|nz|om|pa|pe|pf|pg|ph|pk|pl|pm|pn|pr|ps|pt|pw|py|qa|re|ro|ru|rw|sa|sb|sc|sd|se|sg|sh|si|sj|sk|sl|sm|sn|so|sr|st|sv|sy|sz|tc|td|tf|tg|th|tj|tk|tl|tm|tn|to|tp|tr|tt|tv|tw|tz|ua|ug|uk|um|us|uy|uz|va|vc|ve|vg|vi|vn|vu|wf|ws|ye|yt|yu|za|zm|zw)$");

	public final static Pattern IMAGE = Pattern.compile("[\\d\\D]+(\\.jpg|\\.JPG|\\.jpeg|\\.JPEG|\\.png|\\.PNG|\\.gif|\\.GIF)$", Pattern.MULTILINE | Pattern.DOTALL); 
	
	public final static Pattern WORD_CHAR = Pattern.compile("^[0-9A-Za-z_-]+$");
	
	/**
	 * 判断 是否是数字
	 * 
	 * @param str
	 * @return
	 */
	public final static boolean isNumber(String str) {
		return str != null && !str.equals("") && NUMPAT.matcher(str).matches();
	}

	/**
	 * 将tapge从url中移走
	 * 
	 * @param str
	 * @return
	 */
	public final static String movePageToken(String str) {
		if (str == null) {
			return "";
		}
		return str.replaceAll("tpage=(\\d)*(&*)", "").replaceAll(".*\\?$", "");
	}

	/**
	 * 是否有效的email
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isValidEmail(String email) {
		if (isNULL(email))
			return false;
		return EMAIL.matcher(email).find();
	}
	
	/**
	 * 图片校验，是否以指定后缀格式的结尾
	 * @param imageFile
	 * @return
	 */
	public static boolean isImage(String imageFile) {
		Matcher fileMatcher = IMAGE.matcher(imageFile);
		if (!fileMatcher.matches()) {
			return false;
		}
		return true;
	}
	
	public static boolean isWordChar(String str) {
		Matcher strMatcher = WORD_CHAR.matcher(str);
		if(strMatcher.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * 字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	private static boolean isNULL(String str) {
		return (null == str || str.trim().equals(""));
	}
	

}
