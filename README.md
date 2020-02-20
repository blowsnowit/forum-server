# forum-server
基于 springboot + mybatis-plus + redis + Elasticsearch

前端地址 https://github.com/blowsnowit/forum-vue

使用 Elasticsearch 全文索引 文章标题和文章内容
## 配置
数据库请自行导入 bl_forum.sql
然后配置 application.yml 里面的数据库地址
同时也需要配置redis

默认管理员用户 admin 123456


## 文件上传
支持本地上传和 oss上传（暂时未实现具体功能）
