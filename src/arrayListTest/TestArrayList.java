package arrayListTest;

import java.util.ArrayList;

public class TestArrayList {

	public static void main(String[] args) {
		ArrayList<String> list = new ArrayList<String>();
		list.add("aaa");
		list.add("bbb");
		list.add("ccc");
		list.add("ddd");
		list.add("eee");
		
		// test 1: get() will return a reference to the object, never a
		// copy. Any modification you do on the returned reference will
		// be made on the object itself
		String a = list.get(0);
		String b = list.get(0);
		String c = "aaa";
		String d = new String("aaa");
		String e = new String(a);
		
		System.out.println("a == b is " + (a == b));
		System.out.println("a == c is " + (a == c));
		System.out.println("a == d is " + (a == d));
		System.out.println("a == e is " + (a == e));
		
		// test 2: Exception in thread "main" java.util.ConcurrentModificationException
		for (String s:list) {
			if (s.equals("ddd")) {
				// Exception in thread "main" java.util.ConcurrentModificationException
				System.out.println("remove: " + s);
				list.remove(s);
			}
		}
		/*
		今天遍历一个ArrayList去查找某项是否存在，如果存在的话就从列表中删除，方法如下：

		for(Person p : persons){ 
		        if(p.getName().equals(name)) 
		        persons.remove(p); 
		}
		结果出现java.util.ConcurrentModificationException错误，一个并发的错误，上网google了一下，有人给出的解释是如果一边遍历ArrayList一边删除当前元素会引发java.util.ConcurrentModificationException 即”要确保遍历过程顺利完成，必须保证遍历过程中不更改集合的内容（Iterator的remove()方法除外），因此，确保遍历可靠的原则是只在一个线程中使用这个集合，或者在多线程中对遍历代码进行同步。”
		解决方法1： 

		//使用java.util.Iterator 
		for(Iterator it = list.iterator(); it.hasNext();){  
		        Integer i = (Integer)it.next(); 
		        it.remove(); 
		}
		解决方法2：

		 
		for(int i = 0; i<persons.size(); i++){ 
		    if(persons.get(i).getName().equals(name)) 
		        persons.remove(i); 
		}
		方法2不适合大型数组
		*/
		
		// test 3: remove reference, not object
		System.out.println("a == " + a);
		list.remove(0);
		System.out.println("a == " + a);
		
		// test 4: Does ArrayList.clear() method frees memory?
		// 只是移除了引用，如果对象还有别的引用，对象不会被释放
		// You are correct because the memory is cleared asynchronously by the Garbage collector,
		// and not directly by such library functions. clear would only null them all out, and update
		// the ArrayList state accordingly.
		// If any or all of those elements are not not referenced by any other portion of your code,
		// they become eligible for garbage collection, and might be destroyed + de-allocated any
		// time from shortly after that call to the end of time.
		a = list.get(0);
		System.out.println("a == " + a);
		list.clear();
		System.out.println("a == " + a);
	}
}
