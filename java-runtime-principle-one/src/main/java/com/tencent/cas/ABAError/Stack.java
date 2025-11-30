package com.tencent.cas.ABAError;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

/**
 * @author 观自在
 * @description 实现一个栈，后进先出
 * @date 2025-11-30 16:55
 */

public class Stack {
    // top cas无锁修改
    AtomicReference<Node> top =new AtomicReference<>();

    public void push(Node node) {
        Node oldTop;
        do {
            oldTop=top.get();
            node.setNext(oldTop);
        }
        while (!top.compareAndSet(oldTop,node)); //CAS 替换栈顶
    }

    //出栈  --取出栈顶，为了演示ABA效果，增加一个CAS操作的延时
    public Node pop(int time) {
        Node oldTop;
        Node newTop;
        do {
            oldTop=top.get();
            if (oldTop==null) {
                return null;
            }
            newTop=oldTop.getNext();
            if(time!=0){
                LockSupport.parkNanos(1000L *1000L *time); //休眠指定的时间
            }
        }
        while (!top.compareAndSet(oldTop,newTop));  //将下一个节点设置为TOP
        return oldTop; //将旧的top做为值返回
    }
}
