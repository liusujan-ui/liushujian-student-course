package com.tencent.mybatisfifth;

import com.tencent.mybatisfifth.dao.StudentMapper;
import com.tencent.mybatisfifth.entity.StudentDO;
import com.tencent.mybatisfifth.utils.SqlSessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SpringbootFrameworkMybatisFifthApplicationTests {

    @Test
    void contextLoads() {

    }


    /***
     * 这种方式不太常用
     */
    @Test
    public void testSelect() {
        SqlSession sqlSession = SqlSessionFactoryUtil.openSession();
        Object o = sqlSession.selectOne("com.tencent.mybatisfifth.dao.StudentMapper.selectById", 1001);
        System.out.println(o);
    }


    /***
     * 这种方式比较常用
     */
    @Test
    public void testSelect2() {
        SqlSession sqlSession = SqlSessionFactoryUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        List<StudentDO> studentDO = mapper.selectByName("张");
        System.out.println(studentDO);
    }
}
