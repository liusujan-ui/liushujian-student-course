package com.tencent.appthird.repository;

import com.tencent.appthird.entity.Person;
import com.tencent.appthird.entity.PersonInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author 观自在
 * @date 2025-11-10 22:31
 */
public interface PersonRepository extends JpaRepository<Person, String> {
    List<Person> findAllByPageLessThanEqual(int page);
//    2、查询出年龄在20-22岁之间并且性别是男的人
    List<Person> findAllByPageBetweenAndPsexEquals(int p1, int p2,String sex);
//    3、查询出已经结婚并且性别是男的人
    List<Person> findAllByGetMarriedAndPsexEquals(boolean isMarried,String psex);
//    4、根据pname来模糊删除一个person的数据

    @Modifying
    @Transactional
    @Query(value = "delete from Person where pname like %:pname%")
    void deleteByName(@Param("pname") String name);

//    @Query(value = "select * from person where page between 20 and 22 and psex='女'",nativeQuery = true)
    @Query(value = "select p from Person p where p.page between 20 and 22 and p.psex='女'")
    List<Person> findPerson();


//    6、使用spel表达式来完成person表的修改操作
    @Modifying
    @Transactional
    @Query(value = "update person set pname=:#{#person.pname},psex=:#{#person.psex}," +
            "page=:#{#person.page} where pid=:#{#person.pid}",nativeQuery = true)
    void updatePerson(@Param("person") Person person);

//    7、根据书名来查询该书的拥有者
    @Query(value = "select p from Person p inner join Book b on p.pid=b.pid " +
            "where b.bname=:bname")
    Person findPersonByBname(@Param("bname") String bname);

//    8、连表查询-根据用户id查询person和book
    @Query(value = "select p.pid as pid,p.pname as pname,p.psex as psex,p.getMarried as getMarried" +
            ",b.bid as bid,b.bname as bname,b.bprice as bprice from Person p inner join Book b on " +
            "p.pid=b.pid where p.pid=:pid")
    List<PersonInfo> findAllInfo(@Param("pid") String pid);


    @Query(value = "select p.pid as pid,p.pname as pname,p.psex as psex,p.getMarried as getMarried" +
            ",b.bid as bid,b.bname as bname,b.bprice as bprice from Person p inner join Book b on " +
            "p.pid=b.pid where p.pid=:pid")
    List<Object> findAllInfo1(@Param("pid") String pid);


    @Query(value = "select p.pid as pid,p.pname as pname,p.psex as psex,p.getMarried as getMarried" +
            ",b.bid as bid,b.bname as bname,b.bprice as bprice from Person p inner join Book b on " +
            "p.pid=b.pid where p.pid=:pid")
    List<Map<String,Object>> findAllInfo2(@Param("pid") String pid);
}


