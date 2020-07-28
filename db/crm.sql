-- --------------------------------------------------------
-- 主机:                           106.55.35.69
-- 服务器版本:                        5.7.26 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Linux
-- HeidiSQL 版本:                  9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- 导出 bibe_crm 的数据库结构
DROP DATABASE IF EXISTS `bibe_crm`;
CREATE DATABASE IF NOT EXISTS `bibe_crm` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `bibe_crm`;

-- 导出  表 bibe_crm.area 结构
DROP TABLE IF EXISTS `area`;
CREATE TABLE IF NOT EXISTS `area` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '地区名称',
  `short_name` varchar(10) DEFAULT NULL COMMENT '地区缩写',
  `code` varchar(20) DEFAULT NULL COMMENT '行政地区编号',
  `parent_id` int(11) DEFAULT NULL COMMENT '地区父id',
  `level` int(11) DEFAULT NULL COMMENT '地区级别 1-省、自治区、直辖市 2-地级市、地区、自治州、盟 3-市辖区、县级市、县',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='地区表';

-- 正在导出表  bibe_crm.area 的数据：~0 rows (大约)
DELETE FROM `area`;
/*!40000 ALTER TABLE `area` DISABLE KEYS */;
/*!40000 ALTER TABLE `area` ENABLE KEYS */;

-- 导出  表 bibe_crm.customer 结构
DROP TABLE IF EXISTS `customer`;
CREATE TABLE IF NOT EXISTS `customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `password` varchar(80) DEFAULT NULL COMMENT '密码',
  `name` varchar(30) DEFAULT NULL COMMENT '客户名称',
  `industry` varchar(40) DEFAULT NULL COMMENT '客户行业',
  `user_id` int(11) DEFAULT NULL COMMENT '负责人id  0为公客',
  `area_id` int(11) DEFAULT NULL COMMENT '城市id',
  `public_customer_id` int(11) DEFAULT NULL COMMENT '公客分组id',
  `intention` int(11) DEFAULT NULL COMMENT '意向度:0:成交客户,1:A+,2:A-,3:B+,4:B-,5:C+,6:D+,7:E',
  `type` int(11) DEFAULT NULL COMMENT '客户类别:0代理商 1直客 2采购方',
  `address` varchar(100) DEFAULT NULL COMMENT '客户地址',
  `remarks` varchar(100) DEFAULT NULL COMMENT '客户介绍',
  `status` int(11) DEFAULT '0' COMMENT '0:启动 1:禁用',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户表';

-- 正在导出表  bibe_crm.customer 的数据：~0 rows (大约)
DELETE FROM `customer`;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;

-- 导出  表 bibe_crm.department 结构
DROP TABLE IF EXISTS `department`;
CREATE TABLE IF NOT EXISTS `department` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) DEFAULT NULL COMMENT '部门名称',
  `parent_id` int(11) DEFAULT NULL COMMENT '父级',
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门';

-- 正在导出表  bibe_crm.department 的数据：~0 rows (大约)
DELETE FROM `department`;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
/*!40000 ALTER TABLE `department` ENABLE KEYS */;

-- 导出  表 bibe_crm.permission 结构
DROP TABLE IF EXISTS `permission`;
CREATE TABLE IF NOT EXISTS `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `path` varchar(50) DEFAULT NULL COMMENT '路径',
  `remark` varchar(250) DEFAULT NULL COMMENT '描述',
  `status` int(11) DEFAULT NULL COMMENT '启用：0 禁用 1',
  `level` int(11) DEFAULT NULL COMMENT '层级',
  `type` int(11) DEFAULT NULL COMMENT '0菜单,1按钮',
  `parent_id` int(11) DEFAULT NULL COMMENT '父级权限 ',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

-- 正在导出表  bibe_crm.permission 的数据：~2 rows (大约)
DELETE FROM `permission`;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
INSERT INTO `permission` (`id`, `name`, `path`, `remark`, `status`, `level`, `type`, `parent_id`, `create_time`, `update_time`) VALUES
	(1, 'test', '/test', 'zxczxc', 0, 0, 0, 0, '2020-07-18 05:54:15', '2020-07-18 05:54:15'),
	(3, 'test2', '/test2', 'zxczxc', 0, 0, 0, 0, '2020-07-18 05:54:15', '2020-07-18 05:54:15');
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;

-- 导出  表 bibe_crm.roles 结构
DROP TABLE IF EXISTS `roles`;
CREATE TABLE IF NOT EXISTS `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `name` varchar(30) DEFAULT NULL COMMENT '角色名称',
  `code` varchar(20) DEFAULT NULL COMMENT '角色code',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='职务角色';

-- 正在导出表  bibe_crm.roles 的数据：~0 rows (大约)
DELETE FROM `roles`;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` (`id`, `name`, `code`, `create_time`, `update_time`) VALUES
	(1, '管理员', 'admin', '2020-07-18 05:55:04', '2020-07-18 05:55:04');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;

-- 导出  表 bibe_crm.roles_permission_relation 结构
DROP TABLE IF EXISTS `roles_permission_relation`;
CREATE TABLE IF NOT EXISTS `roles_permission_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `permission_id` int(11) DEFAULT NULL COMMENT '权限id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='角色与权限关系表';

-- 正在导出表  bibe_crm.roles_permission_relation 的数据：~1 rows (大约)
DELETE FROM `roles_permission_relation`;
/*!40000 ALTER TABLE `roles_permission_relation` DISABLE KEYS */;
INSERT INTO `roles_permission_relation` (`id`, `role_id`, `permission_id`, `create_time`, `update_time`) VALUES
	(1, 1, 1, '2020-07-18 05:56:13', '2020-07-18 05:56:13');
/*!40000 ALTER TABLE `roles_permission_relation` ENABLE KEYS */;

-- 导出  表 bibe_crm.users 结构
DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phone` varchar(11) DEFAULT NULL COMMENT '手机号（账号）',
  `password` varchar(80) DEFAULT NULL COMMENT '密码',
  `name` varchar(30) DEFAULT NULL COMMENT '姓名',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `dept_id` int(11) DEFAULT NULL COMMENT '部门id',
  `ip_addr` varchar(30) DEFAULT NULL COMMENT '登录ip地址',
  `status` int(11) DEFAULT '0' COMMENT '0:启动 1:禁用',
  `login_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登录时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='用户';

-- 正在导出表  bibe_crm.users 的数据：~1 rows (大约)
DELETE FROM `users`;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`id`, `phone`, `password`, `name`, `role_id`, `dept_id`, `ip_addr`, `status`, `login_time`, `create_time`, `update_time`) VALUES
	(1, 'root', '533466f28a9a5d93fd4dfca96cf2b863', NULL, 1, NULL, NULL, 0, '2020-07-18 06:23:18', '2020-07-18 06:23:18', '2020-07-18 06:23:18');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
