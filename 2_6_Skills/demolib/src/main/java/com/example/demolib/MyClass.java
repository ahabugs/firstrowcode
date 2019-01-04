package com.example.demolib;

public class MyClass extends Base {

    @Override
    public void whoAmI() {
        int i = 0;
        System.out.print(String.format("Main : who am I? -- %s\n", getClass().getSimpleName
                ()));
        /* 调用了父类的方法，使用的对象是MyClass类的对象. super只是代表父类 */
        super.whoAmI();
    }

    public static void main(String[] args) {
        Base tmp = new MyClass();
        System.out.println(tmp.toString());
        tmp.whoAmI();
    }
}
