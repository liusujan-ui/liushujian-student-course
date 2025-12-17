package com.tencent.cas.proxy;

import lombok.extern.slf4j.Slf4j;

/**
 * 使用Enhancer实现动态代理的示例
 */
@Slf4j
public class EnhancerProxyExample {

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
     * 方法拦截器
     */
    public static class MethodInterceptor implements net.sf.cglib.proxy.MethodInterceptor {
        private final UserService target;

        public MethodInterceptor(UserService target) {
            this.target = target;
        }

        @Override
        public Object intercept(Object obj, java.lang.reflect.Method method, Object[] args,
                             net.sf.cglib.proxy.MethodProxy proxy) throws Throwable {
            log.info("Before method: {}", method.getName());
            
            // 调用目标方法
            Object result = method.invoke(target, args);
            
            log.info("After method: {}", method.getName());
            return result;
        }
    }

    /**
 * 创建代理对象
 */
public static UserService createProxy(UserService target) {
    // 创建Enhancer实例，这是CGLIB中用于创建代理的核心类
    net.sf.cglib.proxy.Enhancer enhancer = new net.sf.cglib.proxy.Enhancer();

    // 设置代理类的父类。这里设置为UserServiceImpl.class
    // 这意味着生成的代理类将是UserServiceImpl的子类
    enhancer.setSuperclass(UserServiceImpl.class);

    // 设置方法拦截器。当代理对象的方法被调用时，
    // 会调用MethodInterceptor的intercept方法来处理
    enhancer.setCallback(new MethodInterceptor(target));

    // 创建并返回代理对象
    // enhancer.create()会生成一个UserServiceImpl的子类实例
    return (UserService) enhancer.create();
}


    public static void main(String[] args) {
        // 创建目标对象
        UserService target = new UserServiceImpl();
        
        // 创建代理对象
        UserService proxy = createProxy(target);

        log.info("proxy class-----------{}",proxy.getClass().getName());

        // 使用代理对象
        String userName = proxy.getUserName(123L);
        System.out.println("User name: " + userName);
        
        proxy.updateUser(123L, "NewName");
    }
}
