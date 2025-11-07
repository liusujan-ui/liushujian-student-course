### 本文的知识点

1. 将yml文件中的person的属性，注入到person类中
2. 如果从properties中读取属性，遇到中文乱码，从file encoding中改为utf-8，并勾上ascii即可
3. @Value("${person.name}")
   private String name; 使用这种方式直接注入数据
4. @Value("#{2*11}")
   private int age;  使用这个注解可以添加数值型
5. @PropertySource("classpath:person.properties") 可以制定特定的properties
