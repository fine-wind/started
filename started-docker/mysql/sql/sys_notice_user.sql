SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_notice_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice_user`;
CREATE TABLE `sys_notice_user`  (
  `receiver_id` bigint NOT NULL COMMENT '接收者ID',
  `notice_id` bigint NOT NULL COMMENT '通知ID',
  `read_status` tinyint UNSIGNED NULL DEFAULT NULL COMMENT '阅读状态  0：未读  1：已读',
  `read_date` datetime NULL DEFAULT NULL COMMENT '阅读时间',
  `del_flag` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`receiver_id`, `notice_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '我的通知' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_notice_user
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
