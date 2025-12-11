package com.tencent.springIOCUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 观自在
 * @description
 * @date 2025-12-11 08:28
 */
public class LAnnotationAppContext {

    //思路一样 Map
    //扫描注释，扫包 反射一个类
//    如果没有id 就把类名转换成id
//    写入map
    static Map<String,Object> singletonObjects = new ConcurrentHashMap<>();

    public Object getBean(String id) throws Exception {
        if(singletonObjects.containsKey(id)) {
            return singletonObjects.get(id);
        }else {
            return doCreateBean(id);
        }
    }

    private Object doCreateBean(String beanId) throws Exception {
        //1.扫包，扫描启动类下面的包
        List<Class<?>> classes = ClassUtil.getAllClassByPackageName(App.class.getPackage());
        Object o = null;
        for(Class<?> clazz : classes) {
            Lcomponent annotation = clazz.getAnnotation(Lcomponent.class);
            if(annotation == null) {
                continue;
            }
            String name=clazz.getSimpleName();
            String id=name.substring(0,1).toLowerCase()+name.substring(1);
            Object bean=clazz.newInstance();
            if(beanId.equals(id)) {
                o=bean;
            }
            singletonObjects.put(id,bean);
        }
            return o;
    }
}
