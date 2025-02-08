/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.91.201
 Source Server Type    : MySQL
 Source Server Version : 80031
 Source Host           : 192.168.91.201:3306
 Source Schema         : wysiwyg

 Target Server Type    : MySQL
 Target Server Version : 80031
 File Encoding         : 65001

 Date: 08/02/2025 14:24:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for wysiwyg_admin_menu
-- ----------------------------
DROP TABLE IF EXISTS `wysiwyg_admin_menu`;
CREATE TABLE `wysiwyg_admin_menu`  (
  `ID` int(0) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `PARENT_ID` int(0) NULL DEFAULT NULL COMMENT '父菜单ID',
  `IS_LINK` tinyint(0) NULL DEFAULT NULL COMMENT '是否是超链接',
  `CODE` varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '代码',
  `PATH` varchar(511) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'URL',
  `ICON` varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '图标',
  `NO` int(0) NULL DEFAULT NULL COMMENT '顺序',
  `TITLE` varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '菜单名称',
  `IS_DELETE` tinyint(0) NULL DEFAULT 0 COMMENT '是否删除：0-否，1-是',
  `CREATE_BY` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wysiwyg_admin_menu
-- ----------------------------
INSERT INTO `wysiwyg_admin_menu` VALUES (1, NULL, 0, NULL, '/system/', 'iconfont icon-xitongshezhi', 1, '系统功能', 0, NULL, NULL, '1', '2022-10-14 20:28:16');
INSERT INTO `wysiwyg_admin_menu` VALUES (2, 1, 0, '', '/system/user', 'iconfont icon-icon-', 1, '用户管理', 0, '', '2022-11-15 16:16:58', '1', '2022-10-15 13:17:57');
INSERT INTO `wysiwyg_admin_menu` VALUES (3, 1, NULL, NULL, '/system/role', 'ele-UserFilled', 2, '角色管理', 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu` VALUES (4, 1, NULL, NULL, '/system/menu', 'iconfont icon-caidan', 3, '菜单管理', 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu` VALUES (5, 1, NULL, NULL, '/system/permission', 'iconfont icon-quanxian', 4, '权限管理', 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu` VALUES (6, NULL, NULL, NULL, '/test/menu', 'iconfont icon-xuanzeqi', 2, '测试菜单', 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu` VALUES (7, 6, NULL, NULL, '/test/transfer', 'ele-Switch', 1, '转账前端', 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu` VALUES (8, NULL, NULL, NULL, '/home', 'ele-AddLocation', 0, '首页', 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu` VALUES (9, NULL, NULL, NULL, '/common', 'iconfont icon-diannao101', 4, '通用功能', 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu` VALUES (10, 9, NULL, NULL, '/common/query', 'iconfont icon-diannao', NULL, '通用查询', 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu` VALUES (11, NULL, 0, NULL, '/system/', 'iconfont icon-xitongshezhi', 1, '系统功能', 0, NULL, NULL, '1', '2022-10-14 20:28:16');

-- ----------------------------
-- Table structure for wysiwyg_admin_menu_permission
-- ----------------------------
DROP TABLE IF EXISTS `wysiwyg_admin_menu_permission`;
CREATE TABLE `wysiwyg_admin_menu_permission`  (
  `MENU_ID` int(0) NOT NULL COMMENT '菜单ID',
  `PERMISSION_ID` int(0) NOT NULL COMMENT '权限id',
  `IS_DELETE` tinyint(0) NULL DEFAULT NULL COMMENT '是否删除：0-否，1-是',
  `CREATE_BY` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '修改时间'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wysiwyg_admin_menu_permission
-- ----------------------------
INSERT INTO `wysiwyg_admin_menu_permission` VALUES (3, 15, 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu_permission` VALUES (3, 16, 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu_permission` VALUES (3, 17, 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu_permission` VALUES (3, 18, 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu_permission` VALUES (3, 19, 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu_permission` VALUES (3, 20, 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu_permission` VALUES (4, 3, 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu_permission` VALUES (4, 4, 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu_permission` VALUES (4, 5, 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu_permission` VALUES (4, 6, 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu_permission` VALUES (4, 7, 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu_permission` VALUES (4, 8, 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu_permission` VALUES (4, 9, 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu_permission` VALUES (10, 4, 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu_permission` VALUES (10, 10, 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu_permission` VALUES (10, 15, 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu_permission` VALUES (10, 22, 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu_permission` VALUES (10, 3, 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu_permission` VALUES (10, 8, 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu_permission` VALUES (10, 11, 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu_permission` VALUES (10, 16, 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu_permission` VALUES (10, 21, 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu_permission` VALUES (10, 27, 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu_permission` VALUES (6, 3, 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu_permission` VALUES (6, 4, 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu_permission` VALUES (6, 5, 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu_permission` VALUES (6, 6, 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu_permission` VALUES (6, 7, 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu_permission` VALUES (6, 8, 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu_permission` VALUES (6, 9, 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu_permission` VALUES (2, 21, 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu_permission` VALUES (2, 23, 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu_permission` VALUES (2, 24, 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu_permission` VALUES (2, 25, 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu_permission` VALUES (2, 26, 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu_permission` VALUES (2, 22, 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu_permission` VALUES (2, 21, 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu_permission` VALUES (2, 22, 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu_permission` VALUES (2, 23, 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu_permission` VALUES (2, 24, 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu_permission` VALUES (2, 25, 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu_permission` VALUES (2, 26, 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu_permission` VALUES (2, 21, 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu_permission` VALUES (2, 22, 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu_permission` VALUES (2, 23, 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu_permission` VALUES (2, 24, 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu_permission` VALUES (2, 25, 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_menu_permission` VALUES (2, 26, 0, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for wysiwyg_admin_permission
-- ----------------------------
DROP TABLE IF EXISTS `wysiwyg_admin_permission`;
CREATE TABLE `wysiwyg_admin_permission`  (
  `ID` int(0) NOT NULL AUTO_INCREMENT COMMENT '权限id',
  `ACTION` int(0) NULL DEFAULT NULL COMMENT '动作id',
  `PATH` varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '路径',
  `TITLE` varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '描述',
  `IS_DELETE` tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0-否，1-是',
  `CREATE_BY` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wysiwyg_admin_permission
-- ----------------------------
INSERT INTO `wysiwyg_admin_permission` VALUES (1, 1, '/wysiwyg-admin/system/menu/queryMenuPageInfo', '获取菜单信息', NULL, '1', NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_permission` VALUES (3, 1, '/wysiwyg-admin/system/menu/getCurrentUserMenu', '获取用户所有的菜单信息', 0, '1', '2023-02-02 19:56:51', '1', '2023-02-02 20:00:31');
INSERT INTO `wysiwyg_admin_permission` VALUES (4, 1, '/wysiwyg-admin/system/menu/queryMenuTreeList', '获取菜单树信息', 0, '1', '2023-02-02 20:01:20', NULL, NULL);
INSERT INTO `wysiwyg_admin_permission` VALUES (5, 1, '/wysiwyg-admin/system/menu/addMenu', '新增菜单', 0, '1', '2023-02-02 20:01:37', NULL, NULL);
INSERT INTO `wysiwyg_admin_permission` VALUES (6, 1, '/wysiwyg-admin/system/menu/editMenu', '修改菜单', 0, '1', '2023-02-02 20:01:53', NULL, NULL);
INSERT INTO `wysiwyg_admin_permission` VALUES (7, 1, '/wysiwyg-admin/system/menu/deleteMenu', '删除菜单', 0, '1', '2023-02-02 20:02:14', NULL, NULL);
INSERT INTO `wysiwyg_admin_permission` VALUES (8, 1, '/wysiwyg-admin/system/menu/getMenuByRole', '获取角色的菜单信息', 0, '1', '2023-02-02 20:02:36', '1', '2023-02-02 20:02:46');
INSERT INTO `wysiwyg_admin_permission` VALUES (9, 1, '/wysiwyg-admin/system/menu/addMenuPermission', '新增菜单权限关系', 0, '1', '2023-02-02 20:03:15', NULL, NULL);
INSERT INTO `wysiwyg_admin_permission` VALUES (10, 1, '/wysiwyg-admin/system/permission/queryPermissionTableData', '获取权限表格信息', 0, '1', '2023-02-02 20:04:01', NULL, NULL);
INSERT INTO `wysiwyg_admin_permission` VALUES (11, 1, '/wysiwyg-admin/system/permission/getHasPermissionByMenu', '获取菜单相关的权限信息', 0, '1', '2023-02-02 20:05:04', '1', '2023-02-05 11:05:31');
INSERT INTO `wysiwyg_admin_permission` VALUES (12, 1, '/wysiwyg-admin/system/permission/addPermission', '新增权限', 0, '1', '2023-02-02 20:05:22', NULL, NULL);
INSERT INTO `wysiwyg_admin_permission` VALUES (13, 1, '/wysiwyg-admin/system/permission/updatePermission', '修改权限', 0, '1', '2023-02-02 20:05:42', NULL, NULL);
INSERT INTO `wysiwyg_admin_permission` VALUES (14, 1, '/wysiwyg-admin/system/permission/deletePermission', '删除权限', 0, '1', '2023-02-02 20:06:04', NULL, NULL);
INSERT INTO `wysiwyg_admin_permission` VALUES (15, 1, '/wysiwyg-admin/system/role/queryRoleTableData', '获取角色信息', 0, '1', '2023-02-02 20:11:11', NULL, NULL);
INSERT INTO `wysiwyg_admin_permission` VALUES (16, 1, '/wysiwyg-admin/system/role/getRoleByUser', '获取用户相关角色', 0, '1', '2023-02-02 20:11:36', NULL, NULL);
INSERT INTO `wysiwyg_admin_permission` VALUES (17, 1, '/wysiwyg-admin/system/role/add', '新增角色', 0, '1', '2023-02-02 20:12:02', NULL, NULL);
INSERT INTO `wysiwyg_admin_permission` VALUES (18, 1, '/wysiwyg-admin/system/role/update', '修改角色', 0, '1', '2023-02-02 20:12:16', NULL, NULL);
INSERT INTO `wysiwyg_admin_permission` VALUES (19, 1, '/wysiwyg-admin/system/role/deleteRole', '删除角色', 0, '1', '2023-02-02 20:12:29', NULL, NULL);
INSERT INTO `wysiwyg_admin_permission` VALUES (20, 1, '/wysiwyg-admin/system/role/roleAddMenu', '添加角色菜单关系', 0, '1', '2023-02-02 20:12:49', NULL, NULL);
INSERT INTO `wysiwyg_admin_permission` VALUES (21, 1, '/wysiwyg-admin/system/user/getCurrentUser', '获取当前登录用户信息', 0, '1', '2023-02-02 20:13:23', '1', '2023-02-02 20:13:39');
INSERT INTO `wysiwyg_admin_permission` VALUES (22, 1, '/wysiwyg-admin/system/user/queryUserTableData', '获取用户表格信息', 0, '1', '2023-02-02 20:13:55', NULL, NULL);
INSERT INTO `wysiwyg_admin_permission` VALUES (23, 1, '/wysiwyg-admin/system/user/addUser', '新增用户', 0, '1', '2023-02-02 20:14:09', NULL, NULL);
INSERT INTO `wysiwyg_admin_permission` VALUES (24, 1, '/wysiwyg-admin/system/user/editUser', '修改用户', 0, '1', '2023-02-02 20:14:22', NULL, NULL);
INSERT INTO `wysiwyg_admin_permission` VALUES (25, 1, '/wysiwyg-admin/system/user/deleteUser', '删除用户', 0, '1', '2023-02-02 20:14:34', NULL, NULL);
INSERT INTO `wysiwyg_admin_permission` VALUES (26, 1, '/wysiwyg-admin/system/user/userAddRole', '添加用户角色关系', 0, '1', '2023-02-02 20:14:47', NULL, NULL);
INSERT INTO `wysiwyg_admin_permission` VALUES (27, 1, '/wysiwyg-admin/system/dictionary/getListByKey', '获取字典数据（通用下拉）', 0, '1', '2023-02-08 12:26:36', '1', '2023-02-09 09:01:01');

-- ----------------------------
-- Table structure for wysiwyg_admin_role
-- ----------------------------
DROP TABLE IF EXISTS `wysiwyg_admin_role`;
CREATE TABLE `wysiwyg_admin_role`  (
  `ID` int(0) NOT NULL AUTO_INCREMENT,
  `TYPE` int(0) NULL DEFAULT NULL COMMENT '类型',
  `TITLE` varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '描述',
  `IS_DELETE` tinyint(0) NULL DEFAULT NULL COMMENT '是否删除：0-否，1-是',
  `CREATE_BY` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wysiwyg_admin_role
-- ----------------------------
INSERT INTO `wysiwyg_admin_role` VALUES (1, -1, '系统开发岗', 0, '1', '2022-11-13 16:11:36', NULL, NULL);
INSERT INTO `wysiwyg_admin_role` VALUES (2, -1, '系统管理岗', 0, '1', '2022-11-13 16:12:10', NULL, NULL);
INSERT INTO `wysiwyg_admin_role` VALUES (3, -1, '系统架构岗', 0, '1', '2022-11-13 16:12:19', NULL, NULL);
INSERT INTO `wysiwyg_admin_role` VALUES (4, -1, '参数配置岗', 0, '1', '2022-11-13 16:12:26', NULL, NULL);
INSERT INTO `wysiwyg_admin_role` VALUES (5, -2, '查看角色', 0, '1', '2022-11-13 16:13:11', '1', '2023-02-07 14:39:39');
INSERT INTO `wysiwyg_admin_role` VALUES (6, -2, '售后服务经理', 0, '1', '2022-11-13 16:13:47', NULL, NULL);

-- ----------------------------
-- Table structure for wysiwyg_admin_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `wysiwyg_admin_role_menu`;
CREATE TABLE `wysiwyg_admin_role_menu`  (
  `ROLE_ID` int(0) NOT NULL COMMENT '角色id',
  `MENU_ID` int(0) NOT NULL COMMENT '菜单ID',
  `IS_DELETE` tinyint(0) NULL DEFAULT 0 COMMENT '是否删除：0-否，1-是',
  `CREATE_BY` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '修改时间'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wysiwyg_admin_role_menu
-- ----------------------------
INSERT INTO `wysiwyg_admin_role_menu` VALUES (1, 2, 1, '1', '2022-12-20 19:20:27', NULL, NULL);
INSERT INTO `wysiwyg_admin_role_menu` VALUES (1, 4, 1, '1', '2022-12-20 19:20:27', NULL, NULL);
INSERT INTO `wysiwyg_admin_role_menu` VALUES (1, 5, 1, '1', '2022-12-20 19:20:27', NULL, NULL);
INSERT INTO `wysiwyg_admin_role_menu` VALUES (1, 6, 1, '1', '2022-12-20 19:20:27', NULL, NULL);
INSERT INTO `wysiwyg_admin_role_menu` VALUES (1, 7, 1, '1', '2022-12-20 19:20:27', NULL, NULL);
INSERT INTO `wysiwyg_admin_role_menu` VALUES (1, 1, 1, '1', '2022-12-20 19:20:36', NULL, NULL);
INSERT INTO `wysiwyg_admin_role_menu` VALUES (1, 3, 1, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_role_menu` VALUES (1, 1, 1, '1', '2023-01-30 19:23:02', NULL, NULL);
INSERT INTO `wysiwyg_admin_role_menu` VALUES (1, 2, 1, '1', '2023-01-30 19:23:02', NULL, NULL);
INSERT INTO `wysiwyg_admin_role_menu` VALUES (1, 3, 1, '1', '2023-01-30 19:23:02', NULL, NULL);
INSERT INTO `wysiwyg_admin_role_menu` VALUES (1, 4, 1, '1', '2023-01-30 19:23:02', NULL, NULL);
INSERT INTO `wysiwyg_admin_role_menu` VALUES (1, 5, 1, '1', '2023-01-30 19:23:02', NULL, NULL);
INSERT INTO `wysiwyg_admin_role_menu` VALUES (2, 2, 1, '1', '2023-01-30 19:23:10', NULL, NULL);
INSERT INTO `wysiwyg_admin_role_menu` VALUES (2, 6, 1, '1', '2023-01-30 19:23:10', NULL, NULL);
INSERT INTO `wysiwyg_admin_role_menu` VALUES (2, 7, 1, '1', '2023-01-30 19:23:10', NULL, NULL);
INSERT INTO `wysiwyg_admin_role_menu` VALUES (1, 1, 1, '1', '2023-02-05 11:06:05', NULL, NULL);
INSERT INTO `wysiwyg_admin_role_menu` VALUES (1, 2, 1, '1', '2023-02-05 11:06:05', NULL, NULL);
INSERT INTO `wysiwyg_admin_role_menu` VALUES (1, 3, 1, '1', '2023-02-05 11:06:05', NULL, NULL);
INSERT INTO `wysiwyg_admin_role_menu` VALUES (1, 4, 1, '1', '2023-02-05 11:06:05', NULL, NULL);
INSERT INTO `wysiwyg_admin_role_menu` VALUES (1, 5, 1, '1', '2023-02-05 11:06:05', NULL, NULL);
INSERT INTO `wysiwyg_admin_role_menu` VALUES (1, 6, 1, '1', '2023-02-05 11:06:05', NULL, NULL);
INSERT INTO `wysiwyg_admin_role_menu` VALUES (1, 7, 1, '1', '2023-02-05 11:06:05', NULL, NULL);
INSERT INTO `wysiwyg_admin_role_menu` VALUES (1, 8, 1, '1', '2023-02-05 11:06:05', NULL, NULL);
INSERT INTO `wysiwyg_admin_role_menu` VALUES (6, 9, 1, '1', '2023-02-08 12:28:06', NULL, NULL);
INSERT INTO `wysiwyg_admin_role_menu` VALUES (6, 10, 1, '1', '2023-02-08 12:28:06', NULL, NULL);
INSERT INTO `wysiwyg_admin_role_menu` VALUES (5, 9, 1, '1', '2023-02-08 12:31:58', NULL, NULL);
INSERT INTO `wysiwyg_admin_role_menu` VALUES (5, 10, 1, '1', '2023-02-08 12:31:58', NULL, NULL);
INSERT INTO `wysiwyg_admin_role_menu` VALUES (2, 2, 1, '1685964442237423616', '2023-07-31 18:45:51', NULL, NULL);
INSERT INTO `wysiwyg_admin_role_menu` VALUES (2, 6, 1, '1685964442237423616', '2023-07-31 18:45:51', NULL, NULL);
INSERT INTO `wysiwyg_admin_role_menu` VALUES (2, 7, 1, '1685964442237423616', '2023-07-31 18:45:51', NULL, NULL);
INSERT INTO `wysiwyg_admin_role_menu` VALUES (2, 8, 1, '1685964442237423616', '2023-07-31 18:45:51', NULL, NULL);
INSERT INTO `wysiwyg_admin_role_menu` VALUES (1, 1, 0, '1685964442237423616', '2023-07-31 18:46:03', NULL, NULL);
INSERT INTO `wysiwyg_admin_role_menu` VALUES (1, 2, 0, '1685964442237423616', '2023-07-31 18:46:03', NULL, NULL);
INSERT INTO `wysiwyg_admin_role_menu` VALUES (1, 3, 0, '1685964442237423616', '2023-07-31 18:46:03', NULL, NULL);
INSERT INTO `wysiwyg_admin_role_menu` VALUES (1, 4, 0, '1685964442237423616', '2023-07-31 18:46:03', NULL, NULL);
INSERT INTO `wysiwyg_admin_role_menu` VALUES (1, 5, 0, '1685964442237423616', '2023-07-31 18:46:03', NULL, NULL);
INSERT INTO `wysiwyg_admin_role_menu` VALUES (1, 6, 0, '1685964442237423616', '2023-07-31 18:46:03', NULL, NULL);
INSERT INTO `wysiwyg_admin_role_menu` VALUES (1, 7, 0, '1685964442237423616', '2023-07-31 18:46:03', NULL, NULL);
INSERT INTO `wysiwyg_admin_role_menu` VALUES (1, 8, 0, '1685964442237423616', '2023-07-31 18:46:03', NULL, NULL);
INSERT INTO `wysiwyg_admin_role_menu` VALUES (1, 9, 0, '1685964442237423616', '2023-07-31 18:46:03', NULL, NULL);
INSERT INTO `wysiwyg_admin_role_menu` VALUES (1, 10, 0, '1685964442237423616', '2023-07-31 18:46:03', NULL, NULL);
INSERT INTO `wysiwyg_admin_role_menu` VALUES (5, 6, 0, '1', '2023-07-31 18:48:39', NULL, NULL);
INSERT INTO `wysiwyg_admin_role_menu` VALUES (5, 7, 0, '1', '2023-07-31 18:48:39', NULL, NULL);
INSERT INTO `wysiwyg_admin_role_menu` VALUES (2, 1, 0, '1', '2024-04-23 20:02:22', NULL, NULL);
INSERT INTO `wysiwyg_admin_role_menu` VALUES (2, 2, 0, '1', '2024-04-23 20:02:22', NULL, NULL);
INSERT INTO `wysiwyg_admin_role_menu` VALUES (2, 3, 0, '1', '2024-04-23 20:02:22', NULL, NULL);
INSERT INTO `wysiwyg_admin_role_menu` VALUES (2, 4, 0, '1', '2024-04-23 20:02:22', NULL, NULL);
INSERT INTO `wysiwyg_admin_role_menu` VALUES (2, 5, 0, '1', '2024-04-23 20:02:22', NULL, NULL);
INSERT INTO `wysiwyg_admin_role_menu` VALUES (2, 6, 0, '1', '2024-04-23 20:02:22', NULL, NULL);
INSERT INTO `wysiwyg_admin_role_menu` VALUES (2, 7, 0, '1', '2024-04-23 20:02:22', NULL, NULL);
INSERT INTO `wysiwyg_admin_role_menu` VALUES (2, 8, 0, '1', '2024-04-23 20:02:22', NULL, NULL);

-- ----------------------------
-- Table structure for wysiwyg_admin_user
-- ----------------------------
DROP TABLE IF EXISTS `wysiwyg_admin_user`;
CREATE TABLE `wysiwyg_admin_user`  (
  `ID` varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户id',
  `STATUS` int(0) NULL DEFAULT NULL COMMENT '状态：0-未激活，1-正常，2-冻结，3-注销',
  `IDENTITY_NUMBER` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '身份证',
  `USERNAME` varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '姓名',
  `TELEPHONE_NUMBER` varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '座机',
  `MOBILE` varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '手机号码',
  `MAIL` varchar(511) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '邮箱',
  `JOB` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '职位',
  `MANAGER` varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '上级领导(预留)',
  `EMERGENCY` varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '紧急联系人',
  `WORKPLACE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '工作地点',
  `GENDER` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '性别',
  `BIRTH` date NULL DEFAULT NULL COMMENT '生日',
  `IS_DELETE` tinyint(0) NOT NULL DEFAULT 0 COMMENT '是否删除：0-否，1-是',
  `SELF_DESCRIPTION` varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '自我描述',
  `PASSWORD` varchar(511) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '密码(加密串)',
  `EXPIRATION_TIME` date NULL DEFAULT NULL COMMENT '密码过期时间',
  `CREATE_BY` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wysiwyg_admin_user
-- ----------------------------
INSERT INTO `wysiwyg_admin_user` VALUES ('1', 1, '142725200004060014', '贾王聪', NULL, '13546596594', '1351786511@qq.com', NULL, NULL, NULL, NULL, '1', '2022-10-25', 0, '一个很帅的男人111', '$2a$10$Rit8LrMtHDsZxV.jir.nyeoX0.T9d16tskB9IWoo2bHKjyBRpNQjy', NULL, NULL, NULL, '1', '2022-10-15 13:11:39');

-- ----------------------------
-- Table structure for wysiwyg_admin_user_role
-- ----------------------------
DROP TABLE IF EXISTS `wysiwyg_admin_user_role`;
CREATE TABLE `wysiwyg_admin_user_role`  (
  `ROLE_ID` int(0) NOT NULL COMMENT '角色id',
  `USER_ID` int(0) NOT NULL COMMENT '用户id',
  `IS_DELETE` tinyint(0) NOT NULL DEFAULT 0 COMMENT '是否删除：0-否，1-是',
  `CREATE_BY` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '修改时间'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wysiwyg_admin_user_role
-- ----------------------------
INSERT INTO `wysiwyg_admin_user_role` VALUES (1, 1, 0, NULL, NULL, NULL, NULL);
INSERT INTO `wysiwyg_admin_user_role` VALUES (2, 1, 0, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for wysiwyg_dictionary_code
-- ----------------------------
DROP TABLE IF EXISTS `wysiwyg_dictionary_code`;
CREATE TABLE `wysiwyg_dictionary_code`  (
  `ID` int(0) NOT NULL,
  `CODE_GROUP_ID` int(0) NOT NULL,
  `CODE_VAL` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `CODE_NOTE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `CODE_SORT` int(0) NULL DEFAULT NULL,
  `CREATE_BY` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `IS_DELETE` tinyint(0) NULL DEFAULT 0 COMMENT '删除状态',
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `CODE_GROUP_ID`(`CODE_GROUP_ID`) USING BTREE,
  CONSTRAINT `CODE_GROUP_ID` FOREIGN KEY (`CODE_GROUP_ID`) REFERENCES `wysiwyg_dictionary_group` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wysiwyg_dictionary_code
-- ----------------------------
INSERT INTO `wysiwyg_dictionary_code` VALUES (1, 1, '0', '隐藏', 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO `wysiwyg_dictionary_code` VALUES (2, 1, '1', '显示', 2, NULL, NULL, NULL, NULL, 0);
INSERT INTO `wysiwyg_dictionary_code` VALUES (3, 2, '-1', '系统角色', 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO `wysiwyg_dictionary_code` VALUES (4, 2, '-2', '客户角色', 2, NULL, NULL, NULL, NULL, 0);
INSERT INTO `wysiwyg_dictionary_code` VALUES (5, 3, '0', 'GET', 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO `wysiwyg_dictionary_code` VALUES (6, 3, '1', 'POST', 2, NULL, NULL, NULL, NULL, 0);
INSERT INTO `wysiwyg_dictionary_code` VALUES (7, 3, '2', 'PUT', 3, NULL, NULL, NULL, NULL, 0);
INSERT INTO `wysiwyg_dictionary_code` VALUES (8, 4, '0', '未删除', 0, NULL, NULL, NULL, NULL, 0);
INSERT INTO `wysiwyg_dictionary_code` VALUES (9, 4, '1', '删除', 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO `wysiwyg_dictionary_code` VALUES (10, 4, '2', '测试1', 2, NULL, NULL, NULL, NULL, 0);
INSERT INTO `wysiwyg_dictionary_code` VALUES (11, 4, '3', '测试2', 2, NULL, NULL, NULL, NULL, 0);
INSERT INTO `wysiwyg_dictionary_code` VALUES (12, 5, '1', '生效', 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO `wysiwyg_dictionary_code` VALUES (13, 5, '2', '失效', 2, NULL, NULL, NULL, NULL, 0);
INSERT INTO `wysiwyg_dictionary_code` VALUES (14, 6, '0', '未激活', 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO `wysiwyg_dictionary_code` VALUES (15, 6, '1', '正常', 2, NULL, NULL, NULL, NULL, 0);
INSERT INTO `wysiwyg_dictionary_code` VALUES (16, 6, '2', '冻结', 3, NULL, NULL, NULL, NULL, 0);
INSERT INTO `wysiwyg_dictionary_code` VALUES (17, 6, '3', '注销', 4, NULL, NULL, NULL, NULL, 0);

-- ----------------------------
-- Table structure for wysiwyg_dictionary_group
-- ----------------------------
DROP TABLE IF EXISTS `wysiwyg_dictionary_group`;
CREATE TABLE `wysiwyg_dictionary_group`  (
  `ID` int(0) NOT NULL AUTO_INCREMENT,
  `GROUP_KEY` varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `GROUP_NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `CREATE_BY` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `IS_DELETE` tinyint(0) NULL DEFAULT 0 COMMENT '删除状态',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wysiwyg_dictionary_group
-- ----------------------------
INSERT INTO `wysiwyg_dictionary_group` VALUES (1, 'MENU_TYPE', '菜单类型', NULL, NULL, NULL, NULL, 0);
INSERT INTO `wysiwyg_dictionary_group` VALUES (2, 'ROLE_TYPE', '角色类型', NULL, NULL, NULL, NULL, 0);
INSERT INTO `wysiwyg_dictionary_group` VALUES (3, 'ACTION_TYPE', '请求类型', NULL, NULL, NULL, NULL, 0);
INSERT INTO `wysiwyg_dictionary_group` VALUES (4, 'IS_DELETE', '删除类型', NULL, NULL, NULL, NULL, 0);
INSERT INTO `wysiwyg_dictionary_group` VALUES (5, 'STATUS_TYPE', '状态类型', NULL, NULL, NULL, NULL, 0);
INSERT INTO `wysiwyg_dictionary_group` VALUES (6, 'USER_STATUS', '用户状态', NULL, NULL, NULL, NULL, 0);

SET FOREIGN_KEY_CHECKS = 1;
