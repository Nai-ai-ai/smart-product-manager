# 智慧商品信息管理系统

基于 Spring Boot + MyBatis + Spring Security + Redis 的智慧商品信息管理系统，JavaEE 编程课程设计实践项目。

## 技术栈

| 类别 | 技术 | 版本 |
|------|------|------|
| 后端框架 | Spring Boot | 2.7.18 |
| 持久层 | MyBatis | 3.5.13 |
| 安全框架 | Spring Security | 5.7.11 |
| 缓存 | Redis + Spring Cache | — |
| 模板引擎 | Thymeleaf + thymeleaf-extras-springsecurity5 | 3.0.15 |
| 分页插件 | PageHelper | 1.4.7 |
| 数据库 | MySQL | 5.7+ |
| 连接池 | Druid | 1.2.20 |
| JDK | Zulu OpenJDK | 17 |
| 构建工具 | Maven | 3.6+ |

## 项目结构

```
smart-product-manager
├── src/main/java/com/guat/lzp/product
│   ├── ProductManagerApplication.java
│   ├── config/
│   │   ├── CacheConfig.java
│   │   ├── CustomLoginSuccessHandler.java
│   │   ├── CustomUserDetailsService.java
│   │   ├── JacksonConfig.java
│   │   ├── RedisConfig.java
│   │   ├── SecurityConfig.java
│   │   └── StartupUrlPrinter.java
│   ├── controller/
│   │   ├── CacheTestController.java
│   │   ├── CategoryController.java
│   │   ├── PageController.java
│   │   ├── ProductController.java
│   │   ├── TestController.java
│   │   └── UserController.java
│   ├── entity/
│   │   ├── ApiResult.java
│   │   ├── Category.java
│   │   ├── PageResult.java
│   │   ├── Product.java
│   │   ├── ProductVO.java
│   │   └── User.java
│   ├── mapper/
│   │   ├── CategoryMapper.java
│   │   ├── ProductMapper.java
│   │   └── UserMapper.java
│   └── service/
│       ├── CategoryService.java
│       ├── ProductService.java
│       ├── UserService.java
│       └── impl/
│           ├── CategoryServiceImpl.java
│           ├── ProductServiceImpl.java
│           └── UserServiceImpl.java
├── src/main/resources
│   ├── application.yml
│   ├── application-dev.yml
│   ├── application-prd.yml
│   ├── mapper/
│   │   ├── CategoryMapper.xml
│   │   ├── ProductMapper.xml
│   │   └── UserMapper.xml
│   ├── sql/
│   │   └── init_report.sql
│   ├── static/
│   │   ├── css/
│   │   ├── img/
│   │   ├── images/
│   │   └── js/
│   │       ├── admin.js
│   │       └── user.js
│   └── templates/
│       ├── login.html
│       ├── register.html
│       ├── admin/
│       │   ├── index.html
│       │   ├── welcome.html
│       │   ├── category.html
│       │   ├── product.html
│       │   └── user.html
│       └── user/
│           └── index.html
└── pom.xml
```

## 快速开始

### 环境要求

- JDK 17+
- Maven 3.6+
- MySQL 5.7+
- Redis 5.0+

### 运行步骤

1. 创建数据库，执行 `src/main/resources/sql/` 下的 SQL 脚本初始化表结构和数据
2. 修改 `application-dev.yml` 中的数据库和 Redis 连接信息
3. 启动 Redis 服务
4. 使用 IDEA 打开项目，等待 Maven 依赖下载完成
5. 运行 `ProductManagerApplication.java`
6. 访问 http://localhost:8080/

### 默认账号

| 角色 | 用户名 | 密码 |
|------|--------|------|
| 管理员 | admin | 123456 |
| 普通用户 | bob | 123456 |

## 功能模块

### 用户登录
- 自定义登录页面（液态玻璃风格）
- Spring Security 认证 + 角色权限控制（admin / normal）
- 记住我（7 天有效期）
- 登录成功按角色跳转不同首页

### 用户注册
- 注册页面（与登录页统一液态玻璃风格）
- 用户名重复检测
- 注册成功自动登录并跳转

### 用户管理（管理员）
- 用户列表展示（分页）
- 新增用户
- 编辑用户信息
- 删除用户
- 角色标签显示（管理员/普通用户）

### 商品管理
- 商品列表分页查询
- 多条件搜索（名称、分类）
- 多表联查（关联分类名称）
- 新增 / 编辑 / 删除商品
- Redis 缓存优化（@Cacheable / @CachePut / @CacheEvict）

### 分类管理
- 分类列表查询
- 新增 / 编辑 / 删除分类
- 删除前检查是否有关联商品
- Redis 缓存优化（allEntries 策略）

### 首页统计
- 管理员后台：商品总数、分类总数、用户总数
- 普通用户首页：商品总数、分类总数、商品均价

## 数据库设计

### 表结构

- **t_user** — 用户表（id, username, password, active）
- **t_role** — 角色表（id, role）
- **t_user_role** — 用户角色关联表（user_id, role_id）
- **product** — 商品表（id, name, photo_url, price, descp, release_date, cat_id）
- **category** — 分类表（id, name, descp）

## 配置说明

### 多环境配置

| 文件 | 用途 |
|------|------|
| `application.yml` | 主配置，指定激活环境、Thymeleaf、PageHelper |
| `application-dev.yml` | 开发环境：数据库、Redis、Druid、MyBatis |
| `application-prd.yml` | 生产环境配置 |

### Spring Security 权限规则

| 路径 | 权限 |
|------|------|
| `/`, `/login`, `/register`, 静态资源 | 公开访问 |
| `/admin/**` | 仅管理员（ROLE_admin） |
| `/bob/**` | 已认证用户 |
| `/api/category/**`, `/api/product/**` | 仅管理员 |
| 其余 | 已认证用户 |
