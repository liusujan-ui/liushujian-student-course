package com.tencent.mybatisfifth.utils;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

public class SqlSessionFactoryUtil {

    /**
     * 获得会话工厂
     */
    private static SqlSessionFactory getFactory() {
        InputStream inputStream=null;
        SqlSessionFactory sqlSessionFactory=null;

        try {
            inputStream=SqlSessionFactoryUtil.class.getClassLoader().getResourceAsStream("mybatis-config.xml");

            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        }
        finally {
            if(inputStream!=null){
                try {
                    inputStream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return sqlSessionFactory;
    }

    public static SqlSession openSession(boolean isAutoCommit){
        return getFactory().openSession(isAutoCommit);
    }

    public static SqlSession openSession(){
        return openSession(true);
    }

    public static void closeSession(SqlSession sqlSession){
        if(sqlSession!=null){
            sqlSession.close();
        }
    }
}
