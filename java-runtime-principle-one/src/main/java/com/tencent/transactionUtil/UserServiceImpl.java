package com.tencent.transactionUtil;

import com.tencent.transactionUtil.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author 观自在
 * @description 事务的源码
 * @date 2025-12-13 21:32
 */
public class UserServiceImpl {


    private JdbcTemplate jdbctemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbctemplate = new JdbcTemplate(dataSource);
    }

    @Autowired
    DataSource dataSource;

    public int save(User user) throws SQLException {


        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        try {
            statement.execute("delete from users where id=3");
//            int a=0/0;
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("回滚");
            connection.rollback();
        }
        return 0;
    }

    public static void main(String[] args) throws SQLException {
        UserServiceImpl userService = new UserServiceImpl();
        userService.save(null);


//        InvocationHandler invocationHandler = new InvocationHandler() {
//            @Override
//            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                return null;
//            }
//        };
//        Object o = Proxy.newProxyInstance(invocationHandler.getClass().getClassLoader(), invocationHandler.getClass().getInterfaces(), invocationHandler);
//        new Method();

    }
}
