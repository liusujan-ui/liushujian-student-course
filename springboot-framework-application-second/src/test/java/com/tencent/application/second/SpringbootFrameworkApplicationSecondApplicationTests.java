package com.tencent.application.second;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Iterator;

@SpringBootTest
class SpringbootFrameworkApplicationSecondApplicationTests {

    @Test
    void contextLoads() {
        ArrayList<Integer> list=new ArrayList<>();
        list.add(1);
        list.add(1);
        list.add(3);
        list.add(4);

//        for (Integer i : list) { // foreach是copy到一个迭代器中，是不能这样删除掉报错。
//            if (i==1){
//                list.remove(i);
//            }
//        }


//        for (int i = list.size()-1; i >=0 ; i--) {
//            if (list.get(i)==1){
//                list.remove(i);
//            }
//        }


        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            if (next==1){
                iterator.remove();
            }
        }

        System.out.println(list);
    }

}
