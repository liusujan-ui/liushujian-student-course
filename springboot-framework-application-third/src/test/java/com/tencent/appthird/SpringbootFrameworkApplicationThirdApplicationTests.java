package com.tencent.appthird;

import com.tencent.appthird.entity.Person;
import com.tencent.appthird.entity.PersonInfo;
import com.tencent.appthird.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.*;

@SpringBootTest
class SpringbootFrameworkApplicationThirdApplicationTests {

    @Resource
    private PersonRepository personRepository;

    @Test
    void contextLoads() {
//        personRepository.deleteByName("zhang");
//        System.out.println(personRepository.findPerson());
//        personRepository.updatePerson(Person.builder().pid("402880479a6e3bc1019a6e3bcbdb0004").pname("刘德华").psex("男").page(60).build());
//        System.out.println(personRepository.findPersonByBname("三国演义"));

//        List<PersonInfo> allInfo = personRepository.findAllInfo("402880479a6e3bc1019a6e3bcbdb0003");
//        allInfo.forEach(x-> System.out.println(x.getPid()+" "+x.getPname()+" "+x.getPsex()+" "+x.getPage()+" "
//                +x.getMarried()+" "+x.getBid()+" "+x.getBname()+" "+x.getBprice()));


//        List<Object> allInfo = personRepository.findAllInfo1("402880479a6e3bc1019a6e3bcbdb0003");
//        Object[] o = (Object[]) allInfo.get(0);
//        System.out.println(Arrays.toString(o));

        List<Map<String,Object>> allInfo = personRepository.findAllInfo2("402880479a6e3bc1019a6e3bcbdb0003");
        for (Map<String,Object> map : allInfo) {
            System.out.println(map.get("bid")+" "+map.get("bname")+" "+map.get("pname")+" "+map.get("bid")+" "+map.get("bname")
            +" "+map.get("bprice"));
        }
    }

    private void test() {
        System.out.println(personRepository.findAllByPageLessThanEqual(22));
        System.out.println(personRepository.findAllByPageBetweenAndPsexEquals(20,22,"男"));
        System.out.println(personRepository.findAllByGetMarriedAndPsexEquals(true,"男"));
    }

    private void initPerson() {
        List<Person> personList=new ArrayList<>();
        Collections.addAll(personList,
                Person.builder().pname("zhangsan").psex("男").page(22).getMarried(true).build(),
                Person.builder().pname("lisi").psex("女").page(21).getMarried(false).build(),
                Person.builder().pname("wangwu").psex("男").page(25).getMarried(false).build(),
                Person.builder().pname("zhaoliu").psex("女").page(23).getMarried(true).build(),
                Person.builder().pname("tianqi").psex("男").page(24).getMarried(true).build());
        personRepository.saveAll(personList);
    }

}
