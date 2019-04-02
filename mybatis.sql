/*
 Navicat Premium Data Transfer

 Source Server         : ImmerTry
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : localhost:3306
 Source Schema         : mybatis

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 02/04/2019 18:30:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for country
-- ----------------------------
DROP TABLE IF EXISTS `country`;
CREATE TABLE `country`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '国家id',
  `countryname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '国家名称',
  `countrycode` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '国家代码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '国家表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of country
-- ----------------------------
INSERT INTO `country` VALUES (1, '中国', 'CN');
INSERT INTO `country` VALUES (2, '美国', 'US');
INSERT INTO `country` VALUES (3, '俄罗斯', 'RU');
INSERT INTO `country` VALUES (4, '英国', 'GB');
INSERT INTO `country` VALUES (5, '法国', 'FR');

-- ----------------------------
-- Table structure for sys_privilege
-- ----------------------------
DROP TABLE IF EXISTS `sys_privilege`;
CREATE TABLE `sys_privilege`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `privilege_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '权限名称',
  `privilege_url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '权限URL',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_privilege
-- ----------------------------
INSERT INTO `sys_privilege` VALUES (1, '用户管理', '/users');
INSERT INTO `sys_privilege` VALUES (2, '角色管理', '/roles');
INSERT INTO `sys_privilege` VALUES (3, '系统日志', '/logs');
INSERT INTO `sys_privilege` VALUES (4, '人员维护', '/persons');
INSERT INTO `sys_privilege` VALUES (5, '单位维护', '/companies');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色名',
  `enabled` int(11) DEFAULT NULL COMMENT '有效标志',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '管理员', 1, 0, '2016-04-01 17:02:14');
INSERT INTO `sys_role` VALUES (2, '普通用户', 1, 1, '2016-04-01 17:02:34');

-- ----------------------------
-- Table structure for sys_role_privilege
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_privilege`;
CREATE TABLE `sys_role_privilege`  (
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `privilege_id` bigint(20) DEFAULT NULL COMMENT '权限ID'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色权限关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_privilege
-- ----------------------------
INSERT INTO `sys_role_privilege` VALUES (1, 0);
INSERT INTO `sys_role_privilege` VALUES (1, 2);
INSERT INTO `sys_role_privilege` VALUES (2, 4);
INSERT INTO `sys_role_privilege` VALUES (2, 5);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户名',
  `user_password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '密码',
  `user_email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT 'test@mybatis.tk' COMMENT '邮箱',
  `user_info` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '简介',
  `head_img` blob COMMENT '头像',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1002 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '123456', 'admin@test.tk', '管理员', NULL, '2016-04-01 17:00:58');
INSERT INTO `sys_user` VALUES (1001, 'test', '123456', 'test@mybatis.tk', '测试用户', NULL, '2016-04-01 17:01:52');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (1, 2);
INSERT INTO `sys_user_role` VALUES (1001, 2);

-- ----------------------------
-- Procedure structure for delete_user_by_id
-- ----------------------------
DROP PROCEDURE IF EXISTS `delete_user_by_id`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_user_by_id`(IN userId BIGINT)
BEGIN
 DELETE FROM sys_user_role WHERE user_id = userId;
 DELETE FROM sys_user WHERE id = user_id;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for insert_user_and_roles
-- ----------------------------
DROP PROCEDURE IF EXISTS `insert_user_and_roles`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_user_and_roles`(
	OUT userId BIGINT,
	IN userName VARCHAR(50),
	IN userPassWord VARCHAR(50),
	IN userEmail VARCHAR(50),
	IN userInfo TEXT,
	IN headImg BLOB,
	OUT createTime DATETIME,
	IN roleIds VARCHAR(200)
)
BEGIN
#设置当前时间
SET createTime = NOW();
#插入数据
INSERT INTO sys_user (user_name,user_password,user_email,user_info,head_img,create_time)
VALUES(userName,userPassWord,userEmail,userInfo,heaedImg,createtime);
# 获取自增主键
SELECT LAST_INSERT_ID() INTO userId;
#保存用户和角色关系数据
SET roleIds = CONCAT(',',roleIds,',');
INSERT INTO sys_user_role(user_id,role_id)
SELECT userId,id FROM sys_role
WHERE INSTR(roleIds,CONCAT(',',id,',')) > 0;
ENd
;;
delimiter ;

-- ----------------------------
-- Procedure structure for select_user_by_id
-- ----------------------------
DROP PROCEDURE IF EXISTS `select_user_by_id`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `select_user_by_id`(
	IN userId BIGINT,
	OUT userName VARCHAR(50),
	OUT userPassWord VARCHAR(50),
	OUT userEmail VARCHAR(50),
	OUT userInfo TEXT,
	OUT headImg BLOB,
	OUT createTime DATETIME
)
BEGIN
# 根据用户 ID 查询其他数据
SELECT user_name,user_password,user_email,user_info, head_img,create_time
INTO userName,userPassWord,userEmail,userInfo,headImg,createTime
FROM sys_user
WHERE id = userId;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for select_user_page
-- ----------------------------
DROP PROCEDURE IF EXISTS `select_user_page`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `select_user_page`(
	IN userName VARCHAR(50),
	IN _offset BIGINT,
	IN _limit BIGINT,
	IN total BiGINT)
BEGIN
#查询总数
SELECT count(*) INTO total
FROM sys_user
WHERE user_name LIKE CONCAT('%',userName,'%');
#分页查询数据
SELECT *
FROM sys_user
WHERE user_name LIKE CONCAT('%',userName,'%')
LIMIT _offset, _limit;
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
