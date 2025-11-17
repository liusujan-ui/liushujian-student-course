package com.tencent;

/**
 * @author 观自在
 * @date 2025-11-17 19:51
 *
 * @Description 使用javap -v main.class>main.txt
 *              将class中的内容，反编译成class字节码内容，然后分析
 */

public class Main {

    public static void func(){
        int x=500;
        int y=100;
        int a=x/y;
        int b=50;
        System.out.println(a+b);
    }
    public static void main(String[] args) {
        //TIP 当文本光标位于高亮显示的文本处时按 <shortcut actionId="ShowIntentionActions"/>
        // 查看 IntelliJ IDEA 建议如何修正。
       func();
    }
}