package rxJavaTimeout;

import java.util.ArrayList;

public class Tests {
	
	void setaa(String arg) {
		arg = new String("test");
	}
	
	void setbb(String arg) {
		arg.concat("test");
	}
	
	void setcc(ArrayList<String> arg) {
		arg.add(0, "World");
	}
	
	void doTest() {
		String aaa = new String();
		setbb(aaa);
		System.out.println(aaa);
		
		aaa = new String();
		setaa(aaa);
		System.out.println(aaa);
		
		ArrayList<String> ccc = new ArrayList<String>();
		ccc.add(0, "Hello");
		System.out.println(ccc);
		setcc(ccc);
		System.out.println(ccc);
	}
	
	public static void main(String[] args) {
		Tests tests = new Tests();
		tests.doTest();
	}
}


