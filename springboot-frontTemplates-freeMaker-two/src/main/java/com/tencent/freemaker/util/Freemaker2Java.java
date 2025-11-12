//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tencent.freemaker.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;

public class Freemaker2Java {
    private static final String TEMPLATE_PATH = "src/main/resources/templates";
    private static final String CLASS_PATH = "src/main/java/com/tencent/freemaker/javaTemplates";



    public static void main(String[] args) throws IOException, TemplateException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setDirectoryForTemplateLoading(new File(TEMPLATE_PATH));
        HashMap<Object, Object> dataMap = new HashMap();
        dataMap.put("classPath", "com.tencent.freemaker.javaTemplates");
        dataMap.put("className", "HelloFreeMaker");
        dataMap.put("helloworld", "这是通过freemarker自动生成的代码！！！");
        Template template = configuration.getTemplate("hello.ftl");
        File file = new File(CLASS_PATH+"\\"+"HelloFreeMaker.java");
        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
        template.process(dataMap, writer);
        System.out.println("文件创建成功！！！");
        writer.close();
    }
}
