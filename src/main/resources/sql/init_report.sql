-- =====================================================
-- 智慧商品信息管理系统 数据库建表脚本（完整版）
-- 数据库: 2024lzp
-- 作者: 赖增鹏
-- 日期: 2026-07-01
-- =====================================================

-- 删除旧表（如果存在）
DROP TABLE IF EXISTS `t_user_role`;
DROP TABLE IF EXISTS `t_role`;
DROP TABLE IF EXISTS `t_user`;
DROP TABLE IF EXISTS `product`;
DROP TABLE IF EXISTS `category`;

-- =====================================================
-- 1. 创建用户表 (t_user)
-- =====================================================
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(200) NOT NULL COMMENT '登录账号',
  `password` varchar(200) NOT NULL COMMENT '登录密码（BCrypt加密）',
  `active` int(1) DEFAULT 1 COMMENT '1可用，0不可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- =====================================================
-- 2. 创建角色表 (t_role)
-- =====================================================
CREATE TABLE `t_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role` varchar(200) COMMENT '角色名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- =====================================================
-- 3. 创建用户角色关联表 (t_user_role)
-- =====================================================
CREATE TABLE `t_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` int(11) REFERENCES `t_user`(`id`),
  `role_id` int(11) REFERENCES `t_role`(`id`),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

-- =====================================================
-- 4. 创建商品分类表 (category)
-- =====================================================
CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `name` varchar(100) NOT NULL COMMENT '分类名称',
  `descp` varchar(500) COMMENT '分类描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品分类表';

-- =====================================================
-- 5. 创建商品详情表 (product)
-- =====================================================
CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `name` varchar(100) NOT NULL COMMENT '商品名称',
  `photo_url` varchar(500) COMMENT '商品图片',
  `price` double COMMENT '商品价格',
  `descp` varchar(500) COMMENT '商品描述',
  `release_date` date COMMENT '发布日期',
  `cat_id` int(11) REFERENCES `category`(`id`),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品详情表';

-- =====================================================
-- 插入系统基础测试数据 (Demo Data)
-- 密码使用BCrypt加密：admin/123456, bob/123456
-- =====================================================

-- 插入用户数据（密码为 123456 的 BCrypt 哈希值）
INSERT INTO `t_user` (`username`, `password`, `active`) VALUES 
('admin', '$2a$10$tgmoDtjYB/IP.0QoTU2hNOA6TuYUidtVWe4YGbYfGel3waJ2W2Zoi', 1), 
('bob', '$2a$10$tgmoDtjYB/IP.0QoTU2hNOA6TuYUidtVWe4YGbYfGel3waJ2W2Zoi', 1);

-- 插入角色数据
INSERT INTO `t_role` (`role`) VALUES 
('ROLE_admin'), 
('ROLE_normal');

-- 插入用户角色关联数据
INSERT INTO `t_user_role` (`user_id`, `role_id`) VALUES 
(1, 1),  -- admin -> ROLE_admin
(2, 2);  -- bob -> ROLE_normal

-- 插入商品分类数据
INSERT INTO `category` (`name`, `descp`) VALUES
('数码电子', '手机、电脑等3C数码产品'),
('图书教材', '各类学习与课外阅读书籍'),
('生活居家', '日常居家必备好物');

-- 插入商品数据
INSERT INTO `product` (`name`, `photo_url`, `price`, `descp`, `release_date`, `cat_id`) VALUES
('高性能轻薄本', '/img/p1.jpg', 5999.0, '适合办公和学习的轻薄笔记本，续航超长', '2024-02-15', 1),
('Spring Boot实战', '/img/p2.jpg', 69.0, '企业级开发权威指南，快速上手Web应用', '2024-04-20', 2),
('MyBatis底层原理', '/img/p3.jpg', 59.0, '深入理解持久层框架，掌握动态SQL技术', '2024-05-01', 2),
('纯棉亲肤毛巾', '/img/p4.jpg', 29.9, '100%纯棉，柔软亲肤，不掉毛', '2024-03-10', 3),
('无线降噪耳机', '/img/p5.jpg', 499.0, '深度降噪，沉浸式音质体验', '2024-06-01', 1);

-- =====================================================
-- 数据初始化完成
-- 默认账号：admin / 123456 （管理员）
-- 默认账号：bob / 123456 （普通用户）
-- =====================================================
