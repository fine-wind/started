/*
 Navicat Premium Data Transfer

 Source Server         : spark_3306
 Source Server Type    : MySQL
 Source Server Version : 80032 (8.0.32)
 Source Host           : spark:3306
 Source Schema         : started

 Target Server Type    : MySQL
 Target Server Version : 80032 (8.0.32)
 File Encoding         : 65001

 Date: 31/03/2023 11:30:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_group
-- ----------------------------
DROP TABLE IF EXISTS `sys_group`;
CREATE TABLE `sys_group`  (
  `id` bigint NOT NULL COMMENT 'id',
  `pid` bigint NULL DEFAULT NULL COMMENT '上级ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组名称',
  `s` int UNSIGNED NULL DEFAULT NULL COMMENT '开始',
  `e` int UNSIGNED NULL DEFAULT NULL COMMENT '结束',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `creator` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_date` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_date` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_pid`(`pid` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统-部门管理' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_group
-- ----------------------------
INSERT INTO `sys_group` VALUES (11111111111111111, 1469562801327542273, '零和公司', 0, 1, NULL, NULL, '2020-02-23 21:46:58', NULL, NULL, '0');
INSERT INTO `sys_group` VALUES (1469561453223710722, 11111111111111111, '产品部', 0, 1, NULL, NULL, '2021-12-11 14:55:28', 1, '2021-12-11 14:55:28', '0');
INSERT INTO `sys_group` VALUES (1469562218738716674, 1469561453223710722, '产品长沙分部', 1, 1, NULL, NULL, '2021-12-11 14:58:31', 1, '2021-12-11 14:58:31', '1');
INSERT INTO `sys_group` VALUES (1469562399349641218, 11111111111111111, '研发部', 0, 1, NULL, NULL, '2021-12-11 14:59:14', 1, '2021-12-11 14:59:14', '0');
INSERT INTO `sys_group` VALUES (1469562568753385473, 11111111111111111, '销售部', 0, 1, NULL, NULL, '2021-12-11 14:59:54', 1, '2021-12-11 14:59:54', '0');
INSERT INTO `sys_group` VALUES (1469562611027775489, 11111111111111111, '售后部', 0, 1, NULL, NULL, '2021-12-11 15:00:04', 1, '2021-12-11 15:00:04', '0');
INSERT INTO `sys_group` VALUES (1469562702904004610, 1469562399349641218, '研发组', 1, 1, NULL, NULL, '2021-12-11 15:00:26', 1, '2021-12-11 15:00:26', '1');
INSERT INTO `sys_group` VALUES (1469562801327542273, 0, '零零', 0, 1, NULL, NULL, '2021-12-11 15:00:50', 1, '2021-12-11 15:00:50', '0');
INSERT INTO `sys_group` VALUES (1469578527996125185, 0, '11', 0, 1, NULL, NULL, '2021-12-11 16:03:19', NULL, NULL, '1');
INSERT INTO `sys_group` VALUES (1469579994677125122, 1469562399349641218, '123', 0, 1, NULL, NULL, '2021-12-11 16:09:09', NULL, NULL, '0');
INSERT INTO `sys_group` VALUES (1469582281235505154, 0, '12', 0, 1, NULL, NULL, '2021-12-11 16:18:14', NULL, NULL, '1');
INSERT INTO `sys_group` VALUES (1469582521682370562, 1469562801327542273, '123', 0, 1, NULL, NULL, '2021-12-11 16:19:11', NULL, NULL, '0');

SET FOREIGN_KEY_CHECKS = 1;
