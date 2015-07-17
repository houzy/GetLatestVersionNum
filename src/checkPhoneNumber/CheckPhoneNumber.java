package checkPhoneNumber;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckPhoneNumber {

	// 注意下面所有//转义应该是\\才对，可能是Windows环境//是对的
	// 手机：//D0?(1//d{10})(//D|$)
	// 电话：//D((//+86-)?((0//d{2,3}//-)?//d{7,8}))(//D|$)
	// Email：//w+([-+.]//w+)*@//w+([-.]//w+)*//.//w+([-.]//w+)*
	// 改进,这样就可以直接用目标串形如： str="010-12345678"; 的字串,不要求前面有字符.
	// 手机：(?<!//d)0?(1//dquesauthor)(?!//d)
	// 电话：(?<!//d)((//+86-)?((0//d{2,3}//-)?//d{7,8}))(?!//d)

	public boolean isPhoneNumber(String phoneNumber, String isPhoneNumberRegex) {
        //Pattern p = Pattern.compile(isPhoneNumberRegex);
        //Matcher m = p.matcher(phoneNumber);
        //if (m.matches()) {
        //    return true;
        //} else {
        //    return false;
        //}
        // 此处匹配整个字符串才会返回true
        return Pattern.matches(isPhoneNumberRegex, phoneNumber);
    }
	
	/**
	 * 得一正则表达对应的内容
	 * 
	 * @param con
	 * @param reg
	 * @return
	 */
	public String getValue(String con, String reg) {
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(con);
		String res = "";
		while (m.find()) {
			res = m.group(1);
			System.out.println(res);
		}
		return res;
	}

	// 用JAVA正则表达式来校验手机号码以及替换+86
	/**
	 * user java reg to check phone number and replace 86 or +86 only check
	 * start with "+86" or "86" ex +8615911119999 13100009999 replace +86 or 86
	 * with ""
	 * 
	 * @param phoneNum
	 * @return
	 * @throws Exception
	 */
	public String checkPhoneNum(String phoneNum) throws Exception {

		Pattern p1 = Pattern.compile("^((\\+{0,1}86){0,1})1[0-9]{10}");
		Matcher m1 = p1.matcher(phoneNum);
		if (m1.matches()) {
			Pattern p2 = Pattern.compile("^((\\+{0,1}86){0,1})");
			Matcher m2 = p2.matcher(phoneNum);
			StringBuffer sb = new StringBuffer();
			while (m2.find()) {
				m2.appendReplacement(sb, "");
			}
			m2.appendTail(sb);
			System.out.println(sb.toString());
			return sb.toString();

		} else {
			throw new Exception("The format of phoneNum " + phoneNum
					+ "  is not correct!Please correct it");
		}

	}

	// Java 电话号码去除IP号、+86
	private static final String[] IPPFXS4 = { "1790", "1791", "1793", "1795",
			"1796", "1797", "1799" };
	private static final String[] IPPFXS5 = { "12583", "12593", "12589",
			"12520", "10193", "11808" };
	private static final String[] IPPFXS6 = { "118321" };

	/**
	 * 消除电话号码中 可能含有的 IP号码、+86、0086等前缀
	 * 
	 * @param telNum
	 * @return
	 */
	public static String trimTelNum(String telNum) {

		if (telNum == null || "".equals(telNum)) {
			System.out.println("trimTelNum is null");
			return null;
		}

		String ippfx6 = substring(telNum, 0, 6);
		String ippfx5 = substring(telNum, 0, 5);
		String ippfx4 = substring(telNum, 0, 4);

		if (telNum.length() > 7
				&& (substring(telNum, 5, 1).equals("0")
						|| substring(telNum, 5, 1).equals("1")
						|| substring(telNum, 5, 3).equals("400") || substring(
							telNum, 5, 3).equals("+86"))
				&& (inArray(ippfx5, IPPFXS5) || inArray(ippfx4, IPPFXS4)))
			telNum = substring(telNum, 5);
		else if (telNum.length() > 8
				&& (substring(telNum, 6, 1).equals("0")
						|| substring(telNum, 6, 1).equals("1")
						|| substring(telNum, 6, 3).equals("400") || substring(
							telNum, 6, 3).equals("+86"))
				&& inArray(ippfx6, IPPFXS6))
			telNum = substring(telNum, 6);
		// remove ip dial

		telNum = telNum.replace("-", "");
		telNum = telNum.replace(" ", "");

		if (substring(telNum, 0, 4).equals("0086"))
			telNum = substring(telNum, 4);
		else if (substring(telNum, 0, 3).equals("+86"))
			telNum = substring(telNum, 3);
		else if (substring(telNum, 0, 5).equals("00186"))
			telNum = substring(telNum, 5);

		return telNum;
	}

	/**
	 * 截取字符串
	 * 
	 * @param s
	 * @param from
	 * @return
	 */
	protected static String substring(String s, int from) {
		try {
			return s.substring(from);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	protected static String substring(String s, int from, int len) {
		try {
			return s.substring(from, from + len);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 判断一个字符串是否在一个字符串数组中
	 * 
	 * @param target
	 * @param arr
	 * @return
	 */
	protected static boolean inArray(String target, String[] arr) {
		if (arr == null || arr.length == 0) {
			return false;
		}
		if (target == null) {
			return false;
		}
		for (String s : arr) {
			if (target.equals(s)) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		Logger logger = Logger.getLogger("main");
		logger.setLevel(Level.ALL);

		CheckPhoneNumber t = new CheckPhoneNumber();

		logger.info("11111111");
		String str = "电话：13668978333";
		String reg = "\\D0?(1\\d{10})(\\D|$)";
		String tel = t.getValue(str, reg);
		System.out.println(tel);

		logger.info("22222222");
		str = "电话：0370-6541114[白天]电话0370-6541000[晚上]";
		reg = "\\D((\\+86-)?((0\\d{2,3}\\-)?\\d{7,8}))(\\D|$)";
		tel = t.getValue(str, reg);
		System.out.println(tel);

		logger.info("33333333");
		str = "电话：0734-6570769,小灵通5386568";
		reg = "\\D((\\+86-)?((0\\d{2,3}\\-)?\\d{7,8}))(\\D|$)";
		tel = t.getValue(str, reg);
		System.out.println(tel);

		logger.info("44444444");
		str = "电话：13402158967";
		reg = "\\D((\\+86-)?((0\\d{2,3}\\-)?\\d{7,8}))(\\D|$)";
		tel = t.getValue(str, reg);
		System.out.println(tel);

		logger.info("55555555");
		str = "市场办公电话-010-82238887";
		reg = "\\D((\\+86-)?((0\\d{2,3}\\-)?\\d{7,8}))(\\D|$)";
		tel = t.getValue(str, reg);
		System.out.println(tel);

		logger.info("66666666");
		str = "电话：+86-0734-63702731 手机:...";
		reg = "\\D((\\+86-)?((0\\d{2,3}\\-)?\\d{7,8}))(\\D|$)";
		tel = t.getValue(str, reg);
		System.out.println(tel);

		try {
			t.checkPhoneNum(tel);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("77777777");
		str = "电话：+8613866204548 手机:...";
		reg = "\\D((\\+86-)?((0\\d{2,3}\\-)?\\d{7,8}))(\\D|$)";
		tel = t.getValue(str, reg);
		System.out.println(tel);

		try {
			t.checkPhoneNum(tel);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("88888888");
		// java中判断电话号码（手机和一般电话），正则表达式
		str = "13112341234,010-12456789,01012456789,(010)12456789,00861012456789,+861012456789,17951+8618211503458";
		Pattern p = Pattern
				.compile("1([\\d]{10})|((\\+[0-9]{2,4})?\\(?[0-9]+\\)?-?)?[0-9]{7,8}");
		Matcher m = p.matcher(str);
		while (m.find()) {
			System.out.println(m.group());
		}

		logger.info("99999999");
		str = "+8613466364545";
		p = Pattern
				.compile("1([\\d]{10})|((\\+[0-9]{2,4})?\\(?[0-9]+\\)?-?)?[0-9]{7,8}");
		m = p.matcher(str);
		while (m.find()) {
			System.out.println(m.group());
			tel = m.group(0);
		}

		try {
			t.checkPhoneNum(tel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//测试数据  
        String telNum = "+8618611503575";  
        System.out.println("before trim telNum=" + telNum);  
        boolean isPhoneNumber = 
				t.isPhoneNumber(telNum, "1([\\d]{10})|((\\+[0-9]{2,4})?\\(?[0-9]+\\)?-?)?[0-9]{7,8}");
		System.out.println("isPhoneNumber: " + isPhoneNumber);
        telNum = trimTelNum(telNum);  
        System.out.println("trimTelNum telNum=" + telNum);  
          
        telNum = "008618611503575";  
        System.out.println("before trim telNum=" + telNum);  
        isPhoneNumber = 
				t.isPhoneNumber(telNum, "1([\\d]{10})|((\\+[0-9]{2,4})?\\(?[0-9]+\\)?-?)?[0-9]{7,8}");
		System.out.println("isPhoneNumber: " + isPhoneNumber);
        telNum = trimTelNum(telNum);  
        System.out.println("trimTelNum telNum=" + telNum);  
  
        telNum = "17951+8618211503458";  
        System.out.println("before trim telNum=" + telNum);  
        isPhoneNumber = 
				t.isPhoneNumber(telNum, "1([\\d]{10})|((\\+[0-9]{2,4})?\\(?[0-9]+\\)?-?)?[0-9]{7,8}");
		System.out.println("isPhoneNumber: " + isPhoneNumber);
        telNum = trimTelNum(telNum);  
        System.out.println("trimTelNum telNum=" + telNum);  
          
        telNum = "1795818211503458";  
        System.out.println("before trim telNum=" + telNum); 
        isPhoneNumber = 
				t.isPhoneNumber(telNum, "1([\\d]{10})|((\\+[0-9]{2,4})?\\(?[0-9]+\\)?-?)?[0-9]{7,8}");
		System.out.println("isPhoneNumber: " + isPhoneNumber);
        telNum = trimTelNum(telNum);  
        System.out.println("trimTelNum telNum=" + telNum);  
          
        telNum = "1252015611503575";  
        System.out.println("before trim telNum=" + telNum);
        isPhoneNumber = 
				t.isPhoneNumber(telNum, "1([\\d]{10})|((\\+[0-9]{2,4})?\\(?[0-9]+\\)?-?)?[0-9]{7,8}");
		System.out.println("isPhoneNumber: " + isPhoneNumber);
        telNum = trimTelNum(telNum);  
        System.out.println("trimTelNum telNum=" + telNum);  
          
        telNum = "11832115611503575";  
        System.out.println("before trim telNum=" + telNum);  
        isPhoneNumber = 
				t.isPhoneNumber(telNum, "1([\\d]{10})|((\\+[0-9]{2,4})?\\(?[0-9]+\\)?-?)?[0-9]{7,8}");
		System.out.println("isPhoneNumber: " + isPhoneNumber);
        telNum = trimTelNum(telNum);  
        System.out.println("trimTelNum telNum=" + telNum);  
          
        telNum = "118321+8615611503575";  
        System.out.println("before trim telNum=" + telNum); 
        isPhoneNumber = 
				t.isPhoneNumber(telNum, "1([\\d]{10})|((\\+[0-9]{2,4})?\\(?[0-9]+\\)?-?)?[0-9]{7,8}");
		System.out.println("isPhoneNumber: " + isPhoneNumber);
        telNum = trimTelNum(telNum);  
        System.out.println("trimTelNum telNum=" + telNum);  
	}
}
