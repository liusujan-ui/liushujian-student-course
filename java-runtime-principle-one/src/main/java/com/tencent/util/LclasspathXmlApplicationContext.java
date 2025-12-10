package com.tencent.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 观自在
 * @description spring源码的解析，主要围绕map进行封装
 * @date 2025-12-10 22:40
 */
public class LclasspathXmlApplicationContext {

    private String xmlPath;

    static Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

    public LclasspathXmlApplicationContext(String xmlPath) {
        this.xmlPath = xmlPath;
    }

    public Object getBean(String id) throws DocumentException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        if (id == null) {throw new IllegalArgumentException("id is null");}
        if (singletonObjects.containsKey(id)) {return singletonObjects.get(id);}
        //map里面没有bean -- 创建Bean -- 解析XML -- 把bean返回
        List<Element> readXml=readXml();
        if (readXml == null) {throw new DocumentException("readXml is no id");}
        String className = findByElementClass(readXml,id);
        if (className == null) {throw new DocumentException("className is null");}
        return newInstance(className);
    }

    public String findByElementClass(List<Element> readerXml,String beanId) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        for (Element element : readerXml) {
            String xmlBeanId = element.attributeValue("id");
            if (xmlBeanId.equals(beanId)) {
                continue;//继续执行
            }
            String xmlClass=element.attributeValue("class");
            singletonObjects.put(beanId,newInstance(xmlClass));
            return xmlClass;
        }
        return null;
    }

    public Object newInstance(String className) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        Class<?> aClass = Class.forName(className);
        return aClass.newInstance();
    }

    public List<Element> readXml() throws DocumentException {
        SAXReader reader = new SAXReader();
        Document read = reader.read(getResourceAsStream("applicationContext.xml"));
        Element element = read.getRootElement();
        //递归
        List<Element> elements = element.elements();
        return elements;
    }

    public InputStream getResourceAsStream(String xmlPath) {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(xmlPath);
        return inputStream;
    }
}
