package com.tencent.cas;

import lombok.Builder;
import lombok.Data;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author 观自在
 * @description
 * @date 2025-11-30 14:18
 */
public class AtomicTest {
    private static AtomicIntegerFieldUpdater<person> updater = AtomicIntegerFieldUpdater.newUpdater(person.class, "id");

    public static void main(String[] args) {
        person person = com.tencent.cas.person.builder().id(1).build();
        updater.addAndGet(person,2);
        System.out.println(person);
    }

}


@Data
@Builder
class person{
    volatile int id; // 需要更新的一定要用volatile修饰
}
