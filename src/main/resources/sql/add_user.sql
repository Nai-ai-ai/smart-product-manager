-- 添加普通用户 user（密码：123456）
-- 执行前请确保数据库 2024lzp 中还没有 username='user' 的记录

INSERT INTO `t_user` (`username`, `password`, `nickname`, `email`, `phone`, `active`) 
VALUES ('user', '$2a$10$tgmoDtjYB/IP.0QoTU2hNOA6TuYUidtVWe4YGbYfGel3waJ2W2Zoi', '普通用户', 'user@guat.edu.cn', '13700137000', 1);

-- 获取刚插入的用户ID，关联 ROLE_normal 角色（假设 ROLE_normal 的 id=2）
INSERT INTO `t_user_role` (`user_id`, `role_id`) 
SELECT id, 2 FROM `t_user` WHERE `username` = 'user';
