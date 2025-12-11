package com.tencent.springIOCUtil;

import java.io.File;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author 观自在
 * @description 获取包内所有带自定义注解的类
 * @date 2025-12-11 07:44
 */
public class ClassUtil {
    /**
     * 通过包名获取包内所有类
     */
    public static List<Class<?>> getAllClassByPackageName(Package pkg) throws Exception {
        String packageName = pkg.getName();
        //获取当前包下以及子包下所有的类
        List<Class<?>> classList = getClasses(packageName);
        return classList;
    }

    /**
     * 通过接口名取得某个接口下所有实现这个接口的类
     * @param clazz
     * @return
     * @throws Exception
     */
    public static List<Class<?>> getAllClassByInterface(Class<?> clazz) throws Exception {
        List<Class<?>> returnClassList = null;
        if (clazz.isInterface()) {
            //获取当前的包名
            String packageName = clazz.getPackage().getName();
            //获取当前包下以及子包下所有的类
            List<Class<?>> allClass = getClasses(packageName);
            if (allClass != null) {
                returnClassList=new ArrayList<Class<?>>();
                for (Class<?> cls : allClass) {
                    if (clazz.isAssignableFrom(cls)) {
                        if (!clazz.equals(cls)) {
                            returnClassList.add(cls);
                        }
                    }
                }

            }
        }
        return returnClassList;
    }


    public static String[] getPackageAllClassName(String classLocation,String packageName) throws Exception {
        //将packageName分解
        String[] packagePathSplit = packageName.split("[.]");
        String realClassLocation = classLocation;
        int packageLength = packagePathSplit.length;
        for (int i = 0; i < packageLength; i++) {
            realClassLocation = realClassLocation + File.separator + packagePathSplit[i];
        }
        File packageDir = new File(realClassLocation);
        if (packageDir.isDirectory()) {
            String[] allClassName = packageDir.list();
            return allClassName;
        }
        return null;
    }

    private static List<Class<?>> getClasses(String packageName) {
        List<Class<?>> classes=new ArrayList<>();
        boolean recursive = true;
        String packageDirName = packageName.replace('.', '/');
        Enumeration<URL> dirs;
        try {
            dirs=Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            while (dirs.hasMoreElements()) {
                URL url = dirs.nextElement();
                String protocol = url.getProtocol();
                if ("file".equals(protocol)) {
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    findAndAddClassesInPackageByFile(packageName,filePath,recursive,classes);
                } else if ("jar".equals(protocol)) {
                    JarFile jar;
                    try {
                        jar=((JarURLConnection)url.openConnection()).getJarFile();
                        Enumeration<JarEntry> entries = jar.entries();
                        while (entries.hasMoreElements()) {
                            JarEntry entry = entries.nextElement();
                            String name = entry.getName();
                            if (name.charAt(0) == '/') {
                                name = name.substring(1);
                            }
                            if (name.startsWith(packageDirName)) {
                                int idx = name.lastIndexOf('/');
                                if (idx != -1) {
                                    packageName = name.substring(0, idx).replace('/', '.');
                                }
                                if ((idx != -1) || recursive) {
                                    if (name.endsWith(".class") && !entry.isDirectory()) {
                                        String className = name.substring(packageName.length() + 1, name.length() - 6);
                                        try {
                                            classes.add(Class.forName(packageName+'.'+className));
                                        }catch (ClassNotFoundException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return classes;
    }

    private static void findAndAddClassesInPackageByFile(String packageName, String filePath, boolean recursive, List<Class<?>> classes) {
    File directory = new File(filePath);
    if (!directory.exists() || !directory.isDirectory()) {
        return;
    }

    File[] files = directory.listFiles();
    if (files == null) {
        return;
    }

    for (File file : files) {
        if (file.isDirectory()) {
            if (recursive) {
                // 如果是目录且需要递归，则继续扫描子目录
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, classes);
            }
        } else if (file.getName().endsWith(".class")) {
            // 如果是类文件，则加载类并添加到列表中
            String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
            try {
                classes.add(Class.forName(className));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}

}
