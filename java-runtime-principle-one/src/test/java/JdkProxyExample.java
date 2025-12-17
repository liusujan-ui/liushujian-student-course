package com.tencent.cas.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 使用JDK Proxy.newProxyInstance实现动态代理的示例
 */
@Slf4j
public class JdkProxyExample {

    /**
     * 目标对象接口
     */
    public interface UserService {
        String getUserName(Long userId);
        void updateUser(Long userId, String newName);
    }

    /**
     * 目标对象实现类
     */
    public static class UserServiceImpl implements UserService {
        @Override
        public String getUserName(Long userId) {
            log.info("Getting user name for userId: {}", userId);
            return "User-" + userId;
        }

        @Override
        public void updateUser(Long userId, String newName) {
            log.info("Updating user {} with new name: {}", userId, newName);
        }
    }

    /**
     * 自定义调用处理器
     * 实现InvocationHandler接口，用于处理代理对象的方法调用
     */
    public static class CustomInvocationHandler implements InvocationHandler {
        private final UserService target;

        public CustomInvocationHandler(UserService target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            log.info("Before method: {}", method.getName());
            
            // 调用目标方法
            Object result = method.invoke(target, args);
            
            log.info("After method: {}", method.getName());
            return result;
        }
    }

    /**
     * 创建代理对象
     * 
     * 使用JDK的Proxy.newProxyInstance方法创建动态代理对象。
     * 这种方式需要目标对象必须实现接口。
     * 
     * @param target 目标对象，将被代理的实际对象
     * @return UserService 代理对象，调用方法时会通过InvocationHandler进行拦截处理
     * 
     * 创建过程：
     * 1. 创建InvocationHandler实例
     * 2. 使用Proxy.newProxyInstance创建代理对象
     * 3. 指定类加载器、接口数组和调用处理器
     * 
     * 注意：这种方式只能代理接口，不能代理类
     */
    public static UserService createProxy(UserService target) {
        // 创建调用处理器
        InvocationHandler handler = new CustomInvocationHandler(target);
        
        // 使用Proxy.newProxyInstance创建代理对象
        // 参数1：类加载器（通常使用目标对象的类加载器）
        // 参数2：目标对象实现的接口数组
        // 参数3：调用处理器
        return (UserService) Proxy.newProxyInstance(
            target.getClass().getClassLoader(),
            target.getClass().getInterfaces(),
            handler
        );
    }

    public static void main(String[] args) {
        // 创建目标对象
        UserService target = new UserServiceImpl();
        
        // 创建代理对象
        UserService proxy = createProxy(target);

        log.info("proxy class-----------{}", proxy.getClass().getName());

        // 使用代理对象
        String userName = proxy.getUserName(123L);
        System.out.println("User name: " + userName);
        
        proxy.updateUser(123L, "NewName");
    }
}
