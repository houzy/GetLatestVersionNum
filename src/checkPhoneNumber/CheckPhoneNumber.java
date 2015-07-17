package checkPhoneNumber;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckPhoneNumber {
	
	// 注意下面所有//转义应该是\\才对，可能是Windows环境//是对的
	//手机：//D0?(1//d{10})(//D|$) 
	//电话：//D((//+86-)?((0//d{2,3}//-)?//d{7,8}))(//D|$)
	//Email：//w+([-+.]//w+)*@//w+([-.]//w+)*//.//w+([-.]//w+)*
	//改进,这样就可以直接用目标串形如： str="010-12345678"; 的字串,不要求前面有字符.
	//手机：(?<!//d)0?(1//dquesauthor)(?!//d) 
	//电话：(?<!//d)((//+86-)?((0//d{2,3}//-)?//d{7,8}))(?!//d)
	
	/**
	 * 得一正则表达对应的内容
	 * 
	 * @param con
	 * @param reg
	 * @return
	 */
	public String getValue(String con, String reg){
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(con);
		String res = "";
		while (m.find()) {
			res = m.group(1);
			System.out.println(res);
		}
		return res;
	}
	
	public static void main(String[] args) {
		CheckPhoneNumber t = new CheckPhoneNumber();
		
		String str = "电话：13668978333";
		String reg = "\\D0?(1\\d{10})(\\D|$)";
		String tel = t.getValue(str, reg);
		System.out.println(tel);
		
        str = "电话：0370-6541114[白天]电话0370-6541000[晚上]";
		reg = "\\D((\\+86-)?((0\\d{2,3}\\-)?\\d{7,8}))(\\D|$)";
		tel = t.getValue(str, reg);
		System.out.println(tel);
        
		str = "电话：0734-6570769,小灵通5386568";
		reg = "\\D((\\+86-)?((0\\d{2,3}\\-)?\\d{7,8}))(\\D|$)";
		tel = t.getValue(str, reg);
		System.out.println(tel);
        
		str = "电话：13402158967";
		reg = "\\D((\\+86-)?((0\\d{2,3}\\-)?\\d{7,8}))(\\D|$)";
		tel = t.getValue(str, reg);
		System.out.println(tel);
		
		str = "电话：+86-0734-63702731 手机:...";
		reg = "\\D((\\+86-)?((0\\d{2,3}\\-)?\\d{7,8}))(\\D|$)";
		tel = t.getValue(str, reg);
		System.out.println(tel);
		
		str = "市场办公电话-010-82238887";
		reg = "\\D((\\+86-)?((0\\d{2,3}\\-)?\\d{7,8}))(\\D|$)";
		tel = t.getValue(str, reg);
		System.out.println(tel);
	}
}
