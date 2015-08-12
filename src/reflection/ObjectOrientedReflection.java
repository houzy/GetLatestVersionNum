package reflection;

/*
我们在开发过程中总会碰到反射，一直在寻找封装的好用的反射库项目。昨天研究了一下，得到了很好的结果。
相信你看完这篇文章后立马就能学会反射。

首先请clone这个项目：https://github.com/jOOQ/jOOR

然后复制里面的两个类（https://github.com/jOOQ/jOOR/tree/master/jOOR/src/main/java/org/joor）
进入自己的项目即可。
*/

public class ObjectOrientedReflection {
	private String name;

    private String className;

    ObjectOrientedReflection() {

    }

    ObjectOrientedReflection(String clsName) {
        this.className = clsName;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String getName() {
        return name;
    }

    public String getClassName() {
        return className;
    }

    public static void method() {

    }

    public static void main(String[] args) {
    	String name = null;
    	ObjectOrientedReflection kale;
        // 【创建类】
        kale = Reflect.on(ObjectOrientedReflection.class).create().get(); // 无参数
        System.err.println("------------------> class name = " + kale.getClassName());
        kale = Reflect.on(ObjectOrientedReflection.class).create("kale class name").get();// 有参数
        System.err.println("------------------> class name = " + kale.getClassName());

        // 【调用方法】
        Reflect.on(kale).call("setName","调用setName");// 多参数
        System.err.println("调用方法：name = " + Reflect.on(kale).call("getName"));// 无参数

        // 【得到变量】
        name = Reflect.on(kale).field("name").get();// 复杂
        name = Reflect.on(kale).get("name");// 简单
        System.err.println("得到变量值： name = " + name);

        // 【设置变量的值】
        Reflect.on(kale).set("className", "hello");
        System.err.println("设置变量的值： name = " + kale.getClassName());
        System.err.println("设置变量的值： name = " + Reflect.on(kale).set("className", "hello2").get("className"));
    }
}

/*
这样就完成了常用功能的讲解啦，如果你想看看详细的测试用例，可以到原本项目中的test文件夹中查看。
test类都在这里：https://github.com/jOOQ/jOOR/tree/master/jOOR/src/test/java/org/joor/test
 */
