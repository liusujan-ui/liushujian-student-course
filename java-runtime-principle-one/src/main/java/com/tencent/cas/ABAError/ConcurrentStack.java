package com.tencent.cas.ABAError;

import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.locks.LockSupport;

/**
 * @author 观自在
 * @description
 * @date 2025-11-30 16:47
 */
public class ConcurrentStack {
    //top cas 无锁修改
    //AtomicReference<Node> top=new AtomicReference<Node>();
    AtomicStampedReference<Node> top = new AtomicStampedReference<>(null,0);

    public void push(Node node) {
        Node oldTop;
        int v;
        do {
            v=top.getStamp();
            oldTop=top.getReference();
            node.setNext(oldTop);
        }
        while(!top.compareAndSet(oldTop,node,v,v+1)); //CAS 替换栈顶
    }

    //出栈  -- 取出栈顶，为了演示ABA效果，增加一个CAS操作的延时
    public Node pop(int time) {
        Node oldTop;
        Node newTop;
        int v;
        do {
            v=top.getStamp();
            oldTop=top.getReference();
            if (oldTop==null) {
                return null;
            }
            newTop=oldTop.getNext();
            if(time!=0){
                LockSupport.parkNanos(1000L *1000L *time); //休眠指定的时间
            }
        }
        while (!top.compareAndSet(oldTop,newTop,v,v+1));  //将下一个节点设置为TOP
        return oldTop; //将旧的top做为值返回
    }
}
