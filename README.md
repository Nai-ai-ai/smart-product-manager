# 智慧商品信息管理系统

## 项目简介

本项目是基于Spring Boot + MyBatis + Spring Security + Redis开发的智慧商品信息管理系统，是JavaEE编程课程设计的实践项目。

## 技术栈

- **后端框架**：Spring Boot 2.7.18
- **持久层框架**：MyBatis 2.3.1
- **安全框架**：Spring Security
- **缓存数据库**：Redis
- **模板引擎**：Thymeleaf
- **分页插件**：PageHelper 1.4.7
- **数据库**：MySQL 5.7+
- **连接池**：Druid 1.2.20
- **构建工具**：Maven

## 项目结构

```
smart-product-manager
├── src/main/java/com/guat/lzp/product
│   ├── ProductManagerApplication.java    # 启动类
│   ├── config/                           # 配置类
│   │   ├── RedisConfig.java              # Redis配置
│   │   └── SecurityConfig.java           # Spring Security配置
│   ├── controller/                       # 控制层
│   │   ├── PageController.java           # 页面跳转控制器
│   │   ├── CategoryController.java       # 分类管理控制器
│   │   └── ProductController.java        # 商品管理控制器
│   ├── entity/                           # 实体层
│   │   ├── User.java                     # 用户实体
│   │   ├── Category.java                 # 分类实体
│   │   ├── Product.java                  # 商品实体
│   │   ├── ProductVO.java                # 商品视图对象
│   │   └── PageResult.java               # 分页结果
│   ├── mapper/                           # 数据访问层
│   │   ├── UserMapper.java               # 用户Mapper
│   │   ├── CategoryMapper.java           # 分类Mapper
│   │   └── ProductMapper.java            # 商品Mapper
│   └── service/                          # 业务逻辑层
│       ├── UserService.java              # 用户服务接口
│       ├── CategoryService.java          # 分类服务接口
│       ├── ProductService.java           # 商品服务接口
│       └── impl/                         # 服务实现类
│           ├── UserServiceImpl.java
│           ├── CategoryServiceImpl.java
│           └── ProductServiceImpl.java
├── src/main/resources
│   ├── application.yml                   # 主配置文件
│   ├── application-dev.yml               # 开发环境配置
│   ├── application-prd.yml               # 生产环境配置
│   ├── mapper/                           # MyBatis XML映射文件
│   │   ├── CategoryMapper.xml
│   │   └── ProductMapper.xml
│   ├── sql/                              # SQL脚本
│   │   └── init.sql                      # 初始化SQL
│   ├── static/                           # 静态资源
│   │   ├── css/
│   │   │   ├── login.css
│   │   │   └── admin.css
│   │   └── js/
│   │       ├── admin.js
│   │       ├── welcome.js
│   │       ├── category.js
│   │       └── product.js
│   └── templates/                        # 模板页面
│       ├── login.html
│       └── admin/
│           ├── index.html
│           ├── welcome.html
│           ├── category.html
│           └── product.html
└── pom.xml                               # Maven配置文件
```

## 数据库配置

数据库配置已在application-dev.yml和application-prd.yml中配置：

- **数据库IP**：10.1.21.22
- **数据库名**：2024_lzp
- **用户名**：2024_lzp
- **密码**：bigdata22

### 初始化数据库

执行`src/main/resources/sql/init.sql`脚本创建表并初始化数据。

## 快速开始

### 环境要求

- JDK 1.8+
- Maven 3.6+
- MySQL 5.7+
- Redis 5.0+

### 运行步骤

1. 克隆或下载项目到本地
2. 创建数据库并执行初始化SQL脚本
3. 修改application-dev.yml中的数据库和Redis配置（如需要）
4. 使用IntelliJ IDEA打开项目
5. 等待Maven依赖下载完成
6. 运行`ProductManagerApplication.java`启动项目
7. 访问 http://localhost:8080/

### 默认账号

- **管理员账号**：admin / 123456
- **普通用户账号**：user / 123456

## 功能模块

### 1. 用户登录模块
- 自定义登录页面
- 用户名密码认证
- 角色权限控制（管理员/普通用户）
- 记住我功能
- 安全退出

### 2. 商品分类管理
- 分类列表查询
- 新增分类
- 编辑分类
- 删除分类
- Redis缓存优化

### 3. 商品管理
- 商品列表分页查询
- 多条件搜索（名称、分类、状态）
- 动态SQL查询
- 多表联查（关联分类名称）
- 新增商品
- 编辑商品
- 删除商品
- Redis缓存优化

## 开发阶段说明

### 阶段一：项目搭建与前端页面
- Spring Boot项目搭建
- 多环境配置（dev/prd）
- Thymeleaf模板引擎集成
- iframe伪单页布局
- 登录页、首页、分类管理页、商品管理页开发

### 阶段二：MyBatis整合
- MyBatis框架整合
- 实体类设计
- Mapper接口和XML映射文件
- 多表联查实现

### 阶段三：动态SQL与分页
- 动态SQL多条件查询
- PageHelper分页插件集成
- 分页查询实现

### 阶段四：Spring Security权限认证
- Spring Security框架整合
- 自定义登录页面
- 用户认证实现
- 角色权限控制

### 阶段五：Redis缓存优化
- Redis集成
- RedisTemplate配置
- Spring声明式缓存注解
- 缓存优化
