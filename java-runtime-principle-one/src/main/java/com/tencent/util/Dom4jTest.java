package com.tencent.util;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;


/**
 * @author 观自在
 * @description
 * @date 2025-12-10 22:08
 */
public class Dom4jTest {


    public static void main(String[] args) throws DocumentException {
        new Dom4jTest().test1();
    }
    private void test1() throws DocumentException {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(getResourceAsStream("applicationContext.xml"));

        Element root = document.getRootElement();
        //递归
        getNodes(root);
    }

    private void getNodes(Element root) {
        System.out.println("获取根节点："+root);
        List<Attribute> attributes = root.attributes();
        for (Attribute attribute : attributes) {
            System.out.println(attribute.getName()+"================"+attribute.getText());
        }

        String textTrim = root.getTextTrim();
        if (!StringUtils.isEmpty(textTrim)) {
            System.out.println(textTrim);
        }

        Iterator<Element> iterator = root.elementIterator();
        while (iterator.hasNext()) {
            Element element = iterator.next();
            System.out.println(element.getName());
        }
    }

    public InputStream getResourceAsStream(String xmlPath) {
        InputStream inputStream=this.getClass().getClassLoader().getResourceAsStream(xmlPath);
        return inputStream;
    }



}
