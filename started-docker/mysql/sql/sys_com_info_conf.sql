SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_com_info_conf
-- ----------------------------
DROP TABLE IF EXISTS `sys_com_info_conf`;
CREATE TABLE `sys_com_info_conf`  (
  `id` bigint NOT NULL,
  `tital` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路径',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `creator` bigint NULL DEFAULT NULL,
  `create_date` datetime NULL DEFAULT NULL,
  `updater` bigint NULL DEFAULT NULL,
  `update_date` datetime NULL DEFAULT NULL,
  `company_id` bigint NULL DEFAULT NULL,
  `del_flag` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统-公司个性话配置' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_com_info_conf
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
