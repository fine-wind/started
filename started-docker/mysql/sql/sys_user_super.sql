SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_user_super
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_super`;
CREATE TABLE `sys_user_super`  (
  `id` bigint NOT NULL COMMENT 'id',
  `user_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '用户id',
  `status` tinyint NULL DEFAULT NULL COMMENT '状态  0：停用   1：正常',
  `creator` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_date` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_date` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `company_id` bigint NULL DEFAULT NULL COMMENT '公司id',
  `del_flag` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_create_date`(`create_date` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统用户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_super
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
