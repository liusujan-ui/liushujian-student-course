### 知识点 JPA
> 1.项目加载不出来主要是【项目结构】中的模块没有配置出来，选择外部导入maven的方式，并且【项目】中配置好jdk相关内容即可
> 2.使用jpa，在创建项目的时候要勾选上
> 3.dao层的接口 继承 JpaRepository<User, Integer>，其中user是实体类，integer是该实体类的主键类型
> 4.实体类使用数据库的方式生成，不需要手动创建
> 5.@ModelAttribute User user：是将pojo拼接的方式进行请求 @RequestMapping(value = "user",method = RequestMethod.POST)
> 6.@PathVariable int id：是直接传参的方式请求 @RequestMapping(value = "user/{id}",method = RequestMethod.GET)
> 