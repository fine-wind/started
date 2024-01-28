SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_mail_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_mail_log`;
CREATE TABLE `sys_mail_log`  (
  `id` bigint NOT NULL COMMENT 'id',
  `template_id` bigint NOT NULL COMMENT '邮件模板ID',
  `mail_from` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发送者',
  `mail_to` varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收件人',
  `mail_cc` varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '抄送者',
  `subject` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮件主题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '邮件正文',
  `status` tinyint UNSIGNED NULL DEFAULT NULL COMMENT '发送状态  0：失败  1：成功',
  `creator` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_date` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_create_date`(`create_date` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '邮件发送记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_mail_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
