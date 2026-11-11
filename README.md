# ecommerce-mybatis（电商系统商品管理模块）

基于 Spring Boot 2.7 + MyBatis 的商品信息持久化与动态条件查询实验工程。

## 一、技术栈

- JDK 11
- Spring Boot 2.7.18
- MyBatis Starter 2.3.2
- MySQL 8.x
- Lombok

## 二、目录结构

```
ecommerce-mybatis/
├── pom.xml
├── sql/
│   └── init.sql                         # 建库、建表、测试数据脚本
└── src/
    ├── main/
    │   ├── java/com/ecommerce/
    │   │   ├── EcommerceMybatisApplication.java   # 启动类（@MapperScan）
    │   │   ├── entity/Product.java                 # 商品实体类
    │   │   ├── mapper/ProductMapper.java           # Mapper 接口
    │   │   └── controller/ProductController.java   # 测试接口
    │   └── resources/
    │       ├── application.yml                     # 数据源与 MyBatis 配置
    │       └── mapper/ProductMapper.xml           # XML 映射文件
    └── test/java/com/ecommerce/ProductMapperTest.java  # 单元测试
```

## 三、运行步骤

1. 启动 MySQL，执行 `sql/init.sql` 建库并写入测试数据；
2. 修改 `application.yml` 中的 `username`、`password` 为本机数据库账号；
3. 运行 `EcommerceMybatisApplication` 启动工程（默认端口 8080）；
4. 调用下列接口或运行 `ProductMapperTest` 完成测试。

## 四、接口一览

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| POST | `/products` | 新增商品，返回回填主键 |
| GET | `/products/{id}` | 按主键查询 |
| GET | `/products/list` | 查询全部上架商品 |
| PUT | `/products` | 修改商品 |
| DELETE | `/products/{id}` | 按主键删除 |
| GET | `/products/search?keyword=&categoryId=&minPrice=&maxPrice=` | 动态条件查询 |
| POST | `/products/batch` | 批量插入 |
| DELETE | `/products/batch?ids=1,2` | 批量删除 |
| GET | `/products/order?orderType=price_asc` | 动态排序（price_asc / price_desc / 默认时间倒序） |

## 五、动态 SQL 要点

- `<where>` + `<if>`：各可选条件任意组合，自动去除多余 AND；固定 `status = 1`。
- `<foreach>`：批量插入多行 VALUES、批量删除 IN 列表。
- `<choose>/<when>/<otherwise>`：按价格升序 / 价格降序 / 创建时间倒序切换排序。
- 全部 SQL 参数使用 `#{} ` 预编译占位符，防止 SQL 注入。
