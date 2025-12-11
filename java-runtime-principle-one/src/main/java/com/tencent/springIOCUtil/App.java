package com.tencent.springIOCUtil;

import java.util.List;

/**
 * @author 观自在
 * @description
 * @date 2025-12-10 22:59
 */
public class App {

    public static void main(String[] args) throws Exception {
        //自己去写一个Application --帮助我们解析bean --map放在Application里面
        //spring -- xml 注解
        LclasspathXmlApplicationContext app = new LclasspathXmlApplicationContext("applicationContext.xml");
        try {
            Object bean = app.getBean("object");

            System.out.println(bean.getClass().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
        找到指定类下面的所有的文件，通过扫描找到内容
         */
        try {
            List<Class<?>> allClassByPackageName = ClassUtil.getAllClassByPackageName(App.class.getPackage());
            for (Class<?> aClass : allClassByPackageName) {
                System.out.println(aClass.getName());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //1.扫描包 -- 获取类上的注解
        //2.判断如果类上的注解 和我们的自定义注解一致，那就直接反射
        //3.同xml id -- 把当前类名的首字母小写
        LAnnotationAppContext context = new LAnnotationAppContext();
        Lservice lservice = (Lservice)context.getBean("lservice");
        lservice.test();

    }
}
