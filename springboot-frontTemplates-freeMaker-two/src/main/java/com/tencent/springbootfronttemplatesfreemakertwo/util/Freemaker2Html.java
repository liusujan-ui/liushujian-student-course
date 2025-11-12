//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tencent.springbootfronttemplatesfreemakertwo.util;

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

public class Freemaker2Html {
    private static final String TEMPLATE_PATH = "src/main/resources/templates";
    private static final String CLASS_PATH = "src/main/resources/templates";

    public Freemaker2Html() {
    }

    public static void main(String[] args) throws IOException, TemplateException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setDirectoryForTemplateLoading(new File("src/main/resources/templates"));
        HashMap<Object, Object> dataMap = new HashMap();
        dataMap.put("name", "张三");
        Template template = configuration.getTemplate("welcome.ftl");
        File file = new File("src/main/resources/templates\\welcome.html");
        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
        template.process(dataMap, writer);
        System.out.println("文件创建成功！！！");
        writer.close();
    }
}
