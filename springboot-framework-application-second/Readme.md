
### 今日知识点总结

1. 前后端传值使用modelmap.addAttribute
2. 实现WebMvcConfigurer 这个接口
重写addViewControllers：可以将请求地址进行重新映射
重写addInterceptors：可以将书写的拦截器添加进界面的访问
3. HandlerInterceptor：可以书写拦截器的规则
重写preHandle：可以获取session中的内容，如果没有进行拦截
4. return "redirect:/user/list"
这个方式的controller是在controller中重定向方法
5. 前端代码如果报错了，使用alert(msg) 将信息打印出来调试
6. LoggerFactory.getLogger
这个类打印日志非常好用

知识点一：controller层包括：findAll、deleteUser、login
知识点二：拦截器、视图解析器（addview、addinterceptor）
知识点三：删除用迭代器删除，比较稳
知识点四：重定向、转发
知识点五：httpsession 和 modelmap 不一样