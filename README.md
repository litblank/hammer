# 一、hammer
基于GUNS5.1版本，后台系统管理。

# 二、站在巨人的肩膀
- 修改了页面样式
- 优化了系统管理
- 整合并优化了代码生成器
- 整合了工作流
- 增加了Redis，RabbitMQ
- 配置了Docker自动化部署

# 三、界面
![页面首页](https://note.youdao.com/yws/api/personal/file/259D0A23A9144137BB3B7A2082A5FED8?method=download&shareKey=ec751df1b2d68b17d4ff8a3bf8774ba7)

![工作流](https://note.youdao.com/yws/api/personal/file/E40D679223944571BCA1E4386025A9D6?method=download&shareKey=29d239f4e4d38163644077a971b8237a)

![生成代码](https://note.youdao.com/yws/api/personal/file/D271EEFEAD5D474BA9533FFA59793C5D?method=download&shareKey=a30ca8d32ec029801482d90250014872)

# 四、如何使用
- 导入mysql数据库：/sql/exch.sql
- 在application-commontest.yml文件中。配置自己需要的redis或者MQ配置。运行ExchApplication.java。若MQ未配置，可能报错。不需要MQ注释掉就可以。

# 五、如何使用工作流
https://github.com/litblank/flowable-tomcat

# 六、如何使用Docker
https://juejin.im/post/5c8fc3266fb9a070f30af9cb#heading-3

# 七、如何联系我
wechat: cydcbg
