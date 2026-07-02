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



## 快速开始

### 环境要求

- JDK 17+
- Maven 3.6+
- MySQL 5.7+
- Redis 5.0+

### 运行步骤

1. 克隆项目
   

2. 创建数据库，执行  下的 SQL 脚本初始化表结构和数据

3. 修改  中的数据库和 Redis 连接信息

4. 启动 Redis 服务

5. 使用 IDEA 打开项目，等待 Maven 依赖下载完成

6. 运行 

7. 访问 http://localhost:8080/

### 默认账号

| 角色 | 用户名 | 密码 |
|------|--------|------|
| 管理员 | admin | 123456 |
| 普通用户 | user | 123456 |

## 功能模块

### 用户登录
- 自定义登录页面
- Spring Security 认证 + 角色权限控制（admin / normal）
- 记住我（7 天有效期）
- 登录成功按角色跳转不同首页

### 商品管理
- 商品列表分页查询
- 多条件搜索（名称、分类）
- 多表联查（关联分类名称）
- 新增 / 编辑 / 删除商品
- Redis 缓存优化

### 分类管理
- 分类列表查询
- 新增 / 编辑 / 删除分类
- Redis 缓存优化

## 数据库设计

### 表结构

- **t_user** — 用户表（id, username, password, nickname, email, phone, avatar, active）
- **t_role** — 角色表（id, role）
- **t_user_role** — 用户角色关联表（user_id, role_id）
- **product** — 商品表（id, name, photo_url, price, descp, release_date, cat_id）
- **category** — 分类表（id, name）

## 配置说明

### 多环境配置

| 文件 | 用途 |
|------|------|
|  | 主配置，指定激活环境、Thymeleaf、PageHelper |
|  | 开发环境：数据库、Redis、Druid、MyBatis |
|  | 生产环境配置 |

### Spring Security 权限规则

| 路径 | 权限 |
|------|------|
| , , , 静态资源 | 公开访问 |
| ,  | 仅管理员（ROLE_admin） |
| ,  | 已认证用户 |
| ,  | 仅管理员 |
| 其余 | 已认证用户 |
