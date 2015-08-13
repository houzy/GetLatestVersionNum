package strings;

public class CompareStrings {
	
	public static void main(String[] args) {
		String a1 = new String("A");
		String a2 = new String("A");

		if (a1 == a2) {
			System.out.println("a1 == a2");
		} else {
			System.out.println("a1 != a2");
		}
		
		String b1 = "A";
		String b2 = "A";
		if (b1 == b2) {
			System.out.println("b1 == b2");
		} else {
			System.out.println("b1 != b2");
		}
		
		String c1 = new String(a1);
		if (c1 == a1) {
			System.out.println("c1 == a1");
		} else {
			System.out.println("c1 != a1");
		}
		
		String c2 = a2;
		if (c2 == a2) {
			System.out.println("c2 == a2");
		} else {
			System.out.println("c2 != a2");
		}

		String d1 = null;
		//String d2 = new String(null); // error
		String d2 = new String(d1); // error
		System.out.println(d2);
	}
}
