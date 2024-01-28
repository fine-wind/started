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

 Date: 31/01/2023 09:10:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_mail_template
-- ----------------------------
DROP TABLE IF EXISTS `sys_mail_template`;
CREATE TABLE `sys_mail_template`  (
  `id` bigint NOT NULL COMMENT 'id',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板名称',
  `subject` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮件主题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '邮件正文',
  `creator` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_date` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_date` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_create_date`(`create_date` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '邮件模板' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_mail_template
-- ----------------------------
INSERT INTO `sys_mail_template` VALUES (1, '验证码模板', '修改密码验证码', '<p>验证码：${code}，有效时间300秒，请勿向他人泄露。</p>', NULL, '2020-06-20 15:26:46', NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
